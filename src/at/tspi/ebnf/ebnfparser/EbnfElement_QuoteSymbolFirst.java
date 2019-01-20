package at.tspi.ebnf.ebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementConstant;

public class EbnfElement_QuoteSymbolFirst extends ParserElementConstant {
	public EbnfElement_QuoteSymbolFirst(ParserElement parent) { super(parent, "´", "QuoteSymbolFirst", false); }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_QuoteSymbolFirst(parent); }
	public String toString() { return "QuoteSymbolFirst"; }
}