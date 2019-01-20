import at.tspi.jcliargs.*;

public class testprog {
	public static void main(String args[]) {
		CliParser cp = new CliParser();

		cp.optionRegister(new CliOptionBoolean("a", "testa"));
		cp.optionRegister(new CliOptionBoolean("b", "testb"));
		cp.optionRegister(new CliOptionString("c", "testc"));

		try {
			cp.parse(args);
		} catch(CliParserAlreadyDefinedException e) {
			e.printStackTrace();
		} catch(CliParserUnknownOptionException e) {
			e.printStackTrace();
		} catch(CliParserException e) {
			e.printStackTrace();
		}

		try { if(cp.getBoolean("testa")) { System.out.println("Test A is SET"); } else { System.out.println("Test A is UNSET"); } } catch(CliParserUnknownOptionException e) { e.printStackTrace(); }
		try { if(cp.getBoolean("testb")) { System.out.println("Test B is SET"); } else { System.out.println("Test B is UNSET"); } } catch(CliParserUnknownOptionException e) { e.printStackTrace(); }
		
		try {
			System.out.print("Parameter C is ");
			if(cp.getStringCount("testc") > 1) {
				System.out.println("multiple");
				for(int i = 0; i < cp.getStringCount("testc"); i++) {
					System.out.println("\t" + cp.getString("testc", i));
				}
			} else {
				System.out.println(cp.getString("testc", "UNDEFINED"));
			}
		} catch(CliParserUnknownOptionException e) { e.printStackTrace(); }

		System.out.println("Additional Parameters:");
		for(String s : cp.getAdditional()) {
			System.out.println("\t" + s);
		}

		System.out.println("Done");
	}
}