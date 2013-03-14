package org.productivity.java.syslog4j.impl.backlog;

import org.productivity.java.syslog4j.SyslogBackLogHandlerIF;
import org.productivity.java.syslog4j.SyslogIF;

/**
* NullSyslogBackLogHandler can be used if there's no need for a last-chance
* logging mechanism whenever the Syslog protocol fails.
* 
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
* 
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: NullSyslogBackLogHandler.java,v 1.2 2010/10/25 03:50:25 cvs Exp $
*/
public class NullSyslogBackLogHandler implements SyslogBackLogHandlerIF {
	public static final NullSyslogBackLogHandler INSTANCE = new NullSyslogBackLogHandler();
		
	public void initialize() {
		//
	}

	public void down(SyslogIF syslog, String reason) {
		//
	}

	public void up(SyslogIF syslog) {
		//
	}

	public void log(SyslogIF syslog, int level, String message, String reason) {
		//
	}
}
