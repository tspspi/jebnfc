package at.tspi.ebnf.parser;

import at.tspi.ebnf.parser.Preprocessor;
import at.tspi.ebnf.parser.Parser;
import at.tspi.ebnf.parser.ParserLogger;
import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserDataSource;

import java.io.IOException;

abstract public class ParserElementRepeatOptional extends ParserElement {
	protected ParserElement child;
	private int currentIteration;
	private ParserDataSource.Mark lastSuccessfulMark = null;
	private ParserDataSource.Mark firstMark = null;
	protected int min;
	protected int max;

	public ParserElementRepeatOptional(ParserElement parent, ParserElement child) { super(parent); this.child = child; currentIteration = 0; }
	public ParserElementRepeatOptional(ParserElement parent, String productionName, ParserElement child) { super(parent, productionName); this.child = child; currentIteration = 0; }
	
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
		// EOF case ... do nothing special. The children should handle this
		if(lastStepUp) {
			/*
				Step up:
					If our child succeeded -> Accept this one and try the next one
					If our child failed -> If it has been the first one fail, if it's been another one revert and succeed
			*/
			switch(lastResult) {
				case STATUS_OK_REDELIVER:
				case STATUS_OK_CONSUME:
					/* The child succeeded, so we accept it, create a new mark in case we recurse again */
					currentIteration++;
					if(currentIteration == max) {
						// Done ...
						parser.popCurrentElement(); // We are finished - remove ourselves from the context stack and redeliver the CURRENT element (not the one consumed)
						try { if(!parser.currentElement.empty()) { preproc.setMode(Preprocessor.PROPERTY_WHITESPACE, parser.currentElement.peek().whitespaceMode); } } catch(ParserPreprocessorException e) { e.printStackTrace(); }
						return STATUS_OK_REDELIVER; // We have to redeliver the NEW element
					}
					// recurse again
					if(preproc instanceof ParserDataSource) { lastSuccessfulMark = ((ParserDataSource)preproc).mark(); } else { throw new ParserException("Preprocessor has to extend ParserDataSource"); }
					ParserElement newChild = child.factory(this); // Create our new child
					parser.pushCurrentElement(newChild); // Push onto context stack
					addChild(newChild);
					try { preproc.setMode(Preprocessor.PROPERTY_WHITESPACE, newChild.whitespaceMode); } catch(ParserPreprocessorException e) { e.printStackTrace(); }
					return STATUS_OK_REDELIVER; // We have to redeliver the NEW element ...

				case STATUS_FAILED:
					if((currentIteration < min) || (lastSuccessfulMark == null)) {
						// We fail completely
						if(preproc instanceof ParserDataSource) { ((ParserDataSource)preproc).seek(firstMark); } else { throw new ParserException("Preprocessor has to extend ParserDataSource"); }

						parser.popCurrentElement(); // We are finished - remove ourselves from the context stack and redeliver the CURRENT element (not the one consumed)
						if(parentElement != null) { parentElement.removeChild(this); }
						if(min == 0) {
							return STATUS_OK_CONSUME;
						} else {
							return STATUS_FAILED;
						}
					}

					if(preproc instanceof ParserDataSource) { ((ParserDataSource)preproc).seek(lastSuccessfulMark); } else { throw new ParserException("Preprocessor has to extend ParserDataSource"); }
					parser.popCurrentElement(); // We are finished - remove ourselves from the context stack and redeliver the CURRENT element (not the one consumed)
					return STATUS_OK_CONSUME;
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
				case STATUS_CONTINUE:
				case STATUS_OK_REDELIVER:
				case STATUS_OK_CONSUME:
					currentIteration = 0;
					ParserElement newChild = child.factory(this); // Create our first child
					parser.pushCurrentElement(newChild); // Push onto context stack
					addChild(newChild); // Think this MAY be the correct child (it will remove itself if it doesn't work)
					if(preproc instanceof ParserDataSource) { firstMark = ((ParserDataSource)preproc).mark(); } else { throw new ParserException("Preprocessor has to extend ParserDataSource"); }
					try { preproc.setMode(Preprocessor.PROPERTY_WHITESPACE, newChild.whitespaceMode); } catch(ParserPreprocessorException e) { e.printStackTrace(); }
					return ParserElement.STATUS_OK_REDELIVER; // We have created a new node so she has to receive the event we received (not the one consumed) again ...
				default:	// Cannot happen with this node ...
					throw new ParserException("Last result "+lastResult+" is not possible for this node. Implementation error.");
			}
		}
	}
}
