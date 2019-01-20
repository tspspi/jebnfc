package at.tspi.ebnf.genkeepfileparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementChoice;
import at.tspi.ebnf.parser.Preprocessor;

public class KeepParser_prodoption extends ParserElementChoice {
	private static final ParserElement[] sChildren = new ParserElement[] { new KeepParser_starter(null), new KeepParser_keep(null), new KeepParser_whitespacemode(null) };
	public KeepParser_prodoption(ParserElement parent) { super(parent, "prodoption", sChildren); 		whitespaceMode = Preprocessor.VALUE_WHITESPACE__STRIP; }
	public ParserElement factory(ParserElement parent) { return new KeepParser_prodoption(parent); }
	public String toString() { return "prodoption"; }
}
