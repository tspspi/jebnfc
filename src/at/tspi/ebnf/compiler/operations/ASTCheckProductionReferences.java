package at.tspi.ebnf.compiler.operations;

/*
	This operation traverses the AST and registeres all defined productions
	inside the Compiler State Block
*/

import at.tspi.ebnf.compiler.ast.ASTNode;
import at.tspi.ebnf.compiler.ast.ASTProduction;
import at.tspi.ebnf.compiler.ast.ASTProductionReference;

import at.tspi.ebnf.compiler.ASTOperation;
import at.tspi.ebnf.compiler.ASTException;
import at.tspi.ebnf.compiler.CompilerState;

import java.util.Iterator;
import java.util.Stack;

public class ASTCheckProductionReferences implements ASTOperation {
	public boolean execute(CompilerState state) throws ASTException {
		ASTNode rootNode = state.astRootGet();
		ASTProduction currentProduction;
		Stack<Iterator<ASTNode>> stkChildren = new Stack<Iterator<ASTNode>>();

		/* We only iterate over the top level because production can only be defined there ... */
		for(ASTNode n : rootNode) {
			if(n instanceof ASTProduction) {
				currentProduction = (ASTProduction)n;

				stkChildren.push(currentProduction.iterator());

				while(!stkChildren.empty()) {
					if(!stkChildren.peek().hasNext()) {
						stkChildren.pop();
						continue;
					}

					ASTNode nextchild = stkChildren.peek().next();
					if(nextchild instanceof ASTProductionReference) {
						ASTProductionReference ref = (ASTProductionReference)nextchild;
						state.registerRefcount(currentProduction, ref.getName());
					}

					stkChildren.push(nextchild.iterator());
				}
			}
		}

		return true;
	}
}
