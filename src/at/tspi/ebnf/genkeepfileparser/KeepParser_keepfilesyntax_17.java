package at.tspi.ebnf.genkeepfileparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementRepeatOptional;
import at.tspi.ebnf.parser.Preprocessor;

public class KeepParser_keepfilesyntax_17 extends ParserElementRepeatOptional {
	private static final ParserElement sChild = new KeepParser_productionproperty(null);
	public KeepParser_keepfilesyntax_17(ParserElement parent) { super(parent, null, sChild); this.min = 0; this.max = 0; 		whitespaceMode = Preprocessor.VALUE_WHITESPACE__STRIP; }
	public ParserElement factory(ParserElement parent) { return new KeepParser_keepfilesyntax_17(parent); }
	public String toString() { return "keepfilesyntax_17"; }
}
