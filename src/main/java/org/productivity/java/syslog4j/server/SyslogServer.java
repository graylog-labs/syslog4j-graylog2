package org.productivity.java.syslog4j.server;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.productivity.java.syslog4j.Syslog4jVersion;
import org.productivity.java.syslog4j.SyslogConstants;
import org.productivity.java.syslog4j.SyslogRuntimeException;
import org.productivity.java.syslog4j.server.impl.net.tcp.TCPNetSyslogServerConfig;
import org.productivity.java.syslog4j.server.impl.net.udp.UDPNetSyslogServerConfig;
import org.productivity.java.syslog4j.util.SyslogUtility;

/**
 * This class provides a Singleton-based interface for Syslog4j
 * server implementations.
 * 
 * <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
 * of the LGPL license is available in the META-INF folder in all
 * distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
 * 
 * @author &lt;syslog4j@productivity.org&gt;
 * @version $Id: SyslogServer.java,v 1.14 2011/01/23 20:49:12 cvs Exp $
 */
public class SyslogServer implements SyslogConstants {
	private static final long serialVersionUID = -2260889360828258602L;

	private static boolean SUPPRESS_RUNTIME_EXCEPTIONS = false;

	protected static final Map instances = new Hashtable();
	
	static {
		initialize();
	}
	
	private SyslogServer() {
		//
	}

	/**
	 * @return Returns the current version identifier for Syslog4j.
	 */
	public static final String getVersion() {
		return Syslog4jVersion.VERSION;
	}

	
	/**
	 * @param suppress - true to suppress throwing SyslogRuntimeException in many methods of this class, false to throw exceptions (default)
	 */
	public static void setSuppressRuntimeExceptions(boolean suppress) {
		SUPPRESS_RUNTIME_EXCEPTIONS = suppress;
	}
	
	/**
	 * @return Returns whether or not to suppress throwing SyslogRuntimeException in many methods of this class
	 */
	public static boolean getSuppressRuntimeExceptions() {
		return SUPPRESS_RUNTIME_EXCEPTIONS;
	}
	
	/**
	 * Throws SyslogRuntimeException unless it has been suppressed via setSuppressRuntimeException(boolean).
	 * 
	 * @param message
	 * @throws SyslogRuntimeException
	 */
	private static void throwRuntimeException(String message) throws SyslogRuntimeException {
		if (SUPPRESS_RUNTIME_EXCEPTIONS) {
			return;
			
		} else {
			throw new SyslogRuntimeException(message.toString());
		}		
	}

	public static final SyslogServerIF getInstance(String protocol) throws SyslogRuntimeException {
		String syslogProtocol = protocol.toLowerCase();
		
		if (instances.containsKey(syslogProtocol)) {
			return (SyslogServerIF) instances.get(syslogProtocol);
			
		} else {
			throwRuntimeException("SyslogServer instance \"" + syslogProtocol + "\" not defined; use \"tcp\" or \"udp\" or call SyslogServer.createInstance(protocol,config) first");
			return null;
		}
	}
	
	public static final SyslogServerIF getThreadedInstance(String protocol) throws SyslogRuntimeException {
		SyslogServerIF server = getInstance(protocol);

		if (server.getThread() == null) {
			Thread thread = new Thread(server);
			thread.setName("SyslogServer: " + protocol);
			thread.setDaemon(server.getConfig().isUseDaemonThread());
			if (server.getConfig().getThreadPriority() > -1) {
				thread.setPriority(server.getConfig().getThreadPriority());
			}
			
			server.setThread(thread);
			thread.start();
		}
		
		return server;
	}
	
	public static final boolean exists(String protocol) {
		if (protocol == null || "".equals(protocol.trim())) {
			return false;
		}
		
		return instances.containsKey(protocol.toLowerCase());
	}
	
	public static final SyslogServerIF createInstance(String protocol, SyslogServerConfigIF config) throws SyslogRuntimeException {
		if (protocol == null || "".equals(protocol.trim())) {
			throwRuntimeException("Instance protocol cannot be null or empty");
			return null;
		}
		
		if (config == null) {
			throwRuntimeException("SyslogServerConfig cannot be null");
			return null;
		}
		
		String syslogProtocol = protocol.toLowerCase();
		
		SyslogServerIF syslogServer = null;
		
		synchronized(instances) {
			if (instances.containsKey(syslogProtocol)) {
				throwRuntimeException("SyslogServer instance \"" + syslogProtocol + "\" already defined.");
				return null;
			}
			
			try {
				Class syslogClass = config.getSyslogServerClass();
				
				syslogServer = (SyslogServerIF) syslogClass.newInstance();
				
			} catch (ClassCastException cse) {
				throw new SyslogRuntimeException(cse);
				
			} catch (IllegalAccessException iae) {
				throw new SyslogRuntimeException(iae);
				
			} catch (InstantiationException ie) {
				throw new SyslogRuntimeException(ie);
			}
	
			syslogServer.initialize(syslogProtocol,config);
			
			instances.put(syslogProtocol,syslogServer);
		}

		return syslogServer;
	}

	public static final SyslogServerIF createThreadedInstance(String protocol, SyslogServerConfigIF config) throws SyslogRuntimeException {
		createInstance(protocol,config);
		
		SyslogServerIF server = getThreadedInstance(protocol); 
		
		return server;
	}

	public synchronized static final void destroyInstance(String protocol) {
		if (protocol == null || "".equals(protocol.trim())) {
			return;
		}

		String _protocol = protocol.toLowerCase();
		
		if (instances.containsKey(_protocol)) {
			SyslogUtility.sleep(SyslogConstants.THREAD_LOOP_INTERVAL_DEFAULT);
			
			SyslogServerIF syslogServer = (SyslogServerIF) instances.get(_protocol);

			try {
				syslogServer.shutdown();
				
			} finally {
				instances.remove(_protocol);
			}
			
		} else {
			throwRuntimeException("Cannot destroy server protocol \"" + protocol + "\" instance; call shutdown instead");
			return;
		}
	}
	
	public synchronized static final void destroyInstance(SyslogServerIF syslogServer) {
		if (syslogServer == null) {
			return;
		}
		
		String protocol = syslogServer.getProtocol().toLowerCase();
		
		if (instances.containsKey(protocol)) {
			SyslogUtility.sleep(SyslogConstants.THREAD_LOOP_INTERVAL_DEFAULT);
			
			try {
				syslogServer.shutdown();
				
			} finally {
				instances.remove(protocol);
			}
			
		} else {
			throwRuntimeException("Cannot destroy server protocol \"" + protocol + "\" instance; call shutdown instead");
		}
	}
	
	public synchronized static void initialize() {
		createInstance(UDP,new UDPNetSyslogServerConfig());
		createInstance(TCP,new TCPNetSyslogServerConfig());
	}
	
	public synchronized static final void shutdown() throws SyslogRuntimeException {
		Set protocols = instances.keySet();
		
		Iterator i = protocols.iterator();
		
		while(i.hasNext()) {
			String protocol = (String) i.next();
			
			SyslogServerIF syslogServer = (SyslogServerIF) instances.get(protocol);

			syslogServer.shutdown();
		}

		instances.clear();
	}
	
	public static void main(String[] args) throws Exception {
		SyslogServerMain.main(args);
	}
}
