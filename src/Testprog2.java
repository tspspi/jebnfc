import at.tspi.ebnf.parser.Parser;

import java.util.HashMap;

import java.lang.IllegalArgumentException;

import java.io.File;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;

import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;

import java.nio.charset.Charset;

import at.tspi.ebnf.parser.Preprocessor;
import at.tspi.ebnf.parser.ParserPreprocessorException;
import at.tspi.ebnf.parser.ParserDataSource;
import at.tspi.ebnf.parser.ParserException;
import at.tspi.ebnf.parser.ParserLogger;
import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementTerminal;
import at.tspi.ebnf.parser.ParserElementSequential;
import at.tspi.ebnf.parser.ParserElementChoice;
import at.tspi.ebnf.parser.ParserElementConstant;
import at.tspi.ebnf.parser.ParserGraphemeCluster;

import at.tspi.ebnf.ebnfparser.EbnfParser;

import at.tspi.ebnf.parser.ASCIIFileSource;
import at.tspi.ebnf.parser.PreprocessorSimpleWhitespace;

import at.tspi.ebnf.compiler.ASTBuilder;
import at.tspi.ebnf.compiler.ASTBuilderISO14977;
import at.tspi.ebnf.compiler.ast.ASTNode;

import at.tspi.ebnf.compiler.ASTOperation;
import at.tspi.ebnf.compiler.CompilerState;
import at.tspi.ebnf.compiler.operations.ASTReduceSingleChoiceAndSequence;
import at.tspi.ebnf.compiler.operations.ASTIndexProductions;
import at.tspi.ebnf.compiler.operations.ASTCheckProductionReferences;
import at.tspi.ebnf.compiler.operations.ASTCollectSingleTerminalChoices;
import at.tspi.ebnf.compiler.operations.ASTReduceLiteralOnlyProductions;
import at.tspi.ebnf.compiler.operations.ASTReduceLiteralExceptions;
import at.tspi.ebnf.compiler.operations.ASTReplaceEmptyStrings;

public class Testprog2 {
	private void displayRecursiveTree(ParserElement e, int depth) {
		System.out.println(e.toString(depth));
		for(ParserElement e2 : e) {
			displayRecursiveTree(e2, depth+1);
		}
	}

	private void objMain(String args[]) {
		CompilerState state = new CompilerState();

		ASCIIFileSource ds = null;
		try {
			ds = new ASCIIFileSource("testinput.txt");
		} catch(FileNotFoundException e) {
			System.err.println("File not found");
			return;
		}
		PreprocessorSimpleWhitespace preproc = new PreprocessorSimpleWhitespace(ds);

		EbnfParser parser = new EbnfParser();

		ParserElement parserRootElement = null;
		try {
			parserRootElement = parser.parse(preproc, null);
		} catch(Exception e) {
			e.printStackTrace();
			return;
		}
		displayRecursiveTree(parserRootElement, 0);

		ASTBuilder builder = new ASTBuilderISO14977();

		// state.keepProduction( new String[] { "repetitionsymbol", "exceptsymbol", "concatenatesymbol", "definitionseparatorsymbol", "definingsymbol", "terminatorsymbol", "firstquotesymbol", "secondquotesymbol", "startgroupsymbol", "endgroupsymbol", "startoptionsymbol", "endoptionsymbol", "startrepeatsymbol", "endrepeatsymbol", "specialsequencesymbol", "integer", "optionalsequence", "repeatedsequence", "groupedsequence", "metaidentifier", "terminalstring", "specialsequence" } );
		state.keepProduction( new String[] { "integer", "optionalsequence", "repeatedsequence", "groupedsequence", "metaidentifier", "terminalstring", "specialsequence" } );

		ASTNode astRootElement = builder.build(parserRootElement);
		//System.out.println(builder.debugPrintAST(astRootElement));
		state.astRootSet(astRootElement);

		ASTOperation op = null;
		/*
			Perform optimization steps
		*/
		System.out.print("Executing compactification step: Eliminating empty sequences and choices ... ");
		op = new ASTReduceSingleChoiceAndSequence(); op.execute(state);
		if(state.hasErrors()) { System.out.println("\n\nErrors:"); for(String err : state.getErrors()) { System.out.println(err); } }
		if(state.hasWarnings()) { System.out.println("\n\nWarnings:"); for(String err : state.getWarnings()) { System.out.println(err); } }
		if(state.hasErrors() || state.hasWarnings()) { return; }
		System.out.println("done");

		{
			boolean doneReduction = false;
			while(!doneReduction) {
				System.out.print("Reducing string literals ... ");
				op = new ASTCollectSingleTerminalChoices(); doneReduction = !op.execute(state);
				if(state.hasErrors()) { System.out.println("\n\nErrors:"); for(String err : state.getErrors()) { System.out.println(err); } } if(state.hasWarnings()) { System.out.println("\n\nWarnings:"); for(String err : state.getWarnings()) { System.out.println(err); } } if(state.hasErrors() || state.hasWarnings()) { return; }
				System.out.println("done");

				System.out.print("Executing compactification step: Eliminating empty sequences and choices ... ");
				op = new ASTReduceSingleChoiceAndSequence(); doneReduction = doneReduction & (!op.execute(state));
				if(state.hasErrors()) { System.out.println("\n\nErrors:"); for(String err : state.getErrors()) { System.out.println(err); } } if(state.hasWarnings()) { System.out.println("\n\nWarnings:"); for(String err : state.getWarnings()) { System.out.println(err); } } if(state.hasErrors() || state.hasWarnings()) { return; }
				System.out.println("done");

				System.out.print("Executing compactification step: Replacing exclusivly literal productions ... ");
				op = new ASTReduceLiteralOnlyProductions(); doneReduction = doneReduction & (!op.execute(state));
				if(state.hasErrors()) { System.out.println("\n\nErrors:"); for(String err : state.getErrors()) { System.out.println(err); } } if(state.hasWarnings()) { System.out.println("\n\nWarnings:"); for(String err : state.getWarnings()) { System.out.println(err); } } if(state.hasErrors() || state.hasWarnings()) { return; }
				System.out.println("done");

				System.out.print("Executing compactification step: Reducing exclusions ... ");
				op = new ASTReduceLiteralExceptions(); doneReduction = doneReduction & (!op.execute(state));
				if(state.hasErrors()) { System.out.println("\n\nErrors:"); for(String err : state.getErrors()) { System.out.println(err); } } if(state.hasWarnings()) { System.out.println("\n\nWarnings:"); for(String err : state.getWarnings()) { System.out.println(err); } } if(state.hasErrors() || state.hasWarnings()) { return; }
				System.out.println("done");

				System.out.print("Executing compactification step: Reducing exclusions ... ");
				op = new ASTReplaceEmptyStrings(); doneReduction = doneReduction & (!op.execute(state));
				if(state.hasErrors()) { System.out.println("\n\nErrors:"); for(String err : state.getErrors()) { System.out.println(err); } } if(state.hasWarnings()) { System.out.println("\n\nWarnings:"); for(String err : state.getWarnings()) { System.out.println(err); } } if(state.hasErrors() || state.hasWarnings()) { return; }
				System.out.println("done");
			}
		}

		/*
			Perform indexing steps
		*/
		System.out.print("Executing step: Creating production index ... ");
		op = new ASTIndexProductions(); op.execute(state);
		if(state.hasErrors()) { System.out.println("\n\nErrors:"); for(String err : state.getErrors()) { System.out.println(err); } }
		if(state.hasWarnings()) { System.out.println("\n\nWarnings:"); for(String err : state.getWarnings()) { System.out.println(err); } }
		if(state.hasErrors() || state.hasWarnings()) { return; }
		System.out.println("done");

		/*
			Sanity checking before further steps
		*/
		System.out.print("Executing step: Checking production references ...");
		op = new ASTCheckProductionReferences(); op.execute(state);
		if(state.hasErrors()) { System.out.println("\n\nErrors:"); for(String err : state.getErrors()) { System.out.println(err); } }
		if(state.hasWarnings()) { System.out.println("\n\nWarnings:"); for(String err : state.getWarnings()) { System.out.println(err); } }
		if(state.hasErrors() || state.hasWarnings()) { return; }
		System.out.println("done");		


		System.out.println(builder.debugPrintAST(astRootElement));
	}

	public static void main(String args[]) {
		Testprog2 tp = new Testprog2();
		tp.objMain(args);
	}
}