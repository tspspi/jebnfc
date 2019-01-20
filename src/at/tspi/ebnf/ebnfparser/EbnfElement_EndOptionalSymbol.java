package at.tspi.ebnf.ebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementConstant;

public class EbnfElement_EndOptionalSymbol extends ParserElementConstant {
	public EbnfElement_EndOptionalSymbol(ParserElement parent) { super(parent, "]", "EndOptionalSymbol", false); }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_EndOptionalSymbol(parent); }
	public String toString() { return "]"; }
}
