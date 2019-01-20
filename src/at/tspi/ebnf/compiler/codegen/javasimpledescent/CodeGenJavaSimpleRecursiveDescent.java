package at.tspi.ebnf.compiler.codegen.javasimpledescent;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

import at.tspi.ebnf.compiler.CompilerState;
import at.tspi.ebnf.compiler.ast.ASTChoice;
import at.tspi.ebnf.compiler.ast.ASTEmpty;
import at.tspi.ebnf.compiler.ast.ASTNode;
import at.tspi.ebnf.compiler.ast.ASTOptional;
import at.tspi.ebnf.compiler.ast.ASTProduction;
import at.tspi.ebnf.compiler.ast.ASTProductionReference;
import at.tspi.ebnf.compiler.ast.ASTRepeat;
import at.tspi.ebnf.compiler.ast.ASTSequential;
import at.tspi.ebnf.compiler.ast.ASTSingleTerminal;
import at.tspi.ebnf.compiler.ast.ASTSingleTerminalCollection;
import at.tspi.ebnf.compiler.codegen.ParserCodeGenerator;
import at.tspi.ebnf.parser.Preprocessor;

public class CodeGenJavaSimpleRecursiveDescent implements ParserCodeGenerator {
	/*
		Choice Template:
			%PACKAGENAME%		replace by package name (including "package " prefix)
			%PREFIX%			Prefix including trailing "_"
			%PRODUCTIONNAME%	Name of production (possibly including autogen counter)
			%CHILDLISTNEW%		List of "new CHILDCLASS(null)" entries 
	*/
	private static final String tplChoice = 
			"%PACKAGENAME%;\n" +
			"\n" +
			"import at.tspi.ebnf.parser.ParserElement;\n" +
			"import at.tspi.ebnf.parser.ParserElementChoice;\n" +
			"import at.tspi.ebnf.parser.Preprocessor;\n" +
			"\n" +
			"public class %PREFIX%%PRODUCTIONNAME% extends ParserElementChoice {\n" +
			"\tprivate static final ParserElement[] sChildren = new ParserElement[] { %CHILDLISTNEW% };\n" +
			"\tpublic %PREFIX%%PRODUCTIONNAME%(ParserElement parent) { super(parent, \"%PRODUCTIONNAME%\", sChildren); %WHITESPACEMODE%}\n" +
			"\tpublic ParserElement factory(ParserElement parent) { return new %PREFIX%%PRODUCTIONNAME%(parent); }\n" +
			"\tpublic String toString() { return \"%PRODUCTIONNAME%\"; }\n" +
			"}\n";
	/*
		Sequence Template:
			%PACKAGENAME%		replace by package name (including "package " prefix)
			%PREFIX%			Prefix including trailing "_"
			%PRODUCTIONNAME%	Name of production (possibly including autogen counter)
			%CHILDLISTNEW%		List of "new CHILDCLASS(null)" entries
	*/
	private static final String tplSequence = "%PACKAGENAME%;\n" +
			"\n" +
			"import at.tspi.ebnf.parser.ParserElement;\n" +
			"import at.tspi.ebnf.parser.ParserElementSequential;\n" +
			"import at.tspi.ebnf.parser.Preprocessor;\n" +
			"\n" +
			"public class %PREFIX%%PRODUCTIONNAME% extends ParserElementSequential {\n" +
			"\tprivate static final ParserElement[] sChildren = new ParserElement[] { %CHILDLISTNEW% };\n" +
			"\tpublic %PREFIX%%PRODUCTIONNAME%(ParserElement parent) { super(parent, \"%PRODUCTIONNAME%\", sChildren); %WHITESPACEMODE%}\n" +
			"\tpublic ParserElement factory(ParserElement parent) { return new %PREFIX%%PRODUCTIONNAME%(parent); }\n" +
			"\tpublic String toString() { return \"%PRODUCTIONNAME%\"; }\n" +
			"}\n";
	/*
		Optional / Repeat Template:
			%PACKAGENAME%		replace by package name (including "package " prefix)
			%PREFIX%			Prefix including trailing "_"
			%PRODUCTIONNAME%	Name of production (possibly including autogen counter)
			%CHILDELEMENT%		The name of the only allowed child element
			%MINREPEAT%			Minimum repeat number (min. occurances)
			%MAXREPEAT%			Maximum repeat number (max. occurances; -1 if unbound)
	*/
	private static final String tplOptionRepeat = "%PACKAGENAME%;\n" +
			"\n" +
			"import at.tspi.ebnf.parser.ParserElement;\n" +
			"import at.tspi.ebnf.parser.ParserElementRepeatOptional;\n" +
			"import at.tspi.ebnf.parser.Preprocessor;\n" +
			"\n" +
			"public class %PREFIX%%PRODUCTIONNAME% extends ParserElementRepeatOptional {\n" +
			"\tprivate static final ParserElement sChild = new %CHILDELEMENT%(null);\n" +
			"\tpublic %PREFIX%%PRODUCTIONNAME%(ParserElement parent) { super(parent, null, sChild); this.min = %MINREPEAT%; this.max = %MAXREPEAT%; %WHITESPACEMODE%}\n" +
			"\tpublic ParserElement factory(ParserElement parent) { return new %PREFIX%%PRODUCTIONNAME%(parent); }\n" +
			"\tpublic String toString() { return \"%PRODUCTIONNAME%\"; }\n" +
			"}\n";
	/*
		Optional / Repeat Template:
			%PACKAGENAME%		replace by package name (including "package " prefix)
			%PREFIX%			Prefix including trailing "_"
			%PRODUCTIONNAME%	Name of production (possibly including autogen counter)
			%SYMBOLSTRING%		The string including the constant INCLUDING the quotes (as well as escapes)
	 */
	private static final String tplConstant = "%PACKAGENAME%;\n" + 
			"\n" + 
			"import at.tspi.ebnf.parser.ParserElement;\n" + 
			"import at.tspi.ebnf.parser.ParserElementConstant;\n" +
			"import at.tspi.ebnf.parser.Preprocessor;\n" +
			"\n" + 
			"public class %PREFIX%%PRODUCTIONNAME% extends ParserElementConstant {\n" + 
			"\tpublic %PREFIX%%PRODUCTIONNAME%(ParserElement parent) { super(parent, %SYMBOLSTRING%, \"%PRODUCTIONNAME%\", false); %WHITESPACEMODE%}\n" + 
			"\tpublic ParserElement factory(ParserElement parent) { return new %PREFIX%%PRODUCTIONNAME%(parent); }\n" + 
			"\tpublic String toString() { return %SYMBOLSTRING%; }\n" + 
			"}\n";
	private static final String tplTerminals = "%PACKAGENAME%;\n" +
			"\n" +
			"import java.util.HashMap;\n" +
			"\n" +
			"import at.tspi.ebnf.parser.ParserElement;\n" +
			"import at.tspi.ebnf.parser.ParserElementTerminal;\n" +
			"import at.tspi.ebnf.parser.Preprocessor;\n" +
			"\n" +
			"public class %PREFIX%%PRODUCTIONNAME% extends ParserElementTerminal {\n" +
			"\tpublic %PREFIX%%PRODUCTIONNAME% (ParserElement parent) {\n" +
			"\t\tsuper(parent, \"%PRODUCTIONNAME%\", %MINREPEAT%, %MAXREPEAT%);\n" +
			"%ALLOWCODE%\n" +
			"%WHITESPACEMODE%" +
			"\t}\n" +
			"\tpublic ParserElement factory(ParserElement parent) { return new %PREFIX%%PRODUCTIONNAME%(parent); }\n" +
			"\tpublic String toString() { return \"%PRODUCTIONNAME%\"; }\n" +
			"}\n";
	private static final String tplEmpty = "%PACKAGENAME%;\n" +
			"\n" +
			"import at.tspi.ebnf.parser.ParserElement;\n" +
			"import at.tspi.ebnf.parser.ParserElementEmpty;\n" +
			"\n" +
			"public class %PREFIX%%PRODUCTIONNAME% extends ParserElementEmpty {\n" +
			"\tpublic %PREFIX%%PRODUCTIONNAME% (ParserElement parent) { super(parent, \"%PRODUCTIONNAME%\"); }\n" +
			"\tpublic ParserElement factory(ParserElement parent) { return new %PREFIX%%PRODUCTIONNAME%(parent); }\n" +
			"}\n";
	private static final String tplParserMain = "%PACKAGENAME%;\n" +
			"\n" +
			"import at.tspi.ebnf.parser.Parser;\n" +
			"\n" +
			"public class %PARSERCLASSNAME% extends Parser {\n" +
			"\tpublic %PARSERCLASSNAME%() { super(); }\n" +
			"\n" +
			"\tprotected void initializeRoot() {\n" +
			"\t\trootElement = new %ROOTELEMENTCLASS%(null);\n" +
			"\t\tcurrentElement.push(rootElement);\n" +
			"\t}\n" +
			"}\n";

	private long autogenIndex = 0; // Used for automatically generated classnames
	private String classTargetDir = null;
	private String packageString = "";
	private String prefixString = "";
	/*
		Iterative code generation method. Returns the name of the child
		class created
	*/
	private String createProduction_Iterative(CompilerState state, ASTNode nCurrent, String basename, boolean generateName, int verboseIndentionLevel) throws IOException {
		LinkedList<String> childNodes = new LinkedList<String>();
		String className;
		String productionName;
		String prefixReplacement;

		if(generateName) {
			productionName = basename + "_" + (autogenIndex++);
			className = (prefixString == null ? "" : (prefixString + "_")) + productionName;
		} else {
			productionName = basename;
			className = (prefixString == null ? "" : (prefixString + "_")) + productionName;
		}
		prefixReplacement = (prefixString == null) ? "" : (prefixString + "_");

		String filename = classTargetDir + className + ".java"; 

		// Collect Child Nodes
		for(ASTNode child : nCurrent) {
			childNodes.add(createProduction_Iterative(state, child, basename, true, verboseIndentionLevel+1));
		}

		String genCode = "";

		// Code creation
		if(nCurrent instanceof ASTChoice) {
			String childNewList = null;
			for(String cName : childNodes) {
				if(childNewList == null) {
					childNewList = "new "+cName+"(null)";
				} else {
					childNewList = childNewList + ", new "+cName+"(null)";
				}
			}

			if(childNewList == null) {
				throw new RuntimeException("Empty Child list for Choice Node");
			}

			genCode = tplChoice;
			genCode = genCode.replace("%PACKAGENAME%", packageString);
			genCode = genCode.replace("%PREFIX%", prefixReplacement);
			genCode = genCode.replace("%PRODUCTIONNAME%", productionName);
			genCode = genCode.replace("%CHILDLISTNEW%", childNewList);

			if(state.whitespaceModeProductionGet(basename, state.whitespaceModeDefaultGet()) != Preprocessor.VALUE_WHITESPACE__NOCHANGE) {
				switch(state.whitespaceModeProductionGet(basename, Preprocessor.VALUE_WHITESPACE__STRIP)) {
					case Preprocessor.VALUE_WHITESPACE__REDUCE:
						genCode = genCode.replace("%WHITESPACEMODE%", "\t\twhitespaceMode = Preprocessor.VALUE_WHITESPACE__REDUCE; ");
						break;
					case Preprocessor.VALUE_WHITESPACE__IGNORE:
						genCode = genCode.replace("%WHITESPACEMODE%", "\t\twhitespaceMode = Preprocessor.VALUE_WHITESPACE__IGNORE; ");
						break;
					case Preprocessor.VALUE_WHITESPACE__STRIP:
						genCode = genCode.replace("%WHITESPACEMODE%", "\t\twhitespaceMode = Preprocessor.VALUE_WHITESPACE__STRIP; ");
						break;
					default:
						throw new RuntimeException("Unknown whitespace mode "+state.whitespaceModeProductionGet(basename, Preprocessor.VALUE_WHITESPACE__STRIP)+" encountered");
				}
			} else {
				genCode = genCode.replace("%WHITESPACEMODE%", "");
			}
		} else if(nCurrent instanceof ASTSequential) {
			String childNewList = null;
			for(String cName : childNodes) {
				if(childNewList == null) {
					childNewList = "new "+cName+"(null)";
				} else {
					childNewList = childNewList + ", new "+cName+"(null)";
				}
			}

			if(childNewList == null) {
				throw new RuntimeException("Empty Child list for Choice Node");
			}

			genCode = tplSequence;
			genCode = genCode.replace("%PACKAGENAME%", packageString);
			genCode = genCode.replace("%PREFIX%", prefixReplacement);
			genCode = genCode.replace("%PRODUCTIONNAME%", productionName);
			genCode = genCode.replace("%CHILDLISTNEW%", childNewList);

			if(state.whitespaceModeProductionGet(basename, state.whitespaceModeDefaultGet()) != Preprocessor.VALUE_WHITESPACE__NOCHANGE) {
				switch(state.whitespaceModeProductionGet(basename, Preprocessor.VALUE_WHITESPACE__STRIP)) {
					case Preprocessor.VALUE_WHITESPACE__REDUCE:
						genCode = genCode.replace("%WHITESPACEMODE%", "\t\twhitespaceMode = Preprocessor.VALUE_WHITESPACE__REDUCE; ");
						break;
					case Preprocessor.VALUE_WHITESPACE__IGNORE:
						genCode = genCode.replace("%WHITESPACEMODE%", "\t\twhitespaceMode = Preprocessor.VALUE_WHITESPACE__IGNORE; ");
						break;
					case Preprocessor.VALUE_WHITESPACE__STRIP:
						genCode = genCode.replace("%WHITESPACEMODE%", "\t\twhitespaceMode = Preprocessor.VALUE_WHITESPACE__STRIP; ");
						break;
					default:
						throw new RuntimeException("Unknown whitespace mode "+state.whitespaceModeProductionGet(basename, Preprocessor.VALUE_WHITESPACE__STRIP)+" encountered");
				}
			} else {
				genCode = genCode.replace("%WHITESPACEMODE%", "");
			}

		} else if(nCurrent instanceof ASTProductionReference) {
			// Just return our production's full name ...
			if(generateName) {
				return (prefixString == null ? "" : (prefixString + "_")) + ((ASTProductionReference)nCurrent).getName();
			} else {
				/*
					We are a single production that only contains a ASTProductionReference - i.e. we are an ALIAS!

					Currently: Producte a SEQUENCE with a SINGLE child
				*/
				String childNewList = "new "+((prefixString == null ? "" : (prefixString + "_")) + ((ASTProductionReference)nCurrent).getName())+"(null)";
			
				genCode = tplSequence;
				genCode = genCode.replace("%PACKAGENAME%", packageString);
				genCode = genCode.replace("%PREFIX%", prefixReplacement);
				genCode = genCode.replace("%PRODUCTIONNAME%", productionName);
				genCode = genCode.replace("%CHILDLISTNEW%", childNewList);

				if(state.whitespaceModeProductionGet(basename, state.whitespaceModeDefaultGet()) != Preprocessor.VALUE_WHITESPACE__NOCHANGE) {
					switch(state.whitespaceModeProductionGet(basename, Preprocessor.VALUE_WHITESPACE__STRIP)) {
						case Preprocessor.VALUE_WHITESPACE__REDUCE:
							genCode = genCode.replace("%WHITESPACEMODE%", "\t\twhitespaceMode = Preprocessor.VALUE_WHITESPACE__REDUCE; ");
							break;
						case Preprocessor.VALUE_WHITESPACE__IGNORE:
							genCode = genCode.replace("%WHITESPACEMODE%", "\t\twhitespaceMode = Preprocessor.VALUE_WHITESPACE__IGNORE; ");
							break;
						case Preprocessor.VALUE_WHITESPACE__STRIP:
							genCode = genCode.replace("%WHITESPACEMODE%", "\t\twhitespaceMode = Preprocessor.VALUE_WHITESPACE__STRIP; ");
							break;
						default:
							throw new RuntimeException("Unknown whitespace mode "+state.whitespaceModeProductionGet(basename, Preprocessor.VALUE_WHITESPACE__STRIP)+" encountered");
					}
				} else {
					genCode = genCode.replace("%WHITESPACEMODE%", "");
				}
			}
		} else if((nCurrent instanceof ASTRepeat) || (nCurrent instanceof ASTOptional)) {
			long min; long max;
			if(nCurrent instanceof ASTOptional) {
				min = 0;
				max = 1;
			} else {
				min = ((ASTRepeat)nCurrent).getMin();
				max = ((ASTRepeat)nCurrent).getMax();
			}

			if(max < 0) { max = 0; }
			if(min < 0) { throw new RuntimeException("Negative minimum is never supported"); }

			if(nCurrent.childGetCount() != 1) {
				throw new RuntimeException("ASTRepeat or ASTOptional only can have single children");
			}

			String childName = childNodes.get(0);

			genCode = tplOptionRepeat;
			genCode = genCode.replace("%PACKAGENAME%", packageString);
			genCode = genCode.replace("%PREFIX%", prefixReplacement);
			genCode = genCode.replace("%PRODUCTIONNAME%", productionName);
			genCode = genCode.replace("%CHILDELEMENT%", childName);
			genCode = genCode.replace("%MINREPEAT%", "" + min);
			genCode = genCode.replace("%MAXREPEAT%", "" + max);

			if(state.whitespaceModeProductionGet(basename, state.whitespaceModeDefaultGet()) != Preprocessor.VALUE_WHITESPACE__NOCHANGE) {
				switch(state.whitespaceModeProductionGet(basename, Preprocessor.VALUE_WHITESPACE__STRIP)) {
					case Preprocessor.VALUE_WHITESPACE__REDUCE:
						genCode = genCode.replace("%WHITESPACEMODE%", "\t\twhitespaceMode = Preprocessor.VALUE_WHITESPACE__REDUCE; ");
						break;
					case Preprocessor.VALUE_WHITESPACE__IGNORE:
						genCode = genCode.replace("%WHITESPACEMODE%", "\t\twhitespaceMode = Preprocessor.VALUE_WHITESPACE__IGNORE; ");
						break;
					case Preprocessor.VALUE_WHITESPACE__STRIP:
						genCode = genCode.replace("%WHITESPACEMODE%", "\t\twhitespaceMode = Preprocessor.VALUE_WHITESPACE__STRIP; ");
						break;
					default:
						throw new RuntimeException("Unknown whitespace mode "+state.whitespaceModeProductionGet(basename, Preprocessor.VALUE_WHITESPACE__STRIP)+" encountered");
				}
			} else {
				genCode = genCode.replace("%WHITESPACEMODE%", "");
			}
		} else if(nCurrent instanceof ASTSingleTerminal) {
			// Never has children ...

			if(nCurrent.childGetCount() != 0) {
				throw new RuntimeException("ASTSingleTerminal never has children");
			}
			String constant = ((ASTSingleTerminal)nCurrent).getTerminal();
			constant = "\"" + constant.replace("\"", "\\\"") + "\"";

			genCode = tplConstant;
			genCode = genCode.replace("%PACKAGENAME%", packageString);
			genCode = genCode.replace("%PREFIX%", prefixReplacement);
			genCode = genCode.replace("%PRODUCTIONNAME%", productionName);
			genCode = genCode.replace("%SYMBOLSTRING%", constant);

			if(state.whitespaceModeProductionGet(basename, state.whitespaceModeDefaultGet()) != Preprocessor.VALUE_WHITESPACE__NOCHANGE) {
				switch(state.whitespaceModeProductionGet(basename, Preprocessor.VALUE_WHITESPACE__STRIP)) {
					case Preprocessor.VALUE_WHITESPACE__REDUCE:
						genCode = genCode.replace("%WHITESPACEMODE%", "\t\twhitespaceMode = Preprocessor.VALUE_WHITESPACE__REDUCE; ");
						break;
					case Preprocessor.VALUE_WHITESPACE__IGNORE:
						genCode = genCode.replace("%WHITESPACEMODE%", "\t\twhitespaceMode = Preprocessor.VALUE_WHITESPACE__IGNORE; ");
						break;
					case Preprocessor.VALUE_WHITESPACE__STRIP:
						genCode = genCode.replace("%WHITESPACEMODE%", "\t\twhitespaceMode = Preprocessor.VALUE_WHITESPACE__STRIP; ");
						break;
					default:
						throw new RuntimeException("Unknown whitespace mode "+state.whitespaceModeProductionGet(basename, Preprocessor.VALUE_WHITESPACE__STRIP)+" encountered");
				}
			} else {
				genCode = genCode.replace("%WHITESPACEMODE%", "");
			}
		} else if(nCurrent instanceof ASTSingleTerminalCollection) {
			// Never has children ...
			if(nCurrent.childGetCount() != 0) {
				throw new RuntimeException("ASTSingleTerminal never has children");
			}

			long min; long max;
			min = ((ASTSingleTerminalCollection)nCurrent).getMin();
			max = ((ASTSingleTerminalCollection)nCurrent).getMax();
			if(max < 0) { max = 0; }
			if(min < 0) { throw new RuntimeException("Negative minimum is never supported"); }

			// Create code for every supported terminal ...
			String allowCode = "\t\tallowedGraphemeClusters = new HashMap<String, Boolean>();\n";

			String allowedGraphemeClusters = ((ASTSingleTerminalCollection)nCurrent).choicesGet();
			for(int csel = 0; csel < allowedGraphemeClusters.length(); csel++) {
				char currentChar = allowedGraphemeClusters.charAt(csel);
				String nextChar = (currentChar+"").replace("\\", "\\\\").replace("\"", "\\\"");

				allowCode = allowCode+"\t\tallowedGraphemeClusters.put(\""+nextChar+"\", true);\n"; 
			}

			genCode = tplTerminals;
			genCode = genCode.replace("%PACKAGENAME%", packageString);
			genCode = genCode.replace("%PREFIX%", prefixReplacement);
			genCode = genCode.replace("%PRODUCTIONNAME%", productionName);
			genCode = genCode.replace("%MINREPEAT%", "" + min);
			genCode = genCode.replace("%MAXREPEAT%", "" + max);
			genCode = genCode.replace("%ALLOWCODE%", allowCode);

			if(state.whitespaceModeProductionGet(basename, state.whitespaceModeDefaultGet()) != Preprocessor.VALUE_WHITESPACE__NOCHANGE) {
				switch(state.whitespaceModeProductionGet(basename, Preprocessor.VALUE_WHITESPACE__STRIP)) {
					case Preprocessor.VALUE_WHITESPACE__REDUCE:
						genCode = genCode.replace("%WHITESPACEMODE%", "\t\twhitespaceMode = Preprocessor.VALUE_WHITESPACE__REDUCE;\n");
						break;
					case Preprocessor.VALUE_WHITESPACE__IGNORE:
						genCode = genCode.replace("%WHITESPACEMODE%", "\t\twhitespaceMode = Preprocessor.VALUE_WHITESPACE__IGNORE;\n");
						break;
					case Preprocessor.VALUE_WHITESPACE__STRIP:
						genCode = genCode.replace("%WHITESPACEMODE%", "\t\twhitespaceMode = Preprocessor.VALUE_WHITESPACE__STRIP;\n");
						break;
					default:
						throw new RuntimeException("Unknown whitespace mode "+state.whitespaceModeProductionGet(basename, Preprocessor.VALUE_WHITESPACE__STRIP)+" encountered");
				}
			} else {
				genCode = genCode.replace("%WHITESPACEMODE%", "");
			}
		} else if(nCurrent instanceof ASTEmpty) {
			// Never has children ...
			if(nCurrent.childGetCount() != 0) {
				throw new RuntimeException("ASTEmpty never has children");
			}

			genCode = tplEmpty;
			genCode = genCode.replace("%PACKAGENAME%", packageString);
			genCode = genCode.replace("%PREFIX%", prefixReplacement);
			genCode = genCode.replace("%PRODUCTIONNAME%", productionName);
		} else {
			throw new RuntimeException("Unsupported AST node remained after transformation: "+nCurrent.getClass().getCanonicalName());
		}

		// Write into file
		FileWriter w = new FileWriter(filename, false);
		w.write(genCode);
		w.close();

		if(state.getVerbose()) {
			String indention = "";
			for(int i = 0; i < verboseIndentionLevel; i++) { indention = indention + " "; }
			System.out.println("GEN "+indention+filename);
		}
		
		return className;
	}

	private String createProduction(CompilerState state, ASTProduction prod) throws IOException {
		String productionName = prod.getName();

		/* Validate a production ALWAYS only has ONE child */
		if(prod.childGetCount() != 1) { throw new RuntimeException("Productions always have at most one child"); }

		/* Iteratively create files from rules */
		return createProduction_Iterative(state, prod.childGetLast(), productionName, false, 0);
	}

	@Override
	public boolean generateCode(CompilerState state) throws IOException {
		prefixString = state.getOutPrefix();

		// Directory and package information
		if(state.getOutBasedir() == null) { state.pushError("No output base directory has been specified. Cannot emit Java code"); return false; }
		if(state.getOutPackage() == null) {
			state.pushWarning("No output package has been specified. Generating Java Code for global scope");
			packageString = "";
			classTargetDir = state.getOutBasedir();
			if(File.separatorChar != classTargetDir.charAt(classTargetDir.length()-1)) {
				classTargetDir = classTargetDir + File.separatorChar; 
			}
		} else {
			packageString = "package "+state.getOutPackage();

			classTargetDir = state.getOutBasedir();
			if(File.separatorChar != classTargetDir.charAt(classTargetDir.length()-1)) {
				classTargetDir = classTargetDir + File.separatorChar; 
			}
			// Now create path from package
			classTargetDir = classTargetDir + state.getOutPackage().replace(".", ""+File.separatorChar) + File.separatorChar;
		}

		/*
			If required create directory hierarchy
		*/
		if(!(new File(classTargetDir)).exists()) {
			if(!(new File(classTargetDir)).mkdirs()) {
				state.pushError("Failed to create directory hierarchy "+classTargetDir); return false;
			}
		}

		/*
			Iterate over all productions and recursively create required classes (top down)
		*/

		for(ASTNode astNode : state.astRootGet()) {
			if(!(astNode instanceof ASTProduction)) { throw new RuntimeException("Unknown child type inside AST"); }

			createProduction(state, (ASTProduction)astNode);
		}

		/*
			Now create "Parser" implementations
		*/
		LinkedList<ASTProduction> llStarterProductions;
		if(state.starterSpecificationAvailable()) {
			llStarterProductions = state.starterSpecificationGetProductions();
		} else {
			llStarterProductions = state.productionGetUnreferenced();
		}

		for(ASTProduction astProd : llStarterProductions) {
			String rootClassName = (prefixString == null ? "" : (prefixString + "_")) + astProd.getName();
			String className = (prefixString == null ? "" : (prefixString + "_")) + "Parser";
			if(llStarterProductions.size() > 1) {
				className = className + "_" + astProd.getName();
			}
			String prefixReplacement = (prefixString == null) ? "" : (prefixString + "_");

			String filename = classTargetDir + className + ".java";

			String genCode = tplParserMain;
			genCode = genCode.replace("%PACKAGENAME%", packageString);
			genCode = genCode.replace("%PREFIX%", prefixReplacement);
			genCode = genCode.replace("%ROOTELEMENTCLASS%", rootClassName);
			genCode = genCode.replace("%PARSERCLASSNAME%", className);

			FileWriter w = new FileWriter(filename, false);
			w.write(genCode);
			w.close();

			if(state.getVerbose()) {
				System.out.println("GEN "+filename);
			}
		}
		return true;
	}
}
