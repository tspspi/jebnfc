package at.tspi.ebnf.compiler.codegen;

import java.io.IOException;

import at.tspi.ebnf.compiler.CompilerState;

public interface ParserCodeGenerator {
	public boolean generateCode(CompilerState state) throws IOException;
}
