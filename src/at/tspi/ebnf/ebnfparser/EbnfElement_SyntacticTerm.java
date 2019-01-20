package at.tspi.ebnf.ebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementChoice;

public class EbnfElement_SyntacticTerm extends ParserElementChoice {
	private static final ParserElement[] sChildren = new ParserElement[] { new EbnfElement_SyntacticTerm_1(null), new EbnfElement_SyntacticFactor(null) };
	public EbnfElement_SyntacticTerm(ParserElement parent) { super(parent, "SyntacticTerm", sChildren); }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_SyntacticTerm(parent); }
	public String toString() { return "SyntacticTerm"; }
}