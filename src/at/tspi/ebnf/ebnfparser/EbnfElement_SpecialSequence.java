package at.tspi.ebnf.ebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementSequential;

public class EbnfElement_SpecialSequence extends ParserElementSequential {
	private static final ParserElement[] sChildren;
	static { sChildren = new ParserElement[] { new EbnfElement_SpecialSequenceSymbol(null), new EbnfElement_SpecialSequenceSequence(null), new EbnfElement_SpecialSequenceSymbol(null) }; }
	public EbnfElement_SpecialSequence(ParserElement parent) { super(parent, "SpecialSequence", sChildren); }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_SpecialSequence(parent); }
	public String toString() { return "SpecialSequence"; }
}