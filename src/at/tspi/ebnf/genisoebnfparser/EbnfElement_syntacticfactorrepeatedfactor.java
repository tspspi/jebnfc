package at.tspi.ebnf.genisoebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementSequential;
import at.tspi.ebnf.parser.Preprocessor;

public class EbnfElement_syntacticfactorrepeatedfactor extends ParserElementSequential {
	private static final ParserElement[] sChildren = new ParserElement[] { new EbnfElement_integer(null), new EbnfElement_syntacticfactorrepeatedfactor_26(null), new EbnfElement_syntacticprimary(null) };
	public EbnfElement_syntacticfactorrepeatedfactor(ParserElement parent) { super(parent, "syntacticfactorrepeatedfactor", sChildren); 		whitespaceMode = Preprocessor.VALUE_WHITESPACE__STRIP; }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_syntacticfactorrepeatedfactor(parent); }
	public String toString() { return "syntacticfactorrepeatedfactor"; }
}
