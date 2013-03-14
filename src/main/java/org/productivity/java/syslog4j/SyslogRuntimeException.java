package org.productivity.java.syslog4j;

/**
* SyslogRuntimeException provides an extension of RuntimeException thrown
* by the majority of the classes within Syslog4j.
* 
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
* 
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: SyslogRuntimeException.java,v 1.3 2008/11/13 14:48:36 cvs Exp $
*/
public class SyslogRuntimeException extends RuntimeException {
	private static final long serialVersionUID = 7278123987654320379L;

	public SyslogRuntimeException(String arg0) {
		super(arg0);
	}

	public SyslogRuntimeException(Throwable arg0) {
		super(arg0);
	}
}
