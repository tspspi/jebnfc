package at.tspi.ebnf.genkeepfileparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementConstant;
import at.tspi.ebnf.parser.Preprocessor;

public class KeepParser_whitespacemode_2 extends ParserElementConstant {
	public KeepParser_whitespacemode_2(ParserElement parent) { super(parent, "whitespace", "whitespacemode_2", false); 		whitespaceMode = Preprocessor.VALUE_WHITESPACE__STRIP; }
	public ParserElement factory(ParserElement parent) { return new KeepParser_whitespacemode_2(parent); }
	public String toString() { return "whitespace"; }
}
