package at.tspi.ebnf.genkeepfileparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementSequential;
import at.tspi.ebnf.parser.Preprocessor;

public class KeepParser_productionproperty_12 extends ParserElementSequential {
	private static final ParserElement[] sChildren = new ParserElement[] { new KeepParser_productionproperty_13(null), new KeepParser_prodoption(null) };
	public KeepParser_productionproperty_12(ParserElement parent) { super(parent, "productionproperty_12", sChildren); 		whitespaceMode = Preprocessor.VALUE_WHITESPACE__STRIP; }
	public ParserElement factory(ParserElement parent) { return new KeepParser_productionproperty_12(parent); }
	public String toString() { return "productionproperty_12"; }
}
