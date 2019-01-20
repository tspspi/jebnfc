package at.tspi.jcliargs;

public class CliParserAlreadyDefinedException extends Exception {
	protected String opt;

	public CliParserAlreadyDefinedException() { super(); }
	public CliParserAlreadyDefinedException(String message) { super(message); }
	public CliParserAlreadyDefinedException(String message, Throwable cause) { super(message, cause); }
	public CliParserAlreadyDefinedException(Throwable cause) { super(cause); }

	public CliParserAlreadyDefinedException optionWhichSet(String opt) { this.opt = opt; return this; }
	public String optionWhichGet() { return this.opt; }
}