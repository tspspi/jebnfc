package at.tspi.ebnf.genkeepfileparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementConstant;
import at.tspi.ebnf.parser.Preprocessor;

public class KeepParser_keep extends ParserElementConstant {
	public KeepParser_keep(ParserElement parent) { super(parent, "keep", "keep", false); 		whitespaceMode = Preprocessor.VALUE_WHITESPACE__STRIP; }
	public ParserElement factory(ParserElement parent) { return new KeepParser_keep(parent); }
	public String toString() { return "keep"; }
}
