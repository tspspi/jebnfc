package at.tspi.ebnf.ebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementChoice;

public class EbnfElement_SyntacticPrimary extends ParserElementChoice {
	private static final ParserElement[] sChildren = new ParserElement[] { new EbnfElement_OptionalSequence(null), new EbnfElement_RepeatedSequence(null), new EbnfElement_GroupedSequence(null), new EbnfElement_MetaIdentifier(null), new EbnfElement_TerminalString(null), new  EbnfElement_SpecialSequence(null) };
	public EbnfElement_SyntacticPrimary(ParserElement parent) { super(parent, "SyntacticPrimary", sChildren); }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_SyntacticPrimary(parent); }
	public String toString() { return "SyntacticPrimary"; }
}