package org.productivity.java.syslog4j.impl.log4j;

import org.apache.log4j.helpers.LogLog;

/**
 * Syslog4jAppender provides a Log4j Appender wrapper for Syslog4j.
 * 
 * <p>Note: Syslog4jAppender does NOT extend Log4j's SyslogAppender.</p>
 * 
 * <p>Example log4j.xml configuration:</p>
 * 
 * <pre>
 * <code>
   &lt;appender name="Syslog4j" class="org.productivity.java.syslog4j.impl.log4j.Syslog4jAppender"&gt;
		&lt;param name="Facility" value="user"/&gt;
		&lt;param name="Protocol" value="tcp"/&gt;
		&lt;param name="Host" value="192.168.0.1"/&gt;
		&lt;layout class="org.apache.log4j.PatternLayout"&gt;
			&lt;param name="ConversionPattern" value="%d{ABSOLUTE} %-5p [%c{1}] %m"/&gt;
		&lt;/layout&gt;
   &lt;/appender&gt;
 * </code>
 * </pre>
 * 
 * <p>All available parameters are:</p>
 * 
 * <ul>
 * 	<li>ident</li>
 * 	<li>localName</li>
 * 	<li>protocol</li>
 * 	<li>facility</li>
 * 	<li>host</li>
 * 	<li>port</li>
 * 	<li>charSet</li>
 * 	<li>threaded</li>
 * 	<li>threadLoopInterval</li>
 * 	<li>splitMessageBeginText</li>
 * 	<li>splitMessageEndText</li>
 * 	<li>maxMessageLength</li>
 * 	<li>maxShutdownWait</li>
 * 	<li>writeRetries</li>
 * 	<li>truncateMessage</li>
 * 	<li>useStructuredData</li>
 * </ul>
 * 
 * <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
 * of the LGPL license is available in the META-INF folder in all
 * distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
 * 
 * @author &lt;syslog4j@productivity.org&gt;
 * @version $Id: Syslog4jAppender.java,v 1.2 2011/01/23 20:49:12 cvs Exp $
 */
public class Syslog4jAppender extends Syslog4jAppenderSkeleton {
	private static final long serialVersionUID = -6072552977605816670L;
	
	public String initialize() {
		if (this.protocol == null) {
			this.protocol = UDP;
		}
		
		return this.protocol;
	}

	public boolean getHeader() {
		return false;
	}
	
	public void setHeader(boolean header) {
		LogLog.warn("Syslog4jAppender ignores the \"Header\" parameter.");
	}

	public String getSyslogHost() {
		return this.host;
	}

	public void setSyslogHost(String host) {
		this.host = host;
	}
}
