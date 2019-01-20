package at.tspi.ebnf.compiler.codegen.railroadsvg;

import java.util.LinkedList;

/*
	Choice of nodes.
	
	They are all placed beneath each other, all starting at
	the same left position
	
	Spaced by 10px spacing

	Height = sum(child_i) + (n_childs - 1)*10;
	Width = max(child_i) + width of both choice beams (20 * 2?)
 */
public class RailroadSVG_Choice extends RailroadSVG_Node {
	private LinkedList<RailroadSVG_Node> nodes = new LinkedList<RailroadSVG_Node>();

	public RailroadSVG_Choice(RailroadSVG_CodegenSettings cfg) { super(cfg); }
	public RailroadSVG_Choice(RailroadSVG_CodegenSettings cfg, RailroadSVG_Coordinate origin) { super(cfg, origin); }

	public void childPush(RailroadSVG_Node node) {
		nodes.add(node);
	}
	private void recalculateOrigins() {
		/*
			Simply stack all nodes above each other
		 */
		long originX = 20;
		long currentY = 0;

		for(RailroadSVG_Node n : nodes) {
			n.setOrigin(new RailroadSVG_Coordinate(originX, currentY));
			currentY += n.getHeight() + 10;
		}
	}

	@Override
	public long getWidth() {
		long maxWidth = 0;
		for(RailroadSVG_Node n : nodes) {
			if(maxWidth < n.getWidth()) { maxWidth = n.getWidth(); }
		}
		return maxWidth + (20 * 2);
	}

	@Override
	public long getHeight() {
		long res = 0;
		for(RailroadSVG_Node n : nodes) {
			res += n.getHeight();
		}
		res += (nodes.size()-1)*10;
		return res;
	}

	private long maxL(long l1, long l2) { return (l1 > l2) ? l1 : l2; }
	private long minL(long l1, long l2) { return (l1 < l2) ? l1 : l2; }
	@Override
	public long getAttachmentY() {
		recalculateOrigins();
		return minL(maxL(getHeight()/2, nodes.getFirst().getAttachmentY()+nodes.getFirst().getOrigin().getY()), nodes.getLast().getAttachmentY()+nodes.getLast().getOrigin().getY());
	}

	private String svgLineHorizontal(long x1, long y1, long l) 	{ return "<path class=\"line\" d=\"m "+x1+" "+y1+" h "+l+" \"> </path>"; }
	private String svgLineVertical(long x1, long y1, long l) 	{ return "<path class=\"line\" d=\"m "+x1+" "+y1+" v "+l+" \"> </path>"; }

	private String svgArc_B_R(long x1, long y1) 				{ return "<path class=\"line\" d=\"m "+(x1-10)+" "+(y1+10)+" a 10 10 0 10 -10\"></path>"; }
	private String svgArc_T_R(long x1, long y1) 				{ return "<path class=\"line\" d=\"m "+(x1-10)+" "+(y1-10)+" a 10 10 0 10 10\"></path>"; }
	private String svgArc_L_B(long x1, long y1) 				{ return "<path class=\"line\" d=\"m "+(x1+10)+" "+(y1+10)+" a 10 10 0 -10 -10\"></path>"; }
	private String svgArc_L_T(long x1, long y1) 				{ return "<path class=\"line\" d=\"m "+(x1+10)+" "+(y1-10)+" a 10 10 0 -10 10\"></path>"; }

	private String drawPaddings(RailroadSVG_Coordinate relativeOrigin) {
		long ownWidth = this.getWidth() - (2*20);
		String res = "";

		long absDX = this.getOrigin().getX()+relativeOrigin.getX();
		long absDY = this.getOrigin().getX()+relativeOrigin.getX();

		for(RailroadSVG_Node n : nodes) {
			if(n.getWidth() < ownWidth) {
				res = res + svgLineHorizontal(n.getWidth()+1*20+absDX, n.getOrigin().getY() + n.getAttachmentY() + absDY, ownWidth - n.getWidth());
			}
		}

		return res;
	}

	private String drawBars(RailroadSVG_Coordinate relativeOrigin) {
		long ownHeight = this.getHeight();
		long ownWidth = this.getWidth();

		long absDX = this.getOrigin().getX()+relativeOrigin.getX();
		long absDY = this.getOrigin().getX()+relativeOrigin.getX();

		return svgLineVertical(10+absDX, nodes.getFirst().getOrigin().getY()+nodes.getFirst().getAttachmentY()+10, (nodes.getLast().getAttachmentY()+nodes.getLast().getOrigin().getY())-(nodes.getFirst().getAttachmentY()+nodes.getFirst().getOrigin().getY())-2*10) +
					svgLineVertical(absDX+ownWidth-10, nodes.getFirst().getOrigin().getY()+nodes.getFirst().getAttachmentY()+10, (nodes.getLast().getAttachmentY()+nodes.getLast().getOrigin().getY())-(nodes.getFirst().getAttachmentY()+nodes.getFirst().getOrigin().getY())-2*10);
	}

	private String drawArcs(RailroadSVG_Coordinate relativeOrigin) {
		long ownHeight = this.getHeight();
		long ownWidth = this.getWidth();

		long absDX = this.getOrigin().getX()+relativeOrigin.getX();
		long absDY = this.getOrigin().getX()+relativeOrigin.getX();

		long ownAP = getAttachmentY();

		String res = "";
		for(RailroadSVG_Node n : nodes) {
			if(n.getAttachmentY()+n.getOrigin().getY() > ownAP) {
				// Node situated below our AP, arcing upwards ... i.e. T_R and L_T
				res = res + svgArc_T_R(absDX+20, absDY+n.getAttachmentY()+n.getOrigin().getY());
				res = res + svgArc_L_T(absDX+ownWidth-20, absDY+n.getAttachmentY()+n.getOrigin().getY());
			} else {
				// Node situated above our AP, arcing B R and L B
				res = res + svgArc_B_R(absDX+20, absDY+n.getAttachmentY()+n.getOrigin().getY());
				res = res + svgArc_L_B(absDX+ownWidth-20, absDY+n.getAttachmentY()+n.getOrigin().getY());
			}
		}
		return res;
	}

	@Override
	public String draw(RailroadSVG_Coordinate relativeOrigin) {
		recalculateOrigins();

		String res = drawPaddings(relativeOrigin);
		res = res + drawBars(relativeOrigin);
		res = res + drawArcs(relativeOrigin);

		for(RailroadSVG_Node n : nodes) {
			res = res + n.draw(relativeOrigin.translate(this.getOrigin()));
		}

		return res;
	}

}
