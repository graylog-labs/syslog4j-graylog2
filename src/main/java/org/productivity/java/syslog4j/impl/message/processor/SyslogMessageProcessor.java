package org.productivity.java.syslog4j.impl.message.processor;


/**
* SyslogMessageProcessor wraps AbstractSyslogMessageProcessor.
* 
* <p>Those wishing to replace (or improve upon) this implementation
* can write a custom SyslogMessageProcessorIF and set it per
* instance via the SyslogIF.setMessageProcessor(..) method or set it globally
* via the SyslogMessageProcessor.setDefault(..) method.</p>
* 
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
* 
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: SyslogMessageProcessor.java,v 1.7 2010/02/04 03:41:37 cvs Exp $
*/
public class SyslogMessageProcessor extends AbstractSyslogMessageProcessor {
	private static final long serialVersionUID = -4232803978024990353L;
	
	private static final SyslogMessageProcessor INSTANCE = new SyslogMessageProcessor();
	
	protected static SyslogMessageProcessor defaultInstance = INSTANCE;
	
	public static void setDefault(SyslogMessageProcessor messageProcessor) {
		if (messageProcessor != null) {
			defaultInstance = messageProcessor;
		}
	}
	
	public static SyslogMessageProcessor getDefault() {
		return defaultInstance;
	}
}
