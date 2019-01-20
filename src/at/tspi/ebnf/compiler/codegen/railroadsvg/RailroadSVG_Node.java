package at.tspi.ebnf.compiler.codegen.railroadsvg;

public abstract class RailroadSVG_Node {
	private RailroadSVG_CodegenSettings cfg;
	private RailroadSVG_Coordinate origin;

	private long paddingWidth;
	private long paddingHeight;

	public RailroadSVG_Node(RailroadSVG_CodegenSettings cfg) {
		if(cfg == null) {
			throw new NullPointerException();
		}
		this.cfg = cfg;
		this.origin = new RailroadSVG_Coordinate();
	}
	public RailroadSVG_Node(RailroadSVG_CodegenSettings cfg, RailroadSVG_Coordinate origin) {
		if((cfg == null) || (origin == null)) {
			throw new NullPointerException();
		}
		this.cfg = cfg;
		this.origin = origin;
	}

	public RailroadSVG_Node setOrigin(RailroadSVG_Coordinate origin) {
		if(origin == null) {
			throw new NullPointerException();
		}
		this.origin = origin;
		return this;
	}
	public RailroadSVG_Coordinate getOrigin() {
		return this.origin;
	}

	protected RailroadSVG_CodegenSettings getCfg() { return this.cfg; }

	abstract public long getWidth();
	abstract public long getHeight();

	abstract public long getAttachmentY();
	
	abstract public void childPush(RailroadSVG_Node c);

	abstract public String draw(RailroadSVG_Coordinate relativeOrigin);
}