package at.tspi.ebnf.genisoebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementSequential;
import at.tspi.ebnf.parser.Preprocessor;

public class EbnfElement_terminalstring_48 extends ParserElementSequential {
	private static final ParserElement[] sChildren = new ParserElement[] { new EbnfElement_terminalstring_49(null), new EbnfElement_terminalstringfirst(null), new EbnfElement_terminalstring_51(null) };
	public EbnfElement_terminalstring_48(ParserElement parent) { super(parent, "terminalstring_48", sChildren); 		whitespaceMode = Preprocessor.VALUE_WHITESPACE__IGNORE; }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_terminalstring_48(parent); }
	public String toString() { return "terminalstring_48"; }
}
