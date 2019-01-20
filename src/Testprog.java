import at.tspi.ebnf.parser.Parser;

import java.util.HashMap;

import java.lang.IllegalArgumentException;

import java.io.File;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;

import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;

import java.nio.charset.Charset;

import at.tspi.ebnf.parser.Preprocessor;
import at.tspi.ebnf.parser.ParserPreprocessorException;
import at.tspi.ebnf.parser.ParserDataSource;
import at.tspi.ebnf.parser.ParserException;
import at.tspi.ebnf.parser.ParserLogger;
import at.tspi.ebnf.parser.ParserElement;
import at.tspi.ebnf.parser.ParserElementTerminal;
import at.tspi.ebnf.parser.ParserElementSequential;
import at.tspi.ebnf.parser.ParserElementChoice;
import at.tspi.ebnf.parser.ParserElementConstant;
import at.tspi.ebnf.parser.ParserGraphemeCluster;

public class Testprog {
	private class TestTerminalABC extends ParserElementTerminal {
		TestTerminalABC(ParserElement parent) {
			super(parent, "ABC", 1, 5);

			allowedGraphemeClusters = new HashMap<String, Boolean>();
			allowedGraphemeClusters.put("A", true);
			allowedGraphemeClusters.put("B", true);
			allowedGraphemeClusters.put("C", true);
		}

		public ParserElement factory(ParserElement parent) {
			return new TestTerminalABC(parent);
		}

		public String toString() { return "ABC Node"; }
	}
	private class TestTerminalDEF extends ParserElementTerminal {
		TestTerminalDEF(ParserElement parent) {
			super(parent, "DEF", 1, 0);		// Set to 0,0 for optional!

			allowedGraphemeClusters = new HashMap<String, Boolean>();
			allowedGraphemeClusters.put("D", true);
			allowedGraphemeClusters.put("E", true);
			allowedGraphemeClusters.put("F", true);
		}

		public ParserElement factory(ParserElement parent) {
			return new TestTerminalDEF(parent);
		}

		public String toString() { return "DEF Node"; }
	}
	private class TestTerminalGHI extends ParserElementTerminal {
		TestTerminalGHI(ParserElement parent) {
			super(parent, "GHI", 1, 0);

			allowedGraphemeClusters = new HashMap<String, Boolean>();
			allowedGraphemeClusters.put("G", true);
			allowedGraphemeClusters.put("H", true);
			allowedGraphemeClusters.put("I", true);
		}

		public ParserElement factory(ParserElement parent) {
			return new TestTerminalGHI(parent);
		}

		public String toString() { return "GHI Node"; }
	}
	private class TestSequenceABC_DEF extends ParserElementChoice {
		TestSequenceABC_DEF(ParserElement parent) {
			super(parent, "ABC-DEF", new ParserElement[] { new TestTerminalABC(null), new TestTerminalDEF(null) } );
		}

		public ParserElement factory(ParserElement parent) {
			return new TestSequenceABC_DEF(parent);
		}

		public String toString() { return "ABC or DEF"; }
	}
	private class TestSequenceABCDEF_GHI extends ParserElementSequential {
		TestSequenceABCDEF_GHI(ParserElement parent) {
			super(parent, "(ABC-DEF)-GHI", new ParserElement[] { new TestSequenceABC_DEF(null), new TestConstant_TEST(null), new TestTerminalGHI(null) } );
		}

		public ParserElement factory(ParserElement parent) {
			return new TestSequenceABCDEF_GHI(parent);
		}

		public String toString() { return "(ABC | DEF), GHI"; }
	}
	private class TestConstant_TEST extends ParserElementConstant {
		TestConstant_TEST(ParserElement parent) {
			super(parent, "TEST", "Test Constant", false);
		}

		public ParserElement factory(ParserElement parent) {
			return new TestConstant_TEST(parent);
		}

		public String toString() { return "TEST Constant"; }
	}

	public class ParserAsciiFileSource extends ParserDataSource {
		public class ParserAsciiFileSourceMark extends ParserDataSource.Mark {
			private long fp;
			private long lineNumber;
			private long charIndex;

			public ParserAsciiFileSourceMark(long fp, long lineNumber, long charIndex) { this.fp = fp; this.lineNumber = lineNumber; this.charIndex = charIndex; }
			public long getFP() 		{ return this.fp; }
			public long getLineNumber()	{ return this.lineNumber; }
			public long getCharIndex() 	{ return this.charIndex; }
		}

		private RandomAccessFile raf = null;
		private int previousLinebreak = 0; // 0: None, 1: N, 2: R

		public ParserAsciiFileSource(String filename) throws FileNotFoundException {
			super(filename);

			File file = new File(filename);
			Charset encoding = Charset.defaultCharset();

			raf = new RandomAccessFile(file, "r"); // Read only access for our reader ... of course ...
		}

		public ParserDataSource.Mark mark() throws IOException { return new ParserAsciiFileSourceMark(raf.getFilePointer(), this.lineNumber, this.charIndex); }
		public void seek(ParserDataSource.Mark mark) throws ParserException, IOException {
			if(!(mark instanceof ParserAsciiFileSourceMark)) {
				throw new IllegalArgumentException("ASCII File reader only accepts ASCII marks");
			}
			ParserAsciiFileSourceMark m = (ParserAsciiFileSourceMark)mark;
			long fp = m.getFP();
			if(fp > 0) { fp--; } // We always redeliver the symbol at mark ...
			raf.seek(fp); // Seek ...
			this.lineNumber = m.getLineNumber();
			this.charIndex = m.getCharIndex();
		}
		public ParserGraphemeCluster getNextCluster() throws IOException {
			int nextByte;

			for(;;) {
				nextByte = raf.read();
				if(nextByte < 0) { return null; } // We return null on EOF
				if((nextByte & (~0x7F)) != 0) { throw new UnsupportedEncodingException("ASCII file contains non ASCII symbols"); } // ASCII validation

				char ch = (char)nextByte;
				if(ch == '\n') {
					if(previousLinebreak == 2) { previousLinebreak = 0; continue; }
					previousLinebreak = 1;
					this.lineNumber++;
					this.charIndex = 0;
					continue;
				} else if(ch == '\r') {
					if(previousLinebreak == 1) { previousLinebreak = 0; continue; }
					previousLinebreak = 2;
					this.lineNumber++;
					this.charIndex = 0;
					continue;
				} else {
					previousLinebreak = 0;
				}
				this.charIndex++;
				return new ParserGraphemeCluster(new String(new char[] { ch }));
			}
		}
	}

	private class ParserTest extends Parser {
		ParserTest() { super(); }

		protected void initializeRoot() {
			rootElement = new TestSequenceABCDEF_GHI(null);
			currentElement.push(rootElement);
		}
	}

	private class PreprocessorSimple extends ParserDataSource implements Preprocessor {
		private ParserDataSource 		currentSource;
		private int						whitespaceMode;

		public PreprocessorSimple() { super(null); this.currentSource = null; whitespaceMode = Preprocessor.VALUE_WHITESPACE__IGNORE; }
		public PreprocessorSimple(ParserDataSource src) { super(src.getDSName()); this.currentSource = src; whitespaceMode = Preprocessor.VALUE_WHITESPACE__IGNORE; }

		public void setParserDataSource(ParserDataSource src) { this.currentSource = src; }
		public ParserDataSource getCurrentSource() { return this.currentSource; }

		public String getDSName() 		{ return this.currentSource.getDSName(); }

		/* ParserDataSource */
		public long getLineNumber() 	{ return this.currentSource.getLineNumber(); }
		public long getCharIndex() 		{ return this.currentSource.getCharIndex(); }

		/* Functions implemented by children */
		public ParserDataSource.Mark mark() throws java.io.IOException { return this.currentSource.mark(); }
		public void seek(ParserDataSource.Mark mark) throws ParserException, java.io.IOException { this.currentSource.seek(mark); }
		public ParserGraphemeCluster getNextCluster() throws UnsupportedEncodingException, java.io.IOException {
			ParserGraphemeCluster cluster = this.currentSource.getNextCluster();
			
			return cluster;
		}

		/* Preprocessor */
		public void setMode(int property, int value) throws ParserPreprocessorException {
			if(property == Preprocessor.PROPERTY_WHITESPACE) {
				switch(value) {
					case Preprocessor.VALUE_WHITESPACE__IGNORE: this.whitespaceMode = Preprocessor.VALUE_WHITESPACE__IGNORE; return;
					case Preprocessor.VALUE_WHITESPACE__REDUCE: this.whitespaceMode = Preprocessor.VALUE_WHITESPACE__REDUCE; return;
					case Preprocessor.VALUE_WHITESPACE__STRIP: this.whitespaceMode = Preprocessor.VALUE_WHITESPACE__STRIP; return;
					default: throw new ParserPreprocessorException("Unknown setting for whitespace property");
				}
			}
			throw new ParserPreprocessorException("Unknown property");
		}
	}

	private void displayRecursiveTree(ParserElement e, int depth) {
		System.out.println(e.toString(depth));
		for(ParserElement e2 : e) {
			displayRecursiveTree(e2, depth+1);
		}
	}

	public void objMain(String args[]) {
		Parser p = new ParserTest();
		int lineNum = 1;
		int charNum = 0;

		try {
			ParserAsciiFileSource source = new ParserAsciiFileSource("testinput.txt");
			PreprocessorSimple preproc = new PreprocessorSimple(source);

			ParserGraphemeCluster c = null;
			while((c = preproc.getNextCluster()) != null) {
				p.parseNext(c, preproc.getDSName(), 0, 0, preproc, null);
			}
			p.parseNext(null, preproc.getDSName(), 0, 0, preproc, null);

			displayRecursiveTree(p.getRootElement(), 0);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		Testprog prog = new Testprog();
		prog.objMain(args);

		System.out.println("Done");
	}
}