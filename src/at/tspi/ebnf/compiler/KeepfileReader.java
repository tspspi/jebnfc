package at.tspi.ebnf.compiler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Stack;

import at.tspi.ebnf.genkeepfileparser.KeepParser_keep;
import at.tspi.ebnf.genkeepfileparser.KeepParser_keepfilesyntax;
import at.tspi.ebnf.genkeepfileparser.KeepParser_keepfilesyntax_17;
import at.tspi.ebnf.genkeepfileparser.KeepParser_metaidentifier;
import at.tspi.ebnf.genkeepfileparser.KeepParser_metaidentifier_0;
import at.tspi.ebnf.genkeepfileparser.KeepParser_metaidentifier_1;
import at.tspi.ebnf.genkeepfileparser.KeepParser_prodoption;
import at.tspi.ebnf.genkeepfileparser.KeepParser_productionproperty;
import at.tspi.ebnf.genkeepfileparser.KeepParser_productionproperty_11;
import at.tspi.ebnf.genkeepfileparser.KeepParser_productionproperty_12;
import at.tspi.ebnf.genkeepfileparser.KeepParser_productionproperty_13;
import at.tspi.ebnf.genkeepfileparser.KeepParser_productionproperty_15;
import at.tspi.ebnf.genkeepfileparser.KeepParser_starter;
import at.tspi.ebnf.genkeepfileparser.KeepParser_whitespacemode;
import at.tspi.ebnf.genkeepfileparser.KeepParser_whitespacemode_2;
import at.tspi.ebnf.genkeepfileparser.KeepParser_whitespacemodeidentifier;
import at.tspi.ebnf.genkeepfileparser.KeepParser_whitespacemodeignore;
import at.tspi.ebnf.genkeepfileparser.KeepParser_whitespacemodereduce;
import at.tspi.ebnf.genkeepfileparser.KeepParser_whitespacemodestrip;
import at.tspi.ebnf.parser.ASCIIFileSource;
import at.tspi.ebnf.parser.Parser;
import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserException;
import at.tspi.ebnf.parser.Preprocessor;
import at.tspi.ebnf.parser.PreprocessorSimpleWhitespace;

public class KeepfileReader {
	private static boolean ignoreKeepElement(ParserElement e) {
		if(e instanceof KeepParser_keepfilesyntax) { return true; }
		else if(e instanceof KeepParser_keepfilesyntax_17) { return true; }

		else if(e instanceof KeepParser_productionproperty) { return true; }
		else if(e instanceof KeepParser_productionproperty_11) { return true; }
		else if(e instanceof KeepParser_productionproperty_12) { return true; }
		else if(e instanceof KeepParser_productionproperty_13) { return true; }
		else if(e instanceof KeepParser_productionproperty_15) { return true; }

		else if(e instanceof KeepParser_metaidentifier_0) { return true; }
		else if(e instanceof KeepParser_metaidentifier_1) { return true; }
		
		else if(e instanceof KeepParser_prodoption) { return true; }
		else if(e instanceof KeepParser_whitespacemode) { return true; }
		else if(e instanceof KeepParser_whitespacemode_2) { return true; }
		else if(e instanceof KeepParser_whitespacemodeidentifier) { return true; }
		
		return false;
	}
	public static void keepfileRead(java.io.File f, CompilerState state) throws FileNotFoundException, IOException, ParserException {
		/* Create data source */
		ASCIIFileSource ds = new ASCIIFileSource(f.getAbsolutePath());

		/* Create preprocessor */
		Preprocessor preproc = new PreprocessorSimpleWhitespace();
		preproc.setParserDataSource(ds);

		/* Create and initialize parser */
		Parser p = new at.tspi.ebnf.genkeepfileparser.KeepParser_Parser();
		ParserElement rootNode = p.parse(preproc, null);

		/*
			Now use our parse tree and build keepfile rules ...
				- Meta identifiers are reduced to ASTKEEPMetaIdentifier
				- prodoptions are collected into the given meta ID
		*/
		String currentMetaId = null;

		Stack<Iterator<ParserElement>> itStack = new Stack<Iterator<ParserElement>>();
		itStack.push(rootNode.iterator());
		while(!itStack.empty()) {
			if(!itStack.peek().hasNext()) {
				// We processed the last element from this iterator, pop from stack
				itStack.pop();
				continue;
			}

			ParserElement e = itStack.peek().next();

			// Check if we simply want to ignore (syntactic sugar, etc.)
			if(ignoreKeepElement(e)) {
				itStack.push(e.iterator());
				continue;
			} else if(e instanceof KeepParser_metaidentifier) {
				currentMetaId = ((KeepParser_metaidentifier)e).innerText();
			} else if(e instanceof KeepParser_keep) {
				state.keepProduction(currentMetaId);
			} else if(e instanceof KeepParser_starter) {
				// We have to keep starter productions as well as register them as starters
				state.keepProduction(currentMetaId);
				state.starterSpecificationSetStarter(currentMetaId);
			} else if(e instanceof KeepParser_whitespacemodeignore) {
				state.whitespaceModeProductionSet(currentMetaId, Preprocessor.VALUE_WHITESPACE__IGNORE);
			} else if(e instanceof KeepParser_whitespacemodereduce) {
				state.whitespaceModeProductionSet(currentMetaId, Preprocessor.VALUE_WHITESPACE__REDUCE);
			} else if(e instanceof KeepParser_whitespacemodestrip) {
				state.whitespaceModeProductionSet(currentMetaId, Preprocessor.VALUE_WHITESPACE__STRIP);
			} else {
				throw new ParserException("Unknown keepfile element "+e.getClass().getSimpleName()+" encountered");
			}

			itStack.push(e.iterator());
			continue;
		}
	}

	/*
		private static void debugPrintKeepParseTree(ParserElement curNode, int lvl) {
			for(int i = 0; i < lvl; i++) {
				System.out.print(" ");
			}
	
			System.out.println(curNode);
	
			for(ParserElement e : curNode) {
				debugPrintKeepParseTree(e, lvl+1);
			}
		}
		private static void debugPrintKeepParseTree(ParserElement rootNode) {
			debugPrintKeepParseTree(rootNode, 0);
		}
	*/
}
