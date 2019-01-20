package at.tspi.ebnf.genisoebnfparser;

import at.tspi.ebnf.parser.Parser;

public class EbnfElement_Parser extends Parser {
	public EbnfElement_Parser() { super(); }

	protected void initializeRoot() {
		rootElement = new EbnfElement_syntax(null);
		currentElement.push(rootElement);
	}
}
