package at.tspi.ebnf.genisoebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementConstant;
import at.tspi.ebnf.parser.Preprocessor;

public class EbnfElement_optionalsequence_39 extends ParserElementConstant {
	public EbnfElement_optionalsequence_39(ParserElement parent) { super(parent, "]", "optionalsequence_39", false); 		whitespaceMode = Preprocessor.VALUE_WHITESPACE__STRIP; }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_optionalsequence_39(parent); }
	public String toString() { return "]"; }
}
