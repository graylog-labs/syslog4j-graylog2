package org.productivity.java.syslog4j.impl.backlog;

import org.productivity.java.syslog4j.Syslog;
import org.productivity.java.syslog4j.SyslogConstants;
import org.productivity.java.syslog4j.SyslogIF;
import org.productivity.java.syslog4j.SyslogRuntimeException;

/**
* Syslog4jBackLogHandler is used to send Syslog backLog messages to
* another Syslog4j protocol whenever the main Syslog protocol fails.
* 
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
* 
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: Syslog4jBackLogHandler.java,v 1.1 2009/07/25 18:42:47 cvs Exp $
*/
public class Syslog4jBackLogHandler extends AbstractSyslogBackLogHandler {
	protected SyslogIF syslog = null;
	protected int downLevel = SyslogConstants.LEVEL_WARN;
	protected int upLevel = SyslogConstants.LEVEL_WARN;

	public Syslog4jBackLogHandler(String protocol) {
		this.syslog = Syslog.getInstance(protocol);
	}
	
	public Syslog4jBackLogHandler(String protocol, boolean appendReason) {
		this.syslog = Syslog.getInstance(protocol);
		this.appendReason = appendReason;
	}
	
	public Syslog4jBackLogHandler(SyslogIF syslog) {
		this.syslog = syslog;
	}
	
	public Syslog4jBackLogHandler(SyslogIF syslog, boolean appendReason) {
		this.syslog = syslog;
		this.appendReason = appendReason;
	}
	
	public void initialize() throws SyslogRuntimeException {
		// NO-OP
	}

	public void log(SyslogIF syslog, int level, String message, String reason) throws SyslogRuntimeException {
		if (this.syslog.getProtocol().equals(syslog.getProtocol())) {
			throw new SyslogRuntimeException("Ignoring this log entry since the backLog protocol \"" + this.syslog.getProtocol() + "\" is the same as the main protocol");
		}
		
		String combinedMessage = combine(syslog,level,message,reason);
		
		this.syslog.log(level,combinedMessage);
	}

	public void down(SyslogIF syslog, String reason) {
		if (!this.syslog.getProtocol().equals(syslog.getProtocol())) {
			this.syslog.log(this.downLevel,"Syslog protocol \"" + syslog.getProtocol() + "\" is down: " + reason);
		}		
	}

	public void up(SyslogIF syslog) {
		if (!this.syslog.getProtocol().equals(syslog.getProtocol())) {
			this.syslog.log(this.downLevel,"Syslog protocol \"" + syslog.getProtocol() + "\" is up");
		}
	}
}
