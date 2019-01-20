package at.tspi.ebnf.compiler.operations;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

import at.tspi.ebnf.compiler.ASTException;
import at.tspi.ebnf.compiler.ASTOperation;
import at.tspi.ebnf.compiler.CompilerState;
import at.tspi.ebnf.compiler.ast.ASTChoice;
import at.tspi.ebnf.compiler.ast.ASTEmpty;
import at.tspi.ebnf.compiler.ast.ASTNode;
import at.tspi.ebnf.compiler.ast.ASTSequential;

/*
This module is currently NOT implemented
*/

public class ASTEmptyChoice implements ASTOperation {
	private class RemoveJob {
		public RemoveJob(ASTNode node) { this.node = node; }
		public ASTNode node;
	}
	private class MakeOptionalJob {
		public MakeOptionalJob(ASTNode node) { this.node = node; }
		public ASTNode node;
	}

	public boolean execute(CompilerState state) throws ASTException {
		ASTNode rootNode = state.astRootGet();
		Stack<Iterator<ASTNode>> astStack = new Stack<Iterator<ASTNode>>();
		LinkedList<RemoveJob> jobRemove = new LinkedList<RemoveJob>();
		LinkedList<MakeOptionalJob> jobMakeOptional = new LinkedList<MakeOptionalJob>();
		boolean operationPerformed = false;

		// Iterate over all nodes ...
		astStack.push(rootNode.iterator());
		while(!astStack.empty()) {
			if(!(astStack.peek().hasNext())) {
				astStack.pop();
				continue;
			}

			ASTNode n = astStack.peek().next();

			if(n instanceof ASTEmpty) {
				// Check if our parent is a sequence our choice
				ASTNode par = n.parentGet();
				if(par instanceof ASTSequential) {
					//@ToDo
				} else if(par instanceof ASTChoice) {
					//@ToDo
				}
			} else {
				// Recurse into children
				astStack.push(n.iterator());
			}
		}

		return operationPerformed;
	}
}