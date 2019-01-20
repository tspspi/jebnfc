package at.tspi.ebnf.ebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementSequential;

public class EbnfElement_GroupedSequence extends ParserElementSequential {
	private static final ParserElement[] sChildren = new ParserElement[] { new EbnfElement_StartGroupSymbol(null), new EbnfElement_DefinitionsList(null), new EbnfElement_EndGroupSymbol(null) };
	public EbnfElement_GroupedSequence(ParserElement parent) { super(parent, "GroupedSequence", sChildren); }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_GroupedSequence(parent); }
	public String toString() { return "GroupedSequence"; }
}