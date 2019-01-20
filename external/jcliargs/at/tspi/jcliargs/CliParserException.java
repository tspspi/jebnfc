package at.tspi.jcliargs;

public class CliParserException extends Exception {
	public CliParserException() { super(); }
	public CliParserException(String message) { super(message); }
	public CliParserException(String message, Throwable cause) { super(message, cause); }
	public CliParserException(Throwable cause) { super(cause); }
}