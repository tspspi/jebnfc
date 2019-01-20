package at.tspi.ebnf.ebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementConstant;

public class EbnfElement_SpecialSequenceSymbol extends ParserElementConstant {
	public EbnfElement_SpecialSequenceSymbol(ParserElement parent) { super(parent, "?", "SpecialSequenceSymbol", false); }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_SpecialSequenceSymbol(parent); }
	public String toString() { return "?"; }
}