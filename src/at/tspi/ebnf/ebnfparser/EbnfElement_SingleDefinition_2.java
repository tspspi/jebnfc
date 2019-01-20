package at.tspi.ebnf.ebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementSequential;

public class EbnfElement_SingleDefinition_2 extends ParserElementSequential {
	private static final ParserElement[] sChildren = new ParserElement[] { new EbnfElement_ConcatenateSymbol(null), new EbnfElement_SyntacticTerm(null)  };
	public EbnfElement_SingleDefinition_2(ParserElement parent) { super(parent, null, sChildren); }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_SingleDefinition_2(parent); }
	public String toString() { return "EbnfElement_SingleDefinition_2"; }
}