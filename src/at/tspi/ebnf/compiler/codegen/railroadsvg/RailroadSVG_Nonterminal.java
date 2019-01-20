package at.tspi.ebnf.compiler.codegen.railroadsvg;

public class RailroadSVG_Nonterminal extends RailroadSVG_Node {
	private String text;

	public RailroadSVG_Nonterminal(RailroadSVG_CodegenSettings cfg, String text) { super(cfg); this.text = text; }
	public RailroadSVG_Nonterminal(RailroadSVG_CodegenSettings cfg, RailroadSVG_Coordinate origin, String text) { super(cfg, origin); this.text = text; }

	@Override
	public long getWidth() {
		return (8+2+text.length()*8)+20;
	}

	@Override
	public long getHeight() {
		return 35;
	}

	@Override
	public long getAttachmentY() {
		return 35/2; // Our attachment always happens at 17
	}
	
	@Override
	public void childPush(RailroadSVG_Node c) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public String draw(RailroadSVG_Coordinate relativeOrigin) {
		long left = this.getOrigin().getX()+relativeOrigin.getX();
		long top = this.getOrigin().getY()+relativeOrigin.getY();
		long letters = text.length();

		String tplTerminal = "<rect x=\""+(12+left)+"\" y=\""+(3+top)+"\" width=\""+(8+letters*8)+"\" height=\"32\"> </rect>" +
				"<rect x=\""+(10+left)+"\" y=\""+(1+top)+"\" width=\""+(8+letters*8)+"\" height=\"32\" class=\"nonterminal\"> </rect>" +
				"<text class=\"nonterminal\" x=\""+(16+left)+"\" y=\""+(21+top)+"\">"+text+"</text>" +
				"<path class=\"line\" d=\"m"+left+" "+(17+top)+" h10 m"+(8+2+letters*8)+" 0 h10\"> </path>";

		return tplTerminal;
	}

}
