package at.tspi.ebnf.parser;

import java.io.UnsupportedEncodingException;

public class PreprocessorSimpleWhitespace extends ParserDataSource implements Preprocessor {
	private final int				COMMENT_MODE_NONE = 0;
	private final int				COMMENT_MODE_POTENTIALSTART = 1;
	private final int				COMMENT_MODE_COMMENT = 2;
	private final int				COMMENT_MODE_POTENTIALEND = 3;

	protected String[]				whitespace = { " ", "\t", "\n", "\t" };
	private ParserDataSource 		currentSource;
	private int						whitespaceMode;

	private int 					commentMode;
	private ParserGraphemeCluster	cachedGcCommentMode;

	private class PreprocessorSimpleWhitespaceMark extends ParserDataSource.Mark {
		public ParserDataSource.Mark	wrappedMark;
		public int						whitespaceMode;

		public PreprocessorSimpleWhitespaceMark(ParserDataSource.Mark wrappedMark, int whitespaceMode) {
			this.wrappedMark = wrappedMark;
			this.whitespaceMode = whitespaceMode;
		}
	}

	public PreprocessorSimpleWhitespace() { super(null); this.currentSource = null; whitespaceMode = Preprocessor.VALUE_WHITESPACE__STRIP; this.commentMode = COMMENT_MODE_NONE; cachedGcCommentMode = null; }
	public PreprocessorSimpleWhitespace(ParserDataSource src) { super(src.getDSName()); this.currentSource = src; whitespaceMode = Preprocessor.VALUE_WHITESPACE__STRIP; this.commentMode = COMMENT_MODE_NONE; cachedGcCommentMode = null; }

	public void setParserDataSource(ParserDataSource src) { this.currentSource = src; }
	public ParserDataSource getCurrentSource() { return this.currentSource; }

	public String getDSName() 		{ return this.currentSource.getDSName(); }

	/* ParserDataSource */
	public long getLineNumber() 	{ return this.currentSource.getLineNumber(); }
	public long getCharIndex() 		{ return this.currentSource.getCharIndex(); }

	/* Functions implemented by children */
	public ParserDataSource.Mark mark() throws java.io.IOException { return new PreprocessorSimpleWhitespaceMark(this.currentSource.mark(), this.whitespaceMode); }
	public void seek(ParserDataSource.Mark mark) throws ParserException, java.io.IOException {
		if(mark instanceof PreprocessorSimpleWhitespaceMark) {
			this.currentSource.seek(((PreprocessorSimpleWhitespaceMark)mark).wrappedMark);
			this.whitespaceMode = ((PreprocessorSimpleWhitespaceMark)mark).whitespaceMode;
		} else {
			throw new ParserException("Invalid mark type supplied");
		}
	}
	public ParserGraphemeCluster getNextCluster() throws UnsupportedEncodingException, java.io.IOException {
		ParserGraphemeCluster cluster = null;
		for(;;) {
			for(;;) {
				if((cachedGcCommentMode != null) && (commentMode == COMMENT_MODE_NONE)) {
					cluster = cachedGcCommentMode;
					cachedGcCommentMode = null;
					break;
				}

				cluster = this.currentSource.getNextCluster();

				if(((this.whitespaceMode == VALUE_WHITESPACE__STRIP) || (this.whitespaceMode == VALUE_WHITESPACE__REDUCE)) && (cluster != null)) {
					if(cluster.cluster.equals("(") && (commentMode == COMMENT_MODE_NONE)) {
						cachedGcCommentMode = cluster;
						commentMode = COMMENT_MODE_POTENTIALSTART;
						continue;
					} else if(commentMode == COMMENT_MODE_NONE){
						cachedGcCommentMode = null;
						break;
					} else if(cluster.cluster.equals("*") && (commentMode == COMMENT_MODE_POTENTIALSTART)) {
						cachedGcCommentMode = null;
						commentMode = COMMENT_MODE_COMMENT;
						continue;
					} else if(commentMode == COMMENT_MODE_POTENTIALSTART) {
						commentMode = COMMENT_MODE_NONE;
						ParserGraphemeCluster clTemp = cluster;
						cluster = cachedGcCommentMode;
						cachedGcCommentMode = clTemp;
						break;
					} else if((commentMode == COMMENT_MODE_COMMENT) && cluster.cluster.equals("*")) {
						commentMode = COMMENT_MODE_POTENTIALEND;
						cachedGcCommentMode = null;
						continue;
					} else if((commentMode == COMMENT_MODE_POTENTIALEND) && cluster.cluster.equals(")")) {
						commentMode = COMMENT_MODE_NONE;
						cachedGcCommentMode = null;
						continue;
					} else if(commentMode == COMMENT_MODE_COMMENT) {
						// Skip over grapheme clusters till we reach the end of the comment
						continue;
					} else {
						throw new java.io.IOException("Invalid comment stripper state machine state "+commentMode);
					}
				} else {
					break;
				}
			}

			if(cluster != null) {
				boolean isWhitespace = false;
				for(int wsIndex = 0; wsIndex < whitespace.length; wsIndex++) { if(cluster.cluster.equals(whitespace[wsIndex])) { isWhitespace = true; break; } }

				if(isWhitespace) {
					if(whitespaceMode == Preprocessor.VALUE_WHITESPACE__REDUCE) {
						throw new java.io.IOException("Reduce mode not implemented yet ...");
					} else if(whitespaceMode == Preprocessor.VALUE_WHITESPACE__STRIP) {
						continue;
					}
				}
			}
			break;
		}
			
		return cluster;
	}

	/* Preprocessor */
	public void setMode(int property, int value) throws ParserPreprocessorException {
		if(property == Preprocessor.PROPERTY_WHITESPACE) {
			switch(value) {
				case Preprocessor.VALUE_WHITESPACE__NOCHANGE: return;
				case Preprocessor.VALUE_WHITESPACE__IGNORE: this.whitespaceMode = Preprocessor.VALUE_WHITESPACE__IGNORE; return;
				case Preprocessor.VALUE_WHITESPACE__REDUCE: this.whitespaceMode = Preprocessor.VALUE_WHITESPACE__REDUCE; return;
				case Preprocessor.VALUE_WHITESPACE__STRIP: this.whitespaceMode = Preprocessor.VALUE_WHITESPACE__STRIP; return;
				default: throw new ParserPreprocessorException("Unknown setting for whitespace property");
			}
		}
		throw new ParserPreprocessorException("Unknown property");
	}
	public int getMode(int property) throws ParserPreprocessorException {
		if(property == Preprocessor.PROPERTY_WHITESPACE) {
			return this.whitespaceMode;
		}
		throw new ParserPreprocessorException("Unknown property");
	}
}