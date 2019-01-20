package at.tspi.ebnf.genisoebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementConstant;
import at.tspi.ebnf.parser.Preprocessor;

public class EbnfElement_syntacticfactorrepeatedfactor_26 extends ParserElementConstant {
	public EbnfElement_syntacticfactorrepeatedfactor_26(ParserElement parent) { super(parent, "*", "syntacticfactorrepeatedfactor_26", false); 		whitespaceMode = Preprocessor.VALUE_WHITESPACE__STRIP; }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_syntacticfactorrepeatedfactor_26(parent); }
	public String toString() { return "*"; }
}
