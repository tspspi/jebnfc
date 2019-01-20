package at.tspi.ebnf.genisoebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementRepeatOptional;
import at.tspi.ebnf.parser.Preprocessor;

public class EbnfElement_singledefinition_13 extends ParserElementRepeatOptional {
	private static final ParserElement sChild = new EbnfElement_singledefinition_14(null);
	public EbnfElement_singledefinition_13(ParserElement parent) { super(parent, null, sChild); this.min = 0; this.max = 0; 		whitespaceMode = Preprocessor.VALUE_WHITESPACE__STRIP; }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_singledefinition_13(parent); }
	public String toString() { return "singledefinition_13"; }
}
