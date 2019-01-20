package at.tspi.ebnf.compiler;

import java.util.Iterator;
import java.util.Stack;

import at.tspi.ebnf.compiler.ast.*;
import at.tspi.ebnf.genisoebnfparser.*;
import at.tspi.ebnf.parser.ParserElement;

public class ASTBuilderISO14977Gen implements ASTBuilder {
	private boolean ignoreElement(ParserElement e) {
		// All listed nodes are only syntactic sugar for our language ...
		if(e instanceof EbnfElement_terminalstringsecond_59)				{ return true; }
		else if(e instanceof EbnfElement_terminalstringsecond_58)			{ return true; }
		else if(e instanceof EbnfElement_terminalstringfirst_57)			{ return true; }
		else if(e instanceof EbnfElement_terminalstringfirst_56)			{ return true; }
		else if(e instanceof EbnfElement_terminalstring)					{ return true; }
		else if(e instanceof EbnfElement_terminalstring_55)					{ return true; }
		else if(e instanceof EbnfElement_terminalstring_53)					{ return true; }
		else if(e instanceof EbnfElement_terminalstring_52)					{ return true; }
		else if(e instanceof EbnfElement_terminalstring_51)					{ return true; }
		else if(e instanceof EbnfElement_terminalstring_49)					{ return true; }
		else if(e instanceof EbnfElement_terminalstring_48)					{ return true; }
		else if(e instanceof EbnfElement_syntaxrule_4)						{ return true; }
		else if(e instanceof EbnfElement_syntaxrule_6)						{ return true; }
		else if(e instanceof EbnfElement_syntax_1)							{ return true; }
		else if(e instanceof EbnfElement_syntacticterm)						{ return true; }
		else if(e instanceof EbnfElement_syntacticterm_18)					{ return true; }
		else if(e instanceof EbnfElement_syntacticterm_19)					{ return true; }
		else if(e instanceof EbnfElement_syntacticterm_20)					{ return true; }
		else if(e instanceof EbnfElement_syntacticterm_21)					{ return true; }
		else if(e instanceof EbnfElement_syntacticprimary)					{ return true; }
		// else if(e instanceof EbnfElement_syntacticprimary_36)				{ return true; }		/* ToDo: Possibly keep this too ...? */
		else if(e instanceof EbnfElement_syntacticfactor)					{ return true; }
		else if(e instanceof EbnfElement_syntacticfactorrepeatedfactor_26)	{ return true; }
		else if(e instanceof EbnfElement_specialsequence_62)				{ return true; }
		else if(e instanceof EbnfElement_specialsequence_60)				{ return true; }
		else if(e instanceof EbnfElement_singledefinition_15)				{ return true; }
		else if(e instanceof EbnfElement_singledefinition_14)				{ return true; }
		else if(e instanceof EbnfElement_singledefinition_13)				{ return true; }
		else if(e instanceof EbnfElement_repeatedsequence_42)				{ return true; }
		else if(e instanceof EbnfElement_repeatedsequence_40)				{ return true; }
		else if(e instanceof EbnfElement_optionalsequence_39)				{ return true; }
		else if(e instanceof EbnfElement_optionalsequence_37)				{ return true; }
		else if(e instanceof EbnfElement_metaidentifier_47)					{ return true; }
		else if(e instanceof EbnfElement_metaidentifier_46)					{ return true; }
		else if(e instanceof EbnfElement_integer_29)						{ return true; }
		else if(e instanceof EbnfElement_integer_28)						{ return true; }
		else if(e instanceof EbnfElement_groupedsequence_45)				{ return true; }
		else if(e instanceof EbnfElement_groupedsequence_43)				{ return true; }
		else if(e instanceof EbnfElement_definitionslist_10)				{ return true; }
		else if(e instanceof EbnfElement_definitionslist_9)					{ return true; }
		else if(e instanceof EbnfElement_definitionslist_8)					{ return true; }
		else if(e instanceof EbnfElement_syntacticfactorrepeatedfactor)		{ return true; }

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

			if(e instanceof EbnfElement_syntaxrule) {
				if(astNode.peek() instanceof ASTRoot) {
					ASTProduction newProd = new ASTProduction();
					astNode.peek().childAppend(newProd);
					astNode.push(newProd);
				} else {
					throw new ASTException("Building AST: Syntax rules can only be added to ASTRoot");
				}
			} else if(e instanceof EbnfElement_metaidentifier) {
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
			} else if(e instanceof EbnfElement_singledefinition) {
				ASTSequential astSequential = new ASTSequential();

				astNode.peek().childAppend(astSequential);
				astNode.push(astSequential);
			} else if(e instanceof EbnfElement_definitionslist) {
				ASTChoice astChoice = new ASTChoice();

				astNode.peek().childAppend(astChoice);
				astNode.push(astChoice);
			} else if(e instanceof EbnfElement_optionalsequence) {
				ASTOptional astNewNode = new ASTOptional();
				astNode.peek().childAppend(astNewNode);
				astNode.push(astNewNode);
			} else if((e instanceof EbnfElement_terminalstringfirst) || (e instanceof EbnfElement_terminalstringsecond)) {
				ASTSingleTerminal astNewNode = new ASTSingleTerminal(e.innerText());
				astNode.peek().childAppend(astNewNode);
				astNode.push(astNewNode);
			} else if(e instanceof EbnfElement_repeatedsequence) {
				ASTRepeat astNewNode = new ASTRepeat(0, -1); // Unbound repeat ...
				astNode.peek().childAppend(astNewNode);
				astNode.push(astNewNode);
			} else if(e instanceof EbnfElement_syntacticfactorrepeatedfactor) {
				// Fixed amount repetition; Amount is later on set by an integer node ...
				ASTRepeat astNewNode = new ASTRepeat(-1, -1); // Set to invalid minimum ... this will later be caught by the compiler logic if not modified ...
				astNode.peek().childAppend(astNewNode);
				astNode.push(astNewNode);
			} else if(e instanceof EbnfElement_integer) {
				// Specifies the number of repetitions if resides under ASTRepeat
				if(astNode.peek() instanceof ASTRepeat) {
					((ASTRepeat)(astNode.peek())).setMinMax(0, Integer.parseInt(e.innerText()));
					astNode.push(astNode.peek()); // Push ourself to survive next pop ...
				} else {
					throw new ASTException("Integer should not be located anywhere except under ASTRepeat");
				}
			} else if(e instanceof EbnfElement_syntacticexception) {
				ASTSyntacticException astNewNode = new ASTSyntacticException();
				// Fetch the last child
				ASTNode includedNode = astNode.peek().childGetLast();
				astNode.peek().childReplace(includedNode, astNewNode);
				astNewNode.childAppend(includedNode);
				astNode.push(astNewNode);
			} else if(e instanceof EbnfElement_specialsequencevalue) {
				ASTSpecialSequence astNewNode = new ASTSpecialSequence(e.innerText());
				astNode.peek().childAppend(astNewNode);
				astNode.push(astNewNode);
			} else if(e instanceof EbnfElement_emptysequence) {
				ASTEmpty astNewNode = new ASTEmpty();
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


/*	public String debugPrintParsetree(ParserElement rootNode) {
		return debugPrintParsetree(rootNode, 0);
	}
	private String debugPrintParsetree(ParserElement curNode, int level) {
		String res = curNode.toString(level)+"\n";
		for(ParserElement n : curNode) {
			res = res + debugPrintParsetree(n, level+1);
		}
		return res; 
	} */
}