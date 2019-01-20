package at.tspi.ebnf.ebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementConstant;

public class EbnfElement_EndRepeatSymbol extends ParserElementConstant {
	public EbnfElement_EndRepeatSymbol(ParserElement parent) { super(parent, "}", "EndRepeatSymbol", false); }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_EndRepeatSymbol(parent); }
	public String toString() { return "}"; }
}
