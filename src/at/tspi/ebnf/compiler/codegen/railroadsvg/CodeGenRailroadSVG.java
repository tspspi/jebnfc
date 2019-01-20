package at.tspi.ebnf.compiler.codegen.railroadsvg;

import java.io.File;
import java.io.IOException;

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

public class CodeGenRailroadSVG implements ParserCodeGenerator {
	private boolean createProduction_Iterative(RailroadSVG_CodegenSettings cfg, CompilerState state, ASTNode n, RailroadSVG_Node svgNode) {
		// First create our representation
		
		if(n instanceof ASTChoice) {
			RailroadSVG_Choice newNode = new RailroadSVG_Choice(cfg);

			for(ASTNode childNode : n) {
				if(!createProduction_Iterative(cfg, state, childNode, newNode)) {
					return false;
				}
			}

			svgNode.childPush(newNode);
		} else if(n instanceof ASTSequential) {
			RailroadSVG_Sequential newNode = new RailroadSVG_Sequential(cfg);

			for(ASTNode childNode : n) {
				if(!createProduction_Iterative(cfg, state, childNode, newNode)) {
					return false;
				}
			}

			svgNode.childPush(newNode);
		} else if(n instanceof ASTProductionReference) {
			if(n.childGetCount() > 0) { throw new RuntimeException("ASTProductionReference never has children"); }

			RailroadSVG_Nonterminal newNode = new RailroadSVG_Nonterminal(cfg, ((ASTProductionReference)n).getName());
			svgNode.childPush(newNode);
		} else if(n instanceof ASTRepeat) {
			// throw new RuntimeException("TODO Implement"); // TODO
		} else if(n instanceof ASTOptional) {
			// throw new RuntimeException("TODO Implement"); // TODO
		} else if(n instanceof ASTSingleTerminal) {
			if(n.childGetCount() > 0) { throw new RuntimeException("ASTSingleTerminal never has children"); }

			RailroadSVG_Terminal newNode = new RailroadSVG_Terminal(cfg, ((ASTSingleTerminal)n).getTerminal());
			svgNode.childPush(newNode);
		} else if(n instanceof ASTSingleTerminalCollection) {
			if(n.childGetCount() > 0) { throw new RuntimeException("ASTSingleTerminalCollection never has children"); }

			RailroadSVG_Terminal newNode = new RailroadSVG_Terminal(cfg, "anyOf("+((ASTSingleTerminalCollection)n).choicesGet()+")");
			svgNode.childPush(newNode);
		} else if(n instanceof ASTEmpty) {
			throw new RuntimeException("TODO Implement"); // TODO
		} else {
			throw new RuntimeException("Unsupported AST Node "+n.getClass().getSimpleName()+" encountered");
		}
		return true;
	}

	private boolean createProduction(CompilerState state, ASTProduction p, String targetDir, String targetPrefix) {
		String filename = state.getOutBasedir()+File.separatorChar+targetPrefix+p.getName()+".svg";

		RailroadSVG_Diagram dia = new RailroadSVG_Diagram(filename);
		if(p.childGetCount() != 1) { throw new RuntimeException("Productions always have at most one child"); }
		if(!createProduction_Iterative(dia.getCfg(), state, p.childGetLast(), dia)) {
			return false;
		}
		try {
			boolean res = dia.writeFile();
			return res;
		} catch(IOException e) {
			state.pushError("Unable to write file "+filename);
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean generateCode(CompilerState state) throws IOException {
		String targetDir;
		String targetPrefix="";

		// Get target directory information
		if(state.getOutBasedir() == null) { state.pushError("No output base directory has been specified. Cannot emit Java code"); return false; }
		targetDir = state.getOutBasedir();
		if(File.separatorChar != targetDir.charAt(targetDir.length()-1)) {
			targetDir = targetDir + File.separatorChar; 
		}
		if(state.getOutPrefix() != null) {
			if(!state.getOutBasedir().equals("")) {
				targetPrefix = state.getOutPrefix() + "_";
			}
		}

		/*
			If required create directory hierarchy
		*/
		if(!(new File(state.getOutBasedir())).exists()) {
			if(!(new File(state.getOutBasedir())).mkdirs()) {
				state.pushError("Failed to create directory "+state.getOutBasedir()); return false;
			}
		}
		
		/*
			Iterate over all productions and recursively instantiate all classes
			Then create SVG from this tree
		*/
		for(ASTNode astNode : state.astRootGet()) {
			if(!(astNode instanceof ASTProduction)) { throw new RuntimeException("Unknown child type inside AST"); }
			if(!createProduction(state, (ASTProduction)astNode, targetDir, targetPrefix)) {
				return false;
			}
		}

		return true;
		

		/*
		Simple Test Code:
		RailroadSVG_CodegenSettings cfg = new RailroadSVG_CodegenSettings(); 

		RailroadSVG_Choice c2 = new RailroadSVG_Choice(cfg); 

		RailroadSVG_Sequential t1 = new RailroadSVG_Sequential(cfg);
		t1.childPush(new RailroadSVG_Terminal(cfg, "Terminal 1"));
		t1.childPush(new RailroadSVG_Terminal(cfg, "Terminal 2"));
		t1.childPush(new RailroadSVG_Nonterminal(cfg, "Production 1"));
		t1.childPush(new RailroadSVG_Nonterminal(cfg, "Production 2"));

		RailroadSVG_Choice c1 = new RailroadSVG_Choice(cfg);
		c1.childPush(new RailroadSVG_Terminal(cfg, "Choice 1"));
		c1.childPush(new RailroadSVG_Nonterminal(cfg, "Choice 2"));
		c1.childPush(new RailroadSVG_Nonterminal(cfg, "Choice 3"));
		c1.childPush(new RailroadSVG_Terminal(cfg, "Choice 4"));
		t1.childPush(c1);

		c2.childPush(t1);
		c2.childPush(new RailroadSVG_Terminal(cfg, "Alt 1"));

		t1.childPush(new RailroadSVG_Nonterminal(cfg, "SEQ N1"));
		t1.childPush(new RailroadSVG_Nonterminal(cfg, "SEQ N2"));

		FileWriter w = new FileWriter("c:\\temp\\jebnfc\\test.svg", false);
		w.write(tplSvgHeader + c2.draw(new RailroadSVG_Coordinate()) + tplSvgFooter);
		w.close();
		*/
	}

}
