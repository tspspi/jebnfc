package at.tspi.ebnf.genisoebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementRepeatOptional;
import at.tspi.ebnf.parser.Preprocessor;

public class EbnfElement_definitionslist_8 extends ParserElementRepeatOptional {
	private static final ParserElement sChild = new EbnfElement_definitionslist_9(null);
	public EbnfElement_definitionslist_8(ParserElement parent) { super(parent, null, sChild); this.min = 0; this.max = 0; 		whitespaceMode = Preprocessor.VALUE_WHITESPACE__STRIP; }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_definitionslist_8(parent); }
	public String toString() { return "definitionslist_8"; }
}
