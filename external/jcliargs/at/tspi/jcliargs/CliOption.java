package at.tspi.jcliargs;

public abstract class CliOption {
	private String				shortName;
	private String				longName;

	private long				min;
	private long				max;

	private String				description;

	public String getNameShort() 																{ return this.shortName; }
	public String getNameLong() 																{ return this.longName; }
	public long getMin() 																		{ return this.min; }
	public long getMax() 																		{ return this.max; }
	public String getDescription() 																{ return this.description; }

	public CliOption setNameShort(String nameShort) 											{ this.shortName = nameShort; return this; }
	public CliOption setNameLong(String nameLong) 												{ this.longName = nameLong; return this; }
	public CliOption setMin(long min) 															{ this.min = min; return this; }
	public CliOption setMax(long max) 															{ this.max = max; return this; }
	public CliOption setMinMax(long min, long max) 												{ this.min = min; this.max = max; return this; }

	public CliOption(String longName) 															{ this.longName = longName; }
	public CliOption(String shortName, String longName) 										{ this.longName = longName; this.shortName = shortName; }
	public CliOption(String shortName, String longName, long min, long max) 					{ this.longName = longName; this.shortName = shortName; this.min = min; this.max = max; }
	public CliOption(String shortName, String longName, String description) 					{ this.longName = longName; this.shortName = shortName; this.description = description; }
	public CliOption(String shortName, String longName, long min, long max, String description)	{ this.longName = longName; this.shortName = shortName; this.min = min; this.max = max; this.description = description; }

	public CliOption(CliOption other) {
		this.shortName = other.shortName;
		this.longName = other.longName;
		this.min = other.min;
		this.max = other.max;
		this.description = other.description;
	}
}