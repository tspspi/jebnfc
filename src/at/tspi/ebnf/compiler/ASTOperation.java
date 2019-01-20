package at.tspi.ebnf.compiler;

public interface ASTOperation {
	public boolean execute(CompilerState state) throws ASTException;
}
