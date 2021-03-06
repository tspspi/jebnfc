package at.tspi.ebnf.genisoebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementSequential;
import at.tspi.ebnf.parser.Preprocessor;

public class EbnfElement_definitionslist extends ParserElementSequential {
	private static final ParserElement[] sChildren = new ParserElement[] { new EbnfElement_singledefinition(null), new EbnfElement_definitionslist_8(null) };
	public EbnfElement_definitionslist(ParserElement parent) { super(parent, "definitionslist", sChildren); 		whitespaceMode = Preprocessor.VALUE_WHITESPACE__STRIP; }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_definitionslist(parent); }
	public String toString() { return "definitionslist"; }
}
