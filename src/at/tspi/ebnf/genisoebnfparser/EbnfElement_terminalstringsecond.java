package at.tspi.ebnf.genisoebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementSequential;
import at.tspi.ebnf.parser.Preprocessor;

public class EbnfElement_terminalstringsecond extends ParserElementSequential {
	private static final ParserElement[] sChildren = new ParserElement[] { new EbnfElement_terminalstringsecond_58(null), new EbnfElement_terminalstringsecond_59(null) };
	public EbnfElement_terminalstringsecond(ParserElement parent) { super(parent, "terminalstringsecond", sChildren); 		whitespaceMode = Preprocessor.VALUE_WHITESPACE__STRIP; }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_terminalstringsecond(parent); }
	public String toString() { return "terminalstringsecond"; }
}
