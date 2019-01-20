package at.tspi.ebnf.ebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementConstant;

public class EbnfElement_RepetitionSymbol extends ParserElementConstant {
	public EbnfElement_RepetitionSymbol(ParserElement parent) { super(parent, "*", "RepetitionSymbol", false); }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_RepetitionSymbol(parent); }
	public String toString() { return "*"; }
}
