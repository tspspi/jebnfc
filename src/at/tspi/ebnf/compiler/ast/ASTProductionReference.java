package at.tspi.ebnf.compiler.ast;

public class ASTProductionReference extends ASTNode {
	String productionName = null;

	public ASTProductionReference() 					{ super(); }
	public ASTProductionReference(String name) 			{ super(); this.productionName = name; }

	public ASTProductionReference setName(String name) 	{ this.productionName = name; return this; }
	public String getName() 							{ return this.productionName; }

	public String toString() 							{ return this.getClass().getSimpleName() + ": " + this.productionName; }
}
