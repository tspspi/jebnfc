package at.tspi.ebnf.genisoebnfparser;

import java.util.HashMap;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementTerminal;
import at.tspi.ebnf.parser.Preprocessor;

public class EbnfElement_syntaxrule_6 extends ParserElementTerminal {
	public EbnfElement_syntaxrule_6 (ParserElement parent) {
		super(parent, "syntaxrule_6", 1, 1);
		allowedGraphemeClusters = new HashMap<String, Boolean>();
		allowedGraphemeClusters.put(";", true);
		allowedGraphemeClusters.put(".", true);

		whitespaceMode = Preprocessor.VALUE_WHITESPACE__STRIP;
	}
	public ParserElement factory(ParserElement parent) { return new EbnfElement_syntaxrule_6(parent); }
	public String toString() { return "syntaxrule_6"; }
}
