package org.productivity.java.syslog4j.server;

import java.util.List;

import org.productivity.java.syslog4j.SyslogCharSetIF;
import org.productivity.java.syslog4j.SyslogConstants;
import org.productivity.java.syslog4j.SyslogRuntimeException;

/**
* SyslogServerConfigIF provides a common, extensible configuration interface for all
* implementations of SyslogServerIF.
* 
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
* 
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: SyslogServerConfigIF.java,v 1.12 2011/01/11 05:11:13 cvs Exp $
*/
public interface SyslogServerConfigIF extends SyslogConstants, SyslogCharSetIF {
	public Class getSyslogServerClass();

	public String getHost();
	public void setHost(String host) throws SyslogRuntimeException;

	public int getPort();
	public void setPort(int port) throws SyslogRuntimeException;
	
	public boolean isUseDaemonThread();
	public void setUseDaemonThread(boolean useDaemonThread);
	
	public int getThreadPriority();
	public void setThreadPriority(int threadPriority);
	
	public List getEventHandlers();
	
	public long getShutdownWait();
	public void setShutdownWait(long shutdownWait);
	
	public void addEventHandler(SyslogServerEventHandlerIF eventHandler);
	public void insertEventHandler(int pos, SyslogServerEventHandlerIF eventHandler);
	public void removeEventHandler(SyslogServerEventHandlerIF eventHandler);
	public void removeAllEventHandlers();
	
	public boolean isUseStructuredData();
	public void setUseStructuredData(boolean useStructuredData);
	
	public Object getDateTimeFormatter();
	public void setDateTimeFormatter(Object dateTimeFormatter);
}
