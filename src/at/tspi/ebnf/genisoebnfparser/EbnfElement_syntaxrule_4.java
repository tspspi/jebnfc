package at.tspi.ebnf.genisoebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementConstant;
import at.tspi.ebnf.parser.Preprocessor;

public class EbnfElement_syntaxrule_4 extends ParserElementConstant {
	public EbnfElement_syntaxrule_4(ParserElement parent) { super(parent, "=", "syntaxrule_4", false); 		whitespaceMode = Preprocessor.VALUE_WHITESPACE__STRIP; }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_syntaxrule_4(parent); }
	public String toString() { return "="; }
}
