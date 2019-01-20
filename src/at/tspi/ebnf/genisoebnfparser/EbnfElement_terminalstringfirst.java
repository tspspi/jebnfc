package at.tspi.ebnf.genisoebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementSequential;
import at.tspi.ebnf.parser.Preprocessor;

public class EbnfElement_terminalstringfirst extends ParserElementSequential {
	private static final ParserElement[] sChildren = new ParserElement[] { new EbnfElement_terminalstringfirst_56(null), new EbnfElement_terminalstringfirst_57(null) };
	public EbnfElement_terminalstringfirst(ParserElement parent) { super(parent, "terminalstringfirst", sChildren); 		whitespaceMode = Preprocessor.VALUE_WHITESPACE__STRIP; }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_terminalstringfirst(parent); }
	public String toString() { return "terminalstringfirst"; }
}
