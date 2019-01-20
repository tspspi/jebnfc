package at.tspi.ebnf.compiler.operations;

import at.tspi.ebnf.compiler.ast.ASTNode;
import at.tspi.ebnf.compiler.ast.ASTChoice;
import at.tspi.ebnf.compiler.ast.ASTSequential;
import at.tspi.ebnf.compiler.ast.ASTRepeat;
import at.tspi.ebnf.compiler.ast.ASTSingleTerminal;
import at.tspi.ebnf.compiler.ast.ASTSingleTerminalCollection;
import at.tspi.ebnf.compiler.ast.ASTConstantExpression;
import at.tspi.ebnf.compiler.ASTOperation;
import at.tspi.ebnf.compiler.ASTException;
import at.tspi.ebnf.compiler.CompilerState;

import java.util.Iterator;
import java.util.Stack;

public class ASTCollectSingleTerminalChoices implements ASTOperation {
	public boolean execute(CompilerState state) throws ASTException {
		boolean operationPerformed = false;
		ASTNode rootNode = state.astRootGet();
		Stack<Iterator<ASTNode>> astStack = new Stack<Iterator<ASTNode>>();

		astStack.push(rootNode.iterator());

		while(!astStack.empty()) {
			if(!(astStack.peek().hasNext())) {
				astStack.pop();
				continue;
			}

			ASTNode n = astStack.peek().next();
			if(n instanceof ASTChoice) {
				// Check if all children have the same ASTSingleTerminal type and a length of 1
				boolean sameType = true;
				for(ASTNode n2 : n) {
					if(!((n2 instanceof ASTSingleTerminal)||(n2 instanceof ASTSingleTerminalCollection))) { sameType = false; break; }
					if(n2 instanceof ASTSingleTerminal) {
						if(((ASTSingleTerminal)n2).getTerminal().length() > 1) { sameType = false; break; }
					} else if(n2 instanceof ASTSingleTerminalCollection) {
						if((((ASTSingleTerminalCollection)n2).getMin() != 1) || (((ASTSingleTerminalCollection)n2).getMax() != 1)) { sameType = false; break; } // Condensation only works for non-repeat
					}
				}

				if(sameType) {
					// Condense into a single node ...
					ASTSingleTerminalCollection collection = new ASTSingleTerminalCollection();
					for(ASTNode n2 : n) {
						if(n2 instanceof ASTSingleTerminal) {
							if(((ASTSingleTerminal)n2).getTerminal().length() == 1) {
								collection.choicesAdd(((ASTSingleTerminal)n2).getTerminal());
							} else {
								collection.minMaxSet(0, collection.getMax());
							}
						} else if(n2 instanceof ASTSingleTerminalCollection) {
							collection.choicesAddMultiple(((ASTSingleTerminalCollection)n2).choicesGet());
						}
					}

					// Remove all children ...
					n.childRemoveAll();

					// Append our condensed node
					n.childAppend(collection);

					operationPerformed = true;
				}

				astStack.push(n.iterator());
			} else if(n instanceof ASTSequential) {
				// Check if all children have the same ASTSingleTerminal type and a length of 1
				boolean sameType = true;
				for(ASTNode n2 : n) {
					if(!((n2 instanceof ASTSingleTerminal)||(n2 instanceof ASTConstantExpression))) { sameType = false; break; }
				}

				if(sameType) {
					// Condense into a single constant expression
					ASTConstantExpression expression = new ASTConstantExpression();
					String exp = "";

					for(ASTNode n2 : n) {
						if(n2 instanceof ASTSingleTerminal) {
							exp = exp + ((ASTSingleTerminal)n2).getTerminal();
						} else if(n2 instanceof ASTConstantExpression) {
							exp = exp + ((ASTConstantExpression)n2).getTerminal();
						}
					}
					expression.setTerminal(exp);

					// Remove all children ...
					n.childRemoveAll();

					// Append our condensed node
					n.childAppend(expression);

					operationPerformed = true;
				}

				astStack.push(n.iterator());
			} else if(n instanceof ASTRepeat) {
				// Check if the only child is an ASTSingleTerminalCollection ...
				if(n.childGetCount() == 1) {
					if(n.childGetLast() instanceof ASTSingleTerminalCollection) {
						// Copy min & max into ASTSingleTerminalCollection & reduce
						((ASTSingleTerminalCollection)(n.childGetLast())).minMaxSet(((ASTRepeat)n).getMin(), ((ASTRepeat)n).getMax());
						astStack.push(n.childGetLast().iterator());

						n.parentGet().childReplace(n, n.childGetLast());

						operationPerformed = true;
					} else {
						astStack.push(n.iterator());
					}
				} else {
					astStack.push(n.iterator());
				}
			} else {
				astStack.push(n.iterator());
			}
		}

		return operationPerformed;
	}
}