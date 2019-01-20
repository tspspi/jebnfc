package at.tspi.ebnf.ebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementConstant;

public class EbnfElement_EndGroupSymbol extends ParserElementConstant {
	public EbnfElement_EndGroupSymbol(ParserElement parent) { super(parent, ")", "EndGroupSymbol", false); }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_EndGroupSymbol(parent); }
	public String toString() { return ")"; }
}