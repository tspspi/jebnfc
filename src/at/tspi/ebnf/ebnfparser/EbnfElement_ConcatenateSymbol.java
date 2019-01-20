package at.tspi.ebnf.ebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementConstant;

public class EbnfElement_ConcatenateSymbol extends ParserElementConstant {
	public EbnfElement_ConcatenateSymbol(ParserElement parent) { super(parent, ",", "ConcatenateSymbol", false); }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_ConcatenateSymbol(parent); }
	public String toString() { return ","; }
}