package at.tspi.ebnf.genisoebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementSequential;
import at.tspi.ebnf.parser.Preprocessor;

public class EbnfElement_repeatedsequence extends ParserElementSequential {
	private static final ParserElement[] sChildren = new ParserElement[] { new EbnfElement_repeatedsequence_40(null), new EbnfElement_definitionslist(null), new EbnfElement_repeatedsequence_42(null) };
	public EbnfElement_repeatedsequence(ParserElement parent) { super(parent, "repeatedsequence", sChildren); 		whitespaceMode = Preprocessor.VALUE_WHITESPACE__STRIP; }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_repeatedsequence(parent); }
	public String toString() { return "repeatedsequence"; }
}
