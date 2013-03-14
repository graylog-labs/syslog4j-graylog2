package org.productivity.java.syslog4j;

/**
* SyslogIF provides a common interface for all Syslog4j client implementations.
* 
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
* 
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: SyslogIF.java,v 1.9 2010/02/11 04:59:22 cvs Exp $
*/
public interface SyslogIF extends SyslogConstants {
	public void initialize(String protocol, SyslogConfigIF config) throws SyslogRuntimeException;
	
	public String getProtocol();
	public SyslogConfigIF getConfig();
	
	public void backLog(int level, String message, Throwable reasonThrowable);
	public void backLog(int level, String message, String reason);
	
	public void log(int level, String message);
	
	public void debug(String message);
	public void info(String message);
	public void notice(String message);
	public void warn(String message);
	public void error(String message);
	public void critical(String message);
	public void alert(String message);
	public void emergency(String message);

	public void log(int level, SyslogMessageIF message);
	
	public void debug(SyslogMessageIF message);
	public void info(SyslogMessageIF message);
	public void notice(SyslogMessageIF message);
	public void warn(SyslogMessageIF message);
	public void error(SyslogMessageIF message);
	public void critical(SyslogMessageIF message);
	public void alert(SyslogMessageIF message);
	public void emergency(SyslogMessageIF message);

	public void flush() throws SyslogRuntimeException;
	public void shutdown() throws SyslogRuntimeException;
	
	public void setMessageProcessor(SyslogMessageProcessorIF messageProcessor);
	public SyslogMessageProcessorIF getMessageProcessor();

	public void setStructuredMessageProcessor(SyslogMessageProcessorIF messageProcessor);
	public SyslogMessageProcessorIF getStructuredMessageProcessor();
}
