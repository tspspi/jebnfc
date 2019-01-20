package at.tspi.ebnf.genkeepfileparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementConstant;
import at.tspi.ebnf.parser.Preprocessor;

public class KeepParser_whitespacemodereduce extends ParserElementConstant {
	public KeepParser_whitespacemodereduce(ParserElement parent) { super(parent, "reduce", "whitespacemodereduce", false); 		whitespaceMode = Preprocessor.VALUE_WHITESPACE__STRIP; }
	public ParserElement factory(ParserElement parent) { return new KeepParser_whitespacemodereduce(parent); }
	public String toString() { return "reduce"; }
}
