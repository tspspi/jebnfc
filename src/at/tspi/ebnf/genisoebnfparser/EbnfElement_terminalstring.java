package at.tspi.ebnf.genisoebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementChoice;
import at.tspi.ebnf.parser.Preprocessor;

public class EbnfElement_terminalstring extends ParserElementChoice {
	private static final ParserElement[] sChildren = new ParserElement[] { new EbnfElement_terminalstring_48(null), new EbnfElement_terminalstring_52(null) };
	public EbnfElement_terminalstring(ParserElement parent) { super(parent, "terminalstring", sChildren); 		whitespaceMode = Preprocessor.VALUE_WHITESPACE__IGNORE; }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_terminalstring(parent); }
	public String toString() { return "terminalstring"; }
}
