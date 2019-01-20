package at.tspi.ebnf.genkeepfileparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementSequential;
import at.tspi.ebnf.parser.Preprocessor;

public class KeepParser_productionproperty extends ParserElementSequential {
	private static final ParserElement[] sChildren = new ParserElement[] { new KeepParser_metaidentifier(null), new KeepParser_productionproperty_11(null), new KeepParser_productionproperty_15(null) };
	public KeepParser_productionproperty(ParserElement parent) { super(parent, "productionproperty", sChildren); 		whitespaceMode = Preprocessor.VALUE_WHITESPACE__STRIP; }
	public ParserElement factory(ParserElement parent) { return new KeepParser_productionproperty(parent); }
	public String toString() { return "productionproperty"; }
}
