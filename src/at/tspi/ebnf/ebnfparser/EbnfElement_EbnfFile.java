package at.tspi.ebnf.ebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementRepeatOptional;

public class EbnfElement_EbnfFile extends ParserElementRepeatOptional {
	private static final ParserElement sChild = new EbnfElement_SyntaxRule(null);
	public EbnfElement_EbnfFile(ParserElement parent) { super(parent, "EbnfFile", sChild); this.min = 0; this.max = 0; }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_EbnfFile(parent); }
	public String toString() { return "EbnfElement_EbnfFile"; }
}