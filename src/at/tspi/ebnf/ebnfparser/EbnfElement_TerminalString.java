package at.tspi.ebnf.ebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementChoice;

public class EbnfElement_TerminalString extends ParserElementChoice {
	private static final ParserElement[] sChildren = new ParserElement[] { new EbnfElement_TerminalStringFirstDelim(null), new EbnfElement_TerminalStringSecondDelim(null) };
	public EbnfElement_TerminalString(ParserElement parent) { super(parent, "TerminalString", sChildren); }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_TerminalString(parent); }
	public String toString() { return "TerminalString"; }
}
