package at.tspi.ebnf.genisoebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementRepeatOptional;
import at.tspi.ebnf.parser.Preprocessor;

public class EbnfElement_syntacticterm_21 extends ParserElementRepeatOptional {
	private static final ParserElement sChild = new EbnfElement_syntacticexception(null);
	public EbnfElement_syntacticterm_21(ParserElement parent) { super(parent, null, sChild); this.min = 0; this.max = 1; 		whitespaceMode = Preprocessor.VALUE_WHITESPACE__STRIP; }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_syntacticterm_21(parent); }
	public String toString() { return "syntacticterm_21"; }
}
