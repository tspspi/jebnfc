package at.tspi.ebnf.compiler;

import at.tspi.ebnf.compiler.ast.ASTNode;
import at.tspi.ebnf.parser.ParserElement;

public interface ASTBuilder {
	public ASTNode build(ParserElement rootNode);
	public String debugPrintAST(ASTNode rootNode);
}