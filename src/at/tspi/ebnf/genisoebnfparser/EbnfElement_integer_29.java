package at.tspi.ebnf.genisoebnfparser;

import java.util.HashMap;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementTerminal;
import at.tspi.ebnf.parser.Preprocessor;

public class EbnfElement_integer_29 extends ParserElementTerminal {
	public EbnfElement_integer_29 (ParserElement parent) {
		super(parent, "integer_29", 0, 0);
		allowedGraphemeClusters = new HashMap<String, Boolean>();
		allowedGraphemeClusters.put("0", true);
		allowedGraphemeClusters.put("1", true);
		allowedGraphemeClusters.put("2", true);
		allowedGraphemeClusters.put("3", true);
		allowedGraphemeClusters.put("4", true);
		allowedGraphemeClusters.put("5", true);
		allowedGraphemeClusters.put("6", true);
		allowedGraphemeClusters.put("7", true);
		allowedGraphemeClusters.put("8", true);
		allowedGraphemeClusters.put("9", true);

		whitespaceMode = Preprocessor.VALUE_WHITESPACE__STRIP;
	}
	public ParserElement factory(ParserElement parent) { return new EbnfElement_integer_29(parent); }
	public String toString() { return "integer_29"; }
}
