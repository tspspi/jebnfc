package at.tspi.ebnf.ebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementConstant;

public class EbnfElement_StartRepeatSymbol extends ParserElementConstant {
	public EbnfElement_StartRepeatSymbol(ParserElement parent) { super(parent, "{", "StartRepeatSymbol", false); }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_StartRepeatSymbol(parent); }
	public String toString() { return "{"; }
}