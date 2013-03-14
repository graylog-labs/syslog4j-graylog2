package org.productivity.java.syslog4j.impl.backlog.log4j;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import org.productivity.java.syslog4j.SyslogConstants;
import org.productivity.java.syslog4j.SyslogIF;
import org.productivity.java.syslog4j.SyslogRuntimeException;
import org.productivity.java.syslog4j.impl.backlog.AbstractSyslogBackLogHandler;

/**
* Log4jSyslogBackLogHandler is used to send Syslog backLog messages to
* Log4j whenever the Syslog protocol fails.
* 
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
* 
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: Log4jSyslogBackLogHandler.java,v 1.2 2009/07/22 15:54:23 cvs Exp $
*/
public class Log4jSyslogBackLogHandler extends AbstractSyslogBackLogHandler {
	protected Logger logger = null;
	protected Level downLevel = Level.WARN;
	protected Level upLevel = Level.WARN;
	
	public Log4jSyslogBackLogHandler(Logger logger) throws SyslogRuntimeException {
		this.logger = logger;
		
		initialize();
	}

	public Log4jSyslogBackLogHandler(Logger logger, boolean appendReason) {
		this.logger = logger;
		this.appendReason = appendReason;

		initialize();
	}

	public Log4jSyslogBackLogHandler(Class loggerClass) {
		if (loggerClass == null) {
			throw new SyslogRuntimeException("loggerClass cannot be null");
		}
		
		this.logger = Logger.getLogger(loggerClass);
		
		initialize();
	}

	public Log4jSyslogBackLogHandler(Class loggerClass, boolean appendReason) {
		if (loggerClass == null) {
			throw new SyslogRuntimeException("loggerClass cannot be null");
		}
		
		this.logger = Logger.getLogger(loggerClass);
		this.appendReason = appendReason;
		
		initialize();
	}

	public Log4jSyslogBackLogHandler(String loggerName) {
		if (loggerName == null) {
			throw new SyslogRuntimeException("loggerName cannot be null");
		}
		
		this.logger = Logger.getLogger(loggerName);

		initialize();
	}

	public Log4jSyslogBackLogHandler(String loggerName, boolean appendReason) {
		if (loggerName == null) {
			throw new SyslogRuntimeException("loggerName cannot be null");
		}
		
		this.logger = Logger.getLogger(loggerName);
		this.appendReason = appendReason;

		initialize();
	}

	public Log4jSyslogBackLogHandler(String loggerName, LoggerFactory loggerFactory) {
		if (loggerName == null) {
			throw new SyslogRuntimeException("loggerName cannot be null");
		}
		
		if (loggerFactory == null) {
			throw new SyslogRuntimeException("loggerFactory cannot be null");
		}
		
		this.logger = Logger.getLogger(loggerName,loggerFactory);

		initialize();
	}
	
	public Log4jSyslogBackLogHandler(String loggerName, LoggerFactory loggerFactory, boolean appendReason) {
		if (loggerName == null) {
			throw new SyslogRuntimeException("loggerName cannot be null");
		}
		
		if (loggerFactory == null) {
			throw new SyslogRuntimeException("loggerFactory cannot be null");
		}
		
		this.logger = Logger.getLogger(loggerName,loggerFactory);
		this.appendReason = appendReason;

		initialize();
	}

	public void initialize() throws SyslogRuntimeException {
		if (this.logger == null) {
			throw new SyslogRuntimeException("logger cannot be null");
		}
	}
	
	protected static Level getLog4jLevel(int level) {
		switch(level) {
			case SyslogConstants.LEVEL_DEBUG: 		return Level.DEBUG;
			case SyslogConstants.LEVEL_INFO: 		return Level.INFO;
			case SyslogConstants.LEVEL_NOTICE: 		return Level.INFO;
			case SyslogConstants.LEVEL_WARN: 		return Level.WARN;
			case SyslogConstants.LEVEL_ERROR: 		return Level.ERROR;
			case SyslogConstants.LEVEL_CRITICAL: 	return Level.ERROR;
			case SyslogConstants.LEVEL_ALERT: 		return Level.ERROR;
			case SyslogConstants.LEVEL_EMERGENCY: 	return Level.FATAL;
			
			default:
				return Level.WARN;
		}
	}
	
	public void down(SyslogIF syslog, String reason) {
		this.logger.log(this.downLevel,"Syslog protocol \"" + syslog.getProtocol() + "\" is down: " + reason);
	}

	public void up(SyslogIF syslog) {
		this.logger.log(this.upLevel,"Syslog protocol \"" + syslog.getProtocol() + "\" is up");
	}

	public void log(SyslogIF syslog, int level, String message, String reason) throws SyslogRuntimeException {
		Level log4jLevel = getLog4jLevel(level);
		
		String combinedMessage = combine(syslog,level,message,reason);
		
		this.logger.log(log4jLevel,combinedMessage);
	}
}
