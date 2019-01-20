package at.tspi.ebnf.compiler;

public class ASTException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ASTException() { super(); }
	public ASTException(String cause) { super(cause); }
}