package at.tspi.ebnf.ebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementConstant;

public class EbnfElement_StartOptionalSymbol extends ParserElementConstant {
	public EbnfElement_StartOptionalSymbol(ParserElement parent) { super(parent, "[", "StartOptionalSymbol", false); }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_StartOptionalSymbol(parent); }
	public String toString() { return "["; }
}
