package org.productivity.java.syslog4j.server;

import java.net.SocketAddress;

public interface SyslogServerSessionEventHandlerIF extends SyslogServerEventHandlerIF {
	public Object sessionOpened(SyslogServerIF syslogServer, SocketAddress socketAddress);
	public void event(Object session, SyslogServerIF syslogServer, SocketAddress socketAddress, SyslogServerEventIF event);
	public void exception(Object session, SyslogServerIF syslogServer, SocketAddress socketAddress, Exception exception);
	public void sessionClosed(Object session, SyslogServerIF syslogServer, SocketAddress socketAddress, boolean timeout);
}
