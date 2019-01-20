package at.tspi.ebnf.genkeepfileparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementConstant;
import at.tspi.ebnf.parser.Preprocessor;

public class KeepParser_whitespacemodeignore extends ParserElementConstant {
	public KeepParser_whitespacemodeignore(ParserElement parent) { super(parent, "ignore", "whitespacemodeignore", false); 		whitespaceMode = Preprocessor.VALUE_WHITESPACE__STRIP; }
	public ParserElement factory(ParserElement parent) { return new KeepParser_whitespacemodeignore(parent); }
	public String toString() { return "ignore"; }
}
