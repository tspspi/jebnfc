package at.tspi.ebnf.parser;

import java.io.IOException;

abstract public class ParserElementEmpty extends ParserElement {
	public ParserElementEmpty(ParserElement parent) { super(parent); }
	public ParserElementEmpty(ParserElement parent, String productionName) { super(parent, productionName);  }

	@Override
	public int parseNext(ParserGraphemeCluster nextCluster,
			String dataSourceName, int lineNumber, int characterPosition,
			Parser parser, Preprocessor preproc, ParserLogger logger,
			boolean lastStepUp, int lastResult
	) throws ParserException, IOException {
		if(lastStepUp) {
			// This CANNOT happen
			throw new ParserException("Stepping UP into an empty element cannot happen. Implementation error.");
		}
		
		if(nextCluster == null) {
			return ParserElement.STATUS_OK_REDELIVER;
		}

		// We accept ANY symbol and redeliver it to the next element ... because we are empty
		parser.popCurrentElement();
		return ParserElement.STATUS_OK_REDELIVER;
	}
}
