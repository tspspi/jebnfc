package at.tspi.ebnf.ebnfparser;

import at.tspi.ebnf.parser.Parser;

public class EbnfParser extends Parser{
	public EbnfParser() { super(); }

	protected void initializeRoot() {
		rootElement = new EbnfElement_EbnfFile(null);
		currentElement.push(rootElement);
	}
}