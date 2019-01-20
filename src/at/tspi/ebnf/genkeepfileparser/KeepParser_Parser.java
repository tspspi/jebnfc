package at.tspi.ebnf.genkeepfileparser;

import at.tspi.ebnf.parser.Parser;

public class KeepParser_Parser extends Parser {
	public KeepParser_Parser() { super(); }

	protected void initializeRoot() {
		rootElement = new KeepParser_keepfilesyntax(null);
		currentElement.push(rootElement);
	}
}
