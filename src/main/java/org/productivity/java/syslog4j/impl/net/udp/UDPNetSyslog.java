package org.productivity.java.syslog4j.impl.net.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import org.productivity.java.syslog4j.SyslogRuntimeException;
import org.productivity.java.syslog4j.impl.AbstractSyslogWriter;
import org.productivity.java.syslog4j.impl.net.AbstractNetSyslog;

/**
* UDPNetSyslog is an extension of AbstractSyslog that provides support for
* UDP/IP-based syslog clients.
* 
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
* 
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: UDPNetSyslog.java,v 1.18 2010/10/27 06:18:10 cvs Exp $
*/
public class UDPNetSyslog extends AbstractNetSyslog {
	private static final long serialVersionUID = 5259485504549037999L;
	
	protected DatagramSocket socket = null;
	
	public void initialize() throws SyslogRuntimeException {
		super.initialize();
		
		createDatagramSocket(true);
	}
	
	protected synchronized void createDatagramSocket(boolean initialize) {
		try {
			this.socket = new DatagramSocket();
			
		} catch (SocketException se) {
			if (initialize) {
				if (this.syslogConfig.isThrowExceptionOnInitialize()) {
					throw new SyslogRuntimeException(se);
				}
				
			} else {
				throw new SyslogRuntimeException(se);
			}
		}
		
		if (this.socket == null) {
			throw new SyslogRuntimeException("Cannot seem to get a Datagram socket");
		}
	}

	protected void write(int level, byte[] message) throws SyslogRuntimeException {
    	if (this.socket == null) {
   			createDatagramSocket(false);
    	}
    	
		InetAddress hostAddress = getHostAddress();
		
        DatagramPacket packet = new DatagramPacket(
        	message,
        	message.length,
        	hostAddress,
        	this.syslogConfig.getPort()
        );

        int attempts = 0;
        
        while(attempts != -1 && attempts < (this.netSyslogConfig.getWriteRetries() + 1)) {
	        try {
	        	this.socket.send(packet);
	        	attempts = -1;
	        	
	        } catch (IOException ioe) {
	        	if (attempts == (this.netSyslogConfig.getWriteRetries() + 1)) {
	        		throw new SyslogRuntimeException(ioe);
	        	}
	        }
        }
	}

	public void flush() throws SyslogRuntimeException {
		shutdown();
		
		createDatagramSocket(true);
	}
	
	public void shutdown() throws SyslogRuntimeException {
		if (this.socket != null) {
			this.socket.close();
			this.socket = null;
		}
	}

	public AbstractSyslogWriter getWriter() {
		return null;
	}

	public void returnWriter(AbstractSyslogWriter syslogWriter) {
		//
	}
}
