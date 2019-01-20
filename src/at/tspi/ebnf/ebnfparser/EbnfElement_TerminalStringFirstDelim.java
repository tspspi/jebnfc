package at.tspi.ebnf.ebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementSequential;

public class EbnfElement_TerminalStringFirstDelim extends ParserElementSequential {
	private static final ParserElement[] sChildren = new ParserElement[] { new EbnfElement_QuoteSymbolFirst(null), new EbnfElement_TerminalStringFirst(null), new EbnfElement_QuoteSymbolFirst(null) };
	public EbnfElement_TerminalStringFirstDelim(ParserElement parent) { super(parent, null, sChildren); }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_TerminalStringFirstDelim(parent); }
	public String toString() { return "TerminalStringFirstDelim"; }
}
