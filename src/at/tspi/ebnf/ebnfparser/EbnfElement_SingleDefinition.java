package at.tspi.ebnf.ebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementSequential;

public class EbnfElement_SingleDefinition extends ParserElementSequential {
	private static final ParserElement[] sChildren = new ParserElement[] { new EbnfElement_SyntacticTerm(null), new EbnfElement_SingleDefinition_1(null)  };
	public EbnfElement_SingleDefinition(ParserElement parent) { super(parent, "SingleDefinition", sChildren); }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_SingleDefinition(parent); }
	public String toString() { return "SingleDefinition"; }
}

