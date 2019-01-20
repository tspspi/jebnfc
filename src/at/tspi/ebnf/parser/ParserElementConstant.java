package at.tspi.ebnf.parser;


abstract public class ParserElementConstant extends ParserElement {
	String constant;
	boolean caseInsensitive = false;
	int matched = 0;

	public ParserElementConstant(ParserElement parent, String constant) { super(parent); this.constant = constant; this.caseInsensitive = false; this.matched = 0; }
	public ParserElementConstant(ParserElement parent, String constant, String productionName) { super(parent, productionName); this.constant = constant; this.caseInsensitive = false; this.matched = 0; }
	public ParserElementConstant(ParserElement parent, String constant, boolean caseInsensitive) { super(parent); this.constant = constant; this.caseInsensitive = caseInsensitive; this.matched = 0; }
	public ParserElementConstant(ParserElement parent, String constant, String productionName, boolean caseInsensitive) { super(parent, productionName); this.constant = constant; this.caseInsensitive = caseInsensitive; this.matched = 0; }

	public int parseNext(
		ParserGraphemeCluster nextCluster,
		String dataSourceName,
		int lineNumber,
		int characterPosition,

		Parser parser,
		Preprocessor preproc,
		ParserLogger logger,
		
		boolean lastStepUp,
		int lastResult
	) throws ParserException {
		if(lastStepUp) {
			// This CANNOT happen
			throw new ParserException("Stepping UP into a terminal constant cannot happen. Implementation error.");
		}
		if(nextCluster == null) {
			// If we have'nt matched we fail (and a choice node will retry)
			if(this.matched != constant.length()) {
				parser.popCurrentElement();
				if(parentElement != null) { parentElement.removeChild(this); }
				try { if(!parser.currentElement.empty()) { preproc.setMode(Preprocessor.PROPERTY_WHITESPACE, parser.currentElement.peek().whitespaceMode); } } catch(ParserPreprocessorException e) { e.printStackTrace(); }
				return ParserElement.STATUS_FAILED;
			}
			// Else we accept the symbol sequence
			return ParserElement.STATUS_OK_REDELIVER;
		}

		/* Compare next substring with current grapheme cluster ... */
		// int clusterLength = nextCluster.cluster.length();
		// int constantLength = this.constant.length();

		try {
			boolean match = false;
			if(this.caseInsensitive) {
				match = constant.substring(this.matched, this.matched+nextCluster.cluster.length()).equalsIgnoreCase(nextCluster.cluster);
			} else {
				match = constant.substring(this.matched, this.matched+nextCluster.cluster.length()).equals(nextCluster.cluster);
			}
			if(!match) {
				// If we have'nt matched we fail (and a choice node will retry)
				parser.popCurrentElement();
				if(parentElement != null) { parentElement.removeChild(this); }
				try { if(!parser.currentElement.empty()) { preproc.setMode(Preprocessor.PROPERTY_WHITESPACE, parser.currentElement.peek().whitespaceMode); } } catch(ParserPreprocessorException e) { e.printStackTrace(); }
				return ParserElement.STATUS_FAILED;
			}
			this.matched = this.matched + nextCluster.cluster.length();
			if(this.matched == this.constant.length()) {
				parser.popCurrentElement(); // We are done and have accepted ...
				try { if(!parser.currentElement.empty()) { preproc.setMode(Preprocessor.PROPERTY_WHITESPACE, parser.currentElement.peek().whitespaceMode); } } catch(ParserPreprocessorException e) { e.printStackTrace(); }
				return ParserElement.STATUS_OK_CONSUME;
			} else {
				// We will consume the next elements later on ...
				return ParserElement.STATUS_OK_CONSUME;
			}
		} catch(java.lang.IndexOutOfBoundsException e) {
			// Input cluster longer than remaining bytes ...
			parser.popCurrentElement();
			if(parentElement != null) { parentElement.removeChild(this); }
			try { if(!parser.currentElement.empty()) { preproc.setMode(Preprocessor.PROPERTY_WHITESPACE, parser.currentElement.peek().whitespaceMode); } } catch(ParserPreprocessorException e2) { e.printStackTrace(); }
			return ParserElement.STATUS_FAILED;
		}
	}

	public String toString() { return super.toString() + " (" + this.constant + ")"; }
	public String toString(int depth) { return super.toString(depth) + " (" + this.constant + ")"; }

	public String innerText() { return this.constant; }
}
