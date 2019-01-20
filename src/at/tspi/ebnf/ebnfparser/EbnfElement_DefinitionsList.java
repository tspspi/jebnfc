package at.tspi.ebnf.ebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementSequential;

public class EbnfElement_DefinitionsList extends ParserElementSequential {
	private static final ParserElement[] sChildren = new ParserElement[] { new EbnfElement_SingleDefinition(null), new EbnfElement_DefinitionsList_1(null)  };
	public EbnfElement_DefinitionsList(ParserElement parent) { super(parent, "DefinitionsList", sChildren); }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_DefinitionsList(parent); }
	public String toString() { return "DefinitionsList"; }
}
