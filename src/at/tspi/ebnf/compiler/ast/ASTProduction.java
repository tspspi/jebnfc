package at.tspi.ebnf.compiler.ast;

public class ASTProduction extends ASTNode {
	String productionName = null;
	boolean starterCandidate = true;

	public ASTProduction() 						{ super(); }

	public ASTProduction setName(String name) 	{ this.productionName = name; return this; }
	public String getName() 					{ return this.productionName; }

	public String toString() 					{ return this.getClass().getSimpleName() + ": " + this.productionName; }

	public ASTProduction setStarterCandidate(boolean candidate) { this.starterCandidate = candidate; return this; }
	public boolean getStarterCandidate() { return this.starterCandidate; }
}
