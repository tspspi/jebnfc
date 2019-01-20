package at.tspi.jcliargs;

public class CliParserUnknownOptionException extends Exception {
	public CliParserUnknownOptionException() { super(); }
	public CliParserUnknownOptionException(String message) { super(message); }
	public CliParserUnknownOptionException(String message, Throwable cause) { super(message, cause); }
	public CliParserUnknownOptionException(Throwable cause) { super(cause); }
}