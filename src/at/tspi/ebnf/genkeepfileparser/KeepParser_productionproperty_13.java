package at.tspi.ebnf.genkeepfileparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementConstant;
import at.tspi.ebnf.parser.Preprocessor;

public class KeepParser_productionproperty_13 extends ParserElementConstant {
	public KeepParser_productionproperty_13(ParserElement parent) { super(parent, ",", "productionproperty_13", false); 		whitespaceMode = Preprocessor.VALUE_WHITESPACE__STRIP; }
	public ParserElement factory(ParserElement parent) { return new KeepParser_productionproperty_13(parent); }
	public String toString() { return ","; }
}
