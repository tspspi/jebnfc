package at.tspi.ebnf.genisoebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementConstant;
import at.tspi.ebnf.parser.Preprocessor;

public class EbnfElement_terminalstring_51 extends ParserElementConstant {
	public EbnfElement_terminalstring_51(ParserElement parent) { super(parent, "´", "terminalstring_51", false); 		whitespaceMode = Preprocessor.VALUE_WHITESPACE__IGNORE; }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_terminalstring_51(parent); }
	public String toString() { return "´"; }
}
