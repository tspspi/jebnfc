package at.tspi.ebnf.genkeepfileparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementChoice;
import at.tspi.ebnf.parser.Preprocessor;

public class KeepParser_whitespacemodeidentifier extends ParserElementChoice {
	private static final ParserElement[] sChildren = new ParserElement[] { new KeepParser_whitespacemodereduce(null), new KeepParser_whitespacemodestrip(null), new KeepParser_whitespacemodeignore(null) };
	public KeepParser_whitespacemodeidentifier(ParserElement parent) { super(parent, "whitespacemodeidentifier", sChildren); 		whitespaceMode = Preprocessor.VALUE_WHITESPACE__STRIP; }
	public ParserElement factory(ParserElement parent) { return new KeepParser_whitespacemodeidentifier(parent); }
	public String toString() { return "whitespacemodeidentifier"; }
}
