package at.tspi.ebnf.genisoebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementSequential;
import at.tspi.ebnf.parser.Preprocessor;

public class EbnfElement_optionalsequence extends ParserElementSequential {
	private static final ParserElement[] sChildren = new ParserElement[] { new EbnfElement_optionalsequence_37(null), new EbnfElement_definitionslist(null), new EbnfElement_optionalsequence_39(null) };
	public EbnfElement_optionalsequence(ParserElement parent) { super(parent, "optionalsequence", sChildren); 		whitespaceMode = Preprocessor.VALUE_WHITESPACE__STRIP; }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_optionalsequence(parent); }
	public String toString() { return "optionalsequence"; }
}
