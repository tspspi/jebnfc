package at.tspi.ebnf.ebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementConstant;

public class EbnfElement_DefiningSymbol extends ParserElementConstant {
	public EbnfElement_DefiningSymbol(ParserElement parent) { super(parent, "=", "DefiningSymbol", false); }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_DefiningSymbol(parent); }
	public String toString() { return "="; }
}