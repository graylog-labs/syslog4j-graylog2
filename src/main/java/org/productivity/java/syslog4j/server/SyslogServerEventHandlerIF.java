package org.productivity.java.syslog4j.server;

import java.io.Serializable;

public abstract interface SyslogServerEventHandlerIF extends Serializable {
	public void initialize(SyslogServerIF syslogServer);
	public void destroy(SyslogServerIF syslogServer);
}
