package at.tspi.ebnf.compiler.operations;

/*
	This operation traverses the AST and registeres all defined productions
	inside the Compiler State Block
*/

import at.tspi.ebnf.compiler.ast.ASTNode;
import at.tspi.ebnf.compiler.ast.ASTProduction;

import at.tspi.ebnf.compiler.ASTOperation;
import at.tspi.ebnf.compiler.ASTException;
import at.tspi.ebnf.compiler.CompilerState;

public class ASTIndexProductions implements ASTOperation {
	public boolean execute(CompilerState state) throws ASTException {
		ASTNode rootNode = state.astRootGet();

		/* We only iterate over the top level because production can only be defined there ... */
		for(ASTNode n : rootNode) {
			if(n instanceof ASTProduction) { state.productionRegisterKnown((ASTProduction)n); }
		}

		return true;
	}
}
