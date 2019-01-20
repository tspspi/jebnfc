package at.tspi.ebnf.genkeepfileparser;

import java.util.HashMap;

import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementTerminal;
import at.tspi.ebnf.parser.Preprocessor;

public class KeepParser_metaidentifier_1 extends ParserElementTerminal {
	public KeepParser_metaidentifier_1 (ParserElement parent) {
		super(parent, "metaidentifier_1", 0, 0);
		allowedGraphemeClusters = new HashMap<String, Boolean>();
		allowedGraphemeClusters.put("a", true);
		allowedGraphemeClusters.put("b", true);
		allowedGraphemeClusters.put("c", true);
		allowedGraphemeClusters.put("d", true);
		allowedGraphemeClusters.put("e", true);
		allowedGraphemeClusters.put("f", true);
		allowedGraphemeClusters.put("g", true);
		allowedGraphemeClusters.put("h", true);
		allowedGraphemeClusters.put("i", true);
		allowedGraphemeClusters.put("j", true);
		allowedGraphemeClusters.put("k", true);
		allowedGraphemeClusters.put("l", true);
		allowedGraphemeClusters.put("m", true);
		allowedGraphemeClusters.put("n", true);
		allowedGraphemeClusters.put("o", true);
		allowedGraphemeClusters.put("p", true);
		allowedGraphemeClusters.put("q", true);
		allowedGraphemeClusters.put("r", true);
		allowedGraphemeClusters.put("s", true);
		allowedGraphemeClusters.put("t", true);
		allowedGraphemeClusters.put("u", true);
		allowedGraphemeClusters.put("v", true);
		allowedGraphemeClusters.put("w", true);
		allowedGraphemeClusters.put("x", true);
		allowedGraphemeClusters.put("y", true);
		allowedGraphemeClusters.put("z", true);
		allowedGraphemeClusters.put("A", true);
		allowedGraphemeClusters.put("B", true);
		allowedGraphemeClusters.put("C", true);
		allowedGraphemeClusters.put("D", true);
		allowedGraphemeClusters.put("E", true);
		allowedGraphemeClusters.put("F", true);
		allowedGraphemeClusters.put("G", true);
		allowedGraphemeClusters.put("H", true);
		allowedGraphemeClusters.put("I", true);
		allowedGraphemeClusters.put("J", true);
		allowedGraphemeClusters.put("K", true);
		allowedGraphemeClusters.put("L", true);
		allowedGraphemeClusters.put("M", true);
		allowedGraphemeClusters.put("N", true);
		allowedGraphemeClusters.put("O", true);
		allowedGraphemeClusters.put("P", true);
		allowedGraphemeClusters.put("Q", true);
		allowedGraphemeClusters.put("R", true);
		allowedGraphemeClusters.put("S", true);
		allowedGraphemeClusters.put("T", true);
		allowedGraphemeClusters.put("U", true);
		allowedGraphemeClusters.put("V", true);
		allowedGraphemeClusters.put("W", true);
		allowedGraphemeClusters.put("X", true);
		allowedGraphemeClusters.put("Y", true);
		allowedGraphemeClusters.put("Z", true);
		allowedGraphemeClusters.put("0", true);
		allowedGraphemeClusters.put("1", true);
		allowedGraphemeClusters.put("2", true);
		allowedGraphemeClusters.put("3", true);
		allowedGraphemeClusters.put("4", true);
		allowedGraphemeClusters.put("5", true);
		allowedGraphemeClusters.put("6", true);
		allowedGraphemeClusters.put("7", true);
		allowedGraphemeClusters.put("8", true);
		allowedGraphemeClusters.put("9", true);

		whitespaceMode = Preprocessor.VALUE_WHITESPACE__STRIP;
	}
	public ParserElement factory(ParserElement parent) { return new KeepParser_metaidentifier_1(parent); }
	public String toString() { return "metaidentifier_1"; }
}
