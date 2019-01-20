package at.tspi.ebnf.genisoebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementChoice;
import at.tspi.ebnf.parser.Preprocessor;

public class EbnfElement_syntacticfactor extends ParserElementChoice {
	private static final ParserElement[] sChildren = new ParserElement[] { new EbnfElement_syntacticfactorrepeatedfactor(null), new EbnfElement_syntacticprimary(null) };
	public EbnfElement_syntacticfactor(ParserElement parent) { super(parent, "syntacticfactor", sChildren); 		whitespaceMode = Preprocessor.VALUE_WHITESPACE__STRIP; }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_syntacticfactor(parent); }
	public String toString() { return "syntacticfactor"; }
}
