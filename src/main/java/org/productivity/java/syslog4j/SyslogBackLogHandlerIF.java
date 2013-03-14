package org.productivity.java.syslog4j;

/**
* SyslogBackLogHandlerIF provides a last-chance mechanism to log messages that fail
* (for whatever reason) within the rest of Syslog.
* 
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
* 
* <p>Implementing the down(SyslogIF) method is an excellent way to add some sort of notification to
* your application when a Syslog service is unavailable.</p>
* 
* <p>Implementing the up(SyslogIF) method can be used to notify your application when a Syslog
* service has returned.</p>
* 
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: SyslogBackLogHandlerIF.java,v 1.2 2009/01/28 15:13:52 cvs Exp $
*/
public interface SyslogBackLogHandlerIF {
	/**
	 * Implement initialize() to handle one-time set-up for this backLog handler.
	 * 
	 * @throws SyslogRuntimeException
	 */
	public void initialize() throws SyslogRuntimeException;
	
	/**
	 * Implement down(syslog,reason) to notify/log when the syslog protocol is unavailable.
	 * 
	 * @param syslog - SyslogIF instance causing this down condition
	 * @param reason - reason given for the down condition
	 */
	public void down(SyslogIF syslog, String reason);

	/**
	 * Implement up(syslog) to notify/log when the syslog protocol becomes available after a down condition.
	 * 
	 * @param syslog - SyslogIF instance which is now available
	 */
	public void up(SyslogIF syslog);

	/**
	 * @param syslog - SyslogIF instance which cannot handle this log event
	 * @param level - message level
	 * @param message - message (in String form)
	 * @param reason - reason given for why this message could not be handled
	 * @throws SyslogRuntimeException - throwing this Exception activates the next backlogHandler in the chain 
	 */
	public void log(SyslogIF syslog, int level, String message, String reason) throws SyslogRuntimeException;
}
