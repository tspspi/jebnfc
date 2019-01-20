package at.tspi.ebnf.ebnfparser;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementSequential;

public class EbnfElement_SyntaxRule extends ParserElementSequential {
	private static final ParserElement[] sChildren = new ParserElement[] { new EbnfElement_MetaIdentifier(null), new EbnfElement_DefiningSymbol(null), new EbnfElement_DefinitionsList(null), new EbnfElement_TerminatorSymbol(null) };
	public EbnfElement_SyntaxRule(ParserElement parent) { super(parent, "SyntaxRule", sChildren); }
	public ParserElement factory(ParserElement parent) { return new EbnfElement_SyntaxRule(parent); }
	public String toString() { return "SyntaxRule"; }
}