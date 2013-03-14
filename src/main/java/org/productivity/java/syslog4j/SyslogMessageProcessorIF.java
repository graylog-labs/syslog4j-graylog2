package org.productivity.java.syslog4j;

import java.io.Serializable;

/**
* SyslogMessageProcessorIF provides an extensible interface for writing custom
* Syslog4j message processors.
* 
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
* 
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: SyslogMessageProcessorIF.java,v 1.4 2010/11/28 04:15:18 cvs Exp $
*/
public interface SyslogMessageProcessorIF extends Serializable {
	public String createSyslogHeader(int facility, int level, String localName, boolean sendLocalTimestamp, boolean sendLocalName);

	public byte[] createPacketData(byte[] header, byte[] message, int start, int length);

	public byte[] createPacketData(byte[] header, byte[] message, int start, int length, byte[] splitBeginText, byte[] splitEndText);
}
