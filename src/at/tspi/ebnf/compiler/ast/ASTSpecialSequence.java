package at.tspi.ebnf.compiler.ast;

public class ASTSpecialSequence extends ASTNode {
	String terminal;

	public ASTSpecialSequence() 						{ super(); this.terminal = null; }
	public ASTSpecialSequence(String term) 				{ super(); this.terminal = term; }

	public ASTSpecialSequence setSquence(String term) 	{ this.terminal = term; return this; }
	public String getSequence() 						{ return this.terminal; }

	public String toString()							{ return this.getClass().getSimpleName() + ": " + this.terminal; }
}
