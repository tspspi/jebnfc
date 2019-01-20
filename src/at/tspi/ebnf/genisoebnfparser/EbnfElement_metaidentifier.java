package at.tspi.ebnf.genisoebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementSequential;
import at.tspi.ebnf.parser.Preprocessor;

public class EbnfElement_metaidentifier extends ParserElementSequential {
	private static final ParserElement[] sChildren = new ParserElement[] { new EbnfElement_metaidentifier_46(null), new EbnfElement_metaidentifier_47(null) };
	public EbnfElement_metaidentifier(ParserElement parent) { super(parent, "metaidentifier", sChildren); 		whitespaceMode = Preprocessor.VALUE_WHITESPACE__STRIP; }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_metaidentifier(parent); }
	public String toString() { return "metaidentifier"; }
}
