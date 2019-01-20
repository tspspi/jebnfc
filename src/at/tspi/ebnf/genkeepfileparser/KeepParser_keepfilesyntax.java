package at.tspi.ebnf.genkeepfileparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementSequential;
import at.tspi.ebnf.parser.Preprocessor;

public class KeepParser_keepfilesyntax extends ParserElementSequential {
	private static final ParserElement[] sChildren = new ParserElement[] { new KeepParser_productionproperty(null), new KeepParser_keepfilesyntax_17(null) };
	public KeepParser_keepfilesyntax(ParserElement parent) { super(parent, "keepfilesyntax", sChildren); 		whitespaceMode = Preprocessor.VALUE_WHITESPACE__STRIP; }
	public ParserElement factory(ParserElement parent) { return new KeepParser_keepfilesyntax(parent); }
	public String toString() { return "keepfilesyntax"; }
}
