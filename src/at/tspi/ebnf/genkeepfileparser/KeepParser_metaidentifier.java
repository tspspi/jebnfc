package at.tspi.ebnf.genkeepfileparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementSequential;
import at.tspi.ebnf.parser.Preprocessor;

public class KeepParser_metaidentifier extends ParserElementSequential {
	private static final ParserElement[] sChildren = new ParserElement[] { new KeepParser_metaidentifier_0(null), new KeepParser_metaidentifier_1(null) };
	public KeepParser_metaidentifier(ParserElement parent) { super(parent, "metaidentifier", sChildren); 		whitespaceMode = Preprocessor.VALUE_WHITESPACE__STRIP; }
	public ParserElement factory(ParserElement parent) { return new KeepParser_metaidentifier(parent); }
	public String toString() { return "metaidentifier"; }
}
