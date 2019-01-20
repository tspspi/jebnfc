package at.tspi.ebnf.ebnfparser;

import java.util.HashMap;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementTerminal;

public class EbnfElement_TerminalStringFirst extends ParserElementTerminal {
	public EbnfElement_TerminalStringFirst(ParserElement parent) {
		super(parent, null, 0, 0);
		
		forbiddenGraphemeClusters = new HashMap<String, Boolean>();
		forbiddenGraphemeClusters.put("´", true);
	}
	public ParserElement factory(ParserElement parent) { return new EbnfElement_TerminalStringFirst(parent); }
	public String toString() { return "TerminalStringFirst"; }
}
