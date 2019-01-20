package at.tspi.ebnf.genisoebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementSequential;
import at.tspi.ebnf.parser.Preprocessor;

public class EbnfElement_definitionslist_9 extends ParserElementSequential {
	private static final ParserElement[] sChildren = new ParserElement[] { new EbnfElement_definitionslist_10(null), new EbnfElement_singledefinition(null) };
	public EbnfElement_definitionslist_9(ParserElement parent) { super(parent, "definitionslist_9", sChildren); 		whitespaceMode = Preprocessor.VALUE_WHITESPACE__STRIP; }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_definitionslist_9(parent); }
	public String toString() { return "definitionslist_9"; }
}
