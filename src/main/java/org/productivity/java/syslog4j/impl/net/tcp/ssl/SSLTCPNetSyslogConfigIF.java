package org.productivity.java.syslog4j.impl.net.tcp.ssl;

import org.productivity.java.syslog4j.impl.net.tcp.TCPNetSyslogConfigIF;

/**
* SSLTCPNetSyslogConfigIF is a configuration interface supporting TCP/IP-based
* (over SSL/TLS) Syslog implementations.
* 
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
* 
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: SSLTCPNetSyslogConfigIF.java,v 1.1 2009/03/29 17:38:58 cvs Exp $
*/
public interface SSLTCPNetSyslogConfigIF extends TCPNetSyslogConfigIF {
	public String getKeyStore();	
	public void setKeyStore(String keyStore);
	
	public String getKeyStorePassword();
	public void setKeyStorePassword(String keyStorePassword);

	public String getTrustStore();	
	public void setTrustStore(String trustStore);
	
	public String getTrustStorePassword();
	public void setTrustStorePassword(String trustStorePassword);
}
