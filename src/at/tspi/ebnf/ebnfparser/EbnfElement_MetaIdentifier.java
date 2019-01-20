package at.tspi.ebnf.ebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementSequential;

public class EbnfElement_MetaIdentifier extends ParserElementSequential {
	private static final ParserElement[] sChildren = new ParserElement[] { new EbnfElement_Letter(null), new EbnfElement_MetaIdentifierCharacters(null) };
	public EbnfElement_MetaIdentifier(ParserElement parent) { super(parent, "MetaIdentifier", sChildren); }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_MetaIdentifier(parent); }
	public String toString() { return "MetaIdentifier"; }
}