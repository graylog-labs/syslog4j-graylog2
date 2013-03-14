package org.productivity.java.syslog4j;

/**
* SyslogMessageModifierIF provides a common interface for all
* Syslog4j message modifier implementations.
* 
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
* 
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: SyslogMessageModifierIF.java,v 1.4 2010/10/28 05:10:57 cvs Exp $
*/
public interface SyslogMessageModifierIF extends SyslogConstants {
	public String modify(SyslogIF syslog, int facility, int level, String message);
	public boolean verify(String message);
}
