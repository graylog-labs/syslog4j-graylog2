package org.productivity.java.syslog4j.impl.message.pci;

/**
* PCISyslogMessageIF provides a definition of the fields for audit trails
* defined by section 10.3 of the PCI Data Security Standard (PCI DSS)
* versions 1.1 and 1.2.
* 
* <p>More information on the PCI DSS specification is available here:</p>
* 
* <p>https://www.pcisecuritystandards.org/security_standards/pci_dss.shtml</p>
* 
* <p>The PCI DSS specification is Copyright 2008 PCI Security Standards
* Council LLC.</p>
* 
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
* 
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: PCISyslogMessageIF.java,v 1.1 2008/11/10 04:38:37 cvs Exp $
*/
public interface PCISyslogMessageIF {
	public String getUserId();
	public String getEventType();
	public String getDate();
	public String getTime();
	public String getStatus();
	public String getOrigination();
	public String getAffectedResource();
}
