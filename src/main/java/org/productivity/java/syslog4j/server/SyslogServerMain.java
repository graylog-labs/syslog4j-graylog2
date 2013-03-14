package org.productivity.java.syslog4j.server;

import org.productivity.java.syslog4j.server.impl.event.printstream.FileSyslogServerEventHandler;
import org.productivity.java.syslog4j.server.impl.event.printstream.SystemOutSyslogServerEventHandler;
import org.productivity.java.syslog4j.server.impl.net.tcp.TCPNetSyslogServerConfigIF;
import org.productivity.java.syslog4j.util.SyslogUtility;

/**
 * This class provides a command-line interface for Syslog4j
 * server implementations.
 * 
 * <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
 * of the LGPL license is available in the META-INF folder in all
 * distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
 * 
 * @author &lt;syslog4j@productivity.org&gt;
 * @version $Id: SyslogServerMain.java,v 1.3 2010/11/28 01:38:08 cvs Exp $
 */
public class SyslogServerMain {
	public static boolean CALL_SYSTEM_EXIT_ON_FAILURE = true;
	
	public static class Options {
		public String protocol = null;
		public String fileName = null;
		public boolean append = false;
		public boolean quiet = false;
		
		public String host = null;
		public String port = null;
		public String timeout = null;
		
		public String usage = null;
	}
	
	public static void usage(String problem) {
		if (problem != null) {
			System.out.println("Error: " + problem);
			System.out.println();
		}
		
		System.out.println("Usage:");
		System.out.println();
		System.out.println("SyslogServer [-h <host>] [-p <port>] [-o <file>] [-a] [-q] <protocol>");
		System.out.println();
		System.out.println("-h <host>    host or IP to bind");
		System.out.println("-p <port>    port to bind");
		System.out.println("-t <timeout> socket timeout (in milliseconds)");
		System.out.println("-o <file>    file to write entries (overwrites by default)");
		System.out.println();
		System.out.println("-a           append to file (instead of overwrite)");
		System.out.println("-q           do not write anything to standard out");
		System.out.println();
		System.out.println("protocol     Syslog4j protocol implementation (tcp, udp, ...)");
	}
	
	public static Options parseOptions(String[] args) {
		Options options = new Options();
	
		int i = 0;
		while(i < args.length) {
			String arg = args[i++];
			boolean match = false;
			
			if ("-h".equals(arg)) { if (i == args.length) { options.usage = "Must specify host with -h"; return options; } match = true; options.host = args[i++]; }
			if ("-p".equals(arg)) { if (i == args.length) { options.usage = "Must specify port with -p"; return options; } match = true; options.port = args[i++]; }
			if ("-t".equals(arg)) { if (i == args.length) { options.usage = "Must specify value (in milliseconds)"; return options; } match = true; options.timeout = args[i++]; }
			if ("-o".equals(arg)) { if (i == args.length) { options.usage = "Must specify file with -o"; return options; } match = true; options.fileName = args[i++]; }
			
			if ("-a".equals(arg)) { match = true; options.append = true; }
			if ("-q".equals(arg)) { match = true; options.quiet = true; }
			
			if (!match) {
				if (options.protocol != null) {
					options.usage = "Only one protocol definition allowed";
					return options;
				}
				
				options.protocol = arg;
			}
		}
		
		if (options.protocol == null) {
			options.usage = "Must specify protocol";
			return options;
		}
		
		if (options.fileName == null && options.append) {
			options.usage = "Cannot specify -a without specifying -f <file>";
			return options;
		}
		
		return options;
	}
	
	public static void main(String[] args) throws Exception {
		Options options = parseOptions(args);

		if (options.usage != null) {
			usage(options.usage);
			if (CALL_SYSTEM_EXIT_ON_FAILURE) { System.exit(1); } else { return; }
		}
		
		if (!options.quiet) {
			System.out.println("SyslogServer " + SyslogServer.getVersion());
		}
		
		if (!SyslogServer.exists(options.protocol)) {
			usage("Protocol \"" + options.protocol + "\" not supported");
			if (CALL_SYSTEM_EXIT_ON_FAILURE) { System.exit(1); } else { return; }
		}
		
		SyslogServerIF syslogServer = SyslogServer.getInstance(options.protocol);
		
		SyslogServerConfigIF syslogServerConfig = syslogServer.getConfig();
		
		if (options.host != null) {
			syslogServerConfig.setHost(options.host);
			if (!options.quiet) {
				System.out.println("Listening on host: " + options.host);
			}
		}

		if (options.port != null) {
			syslogServerConfig.setPort(Integer.parseInt(options.port));
			if (!options.quiet) {
				System.out.println("Listening on port: " + options.port);
			}
		}

		if (options.timeout != null) {
			if (syslogServerConfig instanceof TCPNetSyslogServerConfigIF) {
				((TCPNetSyslogServerConfigIF) syslogServerConfig).setTimeout(Integer.parseInt(options.timeout));
				if (!options.quiet) {
					System.out.println("Timeout: " + options.timeout);
				}
				
			} else {
				System.err.println("Timeout not supported for protocol \"" + options.protocol + "\" (ignored)");
			}
		}

		if (options.fileName != null) {
			SyslogServerEventHandlerIF eventHandler = new FileSyslogServerEventHandler(options.fileName,options.append);
			syslogServerConfig.addEventHandler(eventHandler);
			if (!options.quiet) {
				System.out.println((options.append ? "Appending" : "Writing") + " to file: " + options.fileName);
			}
		}
		
		if (!options.quiet) {
			SyslogServerEventHandlerIF eventHandler = SystemOutSyslogServerEventHandler.create();
			syslogServerConfig.addEventHandler(eventHandler);
		}

		if (!options.quiet) {
			System.out.println();
		}

		SyslogServer.getThreadedInstance(options.protocol);
		
		while(true) {
			SyslogUtility.sleep(1000);
		}
	}
}
