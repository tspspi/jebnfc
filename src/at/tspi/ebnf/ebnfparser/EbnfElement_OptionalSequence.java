package at.tspi.ebnf.ebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementSequential;

public class EbnfElement_OptionalSequence extends ParserElementSequential {
	private static final ParserElement[] sChildren = new ParserElement[] { new EbnfElement_StartOptionalSymbol(null), new EbnfElement_DefinitionsList(null), new EbnfElement_EndOptionalSymbol(null) };
	public EbnfElement_OptionalSequence(ParserElement parent) { super(parent, "OptionalSequence", sChildren); }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_OptionalSequence(parent); }
	public String toString() { return "OptionalSequence"; }
}