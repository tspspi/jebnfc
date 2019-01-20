package at.tspi.jcliargs;

import java.util.HashMap;
import java.util.LinkedList;

public class CliParser {
	private HashMap<String, CliOption>			namesShort;
	private HashMap<String, CliOption>			namesLong;

	private HashMap<String, Object>				readOptions;
	private LinkedList<String>					additionalParameters;

	public CliParser() {
		namesShort = new HashMap<String, CliOption>();
		namesLong = new HashMap<String, CliOption>();
		readOptions = new HashMap<String, Object>();
		additionalParameters = new LinkedList<String>();
	}

	public CliParser optionRegister(CliOption opt) {
		if(opt.getNameShort() != null) { namesShort.put(opt.getNameShort(), opt); }
		if(opt.getNameLong() != null) { namesLong.put(opt.getNameLong(), opt); }
		return this;
	}
	public CliParser optionRegister(CliOption opt[]) {
		for(int i = 0; i < opt.length; i++) {
			if(opt[i].getNameShort() != null) { namesShort.put(opt[i].getNameShort(), opt[i]); }
			if(opt[i].getNameLong() != null) { namesLong.put(opt[i].getNameLong(), opt[i]); }
		}
		return this;
	}

	public CliParser parse(String args[]) throws CliParserException, CliParserAlreadyDefinedException, CliParserUnknownOptionException {
		int i;
		for(i = 0; i < args.length; i++) {
			if(args[i].startsWith("-")) {
				CliOption opt = null;
				String sOpt = null;
				if(args[i].startsWith("--")) {
					/* Long Argument */
					opt = namesLong.get(args[i].substring(2));
					sOpt = args[i].substring(2);
				} else {
					/* Short Argument */
					opt = namesShort.get(args[i].substring(1));
					sOpt = args[i].substring(1);
				}
				/* Determine argument type and perform parse ... */
				if(opt instanceof CliOptionBoolean) {
					readOptions.put(opt.getNameLong(), opt);
				} else if(opt instanceof CliOptionString) {
					if(i == (args.length - 1)) {
						throw new CliParserException("Missing value for parameter "+sOpt);
					}
					String value = args[i+1];
					if(readOptions.get(opt.getNameLong()) == null) {
						readOptions.put(opt.getNameLong(), (new CliOptionString((CliOptionString)opt)).valueSet(value));
					} else {
						if(opt.getMax() == 1) { throw new CliParserAlreadyDefinedException("Option "+sOpt+" already specified").optionWhichSet(sOpt); }

						if(readOptions.get(opt.getNameLong()) instanceof CliOptionString) {
							LinkedList<CliOptionString> newList = new LinkedList<CliOptionString>();
							newList.add((CliOptionString)(readOptions.get(opt.getNameLong())));
							newList.add((new CliOptionString((CliOptionString)opt)).valueSet(value));
							readOptions.put(opt.getNameLong(), newList);
						} else {
							((LinkedList<CliOptionString>)(readOptions.get(opt.getNameLong()))).add((new CliOptionString((CliOptionString)opt)).valueSet(value));
						}
					}
					i++;
				} else {
					throw new CliParserUnknownOptionException("Unknown option "+sOpt);
				}
			} else {
				/* "default" parameter */
				additionalParameters.add(args[i]);
			}
		}

		return this;
	}

	public boolean getBoolean(String longname) throws CliParserUnknownOptionException {
		Object genOpt = readOptions.get(longname);

		if(genOpt == null) {
			return false;
		}
		if(genOpt instanceof CliOptionBoolean) {
			return true;
		} else if(genOpt instanceof LinkedList) {
			Object o = ((LinkedList)genOpt).get(0);
			if(o instanceof CliOptionBoolean) {
				return true;
			}
		}
		throw new CliParserUnknownOptionException();
	}
	
	public String getString(String longname) throws CliParserUnknownOptionException {
		Object genOpt = readOptions.get(longname);

		if(genOpt == null) {
			throw new CliParserUnknownOptionException();
		}
		if(genOpt instanceof CliOptionString) {
			return ((CliOptionString)genOpt).valueGet();
		}
		throw new CliParserUnknownOptionException();
	}
	public String getString(String longname, String defaultValue) throws CliParserUnknownOptionException {
		Object genOpt = readOptions.get(longname);

		if(genOpt == null) {
			return defaultValue;
		}
		if(genOpt instanceof CliOptionString) {
			return ((CliOptionString)genOpt).valueGet();
		}
		throw new CliParserUnknownOptionException();
	}

	public String getString(String longname, int idx) throws CliParserUnknownOptionException {
		Object genOpt = readOptions.get(longname);

		if(genOpt == null) {
			throw new CliParserUnknownOptionException();
		}
		if((genOpt instanceof CliOptionString) && (idx == 0)) {
			return ((CliOptionString)genOpt).valueGet();
		}
		if(genOpt instanceof LinkedList) {
			if(((LinkedList)genOpt).size() <= idx) {
				throw new IndexOutOfBoundsException();
			}
			Object o = (((LinkedList)genOpt).get(idx));
			if(o instanceof CliOptionString) {
				return ((CliOptionString)o).valueGet();
			} else {
				throw new CliParserUnknownOptionException();
			}
		}
		throw new CliParserUnknownOptionException();
	}
	public String getString(String longname, int idx, String defaultValue) throws CliParserUnknownOptionException {
		Object genOpt = readOptions.get(longname);

		if(genOpt == null) {
			return defaultValue;
		}
		if((genOpt instanceof CliOptionString) && (idx == 0)) {
			return ((CliOptionString)genOpt).valueGet();
		}
		if(genOpt instanceof LinkedList) {
			if(((LinkedList)genOpt).size() <= idx) {
				return defaultValue;
			}
			Object o = (((LinkedList)genOpt).get(idx));
			if(o instanceof CliOptionString) {
				return ((CliOptionString)o).valueGet();
			} else {
				throw new CliParserUnknownOptionException();
			}
		}
		throw new CliParserUnknownOptionException();
	}
	public int getStringCount(String longname) throws CliParserUnknownOptionException {
		Object genOpt = readOptions.get(longname);

		if(genOpt == null) {
			return 0;
		}
		if(genOpt instanceof CliOptionString) {
			return 1;
		}
		if(genOpt instanceof LinkedList) {
			Object o = (((LinkedList)genOpt).get(0));
			if(o instanceof CliOptionString) {
				return ((LinkedList)genOpt).size();
			} else {
				throw new CliParserUnknownOptionException();
			}
		}
		throw new CliParserUnknownOptionException();
	}

		public int getInteger(String longname) throws CliParserUnknownOptionException {
		Object genOpt = readOptions.get(longname);

		if(genOpt == null) {
			throw new CliParserUnknownOptionException();
		}
		if(genOpt instanceof CliOptionInteger) {
			return ((CliOptionInteger)genOpt).valueGet();
		}
		throw new CliParserUnknownOptionException();
	}
	public int getInteger(String longname, int defaultValue) throws CliParserUnknownOptionException {
		Object genOpt = readOptions.get(longname);

		if(genOpt == null) {
			return defaultValue;
		}
		if(genOpt instanceof CliOptionInteger) {
			return ((CliOptionInteger)genOpt).valueGet();
		}
		throw new CliParserUnknownOptionException();
	}

	public int getIntegerArray(String longname, int idx) throws CliParserUnknownOptionException {
		Object genOpt = readOptions.get(longname);

		if(genOpt == null) {
			throw new CliParserUnknownOptionException();
		}
		if((genOpt instanceof CliOptionInteger) && (idx == 0)) {
			return ((CliOptionInteger)genOpt).valueGet();
		}
		if(genOpt instanceof LinkedList) {
			if(((LinkedList)genOpt).size() <= idx) {
				throw new IndexOutOfBoundsException();
			}
			Object o = (((LinkedList)genOpt).get(idx));
			if(o instanceof CliOptionInteger) {
				return ((CliOptionInteger)o).valueGet();
			} else {
				throw new CliParserUnknownOptionException();
			}
		}
		throw new CliParserUnknownOptionException();
	}
	public int getIntegerArray(String longname, int idx, int defaultValue) throws CliParserUnknownOptionException {
		Object genOpt = readOptions.get(longname);

		if(genOpt == null) {
			return defaultValue;
		}
		if((genOpt instanceof CliOptionInteger) && (idx == 0)) {
			return ((CliOptionInteger)genOpt).valueGet();
		}
		if(genOpt instanceof LinkedList) {
			if(((LinkedList)genOpt).size() <= idx) {
				return defaultValue;
			}
			Object o = (((LinkedList)genOpt).get(idx));
			if(o instanceof CliOptionInteger) {
				return ((CliOptionInteger)o).valueGet();
			} else {
				throw new CliParserUnknownOptionException();
			}
		}
		throw new CliParserUnknownOptionException();
	}
	public int getIntegerArrayCount(String longname) throws CliParserUnknownOptionException {
		Object genOpt = readOptions.get(longname);

		if(genOpt == null) {
			return 0;
		}
		if(genOpt instanceof CliOptionInteger) {
			return 1;
		}
		if(genOpt instanceof LinkedList) {
			Object o = (((LinkedList)genOpt).get(0));
			if(o instanceof CliOptionInteger) {
				return ((LinkedList)genOpt).size();
			} else {
				throw new CliParserUnknownOptionException();
			}
		}
		throw new CliParserUnknownOptionException();
	}


	public LinkedList<String> getAdditional() {
		return additionalParameters;
	}
}