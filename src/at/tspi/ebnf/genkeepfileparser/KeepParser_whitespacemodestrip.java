package at.tspi.ebnf.genkeepfileparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementConstant;
import at.tspi.ebnf.parser.Preprocessor;

public class KeepParser_whitespacemodestrip extends ParserElementConstant {
	public KeepParser_whitespacemodestrip(ParserElement parent) { super(parent, "strip", "whitespacemodestrip", false); 		whitespaceMode = Preprocessor.VALUE_WHITESPACE__STRIP; }
	public ParserElement factory(ParserElement parent) { return new KeepParser_whitespacemodestrip(parent); }
	public String toString() { return "strip"; }
}
