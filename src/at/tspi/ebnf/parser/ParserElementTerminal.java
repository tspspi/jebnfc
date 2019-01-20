package at.tspi.ebnf.parser;

import java.util.HashMap;

import at.tspi.ebnf.parser.Preprocessor;
import at.tspi.ebnf.parser.Parser;
import at.tspi.ebnf.parser.ParserLogger;
import at.tspi.ebnf.parser.ParserElement;

abstract public class ParserElementTerminal extends ParserElement {
	String collectedTerminals;
	protected HashMap<String, Boolean> allowedGraphemeClusters = null;
	protected HashMap<String, Boolean> forbiddenGraphemeClusters = null;

	int minLength, maxLength;

	public ParserElementTerminal(ParserElement parent) { super(parent); collectedTerminals = "";  }
	public ParserElementTerminal(ParserElement parent, String productionName) { super(parent, productionName); collectedTerminals = ""; }

	public ParserElementTerminal(ParserElement parent, int minLength, int maxLength) { super(parent); collectedTerminals = ""; this.minLength = minLength; this.maxLength = maxLength; }
	public ParserElementTerminal(ParserElement parent, String productionName, int minLength, int maxLength) { super(parent, productionName); collectedTerminals = ""; this.minLength = minLength; this.maxLength = maxLength; }

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
		if(lastStepUp) {
			// This CANNOT happen
			throw new ParserException("Stepping UP into a terminal cannot happen. Implementation error.");
		}

		if(nextCluster == null) {
			parser.popCurrentElement();
			if(collectedTerminals.length() < minLength) {
				// parser.popCurrentElement(); // Is this doubled?? (ToDo)
				if(parentElement != null) { parentElement.removeChild(this); }
				return ParserElement.STATUS_FAILED;
			}
			return ParserElement.STATUS_OK_REDELIVER;
		}

		if(forbiddenGraphemeClusters != null) {
			if(forbiddenGraphemeClusters.containsKey(nextCluster.cluster)) {
				if(collectedTerminals.length() < minLength) {
					parser.popCurrentElement(); // We are finished - remove ourselves from the context stack and redeliver the CURRENT element (not the one consumed)
					if(parentElement != null) { parentElement.removeChild(this); }
					return ParserElement.STATUS_FAILED;
				}
				parser.popCurrentElement(); // We are finished - remove ourselves from the context stack and redeliver the CURRENT element (not the one consumed)
				return ParserElement.STATUS_OK_REDELIVER;
			}
		}

		if((allowedGraphemeClusters == null) || (allowedGraphemeClusters.containsKey(nextCluster.cluster))) {
			// Allowed cluster
			/* if((maxLength > 0) && (collectedTerminals.length() >= maxLength)) {
				parser.popCurrentElement(); // We are finished - remove ourselves from the context stack and redeliver the CURRENT element (not the one consumed)
				if(parentElement != null) { parentElement.removeChild(this); }
				return ParserElement.STATUS_FAILED;
			} */
			if((maxLength > 0) && (collectedTerminals.length() >= maxLength)) { // We are done with OUR symbols. The next one belongs to our parent
				parser.popCurrentElement(); // We are finished - remove ourselves from the context stack and redeliver the CURRENT element (not the one consumed)
				return ParserElement.STATUS_OK_REDELIVER;
			}
			collectedTerminals = collectedTerminals + nextCluster.cluster;
			return ParserElement.STATUS_OK_CONSUME;
		}
		// In case an unsupported grapheme cluster is found, simply go up inside the tree. No errors here ... maybe these chars belong to the next layer ...
		if(collectedTerminals.length() < minLength) {
			parser.popCurrentElement(); // We are finished - remove ourselves from the context stack and redeliver the CURRENT element (not the one consumed)
			if(parentElement != null) { parentElement.removeChild(this); }
			return ParserElement.STATUS_FAILED;
		}
		parser.popCurrentElement(); // We are finished - remove ourselves from the context stack and redeliver the CURRENT element (not the one consumed)
		return ParserElement.STATUS_OK_REDELIVER;
	}

	public String toString() { return super.toString() + " (" + collectedTerminals + ")"; }
	public String toString(int depth) { return super.toString(depth) + " (" + collectedTerminals + ")"; }

	public String innerText() { return collectedTerminals; }
}