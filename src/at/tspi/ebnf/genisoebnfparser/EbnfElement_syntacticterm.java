package at.tspi.ebnf.genisoebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementSequential;
import at.tspi.ebnf.parser.Preprocessor;

public class EbnfElement_syntacticterm extends ParserElementSequential {
	private static final ParserElement[] sChildren = new ParserElement[] { new EbnfElement_syntacticfactor(null), new EbnfElement_syntacticterm_18(null) };
	public EbnfElement_syntacticterm(ParserElement parent) { super(parent, "syntacticterm", sChildren); 		whitespaceMode = Preprocessor.VALUE_WHITESPACE__STRIP; }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_syntacticterm(parent); }
	public String toString() { return "syntacticterm"; }
}
