package at.tspi.ebnf.genisoebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementConstant;
import at.tspi.ebnf.parser.Preprocessor;

public class EbnfElement_definitionslist_10 extends ParserElementConstant {
	public EbnfElement_definitionslist_10(ParserElement parent) { super(parent, "|", "definitionslist_10", false); 		whitespaceMode = Preprocessor.VALUE_WHITESPACE__STRIP; }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_definitionslist_10(parent); }
	public String toString() { return "|"; }
}
