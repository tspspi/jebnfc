package at.tspi.ebnf.ebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementConstant;

public class EbnfElement_QuoteSymbolSecond extends ParserElementConstant {
	public EbnfElement_QuoteSymbolSecond(ParserElement parent) { super(parent, "\"", "QuoteSymbolSecond", false); }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_QuoteSymbolSecond(parent); }
	public String toString() { return "QuoteSymbolSecond"; }
}