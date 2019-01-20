package at.tspi.ebnf.ebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementConstant;

public class EbnfElement_DefinitionSeparatorSymbol extends ParserElementConstant {
	public EbnfElement_DefinitionSeparatorSymbol(ParserElement parent) { super(parent, "|", "DefinitionSeparatorSymbol", false); }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_DefinitionSeparatorSymbol(parent); }
	public String toString() { return "|"; }
}