package org.productivity.java.syslog4j.server.impl.net.tcp.ssl;

import java.io.IOException;

import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocketFactory;

import org.productivity.java.syslog4j.SyslogRuntimeException;
import org.productivity.java.syslog4j.server.impl.net.tcp.TCPNetSyslogServer;

/**
* SSLTCPNetSyslogServer provides a simple threaded TCP/IP server implementation
* which uses SSL/TLS.
* 
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
* 
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: SSLTCPNetSyslogServer.java,v 1.1 2009/03/29 17:38:58 cvs Exp $
*/
public class SSLTCPNetSyslogServer extends TCPNetSyslogServer {
	public void initialize() throws SyslogRuntimeException {
		super.initialize();
		
		SSLTCPNetSyslogServerConfigIF sslTcpNetSyslogServerConfig = (SSLTCPNetSyslogServerConfigIF) this.tcpNetSyslogServerConfig;
		
		String keyStore = sslTcpNetSyslogServerConfig.getKeyStore();
		
		if (keyStore != null && !"".equals(keyStore.trim())) {
			System.setProperty("javax.net.ssl.keyStore",keyStore);
		}

		String keyStorePassword = sslTcpNetSyslogServerConfig.getKeyStorePassword();
		
		if (keyStorePassword != null && !"".equals(keyStorePassword.trim())) {
			System.setProperty("javax.net.ssl.keyStorePassword",keyStorePassword);
		}

		String trustStore = sslTcpNetSyslogServerConfig.getTrustStore();
		
		if (trustStore != null && !"".equals(trustStore.trim())) {
			System.setProperty("javax.net.ssl.trustStore",trustStore);
		}

		String trustStorePassword = sslTcpNetSyslogServerConfig.getTrustStorePassword();
		
		if (trustStorePassword != null && !"".equals(trustStorePassword.trim())) {
			System.setProperty("javax.net.ssl.trustStorePassword",trustStorePassword);
		}
	}

	protected ServerSocketFactory getServerSocketFactory() throws IOException {
		ServerSocketFactory serverSocketFactory = SSLServerSocketFactory.getDefault();
		
		return serverSocketFactory;
	}
}
