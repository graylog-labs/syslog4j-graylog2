package org.productivity.java.syslog4j.server;

import java.net.SocketAddress;


/**
* SyslogServerEventHandlerIF provides an extensible interface for Syslog4j
* server event handlers.
* 
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
* 
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: SyslogServerSessionlessEventHandlerIF.java,v 1.1 2010/11/12 02:56:44 cvs Exp $
*/
public interface SyslogServerSessionlessEventHandlerIF extends SyslogServerEventHandlerIF {
	public void event(SyslogServerIF syslogServer, SocketAddress socketAddress, SyslogServerEventIF event);
	public void exception(SyslogServerIF syslogServer, SocketAddress socketAddress, Exception exception);
}
