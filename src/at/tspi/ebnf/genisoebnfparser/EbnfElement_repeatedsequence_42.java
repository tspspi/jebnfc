package at.tspi.ebnf.genisoebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementConstant;
import at.tspi.ebnf.parser.Preprocessor;

public class EbnfElement_repeatedsequence_42 extends ParserElementConstant {
	public EbnfElement_repeatedsequence_42(ParserElement parent) { super(parent, "}", "repeatedsequence_42", false); 		whitespaceMode = Preprocessor.VALUE_WHITESPACE__STRIP; }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_repeatedsequence_42(parent); }
	public String toString() { return "}"; }
}
