package at.tspi.ebnf.ebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementSequential;

public class EbnfElement_SyntacticTerm_1 extends ParserElementSequential {
	private static final ParserElement[] sChildren = new ParserElement[] { new EbnfElement_SyntacticFactor(null), new EbnfElement_ExceptSymbol(null), new EbnfElement_SyntacticException(null)  };
	public EbnfElement_SyntacticTerm_1(ParserElement parent) { super(parent, null, sChildren); }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_SyntacticTerm_1(parent); }
	public String toString() { return "SyntacticTerm_1"; }
}