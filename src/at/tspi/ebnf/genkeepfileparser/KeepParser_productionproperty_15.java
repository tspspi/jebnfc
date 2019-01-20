package at.tspi.ebnf.genkeepfileparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementConstant;
import at.tspi.ebnf.parser.Preprocessor;

public class KeepParser_productionproperty_15 extends ParserElementConstant {
	public KeepParser_productionproperty_15(ParserElement parent) { super(parent, ";", "productionproperty_15", false); 		whitespaceMode = Preprocessor.VALUE_WHITESPACE__STRIP; }
	public ParserElement factory(ParserElement parent) { return new KeepParser_productionproperty_15(parent); }
	public String toString() { return ";"; }
}
