package org.productivity.java.syslog4j.impl.net.tcp;

import org.productivity.java.syslog4j.SyslogRuntimeException;
import org.productivity.java.syslog4j.impl.AbstractSyslogWriter;
import org.productivity.java.syslog4j.impl.net.AbstractNetSyslog;

/**
* TCPNetSyslog is an extension of AbstractSyslog that provides support for
* TCP/IP-based syslog clients.
* 
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
* 
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: TCPNetSyslog.java,v 1.21 2010/11/28 04:43:31 cvs Exp $
*/
public class TCPNetSyslog extends AbstractNetSyslog {
	private static final long serialVersionUID = -2157528355215068721L;

	protected TCPNetSyslogWriter writer = null;
	
	protected TCPNetSyslogConfigIF tcpNetSyslogConfig = null;
	
	public void initialize() throws SyslogRuntimeException {
		super.initialize();
		
		try {
			this.tcpNetSyslogConfig = (TCPNetSyslogConfigIF) this.syslogConfig;
			
		} catch (ClassCastException cce) {
			throw new SyslogRuntimeException("config must implement interface TCPNetSyslogConfigIF");
		}
	}

	public AbstractSyslogWriter getWriter() {
		return getWriter(true);
	}

	public synchronized AbstractSyslogWriter getWriter(boolean create) {
		if (this.writer != null || !create) {
			return this.writer;
		}
		
		this.writer = (TCPNetSyslogWriter) createWriter();

		if (this.tcpNetSyslogConfig.isThreaded()) {
			createWriterThread(this.writer);
		}

		return this.writer;
	}
	
	protected void write(int level, byte[] message) throws SyslogRuntimeException {
		AbstractSyslogWriter syslogWriter = getWriter();
		
		try {
			if (syslogWriter.hasThread()) {
				syslogWriter.queue(level,message);
				
			} else {
				synchronized(syslogWriter) {
					syslogWriter.write(message);
				}
			}
			
		} finally {
			returnWriter(syslogWriter);
		}
	}

	public void flush() throws SyslogRuntimeException {
		AbstractSyslogWriter syslogWriter = getWriter(false);
		
		if (syslogWriter != null) {
			syslogWriter.flush();
		}
	}

	public void shutdown() throws SyslogRuntimeException {
		AbstractSyslogWriter syslogWriter = getWriter(false);
	
		if (syslogWriter != null) {
			syslogWriter.shutdown();
		}
	}

	public void returnWriter(AbstractSyslogWriter syslogWriter) {
		//
	}
}
