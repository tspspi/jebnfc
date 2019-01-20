package at.tspi.ebnf.ebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementSequential;

public class EbnfElement_TerminalStringSecondDelim extends ParserElementSequential {
	private static final ParserElement[] sChildren = new ParserElement[] { new EbnfElement_QuoteSymbolSecond(null), new EbnfElement_TerminalStringSecond(null), new EbnfElement_QuoteSymbolSecond(null) };
	public EbnfElement_TerminalStringSecondDelim(ParserElement parent) { super(parent, null, sChildren); }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_TerminalStringSecondDelim(parent); }
	public String toString() { return "TerminalStringSecondDelim"; }
}