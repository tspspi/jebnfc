package at.tspi.ebnf.compiler;

import at.tspi.ebnf.compiler.ast.*;
import at.tspi.ebnf.parser.*;
import at.tspi.ebnf.ebnfparser.*;

import java.util.Iterator;
import java.util.Stack;

public class ASTBuilderISO14977 implements ASTBuilder {
	private boolean ignoreElement(ParserElement e) {
		// All listed nodes are only syntactic sugar for our language ...
		if(e instanceof EbnfElement_ConcatenateSymbol) 						{ return true; }
		else if(e instanceof EbnfElement_DefiningSymbol) 					{ return true; }
		else if(e instanceof EbnfElement_DefinitionSeparatorSymbol) 		{ return true; }
		else if(e instanceof EbnfElement_EndGroupSymbol) 					{ return true; }
		else if(e instanceof EbnfElement_EndOptionalSymbol) 				{ return true; }
		else if(e instanceof EbnfElement_EndRepeatSymbol) 					{ return true; }
		else if(e instanceof EbnfElement_ExceptSymbol) 						{ return true; }
		else if(e instanceof EbnfElement_QuoteSymbolFirst) 					{ return true; }
		else if(e instanceof EbnfElement_QuoteSymbolSecond) 				{ return true; }
		// else if(e instanceof EbnfElement_RepetitionSymbol) 				{ return true; }
		else if(e instanceof EbnfElement_SpecialSequenceSymbol)				{ return true; }
		else if(e instanceof EbnfElement_StartGroupSymbol)					{ return true; }
		else if(e instanceof EbnfElement_StartOptionalSymbol)				{ return true; }
		else if(e instanceof EbnfElement_StartRepeatSymbol)					{ return true; }
		else if(e instanceof EbnfElement_TerminatorSymbol)					{ return true; }
		else if(e instanceof EbnfElement_SingleDefinition_1)				{ return true; }
		else if(e instanceof EbnfElement_SingleDefinition_2)				{ return true; }
		else if(e instanceof EbnfElement_Letter)							{ return true; }
		else if(e instanceof EbnfElement_MetaIdentifierCharacters)			{ return true; }
		else if(e instanceof EbnfElement_SyntacticFactor)					{ return true; }
		// else if(e instanceof EbnfElement_SyntacticFactor_1)					{ return true; }
		else if(e instanceof EbnfElement_SyntacticPrimary)					{ return true; }
		else if(e instanceof EbnfElement_SyntacticTerm)						{ return true; }
		else if(e instanceof EbnfElement_SyntacticTerm_1)					{ return true; }
		else if(e instanceof EbnfElement_DefinitionsList_1)					{ return true; }
		else if(e instanceof EbnfElement_DefinitionsList_2)					{ return true; }
		else if(e instanceof EbnfElement_GroupedSequence)					{ return true; }
		else if(e instanceof EbnfElement_TerminalStringFirstDelim)			{ return true; }
		else if(e instanceof EbnfElement_TerminalStringSecondDelim)			{ return true; }
		else if(e instanceof EbnfElement_TerminalString)					{ return true; }
		else if(e instanceof EbnfElement_RepetitionSymbol)					{ return true; }
		else if(e instanceof EbnfElement_SpecialSequence)					{ return true; }

		return false;
	}

	public ASTNode build(ParserElement rootNode) {
		Stack<Iterator<ParserElement>> itStack = new Stack<Iterator<ParserElement>>();
		Stack<ASTNode> astNode = new Stack<ASTNode>();
		ASTRoot astRoot = new ASTRoot();

		// Bootstrap iterators ...
		astNode.push(astRoot);
		itStack.push(rootNode.iterator());

		while(!itStack.empty()) {
			if(!itStack.peek().hasNext()) {
				// We processed the last element from this iterator, pop from stack (and do so with astNode too ...)
				astNode.pop();
				itStack.pop();
				continue;
			}

			/*
				Process this element ...
			*/
			ParserElement e = itStack.peek().next();
			if(ignoreElement(e)) {
				astNode.push(astNode.peek()); // Push ourself to survive next pop ...
				itStack.push(e.iterator());
				continue;
			}

			if(e instanceof EbnfElement_SyntaxRule) {
				if(astNode.peek() instanceof ASTRoot) {
					ASTProduction newProd = new ASTProduction();
					astNode.peek().childAppend(newProd);
					astNode.push(newProd);
				} else {
					throw new ASTException("Building AST: Syntax rules can only be added to ASTRoot");
				}
			} else if(e instanceof EbnfElement_MetaIdentifier) {
				/*
					Meta identifier can either be used below syntax rule (name of the production) or
					somewhere inside the definition (references to meta id's)
				*/
				if(astNode.peek() instanceof ASTProduction) {
					((ASTProduction)(astNode.peek())).setName(e.innerText());
					astNode.push(astNode.peek()); // Push ourself to survive next pop ...
				} else {
					ASTProductionReference ref = new ASTProductionReference(e.innerText());
					astNode.peek().childAppend(ref);
					astNode.push(ref);
				}
			} else if(e instanceof EbnfElement_SingleDefinition) {
				ASTSequential astSequential = new ASTSequential();

				astNode.peek().childAppend(astSequential);
				astNode.push(astSequential);
			} else if(e instanceof EbnfElement_DefinitionsList) {
				ASTChoice astChoice = new ASTChoice();

				astNode.peek().childAppend(astChoice);
				astNode.push(astChoice);
			} else if(e instanceof EbnfElement_OptionalSequence) {
				ASTOptional astNewNode = new ASTOptional();
				astNode.peek().childAppend(astNewNode);
				astNode.push(astNewNode);
			} else if((e instanceof EbnfElement_TerminalStringFirst) || (e instanceof EbnfElement_TerminalStringSecond)) {
				ASTSingleTerminal astNewNode = new ASTSingleTerminal(e.innerText());
				astNode.peek().childAppend(astNewNode);
				astNode.push(astNewNode);
			} else if(e instanceof EbnfElement_RepeatedSequence) {
				ASTRepeat astNewNode = new ASTRepeat(0, -1); // Unbound repeat ...
				astNode.peek().childAppend(astNewNode);
				astNode.push(astNewNode);
			} else if(e instanceof EbnfElement_SyntacticFactor_1) {
				// Fixed amount repetition; Amount is later on set by an integer node ...
				ASTRepeat astNewNode = new ASTRepeat(-1, -1); // Set to invalid minimum ... this will later be caught by the compiler logic if not modified ...
				astNode.peek().childAppend(astNewNode);
				astNode.push(astNewNode);
			} else if(e instanceof EbnfElement_Integer) {
				// Specifies the number of repetitions if resides under ASTRepeat
				if(astNode.peek() instanceof ASTRepeat) {
					((ASTRepeat)(astNode.peek())).setMinMax(0, Integer.parseInt(e.innerText()));
					astNode.push(astNode.peek()); // Push ourself to survive next pop ...
				} else {
					throw new ASTException("Integer should not be located anywhere except under ASTRepeat");
				}
			} else if(e instanceof EbnfElement_SyntacticException) {
				ASTSyntacticException astNewNode = new ASTSyntacticException();
				// Fetch the last child
				ASTNode includedNode = astNode.peek().childGetLast();
				astNode.peek().childReplace(includedNode, astNewNode);
				astNewNode.childAppend(includedNode);
				astNode.push(astNewNode);
			} else if(e instanceof EbnfElement_SpecialSequenceSequence) {
				ASTSpecialSequence astNewNode = new ASTSpecialSequence(e.innerText());
				astNode.peek().childAppend(astNewNode);
				astNode.push(astNewNode);
			} else {
				/*
					We don't expect this parser object, the parser should already have done syntax analysis and basic error detection
					We have already removed syntactic sugar -> this means we have a mismatch between parser and AST builder
				*/
				throw new ASTException("Unknown or unimplemented parser element encountered: "+e.toString()+". This looks like a mismatch between AST builder and parser");
			}

			/*
				Iterate over all child elements. The AST will already have been modified ...
			*/
			itStack.push(e.iterator());
		}

		return astRoot;
	}

	public String debugPrintAST(ASTNode rootNode) {
		return debugPrintAST(rootNode, 0);
	}
	private String debugPrintAST(ASTNode curNode, int level) {
		String res = curNode.toString(level)+"\n";
		for(ASTNode n : curNode) {
			res = res + debugPrintAST(n, level+1);
		}
		return res;
	}
/*
	public static String dbg_debugPrintAST(ASTNode rootNode) {
		return dbg_debugPrintAST(rootNode, 0);
	}
	private static String dbg_debugPrintAST(ASTNode curNode, int level) {
		String res = curNode.toString(level)+"\n";
		for(ASTNode n : curNode) {
			res = res + dbg_debugPrintAST(n, level+1);
		}
		return res;
	}
*/
}