package at.tspi.ebnf.ebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementChoice;

public class EbnfElement_SyntacticFactor extends ParserElementChoice {
	private static final ParserElement[] sChildren = new ParserElement[] { new EbnfElement_SyntacticFactor_1(null), new EbnfElement_SyntacticPrimary(null) };
	public EbnfElement_SyntacticFactor(ParserElement parent) { super(parent, "SyntacticFactor", sChildren); }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_SyntacticFactor(parent); }
	public String toString() { return "EbnfElement_SyntacticFactor"; }
}