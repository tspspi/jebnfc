package at.tspi.ebnf.compiler;

import java.util.HashMap;

import at.tspi.ebnf.compiler.ast.ASTNode;
import at.tspi.ebnf.compiler.ast.ASTProduction;
import at.tspi.ebnf.compiler.ast.ASTRoot;

public class ASTCombiner {
	public static ASTRoot combineASTs(ASTRoot astMaster, ASTRoot astInclude, CompilerState state) throws ASTException {
		HashMap<String, ASTProduction> hmKnownProductions = new HashMap<String, ASTProduction>();

		if(astMaster == null) { throw new RuntimeException("One has to specify a master to combine with"); }

		/*
			Iterate over all child productions (inside include) and check if they are
			already contained or clashing with definitions from astMaster.

			If they are not contained -> include
			On collision -> error
		 */
		if(astInclude == null) {
			// We don't include anything, we return the master again
			return astMaster;
		}

		/* Register known productions from master */
		for(ASTNode n : astMaster) {
			if(!(n instanceof ASTProduction)) {
				throw new RuntimeException("Assertion failed: ASTRoot should only contain ASTProduction members");
			}
			ASTProduction p = (ASTProduction)n;

			if(hmKnownProductions.containsKey(p.getName())) {
				throw new ASTException("Production "+p.getName()+" already defined");
			}
			hmKnownProductions.put(p.getName(), p);
		}

		for(ASTNode n : astInclude) {
			if(!(n instanceof ASTProduction)) {
				throw new RuntimeException("Assertion failed: ASTRoot should only contain ASTProduction members");
			}

			ASTProduction pNew = (ASTProduction)n;

			/* Check if the production is already defined */
			if(hmKnownProductions.containsKey(pNew.getName())) {
				throw new ASTException("Production "+pNew.getName()+" already defined");
			}

			/* Insert production into master */
			astMaster.childAppend(pNew);
		}

		return astMaster;
	}
}
