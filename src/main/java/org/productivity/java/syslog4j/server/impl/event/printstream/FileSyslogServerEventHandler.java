package org.productivity.java.syslog4j.server.impl.event.printstream;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class FileSyslogServerEventHandler extends PrintStreamSyslogServerEventHandler {
	private static final long serialVersionUID = -755824686809731430L;

	protected static PrintStream createPrintStream(String fileName, boolean append) throws IOException {
		File file = new File(fileName);
		
		OutputStream os = new FileOutputStream(file,append);
		
		PrintStream printStream = new PrintStream(os);
		
		return printStream;
	}
	
	public FileSyslogServerEventHandler(String fileName) throws IOException {
		super(createPrintStream(fileName,true));		
	}
	
	public FileSyslogServerEventHandler(String fileName, boolean append) throws IOException {
		super(createPrintStream(fileName,append));
	}
}
