package at.tspi.ebnf.parser;

import at.tspi.ebnf.parser.Preprocessor;
import at.tspi.ebnf.parser.Parser;
import at.tspi.ebnf.parser.ParserLogger;
import at.tspi.ebnf.parser.ParserElement;

abstract public class ParserElementSequential extends ParserElement {
	protected ParserElement[] children;
	private int currentSelection;

	public ParserElementSequential(ParserElement parent, ParserElement[] children) { super(parent); this.children = children; currentSelection = 0; }
	public ParserElementSequential(ParserElement parent, String productionName, ParserElement[] children) { super(parent, productionName); this.children = children; currentSelection = 0; }

	public int parseNext(
		ParserGraphemeCluster nextCluster,
		String dataSourceName,
		int lineNumber,
		int characterPosition,

		Parser parser,
		Preprocessor preproc,
		ParserLogger logger,
		
		boolean lastStepUp,
		int lastResult
	) throws ParserException {
		// EOF case ...
		if((nextCluster == null) && (currentSelection < (children.length-1))) {
			parser.popCurrentElement(); // We are finished
			if(parentElement != null) { parentElement.removeChild(this); }
			try { if(!parser.currentElement.empty()) { preproc.setMode(Preprocessor.PROPERTY_WHITESPACE, parser.currentElement.peek().whitespaceMode); } } catch(ParserPreprocessorException e) { e.printStackTrace(); }
			return ParserElement.STATUS_FAILED;
		}

		if(lastStepUp) {
			// What happens if one of our children returned ...
			switch(lastResult) {
				case ParserElement.STATUS_FAILED:
					// Our child failed, so we fail too ...
					//@ToDo: LOG ...
					parser.popCurrentElement();
					if(parentElement != null) { parentElement.removeChild(this); }
					try { if(!parser.currentElement.empty()) { preproc.setMode(Preprocessor.PROPERTY_WHITESPACE, parser.currentElement.peek().whitespaceMode); } } catch(ParserPreprocessorException e) { e.printStackTrace(); }
					return ParserElement.STATUS_FAILED;
				case ParserElement.STATUS_OK_CONSUME:
				case ParserElement.STATUS_OK_REDELIVER:
					currentSelection++;
					if(currentSelection < children.length) {
						// Recurse downwards ...
						ParserElement newChild = children[currentSelection].factory(this); // Create our new child
						parser.pushCurrentElement(newChild); // Push onto context stack
						addChild(newChild);
						try { preproc.setMode(Preprocessor.PROPERTY_WHITESPACE, newChild.whitespaceMode); } catch(ParserPreprocessorException e) { e.printStackTrace(); }
						return ParserElement.STATUS_OK_REDELIVER; // We have created a new node so she has to receive the event we received NOW (not the one consumed) again ...
					}
					parser.popCurrentElement(); // We are finished - remove ourselves from the context stack and redeliver the CURRENT element (not the one consumed)
					try { if(!parser.currentElement.empty()) { preproc.setMode(Preprocessor.PROPERTY_WHITESPACE, parser.currentElement.peek().whitespaceMode); } } catch(ParserPreprocessorException e) { e.printStackTrace(); }
//					return lastResult; // We have created a new node so she has to receive the event we received again ...
					return ParserElement.STATUS_OK_REDELIVER;
				case STATUS_CONTINUE:	// Cannot happen with this node ...
					throw new ParserException("Continue is not possible for this node. Implementation error.");
				case STATUS_BRANCH_DOWN: // Cannot happen during step up
					throw new ParserException("BRANCH_DOWN cannot happen during branching up. Implementation error.");
				default:
					throw new ParserException("Unknown code. Implementation error.");
			}
		} else {
			// Someone stepped down into us ...
			switch(lastResult) {
				case STATUS_BRANCH_DOWN:
				case STATUS_OK_REDELIVER:
				case STATUS_OK_CONSUME:
					currentSelection = 0;
					ParserElement newChild = children[0].factory(this); // Create our first child
					parser.pushCurrentElement(newChild); // Push onto context stack
					addChild(newChild);
					try { preproc.setMode(Preprocessor.PROPERTY_WHITESPACE, newChild.whitespaceMode); } catch(ParserPreprocessorException e) { e.printStackTrace(); }
					return ParserElement.STATUS_OK_REDELIVER; // We have created a new node so she has to receive the event we received (not the one consumed) again ...
				default:	// Cannot happen with this node ...
					throw new ParserException("Last result "+lastResult+" is not possible for this node. Implementation error.");
			}
		}
	}
}