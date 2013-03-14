package org.productivity.java.syslog4j.impl.backlog.printstream;

import org.productivity.java.syslog4j.SyslogBackLogHandlerIF;

/**
* SystemErrSyslogBackLogHandler provides a last-chance mechanism to log messages that fail
* (for whatever reason) within the rest of Syslog to System.err.
* 
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
* 
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: SystemErrSyslogBackLogHandler.java,v 1.2 2009/03/29 17:38:59 cvs Exp $
*/
public class SystemErrSyslogBackLogHandler extends PrintStreamSyslogBackLogHandler {
	public static final SyslogBackLogHandlerIF create() {
		return new SystemErrSyslogBackLogHandler();
	}
	
	public SystemErrSyslogBackLogHandler() {
		super(System.err,true);
	}
	public SystemErrSyslogBackLogHandler(boolean appendReason) {
		super(System.err,true,appendReason);
	}
}
