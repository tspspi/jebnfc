package at.tspi.ebnf.genisoebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementSequential;
import at.tspi.ebnf.parser.Preprocessor;

public class EbnfElement_syntacticterm_19 extends ParserElementSequential {
	private static final ParserElement[] sChildren = new ParserElement[] { new EbnfElement_syntacticterm_20(null), new EbnfElement_syntacticterm_21(null) };
	public EbnfElement_syntacticterm_19(ParserElement parent) { super(parent, "syntacticterm_19", sChildren); 		whitespaceMode = Preprocessor.VALUE_WHITESPACE__STRIP; }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_syntacticterm_19(parent); }
	public String toString() { return "syntacticterm_19"; }
}
