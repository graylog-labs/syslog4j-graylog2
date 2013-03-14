package org.productivity.java.syslog4j;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.productivity.java.syslog4j.impl.net.tcp.TCPNetSyslogConfig;
import org.productivity.java.syslog4j.impl.net.udp.UDPNetSyslogConfig;
import org.productivity.java.syslog4j.impl.unix.UnixSyslogConfig;
import org.productivity.java.syslog4j.impl.unix.socket.UnixSocketSyslogConfig;
import org.productivity.java.syslog4j.util.OSDetectUtility;
import org.productivity.java.syslog4j.util.SyslogUtility;

/**
 * This class provides a Singleton interface for Syslog4j client implementations.
 * 
 * <p>Usage examples:</p>
 * 
 * <b>Direct</b>
 * <pre>
 * Syslog.getInstance("udp").info("log message");
 * </pre>
 * 
 * <b>Via Instance</b>
 * <pre>
 * SyslogIF syslog = Syslog.getInstance("udp");
 * syslog.info();
 * </pre>
 * 
 * <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
 * of the LGPL license is available in the META-INF folder in all
 * distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
 * 
 * @author &lt;syslog4j@productivity.org&gt;
 * @version $Id: Syslog.java,v 1.23 2011/01/23 20:49:12 cvs Exp $
 */
public final class Syslog implements SyslogConstants {
	private static final long serialVersionUID = -4662318148650646144L;
	
	private static boolean SUPPRESS_RUNTIME_EXCEPTIONS = false;
	
	protected static final Map instances = new Hashtable();
	
	static {
		initialize();
	}
	
	/**
	 * Syslog is a singleton.
	 */
	private Syslog() {
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
	 * @return Returns whether or not to suppress throwing SyslogRuntimeException in many methods of this class.
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

	/**
	 * Use getInstance(protocol) as the starting point for Syslog4j.
	 * 
	 * @param protocol - the Syslog protocol to use, e.g. "udp", "tcp", "unix_syslog", "unix_socket", or a custom protocol
	 * @return Returns an instance of SyslogIF.
	 * @throws SyslogRuntimeException
	 */
	public static final SyslogIF getInstance(String protocol) throws SyslogRuntimeException {
		String _protocol = protocol.toLowerCase();
		
		if (instances.containsKey(_protocol)) {
			return (SyslogIF) instances.get(_protocol);
			
		} else {
			StringBuffer message = new StringBuffer("Syslog protocol \"" + protocol + "\" not defined; call Syslogger.createSyslogInstance(protocol,config) first");
			
			if (instances.size() > 0) {
				message.append(" or use one of the following instances: ");
				
				Iterator i = instances.keySet().iterator();
				while (i.hasNext()) {
					String k = (String) i.next();
				
					message.append(k);
					if (i.hasNext()) {
						message.append(' ');
					}
				}
			}
			
			throwRuntimeException(message.toString());
			return null;
		}
	}
	
	/**
	 * Use createInstance(protocol,config) to create your own Syslog instance.
	 * 
	 * <p>First, create an implementation of SyslogConfigIF, such as UdpNetSyslogConfig.</p>
	 * 
	 * <p>Second, configure that configuration instance.</p>
	 * 
	 * <p>Third, call createInstance(protocol,config) using a short &amp; simple
	 * String for the protocol argument.</p>
	 * 
	 * <p>Fourth, either use the returned instance of SyslogIF, or in later code
	 * call getInstance(protocol) with the protocol chosen in the previous step.</p> 
	 * 
	 * @param protocol 
	 * @param config
	 * @return Returns an instance of SyslogIF.
	 * @throws SyslogRuntimeException
	 */
	public static final SyslogIF createInstance(String protocol, SyslogConfigIF config) throws SyslogRuntimeException {
		if (protocol == null || "".equals(protocol.trim())) {
			throwRuntimeException("Instance protocol cannot be null or empty");
			return null;
		}
		
		if (config == null) {
			throwRuntimeException("SyslogConfig cannot be null");
			return null;
		}
		
		String syslogProtocol = protocol.toLowerCase();
		
		SyslogIF syslog = null;
		
		synchronized(instances) {
			if (instances.containsKey(syslogProtocol)) {
				throwRuntimeException("Syslog protocol \"" + protocol + "\" already defined");
				return null;
			}
			
			try {
				Class syslogClass = config.getSyslogClass();
				
				syslog = (SyslogIF) syslogClass.newInstance();
				
			} catch (ClassCastException cse) {
				if (!config.isThrowExceptionOnInitialize()) {
					throw new SyslogRuntimeException(cse);
					
				} else {
					return null;
				}
				
			} catch (IllegalAccessException iae) {
				if (!config.isThrowExceptionOnInitialize()) {
					throw new SyslogRuntimeException(iae);
					
				} else {
					return null;
				}
				
			} catch (InstantiationException ie) {
				if (!config.isThrowExceptionOnInitialize()) {
					throw new SyslogRuntimeException(ie);
					
				} else {
					return null;
				}
			}
	
			syslog.initialize(syslogProtocol,config);
			
			instances.put(syslogProtocol,syslog);
		}

		return syslog;
	}

	/**
	 * initialize() sets up the default TCP and UDP Syslog protocols, as
	 * well as UNIX_SYSLOG and UNIX_SOCKET (if running on a Unix-based system).
	 */
	public synchronized static final void initialize() {
		createInstance(UDP,new UDPNetSyslogConfig());
		createInstance(TCP,new TCPNetSyslogConfig());
		
		if (OSDetectUtility.isUnix() && SyslogUtility.isClassExists(JNA_NATIVE_CLASS)) {
			createInstance(UNIX_SYSLOG,new UnixSyslogConfig());
			createInstance(UNIX_SOCKET,new UnixSocketSyslogConfig());
		}
	}
	
	/**
	 * @param protocol - Syslog protocol
	 * @return Returns whether the protocol has been previously defined.
	 */
	public static final boolean exists(String protocol) {
		if (protocol == null || "".equals(protocol.trim())) {
			return false;
		}
		
		return instances.containsKey(protocol.toLowerCase());
	}
	
	/**
	 * shutdown() gracefully shuts down all defined Syslog protocols,
	 * which includes flushing all queues and connections and finally
	 * clearing all instances (including those initialized by default).
	 */
	public synchronized static final void shutdown() {
		Set protocols = instances.keySet();
		
		if (protocols.size() > 0) {
			Iterator i = protocols.iterator();
			
			SyslogUtility.sleep(SyslogConstants.THREAD_LOOP_INTERVAL_DEFAULT);
			
			while(i.hasNext()) {
				String protocol = (String) i.next();
				
				SyslogIF syslog = (SyslogIF) instances.get(protocol);
	
				syslog.shutdown();
			}

			instances.clear();
		}
	}

	/**
	 * destroyInstance() gracefully shuts down the specified Syslog protocol and
	 * removes the instance from Syslog4j.
	 * 
	 * @param protocol - the Syslog protocol to destroy
	 * @throws SyslogRuntimeException
	 */
	public synchronized static final void destroyInstance(String protocol) throws SyslogRuntimeException {
		if (protocol == null || "".equals(protocol.trim())) {
			return;
		}
		
		String _protocol = protocol.toLowerCase();
		
		if (instances.containsKey(_protocol)) {
			SyslogUtility.sleep(SyslogConstants.THREAD_LOOP_INTERVAL_DEFAULT);
			
			SyslogIF syslog = (SyslogIF) instances.get(_protocol);

			try {
				syslog.shutdown();
				
			} finally {
				instances.remove(_protocol);
			}
			
		} else {
			throwRuntimeException("Cannot destroy protocol \"" + protocol + "\" instance; call shutdown instead");
			return;
		}
	}

	/**
	 * destroyInstance() gracefully shuts down the specified Syslog instance and
	 * removes it from Syslog4j.
	 * 
	 * @param syslog - the Syslog instance to destroy
	 * @throws SyslogRuntimeException
	 */
	public synchronized static final void destroyInstance(SyslogIF syslog) throws SyslogRuntimeException {
		if (syslog == null) {
			return;
		}
		
		String protocol = syslog.getProtocol().toLowerCase();
		
		if (instances.containsKey(protocol)) {
			try {
				syslog.shutdown();
				
			} finally {
				instances.remove(protocol);
			}
			
		} else {
			throwRuntimeException("Cannot destroy protocol \"" + protocol + "\" instance; call shutdown instead");
			return;
		}
	}

	public static void main(String[] args) throws Exception {
		SyslogMain.main(args);
	}
}
