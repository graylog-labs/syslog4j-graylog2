package org.productivity.java.syslog4j;

/**
* SyslogMessageModifierConfigIF provides a common configuration interface for all
* Syslog4j message modifier implementations.
* 
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
* 
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: SyslogMessageModifierConfigIF.java,v 1.3 2010/10/28 05:10:57 cvs Exp $
*/
public interface SyslogMessageModifierConfigIF extends SyslogConstants, SyslogCharSetIF {
	public String getPrefix();
	public void setPrefix(String prefix);

	public String getSuffix();
	public void setSuffix(String suffix);
}
