package at.tspi.ebnf.genisoebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementConstant;
import at.tspi.ebnf.parser.Preprocessor;

public class EbnfElement_groupedsequence_45 extends ParserElementConstant {
	public EbnfElement_groupedsequence_45(ParserElement parent) { super(parent, ")", "groupedsequence_45", false); 		whitespaceMode = Preprocessor.VALUE_WHITESPACE__STRIP; }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_groupedsequence_45(parent); }
	public String toString() { return ")"; }
}
