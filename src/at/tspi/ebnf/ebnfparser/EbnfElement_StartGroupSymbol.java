package at.tspi.ebnf.ebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementConstant;

public class EbnfElement_StartGroupSymbol extends ParserElementConstant {
	public EbnfElement_StartGroupSymbol(ParserElement parent) { super(parent, "(", "StartGroupSymbol", false); }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_StartGroupSymbol(parent); }
	public String toString() { return "("; }
}