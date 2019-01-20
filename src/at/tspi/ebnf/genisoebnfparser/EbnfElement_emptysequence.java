package at.tspi.ebnf.genisoebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementEmpty;

public class EbnfElement_emptysequence extends ParserElementEmpty {
	public EbnfElement_emptysequence (ParserElement parent) { super(parent, "emptysequence"); }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_emptysequence(parent); }
}
