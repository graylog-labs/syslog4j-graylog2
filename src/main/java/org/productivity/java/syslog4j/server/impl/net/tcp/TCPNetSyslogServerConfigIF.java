package org.productivity.java.syslog4j.server.impl.net.tcp;

import org.productivity.java.syslog4j.server.SyslogServerConfigIF;

/**
* TCPNetSyslogServerConfigIF provides configuration for TCPNetSyslogServer.
* 
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
* 
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: TCPNetSyslogServerConfigIF.java,v 1.3 2010/11/28 01:38:08 cvs Exp $
*/
public interface TCPNetSyslogServerConfigIF extends SyslogServerConfigIF {
	public final static byte MAX_ACTIVE_SOCKETS_BEHAVIOR_BLOCK = 0;
	public final static byte MAX_ACTIVE_SOCKETS_BEHAVIOR_REJECT = 1;
	
	public int getTimeout();
	public void setTimeout(int timeout);
	
	public int getBacklog();
	public void setBacklog(int backlog);
	
	public int getMaxActiveSockets();
	public void setMaxActiveSockets(int maxActiveSockets);
	
	public byte getMaxActiveSocketsBehavior();
	public void setMaxActiveSocketsBehavior(byte maxActiveSocketsBehavior);
}
