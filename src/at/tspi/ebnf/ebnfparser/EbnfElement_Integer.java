package at.tspi.ebnf.ebnfparser;

import java.util.HashMap;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementTerminal;

public class EbnfElement_Integer extends ParserElementTerminal {
	public EbnfElement_Integer(ParserElement parent) {
		super(parent, "Integer", 1, 0);

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
	}
	public ParserElement factory(ParserElement parent) { return new EbnfElement_Integer(parent); }
	public String toString() { return "Integer"; }
}