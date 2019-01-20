package at.tspi.ebnf.compiler.operations;

import at.tspi.ebnf.compiler.ast.ASTNode;
import at.tspi.ebnf.compiler.ast.ASTProduction;
import at.tspi.ebnf.compiler.ast.ASTSingleTerminal;
import at.tspi.ebnf.compiler.ast.ASTSingleTerminalCollection;
import at.tspi.ebnf.compiler.ast.ASTProductionReference;
import at.tspi.ebnf.compiler.ASTOperation;
import at.tspi.ebnf.compiler.ASTException;
import at.tspi.ebnf.compiler.CompilerState;

import java.util.Iterator;
import java.util.Stack;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class ASTReduceLiteralOnlyProductions implements ASTOperation {
	private class ReplaceJob {
		public ReplaceJob(ASTNode parent, ASTNode oldChild, ASTNode newChild) { this.parent = parent; this.oldChild = oldChild; this.newChild = newChild; }
		public ASTNode			parent;
		public ASTNode			oldChild;
		public ASTNode			newChild;
	}
	/*
		ToDo: Implement "keep" attributes that prevent removal of productions (via a check keep
		function inside the compiler state ...
	*/
	public boolean execute(CompilerState state) throws ASTException {
		boolean operationPerformed = false;
		ASTNode rootNode = state.astRootGet();
		Stack<Iterator<ASTNode>> astStack = new Stack<Iterator<ASTNode>>();
		HashMap<String, ASTProduction> hmReplace = new HashMap<String, ASTProduction>();
		HashMap<String, Boolean> hmReplaced = new HashMap<String, Boolean>();
		LinkedList<ReplaceJob> replaces = new LinkedList<ReplaceJob>();


		/*
			Index productions that are simply built by a single
				ASTSingleTerminalCollection
				ASTSingleTerminal
				ASTConstantExpression <- 	NO no checks for replacement of constant expressions. They are required for keyword detection whereas
											other nodes are used to collect user defined names

			They can be inserted into other productions in place of the references
		*/

		astStack.push(rootNode.iterator());
		while(!astStack.empty()) {
			if(!(astStack.peek().hasNext())) {
				astStack.pop();
				continue;
			}

			ASTNode n = astStack.peek().next();
			if(!(n instanceof ASTProduction)) { continue; }
			ASTProduction p = (ASTProduction)n;

			if(p.childGetCount() != 1) { continue; }

			if(
				(p.childGetLast() instanceof ASTSingleTerminalCollection) ||
				(p.childGetLast() instanceof ASTSingleTerminal)
			) {
				// This rule can be inserted anywhere a reference is found. Remember for later on - we will remove them also later ...
				hmReplace.put(p.getName(), p);
				hmReplaced.put(p.getName(), false);
			}
		}

		// Replacement stage ... iterate over everything and check if ASTProductionReference are referencing replaceable rules
		astStack.push(rootNode.iterator());
		while(!astStack.empty()) {
			if(!(astStack.peek().hasNext())) {
				astStack.pop();
				continue;
			}

			ASTNode n = astStack.peek().next();

			if(n instanceof ASTProductionReference) {
				ASTProductionReference ref = (ASTProductionReference)n;

				// Check if this production reference is replaceable ...
				ASTProduction prod = hmReplace.get(ref.getName());
				if(prod != null) {
					// Remember that we replace  these later ...
					replaces.add(new ReplaceJob(ref.parentGet(), ref, prod));
					/* hmReplaced.put(prod.getName(), true);



					operationPerformed = true; */
				}
			}

			astStack.push(n.iterator());
		}

		/* Now perform replacements ... */
		for(ReplaceJob j : replaces) {
			ASTNode parent 		= j.parent;
			ASTNode oldChild 	= j.oldChild;
			ASTNode newChild 	= j.newChild.childGetLast();

			// Check if we should keep this production even if we could optimize it away ...
			if(state.keepProductionCheck(((ASTProduction)(j.newChild)).getName())) { continue; }

			// Create clone
			if(newChild instanceof ASTSingleTerminalCollection) {
				newChild = new ASTSingleTerminalCollection((ASTSingleTerminalCollection)newChild);
			} else if(newChild instanceof ASTSingleTerminal) {
				newChild = new ASTSingleTerminal((ASTSingleTerminal)newChild);
			}
			newChild.originalProductionNameSet(((ASTProduction)(j.newChild)).getName());

			// Perform replacements
			parent.childReplace(oldChild, newChild);
			hmReplaced.put(((ASTProduction)(j.newChild)).getName(), true);

			operationPerformed = true;
		}

		/* Delete all inserted productions ... */
		for(Map.Entry<String, Boolean> e1 : hmReplaced.entrySet()) {
			if(e1.getValue() == false) {
				continue;
			}

			ASTProduction prod = hmReplace.get(e1.getKey());
			prod.parentGet().childRemove(prod);
		}

		return operationPerformed;
	}
}