package at.tspi.ebnf.compiler.ast;

public class ASTRepeat extends ASTNode {
	long min = 0;
	long max = 0;

	public ASTRepeat() 												{ super(); }
	public ASTRepeat(long min, long max) 							{ super(); this.min = min; this.max = max; }

	public ASTRepeat setMinMax(long min, long max) 					{ this.min = min; this.max = max; return this; }
	public long getMin()											{ return this.min; }
	public long getMax()											{ return this.max; }

	public String toString() {
		if(this.min == -1) {
			return this.getClass().getSimpleName() + " (invalid count)";
		} else if((this.min == 0) && (this.max == 1)) {
			return this.getClass().getSimpleName() + " (optionally once)";
		} else {
			return this.getClass().getSimpleName() + " ( Min: "+ this.min + "; Max: "+((this.max == -1) ? "unbound" : this.max) + ")";
		}
	}
}
