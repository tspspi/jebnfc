package at.tspi.ebnf.genisoebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementSequential;
import at.tspi.ebnf.parser.Preprocessor;

public class EbnfElement_syntaxrule extends ParserElementSequential {
	private static final ParserElement[] sChildren = new ParserElement[] { new EbnfElement_metaidentifier(null), new EbnfElement_syntaxrule_4(null), new EbnfElement_definitionslist(null), new EbnfElement_syntaxrule_6(null) };
	public EbnfElement_syntaxrule(ParserElement parent) { super(parent, "syntaxrule", sChildren); 		whitespaceMode = Preprocessor.VALUE_WHITESPACE__STRIP; }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_syntaxrule(parent); }
	public String toString() { return "syntaxrule"; }
}
