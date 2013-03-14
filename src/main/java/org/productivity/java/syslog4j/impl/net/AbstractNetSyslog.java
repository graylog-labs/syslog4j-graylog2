package org.productivity.java.syslog4j.impl.net;

import java.net.InetAddress;

import org.productivity.java.syslog4j.SyslogRuntimeException;
import org.productivity.java.syslog4j.impl.AbstractSyslog;
import org.productivity.java.syslog4j.util.SyslogUtility;

/**
* AbstractNetSyslog is an abstract extension of AbstractSyslog
* that provides support for network-based syslog clients.
* 
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
* 
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: AbstractNetSyslog.java,v 1.7 2009/01/24 22:00:18 cvs Exp $
*/
public abstract class AbstractNetSyslog extends AbstractSyslog {
	private static final long serialVersionUID = -3250858945515853967L;
	
	protected static final Object cachedHostAddressSyncObject = new Object();
	
	protected InetAddress cachedHostAddress = null;
	
	protected AbstractNetSyslogConfigIF netSyslogConfig = null;
	
	protected void initialize() throws SyslogRuntimeException {
		try {
			this.netSyslogConfig = (AbstractNetSyslogConfigIF) this.syslogConfig;
			
		} catch (ClassCastException cce) {
			throw new SyslogRuntimeException("config must implement interface AbstractNetSyslogConfigIF");
		}
	}
	
	/**
	 * @return Returns an object of InetAddress of the local host, using caching if so directed.
	 */
	public InetAddress getHostAddress() {
		InetAddress hostAddress = null;
		
		if (this.netSyslogConfig.isCacheHostAddress()) {
			if (this.cachedHostAddress == null) {
				synchronized(cachedHostAddressSyncObject) {
					if (this.cachedHostAddress == null) {
						this.cachedHostAddress = SyslogUtility.getInetAddress(this.syslogConfig.getHost());
					}
				}
			}
			
			hostAddress = this.cachedHostAddress;
			
		} else {
			hostAddress = SyslogUtility.getInetAddress(this.syslogConfig.getHost());
		}
		
		return hostAddress;
	}
}
