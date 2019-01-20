package at.tspi.ebnf.ebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementRepeatOptional;

public class EbnfElement_DefinitionsList_1 extends ParserElementRepeatOptional {
	private static final ParserElement sChild = new EbnfElement_DefinitionsList_2(null);
	public EbnfElement_DefinitionsList_1(ParserElement parent) { super(parent, null, sChild); this.min = 0; this.max = 0; }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_DefinitionsList_1(parent); }
	public String toString() { return "EbnfElement_DefinitionsList_1"; }
}