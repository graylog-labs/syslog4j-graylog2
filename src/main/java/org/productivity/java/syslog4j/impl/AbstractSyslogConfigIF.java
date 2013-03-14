package org.productivity.java.syslog4j.impl;

import java.util.List;

import org.productivity.java.syslog4j.SyslogConfigIF;

/**
* AbstractSyslogConfigIF provides an interface for all Abstract Syslog
* configuration implementations.
* 
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
* 
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: AbstractSyslogConfigIF.java,v 1.7 2010/10/29 03:14:20 cvs Exp $
*/
public interface AbstractSyslogConfigIF extends SyslogConfigIF {
	public Class getSyslogWriterClass();
	
	public List getBackLogHandlers();
	
	public List getMessageModifiers();

	public byte[] getSplitMessageBeginText();
	public void setSplitMessageBeginText(byte[] beginText);
	
	public byte[] getSplitMessageEndText();
	public void setSplitMessageEndText(byte[] endText);

	public boolean isThreaded();
	public void setThreaded(boolean threaded);
	
	public boolean isUseDaemonThread();
	public void setUseDaemonThread(boolean useDaemonThread);
	
	public int getThreadPriority();
	public void setThreadPriority(int threadPriority);
	
	public long getThreadLoopInterval();
	public void setThreadLoopInterval(long threadLoopInterval);
	
	public long getMaxShutdownWait();
	public void setMaxShutdownWait(long maxShutdownWait);
	
	public int getWriteRetries();
	public void setWriteRetries(int writeRetries);
	
	public int getMaxQueueSize();	
	/**
	 * Use the (default) value of -1 to allow for a queue of indefinite depth (size).
	 * 
	 * @param maxQueueSize
	 */
	public void setMaxQueueSize(int maxQueueSize);
}
