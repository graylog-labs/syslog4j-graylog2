package org.productivity.java.syslog4j;

import java.io.Serializable;

/**
* SyslogCharSetIF provides control of the encoding character set within
* several class of Syslog4j.
* 
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
* 
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: SyslogCharSetIF.java,v 1.3 2008/11/07 15:15:41 cvs Exp $
*/
public interface SyslogCharSetIF extends Serializable {
	public String getCharSet();
	public void setCharSet(String charSet);
}
