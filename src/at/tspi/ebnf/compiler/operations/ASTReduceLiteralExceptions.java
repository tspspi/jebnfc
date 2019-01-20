package at.tspi.ebnf.compiler.operations;

import at.tspi.ebnf.compiler.ast.ASTNode;
import at.tspi.ebnf.compiler.ast.ASTSingleTerminal;
import at.tspi.ebnf.compiler.ast.ASTSingleTerminalCollection;
import at.tspi.ebnf.compiler.ast.ASTConstantExpression;
import at.tspi.ebnf.compiler.ast.ASTSyntacticException;

import at.tspi.ebnf.compiler.ASTOperation;
import at.tspi.ebnf.compiler.ASTException;
import at.tspi.ebnf.compiler.CompilerState;

import java.util.Iterator;
import java.util.Stack;
import java.util.LinkedList;

public class ASTReduceLiteralExceptions implements ASTOperation {
	private class ReplaceJob {
		public ReplaceJob(ASTNode oldNode, ASTNode newNode) { this.oldNode = oldNode; this.newNode = newNode; }
		public ASTNode oldNode;
		public ASTNode newNode;
	}
	public boolean execute(CompilerState state) throws ASTException {
		boolean operationPerformed = false;
		ASTNode rootNode = state.astRootGet();
		Stack<Iterator<ASTNode>> astStack = new Stack<Iterator<ASTNode>>();
		LinkedList<ReplaceJob> jobQueue = new LinkedList<ReplaceJob>();

		astStack.push(rootNode.iterator());
		while(!astStack.empty()) {
			if(!(astStack.peek().hasNext())) {
				astStack.pop();
				continue;
			}

			ASTNode n = astStack.peek().next();
			if(n instanceof ASTSyntacticException) {
				// Get both children ... if it's a valid node
				ASTSyntacticException exceptionNode = (ASTSyntacticException)n;

				if(exceptionNode.childGetCount() != 2) {
					throw new ASTException("Invalid AST encounteres. ASTSyntacticException with more than two children");
				}

				ASTNode exceptionInclusion = exceptionNode.childGet(0);
				ASTNode exceptionExclusion = exceptionNode.childGet(1);

				/*
					Check if the exclusion is one of our supported literal types - and one
					allowed by the ISO standard

					ToDo: Maybe emit warning only in a last postprocessing stage
				*/
				if(!(
					(exceptionExclusion instanceof ASTSingleTerminal) ||
					(exceptionExclusion instanceof ASTSingleTerminalCollection) ||
					(exceptionExclusion instanceof ASTConstantExpression)
				)) {
					// This error message should be sent after all possible reductions have already happened
					// state.pushWarning("Exception statement "+(exceptionExclusion.getClass().getSimpleName())+" is not completly representable without meta identifiers. This is not supported in ISO EBNF");
					astStack.push(n.iterator());
					continue;
				}

				/*
					If our inclusion is a constant expression and
						- The exclusion is a constant expression:						match -> "false" expression
						- The exclusion is a terminal collection or single terminal: 	constant contains string -> "false" expression; else -> exclusion does not matter

					If our inclusion is a terminal collection or single terminal an
						- The exclusion is a constant expression:						Check if all characters of the constant are contained in inclusion;
																							yes -> normal exception parsing via exception parser logic
																							no -> exception may never be true
						- The exclusion is a terminal collection or single terminal		Iterate over all excluded terminals and remove them from the included
																						list; If the included list is empty -> empty expression
																						Else substitute ...
				*/

				if((exceptionInclusion instanceof ASTConstantExpression) && (exceptionExclusion instanceof ASTConstantExpression)) {
					if(((ASTConstantExpression)exceptionInclusion).getTerminal().equals(((ASTConstantExpression)exceptionExclusion).getTerminal())) {
						// We exclude the same string that we are including ... this means "nothing" is allowed ...
						state.pushError("Excluding the same constant \""+(((ASTConstantExpression)exceptionExclusion).getTerminal())+"\" that's included");
					}
				} else if((exceptionInclusion instanceof ASTConstantExpression) && ((exceptionExclusion instanceof ASTSingleTerminalCollection) || (exceptionExclusion instanceof ASTSingleTerminal))) {
					String excludedChars = "";
					String constant = ((ASTConstantExpression)exceptionInclusion).getTerminal();
					if(exceptionExclusion instanceof ASTSingleTerminalCollection) {
						excludedChars = ((ASTSingleTerminalCollection)exceptionExclusion).choicesGet();
					} else {
						excludedChars = ((ASTSingleTerminal)exceptionExclusion).getTerminal();
					}

					for(int idx = 0; idx < constant.length(); idx++) {
						if(excludedChars.indexOf(constant.charAt(idx)) != -1) {
							// Our constant is forbidden ...
							state.pushError("Excluding characters that would be required for the allowed constants "+constant);
						}
					}

					// Reduce later to be only the constant, exclusion does not have any effect
					state.pushWarning("Specified inclusion does not have any effect");
					jobQueue.add(new ReplaceJob(exceptionNode, exceptionInclusion));
				} else if(exceptionExclusion instanceof ASTConstantExpression) {
					// We cannot compactify these they will be parsed by normal descent through an exclusion node ...
				} else if(exceptionInclusion instanceof ASTSingleTerminal) {
					// Inclusion can be one of the terminal sets
					String exclusionString = "";
					if(exceptionExclusion instanceof ASTSingleTerminal) {
						exclusionString = ((ASTSingleTerminal)exceptionExclusion).getTerminal();
					} else {
						exclusionString = ((ASTSingleTerminalCollection)exceptionExclusion).choicesGet();
					}
					if(exclusionString.indexOf(((ASTSingleTerminal)exceptionInclusion).getTerminal().charAt(0)) != -1) {
						state.pushError("Exclusion leads to empty choice");
					}

					// Reduce later
					state.pushWarning("Specified inclusion does not have any effect");
					jobQueue.add(new ReplaceJob(exceptionNode, exceptionInclusion));
				} else if(exceptionInclusion instanceof ASTSingleTerminalCollection) {
					String exclusionString = "";
					if(exceptionExclusion instanceof ASTSingleTerminal) {
						exclusionString = ((ASTSingleTerminal)exceptionExclusion).getTerminal();
					} else {
						exclusionString = ((ASTSingleTerminalCollection)exceptionExclusion).choicesGet();
					}

					((ASTSingleTerminalCollection)exceptionInclusion).choicesRemove(exclusionString, false);

					if(((ASTSingleTerminalCollection)exceptionInclusion).choicesGet().equals("")) {
						state.pushError("Exclusion leads to empty choice");
					}
					jobQueue.add(new ReplaceJob(exceptionNode, exceptionInclusion));
				}
			}
			astStack.push(n.iterator());
		}

		// Execute replacements
		for(ReplaceJob j : jobQueue) {
			ASTNode parent 		= j.oldNode.parentGet();
			ASTNode oldChild 	= j.oldNode;
			ASTNode newChild 	= j.newNode;

			// Perform replacements
			parent.childReplace(oldChild, newChild);
			operationPerformed = true;
		}

		return operationPerformed;
	}
}