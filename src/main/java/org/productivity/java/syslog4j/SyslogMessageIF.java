package org.productivity.java.syslog4j;

import java.io.Serializable;

/**
* SyslogMessageIF provides a common interface for all Syslog4j event implementations.
* 
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
* 
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: SyslogMessageIF.java,v 1.1 2008/11/10 04:38:37 cvs Exp $
*/
public interface SyslogMessageIF extends Serializable {
	public String createMessage();
}
