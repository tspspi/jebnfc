package at.tspi.ebnf.genkeepfileparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementSequential;
import at.tspi.ebnf.parser.Preprocessor;

public class KeepParser_whitespacemode extends ParserElementSequential {
	private static final ParserElement[] sChildren = new ParserElement[] { new KeepParser_whitespacemode_2(null), new KeepParser_whitespacemodeidentifier(null) };
	public KeepParser_whitespacemode(ParserElement parent) { super(parent, "whitespacemode", sChildren); 		whitespaceMode = Preprocessor.VALUE_WHITESPACE__STRIP; }
	public ParserElement factory(ParserElement parent) { return new KeepParser_whitespacemode(parent); }
	public String toString() { return "whitespacemode"; }
}
