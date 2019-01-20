package at.tspi.ebnf.parser;

import java.io.UnsupportedEncodingException;

public abstract class ParserDataSource {
	public abstract class Mark { }

	String				dsName;
	protected long		lineNumber;
	protected long		charIndex;

	public ParserDataSource(String dsName) {
		this.dsName = dsName;
		this.lineNumber = 0;
		this.charIndex = 0;
	}

	public String getDSName() 		{ return this.dsName; }

	public long getLineNumber() 	{ return this.lineNumber; }
	public long getCharIndex() 		{ return this.charIndex; }

	/* Functions implemented by children */
	public abstract ParserDataSource.Mark mark() throws java.io.IOException;
	public abstract void seek(ParserDataSource.Mark mark) throws ParserException, java.io.IOException;
	public abstract ParserGraphemeCluster getNextCluster() throws UnsupportedEncodingException, java.io.IOException;
}