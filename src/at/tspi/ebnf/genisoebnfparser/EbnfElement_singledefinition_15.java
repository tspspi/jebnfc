package at.tspi.ebnf.genisoebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementConstant;
import at.tspi.ebnf.parser.Preprocessor;

public class EbnfElement_singledefinition_15 extends ParserElementConstant {
	public EbnfElement_singledefinition_15(ParserElement parent) { super(parent, ",", "singledefinition_15", false); 		whitespaceMode = Preprocessor.VALUE_WHITESPACE__STRIP; }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_singledefinition_15(parent); }
	public String toString() { return ","; }
}
