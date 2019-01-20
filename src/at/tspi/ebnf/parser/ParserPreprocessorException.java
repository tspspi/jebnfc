package at.tspi.ebnf.parser;

public class ParserPreprocessorException extends Exception {
	private static final long serialVersionUID = 1L;

	public ParserPreprocessorException() { super(); }
	public ParserPreprocessorException(String message) { super(message); }
	public ParserPreprocessorException(String message, Throwable cause) { super(message, cause); }
}
