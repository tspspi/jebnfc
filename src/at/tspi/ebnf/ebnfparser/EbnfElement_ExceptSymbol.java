package at.tspi.ebnf.ebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementConstant;

public class EbnfElement_ExceptSymbol extends ParserElementConstant {
	public EbnfElement_ExceptSymbol(ParserElement parent) { super(parent, "-", "ExceptSymbol", false); }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_ExceptSymbol(parent); }
	public String toString() { return "-"; }
}
