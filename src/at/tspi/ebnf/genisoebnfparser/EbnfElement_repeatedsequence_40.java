package at.tspi.ebnf.genisoebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementConstant;
import at.tspi.ebnf.parser.Preprocessor;

public class EbnfElement_repeatedsequence_40 extends ParserElementConstant {
	public EbnfElement_repeatedsequence_40(ParserElement parent) { super(parent, "{", "repeatedsequence_40", false); 		whitespaceMode = Preprocessor.VALUE_WHITESPACE__STRIP; }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_repeatedsequence_40(parent); }
	public String toString() { return "{"; }
}
