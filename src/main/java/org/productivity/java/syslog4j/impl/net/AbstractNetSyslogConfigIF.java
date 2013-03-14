package org.productivity.java.syslog4j.impl.net;

import org.productivity.java.syslog4j.impl.AbstractSyslogConfigIF;

/**
* AbstractNetSyslogConfigIF is a configuration interface supporting network-based
* Syslog implementations.
* 
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
* 
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: AbstractNetSyslogConfigIF.java,v 1.4 2009/06/06 19:11:02 cvs Exp $
*/
public interface AbstractNetSyslogConfigIF extends AbstractSyslogConfigIF {
	public boolean isCacheHostAddress();
	public void setCacheHostAddress(boolean cacheHostAddress);
}
