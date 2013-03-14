package org.productivity.java.syslog4j.impl.net.tcp.ssl;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;

import org.productivity.java.syslog4j.impl.net.tcp.TCPNetSyslogWriter;

/**
* SSLTCPNetSyslogWriter is an implementation of Runnable that supports sending
* TCP/IP-based (over SSL/TLS) messages within a separate Thread.
* 
* <p>When used in "threaded" mode (see TCPNetSyslogConfig for the option),
* a queuing mechanism is used (via LinkedList).</p>
* 
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
* 
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: SSLTCPNetSyslogWriter.java,v 1.4 2009/03/29 17:38:58 cvs Exp $
*/
public class SSLTCPNetSyslogWriter extends TCPNetSyslogWriter {
	private static final long serialVersionUID = 8944446235285662244L;

	protected SocketFactory obtainSocketFactory() {
		return SSLSocketFactory.getDefault();
	}
}
