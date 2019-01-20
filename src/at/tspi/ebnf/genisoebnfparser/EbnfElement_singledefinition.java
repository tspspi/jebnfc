package at.tspi.ebnf.genisoebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementSequential;
import at.tspi.ebnf.parser.Preprocessor;

public class EbnfElement_singledefinition extends ParserElementSequential {
	private static final ParserElement[] sChildren = new ParserElement[] { new EbnfElement_syntacticterm(null), new EbnfElement_singledefinition_13(null) };
	public EbnfElement_singledefinition(ParserElement parent) { super(parent, "singledefinition", sChildren); 		whitespaceMode = Preprocessor.VALUE_WHITESPACE__STRIP; }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_singledefinition(parent); }
	public String toString() { return "singledefinition"; }
}
