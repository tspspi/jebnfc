package at.tspi.ebnf.genisoebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementSequential;
import at.tspi.ebnf.parser.Preprocessor;

public class EbnfElement_singledefinition_14 extends ParserElementSequential {
	private static final ParserElement[] sChildren = new ParserElement[] { new EbnfElement_singledefinition_15(null), new EbnfElement_syntacticterm(null) };
	public EbnfElement_singledefinition_14(ParserElement parent) { super(parent, "singledefinition_14", sChildren); 		whitespaceMode = Preprocessor.VALUE_WHITESPACE__STRIP; }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_singledefinition_14(parent); }
	public String toString() { return "singledefinition_14"; }
}
