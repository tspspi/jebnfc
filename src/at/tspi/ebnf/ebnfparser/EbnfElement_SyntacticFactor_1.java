package at.tspi.ebnf.ebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementSequential;

public class EbnfElement_SyntacticFactor_1 extends ParserElementSequential {
	private static final ParserElement[] sChildren = new ParserElement[] { new EbnfElement_Integer(null), new EbnfElement_RepetitionSymbol(null), new EbnfElement_SyntacticPrimary(null) };
	public EbnfElement_SyntacticFactor_1(ParserElement parent) { super(parent, null, sChildren); }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_SyntacticFactor_1(parent); }
	public String toString() { return "EbnfElement_SyntacticFactor_1"; }
}