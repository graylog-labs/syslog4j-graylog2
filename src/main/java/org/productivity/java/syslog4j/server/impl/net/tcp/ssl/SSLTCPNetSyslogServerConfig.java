package org.productivity.java.syslog4j.server.impl.net.tcp.ssl;

import org.productivity.java.syslog4j.server.impl.net.tcp.TCPNetSyslogServerConfig;

/**
* SSLTCPNetSyslogServerConfig provides configuration for SSLTCPNetSyslogServer.
* 
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
* 
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: SSLTCPNetSyslogServerConfig.java,v 1.1 2009/03/29 17:38:58 cvs Exp $
*/
public class SSLTCPNetSyslogServerConfig extends TCPNetSyslogServerConfig implements SSLTCPNetSyslogServerConfigIF {
	private static final long serialVersionUID = -840102682868286462L;

	protected String keyStore = null;
	protected String keyStorePassword = null;

	protected String trustStore = null;
	protected String trustStorePassword = null;

	public String getKeyStore() {
		return this.keyStore;
	}
	
	public void setKeyStore(String keyStore) {
		this.keyStore = keyStore;
	}
	
	public String getKeyStorePassword() {
		return this.keyStorePassword;
	}
	
	public void setKeyStorePassword(String keyStorePassword) {
		this.keyStorePassword = keyStorePassword;
	}

	public String getTrustStore() {
		return this.trustStore;
	}

	public void setTrustStore(String trustStore) {
		this.trustStore = trustStore;
	}

	public String getTrustStorePassword() {
		return this.trustStorePassword;
	}

	public void setTrustStorePassword(String trustStorePassword) {
		this.trustStorePassword = trustStorePassword;
	}

	public Class getSyslogServerClass() {
		return SSLTCPNetSyslogServer.class;
	}
}
