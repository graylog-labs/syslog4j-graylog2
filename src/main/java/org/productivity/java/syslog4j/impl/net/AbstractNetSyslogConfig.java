package org.productivity.java.syslog4j.impl.net;

import org.productivity.java.syslog4j.impl.AbstractSyslogConfig;

/**
* AbstractNetSyslogConfig is an abstract extension of AbstractSyslogConfig
* that provides configuration support for network-based syslog clients.
* 
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
* 
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: AbstractNetSyslogConfig.java,v 1.12 2010/10/25 03:50:25 cvs Exp $
*/
public abstract class AbstractNetSyslogConfig extends AbstractSyslogConfig implements AbstractNetSyslogConfigIF {
	private static final long serialVersionUID = 7240133962159244924L;

	protected String host = SYSLOG_HOST_DEFAULT;
	protected int port = SYSLOG_PORT_DEFAULT;
	
	protected boolean cacheHostAddress = CACHE_HOST_ADDRESS_DEFAULT;	
	
	protected int maxQueueSize = MAX_QUEUE_SIZE_DEFAULT;
	
	public AbstractNetSyslogConfig() {
		//
	}
	
	public AbstractNetSyslogConfig(int facility) {
		this.facility = facility;
	}

	public AbstractNetSyslogConfig(int facility, String host) {
		this.facility = facility;
		this.host = host;
	}

	public AbstractNetSyslogConfig(String host) {
		this.host = host;
	}

	public AbstractNetSyslogConfig(int facility, String host, int port) {
		this.facility = facility;
		this.host = host;
		this.port = port;
	}

	public AbstractNetSyslogConfig(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public boolean isCacheHostAddress() {
		return this.cacheHostAddress;
	}
	
	public void setCacheHostAddress(boolean cacheHostAddress) {
		this.cacheHostAddress = cacheHostAddress;
	}
	
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

	public int getMaxQueueSize() {
		return maxQueueSize;
	}

	public void setMaxQueueSize(int maxQueueSize) {
		this.maxQueueSize = maxQueueSize;
	}
}
