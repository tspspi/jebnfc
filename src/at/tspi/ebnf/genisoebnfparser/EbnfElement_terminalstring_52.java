package at.tspi.ebnf.genisoebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementSequential;
import at.tspi.ebnf.parser.Preprocessor;

public class EbnfElement_terminalstring_52 extends ParserElementSequential {
	private static final ParserElement[] sChildren = new ParserElement[] { new EbnfElement_terminalstring_53(null), new EbnfElement_terminalstringsecond(null), new EbnfElement_terminalstring_55(null) };
	public EbnfElement_terminalstring_52(ParserElement parent) { super(parent, "terminalstring_52", sChildren); 		whitespaceMode = Preprocessor.VALUE_WHITESPACE__IGNORE; }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_terminalstring_52(parent); }
	public String toString() { return "terminalstring_52"; }
}
