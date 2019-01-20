package at.tspi.ebnf.genisoebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementConstant;
import at.tspi.ebnf.parser.Preprocessor;

public class EbnfElement_specialsequence_60 extends ParserElementConstant {
	public EbnfElement_specialsequence_60(ParserElement parent) { super(parent, "?", "specialsequence_60", false); 		whitespaceMode = Preprocessor.VALUE_WHITESPACE__STRIP; }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_specialsequence_60(parent); }
	public String toString() { return "?"; }
}
