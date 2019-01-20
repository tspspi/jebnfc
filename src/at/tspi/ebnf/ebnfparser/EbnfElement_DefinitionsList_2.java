package at.tspi.ebnf.ebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementSequential;

public class EbnfElement_DefinitionsList_2 extends ParserElementSequential {
	private static final ParserElement[] sChildren = new ParserElement[] { new EbnfElement_DefinitionSeparatorSymbol(null), new EbnfElement_SingleDefinition(null)  };
	public EbnfElement_DefinitionsList_2(ParserElement parent) { super(parent, null, sChildren); }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_DefinitionsList_2(parent); }
	public String toString() { return "EbnfElement_DefinitionsList_2"; }
}