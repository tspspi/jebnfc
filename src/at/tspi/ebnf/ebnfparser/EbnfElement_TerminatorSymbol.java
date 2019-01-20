package at.tspi.ebnf.ebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementConstant;

public class EbnfElement_TerminatorSymbol extends ParserElementConstant {
	public EbnfElement_TerminatorSymbol(ParserElement parent) { super(parent, ";", "TerminatorSymbol", false); }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_TerminatorSymbol(parent); }
	public String toString() { return ";"; }
}