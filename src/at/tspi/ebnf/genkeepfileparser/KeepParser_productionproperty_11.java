package at.tspi.ebnf.genkeepfileparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementRepeatOptional;
import at.tspi.ebnf.parser.Preprocessor;

public class KeepParser_productionproperty_11 extends ParserElementRepeatOptional {
	private static final ParserElement sChild = new KeepParser_productionproperty_12(null);
	public KeepParser_productionproperty_11(ParserElement parent) { super(parent, null, sChild); this.min = 0; this.max = 0; 		whitespaceMode = Preprocessor.VALUE_WHITESPACE__STRIP; }
	public ParserElement factory(ParserElement parent) { return new KeepParser_productionproperty_11(parent); }
	public String toString() { return "productionproperty_11"; }
}
