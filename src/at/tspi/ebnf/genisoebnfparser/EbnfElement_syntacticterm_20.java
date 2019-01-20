package at.tspi.ebnf.genisoebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementConstant;
import at.tspi.ebnf.parser.Preprocessor;

public class EbnfElement_syntacticterm_20 extends ParserElementConstant {
	public EbnfElement_syntacticterm_20(ParserElement parent) { super(parent, "-", "syntacticterm_20", false); 		whitespaceMode = Preprocessor.VALUE_WHITESPACE__STRIP; }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_syntacticterm_20(parent); }
	public String toString() { return "-"; }
}
