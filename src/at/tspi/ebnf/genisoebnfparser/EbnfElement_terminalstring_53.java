package at.tspi.ebnf.genisoebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementConstant;
import at.tspi.ebnf.parser.Preprocessor;

public class EbnfElement_terminalstring_53 extends ParserElementConstant {
	public EbnfElement_terminalstring_53(ParserElement parent) { super(parent, "\"", "terminalstring_53", false); 		whitespaceMode = Preprocessor.VALUE_WHITESPACE__IGNORE; }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_terminalstring_53(parent); }
	public String toString() { return "\""; }
}
