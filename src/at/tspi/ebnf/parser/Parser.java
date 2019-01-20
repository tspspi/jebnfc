package at.tspi.ebnf.parser;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Stack;

public abstract class Parser {
	protected ParserElement				rootElement;
	protected Stack<ParserElement>		currentElement;

	boolean								lastStepUp;
	int									lastNextResult;

	public Parser() {
		rootElement = null;
		currentElement = new Stack<ParserElement>();
		lastStepUp = false;
		lastNextResult = ParserElement.STATUS_BRANCH_DOWN;
		initializeRoot();
	}

	protected abstract void initializeRoot(); // Will be implemented by the subclasses and initialize the first root element (start symbol)

	public void pushCurrentElement(ParserElement elm) 	{ currentElement.push(elm); lastStepUp = false; }
	public ParserElement popCurrentElement() 			{ lastStepUp = true; return currentElement.pop(); }

	public ParserElement getRootElement()				{ return this.rootElement; }

	public void parseNext(
		ParserGraphemeCluster nextCluster,
		String dataSourceName,
		int lineNumber,
		int characterPosition,
		Preprocessor preproc,
		ParserLogger logger
	) throws ParserException, IOException {
		int resultCode;

		// Pass to next element ...
		for(;;) {
			if(currentElement.size() == 0) {
				if(lastNextResult == ParserElement.STATUS_FAILED) {
					throw new ParserException("Parsing error signalled");
				}
				if(nextCluster != null) {
					throw new ParserException("Unexpected symbol at "+lineNumber+":"+characterPosition + " (Character is "+nextCluster+")");
				}
				break;
			}

			ParserElement currentHead = currentElement.peek();
			resultCode = currentHead.parseNext(nextCluster, dataSourceName, lineNumber, characterPosition, this, preproc, logger, lastStepUp, lastNextResult);
			lastNextResult = resultCode;

			if(resultCode == ParserElement.STATUS_OK_CONSUME) {
				return;
			} else if(resultCode == ParserElement.STATUS_FAILED) {
				// We are going up ... continue ... like redeliver ...
			} else if(resultCode == ParserElement.STATUS_OK_REDELIVER) {
				// We continue in a new state (up or down) ... continue ...
			} else {
				throw new ParserException("Invalid parser state "+resultCode);
			}

			// We redeliver to our new head - this has been modified by our element visited in the last iteration.
		}
	}



	public ParserElement parse(Preprocessor preproc, ParserLogger logger) throws UnsupportedEncodingException, ParserException, IOException {
		Parser p = this;

		if(!(preproc instanceof ParserDataSource)) { throw new IllegalArgumentException("Preprocessor has to be a data source"); }
		ParserDataSource dsPreproc = (ParserDataSource)preproc;

		ParserGraphemeCluster c = null;
		while((c = dsPreproc.getNextCluster()) != null) {
			p.parseNext(c, dsPreproc.getDSName(), (int)dsPreproc.getLineNumber(), (int)dsPreproc.getCharIndex(), preproc, logger);
		}
		p.parseNext(null, dsPreproc.getDSName(), (int)dsPreproc.getLineNumber(), (int)dsPreproc.getCharIndex(), preproc, logger);

		return p.getRootElement();
	}
}
