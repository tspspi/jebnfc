package at.tspi.ebnf.genisoebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementSequential;
import at.tspi.ebnf.parser.Preprocessor;

public class EbnfElement_groupedsequence extends ParserElementSequential {
	private static final ParserElement[] sChildren = new ParserElement[] { new EbnfElement_groupedsequence_43(null), new EbnfElement_definitionslist(null), new EbnfElement_groupedsequence_45(null) };
	public EbnfElement_groupedsequence(ParserElement parent) { super(parent, "groupedsequence", sChildren); 		whitespaceMode = Preprocessor.VALUE_WHITESPACE__STRIP; }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_groupedsequence(parent); }
	public String toString() { return "groupedsequence"; }
}
