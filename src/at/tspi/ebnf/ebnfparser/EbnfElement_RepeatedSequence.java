package at.tspi.ebnf.ebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementSequential;

public class EbnfElement_RepeatedSequence extends ParserElementSequential {
	private static final ParserElement[] sChildren = new ParserElement[] { new EbnfElement_StartRepeatSymbol(null), new EbnfElement_DefinitionsList(null), new EbnfElement_EndRepeatSymbol(null) };
	public EbnfElement_RepeatedSequence(ParserElement parent) { super(parent, "RepeatedSequence", sChildren); }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_RepeatedSequence(parent); }
	public String toString() { return "RepeatedSequence"; }
}