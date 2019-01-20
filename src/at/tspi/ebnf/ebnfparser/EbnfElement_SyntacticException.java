package at.tspi.ebnf.ebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementSequential;

public class EbnfElement_SyntacticException extends ParserElementSequential {
	private static final ParserElement[] sChildren = new ParserElement[] { new EbnfElement_SyntacticFactor(null) };
	public EbnfElement_SyntacticException(ParserElement parent) { super(parent, "SyntacticException", sChildren); }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_SyntacticException(parent); }
	public String toString() { return "EbnfElement_SyntacticException"; }
}