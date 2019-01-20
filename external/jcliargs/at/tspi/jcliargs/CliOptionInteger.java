package at.tspi.jcliargs;

public class CliOptionInteger extends CliOption {
	protected int value = -1;

	public CliOptionInteger(String ln) { super(ln); }
	public CliOptionInteger(String sn, String ln) { super(sn, ln); }
	public CliOptionInteger(String sn, String ln, long min, long max) { super(sn,ln,min,max); }
	public CliOptionInteger(String sn, String ln, String d) { super(sn,ln,d); }
	public CliOptionInteger(String sn, String ln, long min, long max, String d) { super(sn,ln,min,max,d); }
	public CliOptionInteger(CliOptionInteger other) {
		super(other);
		this.value = other.value;
	}

	public CliOptionInteger valueSet(int value) { this.value = value; return this; }
	public int valueGet() { return this.value; }
}