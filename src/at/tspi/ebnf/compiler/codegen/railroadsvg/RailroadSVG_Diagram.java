package at.tspi.ebnf.compiler.codegen.railroadsvg;

import java.io.FileWriter;
import java.io.IOException;

public class RailroadSVG_Diagram extends RailroadSVG_Node {
	private String filename;
	private RailroadSVG_Node rootNode;

	final String tplSvgHeader = "<svg xmlns=\"http://www.w3.org/2000/svg\">\n" +
			"\t<defs>\n" +
			"\t\t<style type=\"text/css\">\n" +
			"\t\t\t@namespace \"http://www.w3.org/2000/svg\";\n" +
			"\n" +
			"\t\t\t.line { fill: none; stroke: #332900; }\n" +
			"\t\t\t.bold-line { stroke: #141000; shape-rendering: crispEdges; stroke-width: 2; }\n" +
			"\t\t\t.thin-line { stroke: #1F1800; shape-rendering: crispEdges; }\n" +
			"\t\t\t.filled { fill: #332900; stroke: none; }\n" +
			"\n" +
			"\t\t\ttext.nonterminal {\n" +
			"\t\t\t\tfont-family: Verdana, Sans-serif;\n" +
			"\t\t\t\tfont-size: 12px;\n" +
			"\t\t\t\tfill: #141000;\n" +
			"\t\t\t\tfont-weight: bold;\n" +
			"\t\t\t}\n" +
			"\t\t\ttext.nonterminal {\n" +
			"\t\t\t\tfont-family: Verdana, Sans-serif;\n" +
			"\t\t\t\tfont-size: 12px;\n" +
			"\t\t\t\tfill: #1A1400;\n" +
			"\t\t\t}\n" +
			"\n" +
			"\t\t\trect, circle, polygon \t{ fill: #332900; stroke: #332900; }\n" +
			"\t\t\trect.terminal\t\t{ fill: #FFDB4D; stroke: #332900; }\n" +
			"\t\t\trect.nonterminal\t{ fill: #FFEC9E; stroke: #332900; }\n" +
			"\t\t\trect.text\t\t{ fill: none; stroke: none; }\n" +
			"\t\t</style>\n" +
			"\t</defs>\n";
	final String tplSvgFooter = "</svg>\n";

	public RailroadSVG_Diagram(String filename) {
		super(new RailroadSVG_CodegenSettings());

		this.filename = filename;
		this.rootNode = null;
	}
	public RailroadSVG_Diagram(String filename, RailroadSVG_CodegenSettings cfg) {
		super(cfg);
		this.filename = filename;
		this.rootNode = null;
	}

	public RailroadSVG_Diagram rootNodeSet(RailroadSVG_Node n) { this.rootNode = n; return this; }
	public RailroadSVG_Node rootNodeGet() { return this.rootNode; }

	public boolean writeFile() throws IOException {
		if(this.rootNode == null) {
			return true;
		}
System.out.println(this.filename);
		FileWriter w = new FileWriter(this.filename, false);
		w.write(tplSvgHeader + rootNode.draw(new RailroadSVG_Coordinate()) + tplSvgFooter);
		w.close();
		return true;
	}
	
	@Override
	public void childPush(RailroadSVG_Node c) {
		if(this.rootNode != null) {
			throw new UnsupportedOperationException();
		} else {
			this.rootNode = c;
		}
	}



	public long getWidth() { return (this.rootNode != null) ? this.rootNode.getWidth() : 0; }
	public long getHeight() { return (this.rootNode != null) ? this.rootNode.getHeight() : 0; }

	public String draw(RailroadSVG_Coordinate relativeOrigin) {
		throw new UnsupportedOperationException(); // This is not meaningful on diagram ...
	}
	@Override
	public long getAttachmentY() {
		return this.getHeight()/2;
	}
}
