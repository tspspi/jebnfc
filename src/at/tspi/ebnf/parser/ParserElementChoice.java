package at.tspi.ebnf.parser;

import at.tspi.ebnf.parser.Preprocessor;
import at.tspi.ebnf.parser.Parser;
import at.tspi.ebnf.parser.ParserLogger;
import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserDataSource;

import java.io.IOException;

abstract public class ParserElementChoice extends ParserElement {
	protected ParserElement[] children;
	private int currentSelection;

	private ParserDataSource.Mark mark = null;

	public ParserElementChoice(ParserElement parent, ParserElement[] children) { super(parent); this.children = children; currentSelection = 0; }
	public ParserElementChoice(ParserElement parent, String productionName, ParserElement[] children) { super(parent, productionName); this.children = children; currentSelection = 0; }

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
	) throws ParserException, IOException {
		// EOF case ...
		if((nextCluster == null) && (currentSelection == (children.length-1))) {
			if(parentElement != null) { parentElement.removeChild(this); }

			try { if(!parser.currentElement.empty()) {
				preproc.setMode(Preprocessor.PROPERTY_WHITESPACE, parser.currentElement.peek().whitespaceMode); }
			} catch(ParserPreprocessorException e) {
				e.printStackTrace();
			}

			return ParserElement.STATUS_FAILED;
		}

		if(lastStepUp) {
			/*
				Step up:
					If our child succeeded -> Accept and step up too
					If our child failed -> Try next or fail if it has been the last one
			*/
			switch(lastResult) {
				case STATUS_OK_REDELIVER:
				case STATUS_OK_CONSUME:
					/* The child succeeded, so we accept it */
					parser.popCurrentElement(); // We are finished - remove ourselves from the context stack and redeliver the CURRENT element (not the one consumed)

					try { if(!parser.currentElement.empty()) { preproc.setMode(Preprocessor.PROPERTY_WHITESPACE, parser.currentElement.peek().whitespaceMode); } } catch(ParserPreprocessorException e) { e.printStackTrace(); }
					return STATUS_OK_REDELIVER;

				case STATUS_FAILED:
					if(preproc instanceof ParserDataSource) {
						((ParserDataSource)preproc).seek(mark);
					} else {
						throw new ParserException("Preprocessor has to extend ParserDataSource");
					}
					/* Child failed, so we try the next one - this is the "backtracking" step for the recursive descent parser with backtracking */
					currentSelection++;
					if(currentSelection < children.length) {
						// Recurse downwards ...
						ParserElement newChild = children[currentSelection].factory(this); // Create our new child
						parser.pushCurrentElement(newChild); // Push onto context stack
						addChild(newChild);
						try { preproc.setMode(Preprocessor.PROPERTY_WHITESPACE, newChild.whitespaceMode); } catch(ParserPreprocessorException e) { e.printStackTrace(); }
						// return ParserElement.STATUS_OK_CONSUME; // Because seek set moves back we dont have to redeliver
						return ParserElement.STATUS_OK_CONSUME; // Because seek set moves back we dont have to redeliver
					}
					/* None of our children was able to parse the input data ... */
					parser.popCurrentElement(); // We are finished - remove ourselves from the context stack and redeliver the CURRENT element (not the one consumed).
					if(parentElement != null) { parentElement.removeChild(this); }
					return STATUS_FAILED;

				default:
					throw new ParserException("Last result "+lastResult+" is not possible for this node. Implementation error.");
			}
		} else {
			/*
				Step down:
					The only step down should be branch (or one of the "OK" status because this may happen on retry ...)
			*/
			switch(lastResult) {
				case STATUS_BRANCH_DOWN:
				case STATUS_OK_REDELIVER:
				case STATUS_OK_CONSUME:
					currentSelection = 0;
					ParserElement newChild = children[0].factory(this); // Create our first child
					parser.pushCurrentElement(newChild); // Push onto context stack
					addChild(newChild); // Think this MAY be the correct child (it will remove itself if it doesn't work)
					if(preproc instanceof ParserDataSource) {
						mark = ((ParserDataSource)preproc).mark();
					} else {
						throw new ParserException("Preprocessor has to extend ParserDataSource");
					}
					try { preproc.setMode(Preprocessor.PROPERTY_WHITESPACE, newChild.whitespaceMode); } catch(ParserPreprocessorException e) { e.printStackTrace(); }
					return ParserElement.STATUS_OK_REDELIVER; // We have created a new node so she has to receive the event we received (not the one consumed) again ...
				default:	// Cannot happen with this node ...
					throw new ParserException("Last result "+lastResult+" is not possible for this node. Implementation error.");
			}
		}
	}
}
