package at.tspi.jcliargs;

public class CliOptionString extends CliOption {
	protected String value = null;

	public CliOptionString(String ln) { super(ln); }
	public CliOptionString(String sn, String ln) { super(sn, ln); }
	public CliOptionString(String sn, String ln, long min, long max) { super(sn,ln,min,max); }
	public CliOptionString(String sn, String ln, String d) { super(sn,ln,d); }
	public CliOptionString(String sn, String ln, long min, long max, String d) { super(sn,ln,min,max,d); }
	public CliOptionString(CliOptionString other) {
		super(other);
		this.value = other.value;
	}

	public CliOptionString valueSet(String value) { this.value = value; return this; }
	public String valueGet() { return this.value; }
}