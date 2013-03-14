package org.productivity.java.syslog4j.impl;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import org.productivity.java.syslog4j.SyslogConstants;
import org.productivity.java.syslog4j.SyslogRuntimeException;
import org.productivity.java.syslog4j.util.SyslogUtility;

/**
* AbstractSyslogWriter is an implementation of Runnable that supports sending
* syslog messages within a separate Thread or an object pool.
* 
* <p>When used in "threaded" mode (see TCPNetSyslogConfig for the option),
* a queuing mechanism is used (via LinkedList).</p>
* 
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
* 
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: AbstractSyslogWriter.java,v 1.9 2010/10/25 03:50:25 cvs Exp $
*/
public abstract class AbstractSyslogWriter implements Runnable, Serializable {
	private static final long serialVersionUID = 836468466009035847L;
	
	protected AbstractSyslog syslog = null;

	protected List queuedMessages = null;
	
	protected Thread thread = null;

	protected AbstractSyslogConfigIF syslogConfig = null;

	protected boolean shutdown = false;
	
	public void initialize(AbstractSyslog abstractSyslog) {
		this.syslog = abstractSyslog;

		try {
			this.syslogConfig = (AbstractSyslogConfigIF) this.syslog.getConfig();
			
		} catch (ClassCastException cce) {
			throw new SyslogRuntimeException("config must implement interface AbstractSyslogConfigIF");
		}
		
		if (this.syslogConfig.isThreaded()) {
			this.queuedMessages = new LinkedList();
		}
	}
	
	public void queue(int level, byte[] message) {
		synchronized(this.queuedMessages) {
			if (this.syslogConfig.getMaxQueueSize() == -1 || this.queuedMessages.size() < this.syslogConfig.getMaxQueueSize()) {
				this.queuedMessages.add(message);
				
			} else {
				this.syslog.backLog(level,SyslogUtility.newString(syslogConfig,message),"MaxQueueSize (" + this.syslogConfig.getMaxQueueSize() + ") reached");
			}
		}
	}
	
	public void setThread(Thread thread) {
		this.thread = thread;
	}
	
	public boolean hasThread() {
		return this.thread != null && this.thread.isAlive();
	}

	public abstract void write(byte[] message);
	
	public abstract void flush();
	
	public abstract void shutdown();
	
	protected abstract void runCompleted();

	public void run() {
		while(!this.shutdown || !this.queuedMessages.isEmpty()) {
			List queuedMessagesCopy = null;
			
			synchronized(this.queuedMessages) {
				queuedMessagesCopy = new LinkedList(this.queuedMessages);
				this.queuedMessages.clear();
			}
			
			if (queuedMessagesCopy != null) {
				while(!queuedMessagesCopy.isEmpty()) {
					byte[] message = (byte[]) queuedMessagesCopy.remove(0);
							
					try {
						write(message);
						
						this.syslog.setBackLogStatus(false);
							
					} catch (SyslogRuntimeException sre) {
						this.syslog.backLog(SyslogConstants.LEVEL_INFO,SyslogUtility.newString(this.syslog.getConfig(),message),sre);
					}
				}
			}
			
			SyslogUtility.sleep(this.syslogConfig.getThreadLoopInterval());
		}
		
		runCompleted();
	}
}
