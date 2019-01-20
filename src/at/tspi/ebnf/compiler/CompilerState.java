package at.tspi.ebnf.compiler;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import at.tspi.ebnf.compiler.ast.ASTNode;
import at.tspi.ebnf.compiler.ast.ASTProduction;
import at.tspi.ebnf.parser.Preprocessor;

public class CompilerState {
	ASTNode												rootNode = null;
	HashMap<String, ASTProduction>						productionsKnown = new HashMap<String, ASTProduction>();
	HashMap<String, Integer>							productionsKnownRefcount = new HashMap<String, Integer>();

	HashMap<String, Boolean>							productionsKeep = new HashMap<String, Boolean>();
	HashMap<String, Integer>							productionExplicitWhitespaceMode = new HashMap<String, Integer>();
	private int											whitespaceModeDefault = Preprocessor.VALUE_WHITESPACE__STRIP;
	HashMap<String, Boolean>							explicitStarters = new HashMap<String, Boolean>();

	LinkedList<String>									strErrors = new LinkedList<String>();
	LinkedList<String>									strWarnings = new LinkedList<String>();

	private String										outBasedir = null;
	private String										outPackage = null;
	private String										outPrefix = null;
	private boolean										bVerbose = false;

	public CompilerState astRootSet(ASTNode root) 		{ this.rootNode = root; return this; }
	public ASTNode astRootGet() 						{ return this.rootNode; }

	public boolean productionRegisterKnown(ASTProduction p) {
		synchronized(this) {
			if(productionsKnown.get(p.getName()) != null) {
				strErrors.add("Production "+p.getName()+" already defined");
				return false;
			}
			productionsKnown.put(p.getName(), p);
			productionsKnownRefcount.put(p.getName(), 0);
			return true;
		}
	}

	public boolean registerRefcount(ASTProduction referencingProduction, String productionName) {
		synchronized(this) {
			if(productionsKnown.get(productionName) == null) {
				strErrors.add("Undefined production "+productionName+" referenced by "+referencingProduction.getName());
				return false;
			}
			productionsKnownRefcount.put(productionName, productionsKnownRefcount.get(productionName) + 1);
			return true;
		}
	}

	public LinkedList<ASTProduction> productionGetUnreferenced() {
		LinkedList<ASTProduction> res = new LinkedList<ASTProduction>();
		for(Map.Entry<String, Integer> e : productionsKnownRefcount.entrySet()) {
			if(e.getValue() == 0) {
				// Unreferenced Production
				res.add(productionsKnown.get(e.getKey()));
			}
		}
		return res;
	}
	public CompilerState productionRemoveFromIndex(ASTProduction p) {
		if(!productionsKnown.containsKey(p.getName())) {
			throw new RuntimeException("Trying to remove unknown production "+p.getName());
		}

		productionsKnown.remove(p.getName());
		productionsKnownRefcount.remove(p.getName());
		return this;
	}

	public CompilerState keepProduction(String[] productions) {
		for(String s : productions) {
			keepProduction(s);
		}
		return this;
	}
	public CompilerState keepProduction(String production) {
		productionsKeep.put(production, true);
		return this;
	}
	public boolean keepProductionCheck(String production) {
		return (productionsKeep.get(production) != null);
	}

	public boolean starterSpecificationAvailable() {
		if(this.explicitStarters.size() == 0) {
			return false;
		} else {
			return true;
		}
	}
	public void starterSpecificationSetStarter(String production) {
		this.explicitStarters.put(production, true);
	}
	public boolean starterSpecificationIsStarter(String production) {
		if(this.explicitStarters.containsKey(production)) {
			return true;
		} else {
			return false;
		}
	}
	public LinkedList<ASTProduction> starterSpecificationGetProductions() {
		LinkedList<ASTProduction> res = new LinkedList<ASTProduction>();
		for(Map.Entry<String, Boolean> e : explicitStarters.entrySet()) {
			res.add(productionsKnown.get(e.getKey()));
		}
		return res;
	}

	public CompilerState whitespaceModeProductionSet(String production, int whitespaceMode) {
		productionExplicitWhitespaceMode.put(production, whitespaceMode);
		return this;
	}
	public int whitespaceModeProductionGet(String production, int defaultMode) {
		Integer nt = productionExplicitWhitespaceMode.get(production);
		if(nt == null) {
			return defaultMode;
		} else {
			return nt;
		}
	}
	public int whitespaceModeDefaultGet() { return whitespaceModeDefault; }
	public CompilerState whitespaceModeDefaultSet(int whitespaceMode) { this.whitespaceModeDefault = whitespaceMode; return this; }

	public boolean hasErrors() { return (this.strErrors.size() > 0); }
	public boolean hasWarnings() { return (this.strWarnings.size() > 0); }

	public LinkedList<String> getErrors() { return this.strErrors; }
	public LinkedList<String> getWarnings() { return this.strWarnings; }

	public CompilerState pushWarning(String warning) { this.strWarnings.add(warning); return this; }
	public CompilerState pushError(String warning) { this.strErrors.add(warning); return this; }


	public String getOutBasedir() { return this.outBasedir; }
	public String getOutPackage() { return this.outPackage; }
	public String getOutPrefix() { return this.outPrefix; }

	public CompilerState setOutBasedir(String outBasedir) { this.outBasedir = outBasedir; return this; }
	public CompilerState setOutPackage(String outPackage) { this.outPackage = outPackage; return this; }
	public CompilerState setOutPrefix(String outPrefix) { this.outPrefix = outPrefix; return this; }

	public CompilerState setVerbose(boolean verbose) { this.bVerbose = verbose; return this; }
	public boolean getVerbose() { return this.bVerbose; }
};