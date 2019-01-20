package at.tspi.ebnf.compiler.codegen.railroadsvg;

import java.security.InvalidParameterException;
import java.util.LinkedList;

/*
	Sequential list of nodes. These are attached directly
	at each others attachment points ...

	Width: sum(child_i)
	Height: max(child_i)
	Attachment Point 1 X: 0
	Attachment Point 1 Y: floor(Height/2)
	Attachment Point 2 X: width
	Attachment Point 2 Y: floor(Height/2)
*/
public class RailroadSVG_Sequential extends RailroadSVG_Node {
	private LinkedList<RailroadSVG_Node> nodes = new LinkedList<RailroadSVG_Node>();

	public RailroadSVG_Sequential(RailroadSVG_CodegenSettings cfg) { super(cfg); }
	public RailroadSVG_Sequential(RailroadSVG_CodegenSettings cfg, RailroadSVG_Coordinate origin) { super(cfg, origin); }

	private void recalculateOrigins() {
		/*
			Detect maximum area above and below attachment points
		 */
		long hAbove = 0;;
		long hBelow = 0;
		for(RailroadSVG_Node n : nodes) {
			long nodeHeight = n.getHeight();
			long nodeAbove = n.getAttachmentY();
			long nodeBelow = nodeHeight - nodeAbove;

			if(hAbove < nodeAbove) { hAbove = nodeAbove; }
			if(hBelow < nodeBelow) { hBelow = nodeBelow; }
		}

		/*
			Our baseline is at hAbove; our height extends to hAbove+hBelow
		 */
		long currentX = 0;
		for(RailroadSVG_Node n : nodes) {
			/* We extend always sideways */
			long relX = currentX;
			currentX = currentX + n.getWidth();

			/* Get node's attachment point and adjust origin by missing distance to top */
			long relY = hAbove - n.getAttachmentY();
			n.setOrigin(new RailroadSVG_Coordinate(relX, relY));
		}
	}

	public void childPush(RailroadSVG_Node c) {
		if(c == null) { throw new InvalidParameterException(); }

		nodes.add(c);
	}

	@Override
	public long getWidth() {
		long width = 0;
		for(RailroadSVG_Node n : nodes) {
			width += n.getWidth();
		}
		return width;
	}

	@Override
	public long getHeight() {
		/*
			Detect maximum area above and below attachment points
		*/
		long hAbove = 0;;
		long hBelow = 0;
		for(RailroadSVG_Node n : nodes) {
			long nodeHeight = n.getHeight();
			long nodeAbove = n.getAttachmentY();
			long nodeBelow = nodeHeight - nodeAbove;

			if(hAbove < nodeAbove) { hAbove = nodeAbove; }
			if(hBelow < nodeBelow) { hBelow = nodeBelow; }
		}

		return hAbove+hBelow;
	}

	@Override
	public String draw(RailroadSVG_Coordinate relativeOrigin) {
		String res = "";
		recalculateOrigins();

		for(RailroadSVG_Node n : nodes) {
			res = res + n.draw(relativeOrigin.translate(this.getOrigin()));
		}

		return res;
	}
	@Override
	public long getAttachmentY() {
		long hAbove = 0;;
		long hBelow = 0;
		for(RailroadSVG_Node n : nodes) {
			long nodeHeight = n.getHeight();
			long nodeAbove = n.getAttachmentY();
			long nodeBelow = nodeHeight - nodeAbove;

			if(hAbove < nodeAbove) { hAbove = nodeAbove; }
			if(hBelow < nodeBelow) { hBelow = nodeBelow; }
		}
		return hAbove;
	}

}
