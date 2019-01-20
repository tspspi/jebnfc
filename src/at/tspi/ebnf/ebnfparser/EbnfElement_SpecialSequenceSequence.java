package at.tspi.ebnf.ebnfparser;

import java.util.HashMap;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementTerminal;

public class EbnfElement_SpecialSequenceSequence extends ParserElementTerminal {
	public EbnfElement_SpecialSequenceSequence(ParserElement parent) {
		super(parent, null, 0, 0);
		
		/* allowedGraphemeClusters = new HashMap<String, Boolean>();
		allowedGraphemeClusters.put("?", true); */
		forbiddenGraphemeClusters = new HashMap<String, Boolean>();
		forbiddenGraphemeClusters.put("?", true);
	}
		
	public ParserElement factory(ParserElement parent) { return new EbnfElement_SpecialSequenceSequence(parent); }
}