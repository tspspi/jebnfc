package at.tspi.ebnf.compiler.codegen.railroadsvg;

public class RailroadSVG_Coordinate {
	long x,y;

	public RailroadSVG_Coordinate(long x, long y) { this.x = x; this.y = y; }
	public RailroadSVG_Coordinate() { this.x = 0; this.y = 0; }
	public RailroadSVG_Coordinate(RailroadSVG_Coordinate other) { this.x = other.x; this.y = other.y; }

	public long getX() { return this.x; }
	public long getY() { return this.y; }
	public RailroadSVG_Coordinate setX(long X) { this.x = X; return this; }
	public RailroadSVG_Coordinate setY(long Y) { this.y = Y; return this; }
	public RailroadSVG_Coordinate setXY(long X, long Y) { this.x = X; this.y = Y; return this; }

	public String toString() { return "("+this.x+","+this.y+")"; }

	public RailroadSVG_Coordinate translate(RailroadSVG_Coordinate other) {
		return new RailroadSVG_Coordinate(this.x + other.x, this.y + other.y);
	}
}
