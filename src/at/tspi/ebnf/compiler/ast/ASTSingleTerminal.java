package at.tspi.ebnf.compiler.ast;

public class ASTSingleTerminal extends ASTNode {
	String terminal;

	public ASTSingleTerminal() 							{ super(); this.terminal = null; }
	public ASTSingleTerminal(ASTSingleTerminal other)	{ super(); this.terminal = other.terminal; }
	public ASTSingleTerminal(String term) 				{ super(); this.terminal = term; }

	public ASTSingleTerminal setTerminal(String term) 	{ this.terminal = term; return this; }
	public String getTerminal() 						{ return this.terminal; }

	public String toString()							{ return this.getClass().getSimpleName() + ": " + this.terminal; }
}
