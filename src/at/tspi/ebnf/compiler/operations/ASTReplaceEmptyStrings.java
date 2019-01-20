package at.tspi.ebnf.compiler.operations;

import at.tspi.ebnf.compiler.ast.ASTNode;
import at.tspi.ebnf.compiler.ast.ASTSingleTerminal;
import at.tspi.ebnf.compiler.ast.ASTSingleTerminalCollection;
import at.tspi.ebnf.compiler.ast.ASTEmpty;

import at.tspi.ebnf.compiler.ASTOperation;
import at.tspi.ebnf.compiler.ASTException;
import at.tspi.ebnf.compiler.CompilerState;

import java.util.Iterator;
import java.util.Stack;
import java.util.LinkedList;

public class ASTReplaceEmptyStrings implements ASTOperation {
	private class ReplaceJobEmpty {
		public ReplaceJobEmpty(ASTNode parent, ASTNode oldChild) { this.parent = parent; this.oldChild = oldChild; }
		public ASTNode			parent;
		public ASTNode			oldChild;
	}
	/*
		ToDo: Implement "keep" attributes that prevent removal of productions (via a check keep
		function inside the compiler state ...
	*/
	public boolean execute(CompilerState state) throws ASTException {
		boolean operationPerformed = false;
		ASTNode rootNode = state.astRootGet();
		Stack<Iterator<ASTNode>> astStack = new Stack<Iterator<ASTNode>>();
		LinkedList<ReplaceJobEmpty> replaces = new LinkedList<ReplaceJobEmpty>();

		astStack.push(rootNode.iterator());
		while(!astStack.empty()) {
			if(!(astStack.peek().hasNext())) {
				astStack.pop();
				continue;
			}

			ASTNode n = astStack.peek().next();
			if(!((n instanceof ASTSingleTerminal) || (n instanceof ASTSingleTerminalCollection))) {
				astStack.push(n.iterator());
				continue;
			}

			if(n instanceof ASTSingleTerminal) {
				ASTSingleTerminal t = (ASTSingleTerminal)n;

				if(t.getTerminal().equals("")) {
					// Is empty ... replace by ASTEmpty ...
					replaces.add(new ReplaceJobEmpty(n.parentGet(), n));
				}
			} else if(n instanceof ASTSingleTerminalCollection) {
				ASTSingleTerminalCollection t = (ASTSingleTerminalCollection)n;

				if(t.choicesGet().equals("")) {
					// Is empty ... replace by ASTEmpty ...
					replaces.add(new ReplaceJobEmpty(n.parentGet(), n));
				}
			}
		}

		for(ReplaceJobEmpty j : replaces) {
			ASTNode parent 		= j.parent;
			ASTNode oldChild 	= j.oldChild;

			parent.childReplace(oldChild, new ASTEmpty());

			operationPerformed = true;
		}

		return operationPerformed;
	}
}