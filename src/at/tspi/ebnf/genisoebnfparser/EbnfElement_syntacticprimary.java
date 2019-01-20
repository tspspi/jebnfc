package at.tspi.ebnf.genisoebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementChoice;
import at.tspi.ebnf.parser.Preprocessor;

public class EbnfElement_syntacticprimary extends ParserElementChoice {
	private static final ParserElement[] sChildren = new ParserElement[] { new EbnfElement_optionalsequence(null), new EbnfElement_repeatedsequence(null), new EbnfElement_groupedsequence(null), new EbnfElement_metaidentifier(null), new EbnfElement_terminalstring(null), new EbnfElement_specialsequence(null), new EbnfElement_emptysequence(null) };
	public EbnfElement_syntacticprimary(ParserElement parent) { super(parent, "syntacticprimary", sChildren); 		whitespaceMode = Preprocessor.VALUE_WHITESPACE__STRIP; }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_syntacticprimary(parent); }
	public String toString() { return "syntacticprimary"; }
}
