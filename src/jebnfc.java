import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

import at.tspi.ebnf.compiler.ASTBuilder;
import at.tspi.ebnf.compiler.ASTBuilderISO14977;
import at.tspi.ebnf.compiler.ASTBuilderISO14977Gen;
import at.tspi.ebnf.compiler.ASTCombiner;
import at.tspi.ebnf.compiler.ASTOperation;
import at.tspi.ebnf.compiler.CompilerState;
import at.tspi.ebnf.compiler.KeepfileReader;
import at.tspi.ebnf.compiler.ast.ASTChoice;
import at.tspi.ebnf.compiler.ast.ASTNode;
import at.tspi.ebnf.compiler.ast.ASTOptional;
import at.tspi.ebnf.compiler.ast.ASTProduction;
import at.tspi.ebnf.compiler.ast.ASTProductionReference;
import at.tspi.ebnf.compiler.ast.ASTRoot;
import at.tspi.ebnf.compiler.ast.ASTSequential;
import at.tspi.ebnf.compiler.codegen.ParserCodeGenerator;
import at.tspi.ebnf.compiler.codegen.javasimpledescent.CodeGenJavaSimpleRecursiveDescent;
import at.tspi.ebnf.compiler.operations.ASTCheckProductionReferences;
import at.tspi.ebnf.compiler.operations.ASTCollectSingleTerminalChoices;
import at.tspi.ebnf.compiler.operations.ASTIndexProductions;
import at.tspi.ebnf.compiler.operations.ASTReduceLiteralExceptions;
import at.tspi.ebnf.compiler.operations.ASTReduceLiteralOnlyProductions;
import at.tspi.ebnf.compiler.operations.ASTReduceSingleChoiceAndSequence;
import at.tspi.ebnf.compiler.operations.ASTReplaceEmptyStrings;
import at.tspi.ebnf.ebnfparser.EbnfParser;
import at.tspi.ebnf.parser.ASCIIFileSource;
import at.tspi.ebnf.parser.Parser;
import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserException;
import at.tspi.ebnf.parser.Preprocessor;
import at.tspi.ebnf.parser.PreprocessorSimpleWhitespace;
import at.tspi.jcliargs.CliOptionBoolean;
import at.tspi.jcliargs.CliOptionString;
import at.tspi.jcliargs.CliParser;
import at.tspi.jcliargs.CliParserAlreadyDefinedException;
import at.tspi.jcliargs.CliParserException;
import at.tspi.jcliargs.CliParserUnknownOptionException;

public class jebnfc {
	private class EbnfCompilerConfiguration {
		public File										fileSource = null;
		public LinkedList<File>							filesKeep = new LinkedList<File>();
		public LinkedList<File>							filesAlias = new LinkedList<File>();
		public LinkedList<File>							filesInclude = new LinkedList<File>();

		public HashMap<String, LinkedList<String>>		hmAlias = new HashMap<String, LinkedList<String>>();
		public LinkedList<String>						lstKeep = new LinkedList<String>();

		public String									defaultPreproc;
		public String									defaultParser;

		public PreprocessorFactory						factoryPreproc;
		public ParserFactory							factoryParser;

		public boolean									bVerbose;
		public boolean									bPedantic;
		public boolean									bNoOpt;

		public String									outBasename;
		public String									outPackage;
		public String									outPrefix;
	}
	private static final String VERSIONSTRING = "JEBNFC EBNF Compiler, Version 0.0.1";
	private static final String defaultPreprocessor = "iso14977";
	private static final String defaultParser = "iso14977";

	private interface PreprocessorFactory { public Preprocessor createPreproc(); }
	private static class PreprocessorFactory_Bootstrap implements PreprocessorFactory { public Preprocessor createPreproc() { return new PreprocessorSimpleWhitespace(); } }
	private static class PreprocessorFactory_IsoEbnf implements PreprocessorFactory { public Preprocessor createPreproc() { return new PreprocessorSimpleWhitespace(); } }
	
	private interface ParserFactory { public Parser createParser(); public ASTBuilder createASTBuilder(); }
	private static class ParserFactory_Bootstrap implements ParserFactory { public Parser createParser() { return new EbnfParser(); } public ASTBuilder createASTBuilder() { return new ASTBuilderISO14977(); } }
	private static class ParserFactory_IsoEbnf implements ParserFactory { public Parser createParser() { return new at.tspi.ebnf.genisoebnfparser.EbnfElement_Parser(); } public ASTBuilder createASTBuilder() { return new ASTBuilderISO14977Gen(); } }

	private interface CodegeneratorFactory { public ParserCodeGenerator createCodegen(); }
	private static class CodegeneratorFactory_CodeGenJavaSimpleRecursiveDescent implements CodegeneratorFactory { public ParserCodeGenerator createCodegen() { return new CodeGenJavaSimpleRecursiveDescent(); } }

	private static HashMap<String, PreprocessorFactory> 		preprocessorsAvailable;
	private static HashMap<String, ParserFactory>				parsersAvailable;
	private static HashMap<String, CodegeneratorFactory>		codegenAvailable;

	static {
		preprocessorsAvailable = new HashMap<String, PreprocessorFactory>();
		preprocessorsAvailable.put("iso14977", new PreprocessorFactory_IsoEbnf());
		preprocessorsAvailable.put("bootstrap", new PreprocessorFactory_Bootstrap());

		parsersAvailable = new HashMap<String, ParserFactory>();
		parsersAvailable.put("iso14977", new ParserFactory_IsoEbnf());
		parsersAvailable.put("bootstrap", new ParserFactory_Bootstrap());

		codegenAvailable = new HashMap<String, CodegeneratorFactory>();
		codegenAvailable.put("javasimplerd", new CodegeneratorFactory_CodeGenJavaSimpleRecursiveDescent());
	}
	

	private EbnfCompilerConfiguration cfg = new EbnfCompilerConfiguration();

	private void printUsage() {
		System.out.println("Usage: jebnfc [options] filename1.ebnf\n");
		System.out.println("Valid options:");
		System.out.println("\t--keep [filename], -k [filename]");
		System.out.println("\t\tFilename containing a simple list of productions which should never");
		System.out.println("\t\tbe reduced during optimization. One production name per line.");
		System.out.println("\t--alias [filename], -a [filename]");
		System.out.println("\t\tFilename containing aliases. This can be used in case grammar is");
		System.out.println("\t\tambiguous if ambigious situations will be resolved by a higher level");
		System.out.println("\t\tcompiler or parser.");
		System.out.println("\t--include [filename], -i [filename]");
		System.out.println("\t\tSpecifies EBNF files that will be included. All productions from included");
		System.out.println("\t\tfiles that will not be used by the main source will be removed from the grammar");
		System.out.println("");
		System.out.println("\t--noopt, -x");
		System.out.println("\t\tDisable optimizations on raw data. This is mostly useful when generating");
		System.out.println("\t\tEBNF output or railroad diagrams out of EBNF");
		System.out.println("");
		System.out.println("\t--preproc [preprocname], -r [preprocname]");
		System.out.println("\t\tSelect a specific preprocessor:");
		System.out.println("\t\tbootstrap    Bootstrap preprocessor (ISO14977)");
		System.out.println("\t--parser [parsername], -p [parsername]");
		System.out.println("\t\tbootstrap    Bootstrap parser (ISO14977)");
		System.out.println("");
		System.out.println("\t--out [path], -o [path]");
		System.out.println("\t\tSpecifies the output base path. For most output modules this option is required");
		System.out.println("\t--package [pkgname], -u [pkgname]");
		System.out.println("\t\tSpecifies the output package or unit if applicable.");
		System.out.println("\t\tThe format is output module specific.");
		System.out.println("\t\t\tFor JAVA this is the package name");
		System.out.println("\t--prefix [prefix], -f [prefix]");
		System.out.println("");
		System.out.println("\t--help, -h");
		System.out.println("\t\tPrint this usage information");
		System.out.println("\t--version, -v");
		System.out.println("\t\tDisplay current version information");
		System.out.println("\t--verbose, -d");
		System.out.println("\t\tPrint verbose information about compilation stages");
	}
	private boolean cmdLineParse(String args[]) {
		CliParser cp = new CliParser();

		cp.optionRegister(new CliOptionString("k", "keep"));				/* File reference for "keeps" */
		cp.optionRegister(new CliOptionString("a", "alias"));				/* Alias reference */
		cp.optionRegister(new CliOptionString("i", "include", 0, -1));		/* Optional unlimited number of includes */
		cp.optionRegister(new CliOptionBoolean("v", "version"));			/* Optional unlimited number of includes */
		cp.optionRegister(new CliOptionBoolean("h", "help"));				/* Optional unlimited number of includes */
		cp.optionRegister(new CliOptionString("p", "parser", 0, 1));		/* Optionally specify a parser (if none is specified the default one is used) */
		cp.optionRegister(new CliOptionString("r", "preproc", 0, 1));		/* Optionally specify a preprocessor (if none is specified the default one is used) */
		cp.optionRegister(new CliOptionBoolean("d", "verbose"));			/* Setting verbose mode */
		cp.optionRegister(new CliOptionString("u", "package", 0, 1));		/* Output package or unit */
		cp.optionRegister(new CliOptionString("o", "out", 0, 1));			/* Output basedirectory */
		cp.optionRegister(new CliOptionString("f", "prefix", 0, 1));		/* Optional prefix for files, classes, variables, etc. */
		cp.optionRegister(new CliOptionBoolean("x", "noopt")); 				/* Optional: Disable all optimizations */

		try {
			cp.parse(args);
		} catch(CliParserUnknownOptionException e) {
			System.err.println(e.toString());
			return false;
		} catch(CliParserAlreadyDefinedException e) {
			System.err.println(e.toString());
			return false;
		} catch(CliParserException e) {
			e.printStackTrace();
			return false;
		}

		if(cp.getAdditional().size() != 1) {
			printUsage();
			return false;
		}

		try {
			if(cp.getBoolean("version")) 	{ System.out.println(VERSIONSTRING); System.exit(0); }
			if(cp.getBoolean("help")) 		{ printUsage(); System.exit(0); }
			if(cp.getBoolean("verbose"))	{ cfg.bVerbose = true; } else { cfg.bVerbose = false; }
			cfg.bPedantic = false;

			cfg.defaultPreproc = cp.getString("preproc", 		defaultPreprocessor);
			cfg.defaultParser = cp.getString("parser", 			defaultParser);

			cfg.outBasename = cp.getString("out", null);
			cfg.outPackage = cp.getString("package", null);
			cfg.outPrefix = cp.getString("prefix", null);
			cfg.bNoOpt = cp.getBoolean("noopt");

			/* Check if parser and preproc are known */
			if(!preprocessorsAvailable.containsKey(cfg.defaultPreproc)) 	{ System.err.println("Unknown preprocessor "+cfg.defaultPreproc); return false; }
			if(!parsersAvailable.containsKey(cfg.defaultParser)) 			{ System.err.println("Unknown parser "+cfg.defaultParser); return false; }
			cfg.factoryPreproc = preprocessorsAvailable.get(cfg.defaultPreproc);
			cfg.factoryParser = parsersAvailable.get(cfg.defaultParser);

			/* Now fill internal configuration structures */
			cfg.fileSource = new File(cp.getAdditional().get(0));
			if(!cfg.fileSource.exists()) { System.err.println("Sourcefile "+cfg.fileSource.getName()+" not found"); return false; }

			for(int idx = 0; idx < cp.getStringCount("keep"); idx++) {
				File f = new File(cp.getString("keep", idx));
				if(!f.exists()) { System.err.println("Keepfile "+f.getName()+" not found"); return false; }
				cfg.filesKeep.push(f);
			}

			for(int idx = 0; idx < cp.getStringCount("alias"); idx++) {
				File f = new File(cp.getString("alias", idx));
				if(!f.exists()) { System.err.println("Aliasfile "+f.getName()+" not found"); return false; }
				cfg.filesAlias.push(f);
			}

			for(int idx = 0; idx < cp.getStringCount("include"); idx++) {
				File f = new File(cp.getString("include", idx));
				if(!f.exists()) { System.err.println("Includefile "+f.getName()+" not found"); return false; }
				cfg.filesInclude.push(f);
			}
		} catch(CliParserUnknownOptionException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	private ASTNode loadAST(File filename) {
		/* Create file source */
		ASCIIFileSource ds = null;
		try {
			ds = new ASCIIFileSource(filename.getAbsolutePath());
		} catch(FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}

		/* Create preprocessor */
		Preprocessor preproc = cfg.factoryPreproc.createPreproc();
		preproc.setParserDataSource(ds);

		/* Create and initialize parser */
		Parser p = cfg.factoryParser.createParser();
		ASTBuilder astb = cfg.factoryParser.createASTBuilder();

		ParserElement rootNode = null;
		try {
			rootNode = p.parse(preproc, null);
		} catch(IOException e) {
			e.printStackTrace();
			return null;
		} catch(ParserException e) {
			e.printStackTrace();
			return null;
		}

		ASTNode astRootNode = astb.build(rootNode);
		return astRootNode;
	}

	private boolean checkLeftRecursive(ASTProduction prod, CompilerState state) {
		/*
			Iterate over children. On choices take everything, on sequences only the first element
			On each production reference check inside the stack if we have reached a left recursive stage.
			If we have encountered at least one, we terminate 
		*/

return false; /* @ToDo */

	}

	public void objMain(String args[]) {
		CompilerState state = new CompilerState();

		/* Read configuration(s) from commandline and property file(s) */
		if(!cmdLineParse(args)) { System.exit(-1); }

		/*
			Push some configurations right into the state after validation
		 */

		state.setOutBasedir(cfg.outBasename);
		state.setOutPackage(cfg.outPackage);
		state.setOutPrefix(cfg.outPrefix);
		state.setVerbose(cfg.bVerbose);

		if(cfg.outBasename != null) {
			if(!(new File(cfg.outBasename)).exists()) {
				System.err.println("Path "+cfg.outBasename+" does not exist");
				System.exit(-1);
			}
		}

		/*
			Read keep and alias files @ToDo
		*/
		for(File fKeep : cfg.filesKeep) {
			try {
				KeepfileReader.keepfileRead(fKeep, state);
			} catch(FileNotFoundException e) {
				System.err.println("Keepfile "+fKeep.getAbsolutePath()+" not found");
				System.exit(-1);
			} catch(IOException e) {
				e.printStackTrace();
				System.exit(-1);
			} catch(ParserException e) {
				e.printStackTrace();
				System.exit(-1);
			}
		}
/*
state.keepProduction("metaidentifier");
state.keepProduction("syntaxrule");
state.keepProduction("singledefinition");
state.keepProduction("definitionslist");
state.keepProduction("syntacticfactor");
state.keepProduction("integer");
state.keepProduction("optionalsequence");
state.keepProduction("repeatedsequence");
state.keepProduction("groupedsequence");
state.keepProduction("syntacticterm");
state.keepProduction("syntacticfactorrepeatedfactor");
state.keepProduction("syntacticexception");
state.keepProduction("specialsequencevalue");
state.whitespaceModeProductionSet("terminalstring", Preprocessor.VALUE_WHITESPACE__IGNORE);
*/
state.keepProduction("keepfilesyntax");
state.keepProduction("metaidentifier");
state.keepProduction("prodoption");
state.keepProduction("keep");
state.keepProduction("starter");
state.keepProduction("whitespacemode");
state.keepProduction("whitespacemodeignore");
state.keepProduction("whitespacemodestrip");
state.keepProduction("whitespacemodereduce");

		/*
			Run compiler steps:
				- Read all files (Source and Include)
		*/

		/* Parse our include files */
		ASTNode[] astIncludes = new ASTNode[cfg.filesInclude.size()];
		for(int idx = 0; idx < astIncludes.length; idx++) {
			astIncludes[idx] = loadAST(cfg.filesInclude.get(idx));
			if(astIncludes[idx] == null) {
				System.err.println("Failed to parse "+cfg.filesInclude.get(idx));
				System.exit(-1);
			}

			/*
				Mark all productions as "included productions" (no starter candidates)
				so they will be removed if they're not referenced
			*/
			for(ASTNode n : astIncludes[idx]) {
				if(n instanceof ASTProduction) {
					((ASTProduction)n).setStarterCandidate(false);
				}
			}
		}

		/* Parse our sourcefile */
		ASTNode rootSource = loadAST(cfg.fileSource);

		/*
			If we have explicit listing of starter productions now
			mark all productions that have no starter specification
			as non-starter candidates.
		*/
		if(state.starterSpecificationAvailable()) {
			for(ASTNode n : rootSource) {
				if(n instanceof ASTProduction) {
					if(state.starterSpecificationIsStarter(((ASTProduction)n).getName())) {
						((ASTProduction)n).setStarterCandidate(true);
					} else {
						((ASTProduction)n).setStarterCandidate(false);
					}
				}
			}
		}

		// Initialize state
		ASTOperation op = null;
		state.astRootSet(rootSource); /* @ToDo: Replace with combined root after processing includes */

		/*
			Build unified AST (include files and source file),
			eliminate unreferenced productions from include files
			@ToDo
		*/
		for(int idx = 0; idx < astIncludes.length; idx++) {
			ASTCombiner.combineASTs((ASTRoot)rootSource, (ASTRoot)(astIncludes[idx]), state);
		}

		/*
			Perform reductions
		*/

		// First initially remove empty sequences and choices
		{
			op = new ASTReduceSingleChoiceAndSequence(); if(cfg.bVerbose) { System.out.println("Executing compactification steps:\n\nEliminating empty sequences and choices ... "); }
			op.execute(state);
		}

		// Now repeat as long as we can do optimizations
		if(!cfg.bNoOpt) {
			boolean repeatedOptimizationsDone = false;
			do {
				repeatedOptimizationsDone = true;
	
				// Collect single characters into a compact string constant and remove choices
				op = new ASTCollectSingleTerminalChoices(); if(cfg.bVerbose) { System.out.println("Collecting single literals into strings ... "); }
				repeatedOptimizationsDone &= !op.execute(state);
				if((state.hasErrors()) || (state.hasWarnings() && cfg.bPedantic)) { break; }
	
				// Reduce choice and sequence with single children
				op = new ASTReduceSingleChoiceAndSequence(); if(cfg.bVerbose) { System.out.println("Eliminating empty sequences and choices ... "); }
				repeatedOptimizationsDone &= !op.execute(state);
				if((state.hasErrors()) || (state.hasWarnings() && cfg.bPedantic)) { break; }
	
				// Replace literal only productions
				op = new ASTReduceLiteralOnlyProductions(); if(cfg.bVerbose) { System.out.println("Replacing exclusivly literal productions ... "); }
				repeatedOptimizationsDone &= !op.execute(state);
				if((state.hasErrors()) || (state.hasWarnings() && cfg.bPedantic)) { break; }
	
				// Reducing exclusions
				op = new ASTReduceLiteralExceptions(); if(cfg.bVerbose) { System.out.println("Reducing exclusions (literals) ... "); }
				repeatedOptimizationsDone &= !op.execute(state);
				if((state.hasErrors()) || (state.hasWarnings() && cfg.bPedantic)) { break; }
	
				op = new ASTReplaceEmptyStrings(); if(cfg.bVerbose) { System.out.println("Reducing empty strings (literals) ... "); }
				repeatedOptimizationsDone &= !op.execute(state);
				if((state.hasErrors()) || (state.hasWarnings() && cfg.bPedantic)) { break; }
	
				// Combine sequences of ASTSingleTerminalCollection(min:1,max:1), ASTSingleTerminalCollection(min:0,max: (-1:n))
	
				/*
					Propagate "empty" definitions:
						Choice OR empty -> put into optional node (repeat min:0,max:1)
						Sequence EMPTY -> remove empty from sequence ...
				 */
			} while(!repeatedOptimizationsDone);
		}

		// If any uncorrectable errors or warnings have occured, terminate now
		if((state.hasErrors()) || (state.hasWarnings() && cfg.bPedantic)) {
			// Abort and display warnings and errors
			if(state.hasErrors()) {
				System.out.println("\n\nErrors:");
				for(String err : state.getErrors()) { System.out.println(err); }
			}
			if(state.hasWarnings()) {
				System.out.println("\n\nWarnings:");
				for(String err : state.getWarnings()) { System.out.println(err); }
			}

			System.exit(-1);
		}

		// Index all productions ...
		op = new ASTIndexProductions(); if(cfg.bVerbose) { System.out.println("Creating production index ... "); }
		op.execute(state);
		if(state.hasErrors() || (state.hasWarnings() && cfg.bPedantic)) {
			if(state.hasErrors()) {
				System.out.println("\n\nErrors:");
				for(String err : state.getErrors()) { System.err.println(err); }
			}
			if(state.hasWarnings()) {
				System.out.println("\n\nWarnings:");
				for(String err : state.getWarnings()) { System.err.println(err); }
			}
			System.exit(-1);
		}

		// Check that each production reference can be satisfied. If not, abort compilation
		op = new ASTCheckProductionReferences(); if(cfg.bVerbose) { System.out.println("Checking production references ... "); }
		op.execute(state);
		if(state.hasErrors() || (state.hasWarnings() && cfg.bPedantic)) {
			if(state.hasErrors()) {
				System.out.println("\n\nErrors:");
				for(String err : state.getErrors()) { System.err.println(err); }
			}
			if(state.hasWarnings()) {
				System.out.println("\n\nWarnings:");
				for(String err : state.getWarnings()) { System.err.println(err); }
			}
			System.exit(-1);
		}

		// Remove all productions that are no starter candidates and that are NOT referenced. Create list of starters during the same iteration
		if(!cfg.bNoOpt) {
			if(cfg.bVerbose) { System.out.println("Removing unused non-starter productions ... "); }
			for(ASTProduction p : state.productionGetUnreferenced()) {
				if(!p.getStarterCandidate()) {
					state.astRootGet().childRemove(p);
					state.productionRemoveFromIndex(p);
					if(cfg.bVerbose) {
						System.out.println("Removing unused included non-starter candidate "+p.getName());
					}
				}
			}
		}

		/*
			Do left recursion checking & resolution from each starter ...
		*/
		{
			LinkedList<ASTProduction> starters = state.productionGetUnreferenced();
			for(ASTProduction p : starters) {
				if(checkLeftRecursive(p, state)) {
					// This production IS left recursive ...
				} else {
					// This production is NOT left recursive ...
				}
			}
		}

		/*
			Code generation stage
		*/
ASTBuilderISO14977 dbgb = new ASTBuilderISO14977();
// System.out.println(dbgb.debugPrintAST(rootSource));
System.out.println("ToDo ... CONTINUE!");

// CodeGenJavaSimpleRecursiveDescent testGen = new CodeGenJavaSimpleRecursiveDescent();
at.tspi.ebnf.compiler.codegen.railroadsvg.CodeGenRailroadSVG testGen = new at.tspi.ebnf.compiler.codegen.railroadsvg.CodeGenRailroadSVG();
try {
	testGen.generateCode(state);
} catch(Exception e) {
	e.printStackTrace();
}
System.out.println("Done generating code");

if(state.hasErrors() || (state.hasWarnings() && cfg.bPedantic)) {
	if(state.hasErrors()) {
		System.out.println("\n\nErrors:");
		for(String err : state.getErrors()) { System.err.println(err); }
	}
	if(state.hasWarnings()) {
		System.out.println("\n\nWarnings:");
		for(String err : state.getWarnings()) { System.err.println(err); }
	}
	System.exit(-1);
} else if(state.hasWarnings()) {
	if(state.hasWarnings()) {
		System.out.println("\n\nWarnings:");
		for(String err : state.getWarnings()) { System.err.println(err); }
	}
}

	}

	public static void main(String args[]) {
		jebnfc self = new jebnfc();
		self.objMain(args);
	}
}
