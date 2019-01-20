package at.tspi.ebnf.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;

public class ASCIIFileSource extends ParserDataSource {
	public class ASCIIFileSourceMark extends ParserDataSource.Mark {
		private String fileName;
		private long fp;
		private long lineNumber;
		private long charIndex;

		public ASCIIFileSourceMark(String fileName, long fp, long lineNumber, long charIndex) { this.fileName = fileName; this.fp = fp; this.lineNumber = lineNumber; this.charIndex = charIndex; }
		public long getFP() 		{ return this.fp; }
		public long getLineNumber()	{ return this.lineNumber; }
		public long getCharIndex() 	{ return this.charIndex; }
		public String getFileName() { return this.fileName; }

		public String toString() 	{ return "" + this.fp; }
	}

	private RandomAccessFile raf = null;
	private int previousLinebreak = 0; // 0: None, 1: N, 2: R

	public ASCIIFileSource(String filename) throws FileNotFoundException {
		super(filename);

		File file = new File(filename);
		// Charset encoding = Charset.defaultCharset();

		raf = new RandomAccessFile(file, "r"); // Read only access for our reader ... of course ...
	}

	public ParserDataSource.Mark mark() throws IOException { return new ASCIIFileSourceMark(this.getDSName(), raf.getFilePointer(), this.lineNumber, this.charIndex); }
	public void seek(ParserDataSource.Mark mark) throws ParserException, IOException {
		if(!(mark instanceof ASCIIFileSourceMark)) {
			throw new IllegalArgumentException("ASCII File reader only accepts ASCII marks");
		}
		ASCIIFileSourceMark m = (ASCIIFileSourceMark)mark;
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
			if((nextByte & (~0xFF)) != 0) { throw new UnsupportedEncodingException("ASCII file contains non ASCII symbols"); } // ASCII validation

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
