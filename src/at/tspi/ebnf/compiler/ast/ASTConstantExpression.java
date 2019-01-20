package at.tspi.ebnf.compiler.ast;

public class ASTConstantExpression extends ASTNode {
	String terminal;

	public ASTConstantExpression() 							{ super(); this.terminal = null; }
	public ASTConstantExpression(String term) 				{ super(); this.terminal = term; }

	public ASTConstantExpression setTerminal(String term) 	{ this.terminal = term; return this; }
	public String getTerminal() 							{ return this.terminal; }

	public String toString()								{ return this.getClass().getSimpleName() + ": " + this.terminal; }
}
