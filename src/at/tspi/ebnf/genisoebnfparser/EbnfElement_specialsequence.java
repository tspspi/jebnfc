package at.tspi.ebnf.genisoebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementSequential;
import at.tspi.ebnf.parser.Preprocessor;

public class EbnfElement_specialsequence extends ParserElementSequential {
	private static final ParserElement[] sChildren = new ParserElement[] { new EbnfElement_specialsequence_60(null), new EbnfElement_specialsequencevalue(null), new EbnfElement_specialsequence_62(null) };
	public EbnfElement_specialsequence(ParserElement parent) { super(parent, "specialsequence", sChildren); 		whitespaceMode = Preprocessor.VALUE_WHITESPACE__STRIP; }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_specialsequence(parent); }
	public String toString() { return "specialsequence"; }
}
