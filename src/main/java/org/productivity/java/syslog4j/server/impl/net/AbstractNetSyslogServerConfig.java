package org.productivity.java.syslog4j.server.impl.net;

import org.productivity.java.syslog4j.server.impl.AbstractSyslogServerConfig;

/**
* AbstractNetSyslogServerConfig provides a base abstract implementation of the AbstractSyslogServerConfig
* configuration interface.
* 
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
* 
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: AbstractNetSyslogServerConfig.java,v 1.4 2008/11/07 15:15:41 cvs Exp $
*/
public abstract class AbstractNetSyslogServerConfig extends AbstractSyslogServerConfig {
	private static final long serialVersionUID = -3363374941938350263L;
	
	protected String host = null;
	protected int port = SYSLOG_PORT_DEFAULT;
	
	public String getHost() {
		return this.host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return this.port;
	}

	public void setPort(int port) {
		this.port = port;
	}
}
