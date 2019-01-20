package at.tspi.ebnf.compiler.operations;

/*
	This operation traverses the whole AST and checks if there are any nodes
	of the following types:
		ASTChoice
		ASTSequential
	that have only a single child. If they have they are replaced by their
	child.
*/

import at.tspi.ebnf.compiler.ast.ASTNode;
import at.tspi.ebnf.compiler.ast.ASTChoice;
import at.tspi.ebnf.compiler.ast.ASTSequential;
import at.tspi.ebnf.compiler.ASTOperation;
import at.tspi.ebnf.compiler.ASTException;
import at.tspi.ebnf.compiler.CompilerState;

import java.util.Iterator;
import java.util.Stack;

public class ASTReduceSingleChoiceAndSequence implements ASTOperation {
	public boolean execute(CompilerState state) throws ASTException {
		ASTNode rootNode = state.astRootGet();
		boolean operationPerformed = false;

		Stack<Iterator<ASTNode>> astStack = new Stack<Iterator<ASTNode>>();

		astStack.push(rootNode.iterator());

		while(!astStack.empty()) {
			if(!(astStack.peek().hasNext())) {
				astStack.pop();
				continue;
			}

			ASTNode n = astStack.peek().next();
			if((n instanceof ASTChoice) || (n instanceof ASTSequential)) {
				if(n.childGetCount() == 1) {
					// Reduce
					ASTNode parentNode = n.parentGet();
					ASTNode childNode = n.childGetLast();

					parentNode.childReplace(n, childNode); // The replaced node will be collected by the garbage collector later on ...
					astStack.pop();
					astStack.push(parentNode.iterator());
					operationPerformed = true;
					continue;
				}
			}

			// Now iterate over child elements ...
			astStack.push(n.iterator());
		}

		return operationPerformed;
	}
}
