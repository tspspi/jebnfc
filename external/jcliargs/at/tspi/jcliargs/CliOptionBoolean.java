package at.tspi.jcliargs;

public class CliOptionBoolean extends CliOption {
	public CliOptionBoolean(String ln) { super(ln); }
	public CliOptionBoolean(String sn, String ln) { super(sn, ln); }
	public CliOptionBoolean(String sn, String ln, long min, long max) { super(sn,ln,min,max); }
	public CliOptionBoolean(String sn, String ln, String d) { super(sn,ln,d); }
	public CliOptionBoolean(String sn, String ln, long min, long max, String d) { super(sn,ln,min,max,d); }
	public CliOptionBoolean(CliOptionBoolean other) { super(other); }
}