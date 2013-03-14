package org.productivity.java.syslog4j.impl.message.processor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.productivity.java.syslog4j.SyslogConstants;
import org.productivity.java.syslog4j.SyslogMessageProcessorIF;
import org.productivity.java.syslog4j.util.SyslogUtility;

/**
* AbstractSyslogMessageProcessor provides the ability to split a syslog message
* into multiple messages when the message is greater than the syslog
* maximum message length (1024 bytes including the header).
* 
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
* 
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: AbstractSyslogMessageProcessor.java,v 1.2 2010/11/28 04:15:18 cvs Exp $
*/
public abstract class AbstractSyslogMessageProcessor implements SyslogMessageProcessorIF, SyslogConstants {
	private static final long serialVersionUID = -5413127301924500938L;
	protected String localName = null;
	
	public AbstractSyslogMessageProcessor() {
		this.localName = SyslogUtility.getLocalName();
	}

	public byte[] createPacketData(byte[] header, byte[] message, int start, int length) {
		return createPacketData(header,message,start,length,null,null);
	}

	public byte[] createPacketData(byte[] header, byte[] message, int start, int length, byte[] splitBeginText, byte[] splitEndText) {
		if (header == null || message == null || start < 0 || length < 0) {
			return null;
		}
		
		int dataLength = header.length + length;
		
		if (splitBeginText != null) {
			dataLength += splitBeginText.length; 
		}
		if (splitEndText != null) {
			dataLength += splitEndText.length; 
		}
		
		byte[] data = new byte[dataLength];

		System.arraycopy(header,0,data,0,header.length);
		int pos = header.length;
		
		if (splitBeginText != null) {
			System.arraycopy(splitBeginText,0,data,pos,splitBeginText.length);
			pos += splitBeginText.length;
		}
		
		System.arraycopy(message,start,data,pos,length);
		pos += length;

		if (splitEndText != null) {
			System.arraycopy(splitEndText,0,data,pos,splitEndText.length);
		}
		
		return data;
	}

	protected void appendPriority(StringBuffer buffer, int facility, int level) {
		int priority = facility | level;

		buffer.append("<");
		buffer.append(priority);
		buffer.append(">");
	}
	
	protected void appendLocalTimestamp(StringBuffer buffer) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(SYSLOG_DATEFORMAT,Locale.ENGLISH);
		
		String datePrefix = dateFormat.format(new Date());
		
		int pos = buffer.length() + 4;
		
		buffer.append(datePrefix);
	
		//  RFC 3164 requires leading space for days 1-9
		if (buffer.charAt(pos) == '0') {
			buffer.setCharAt(pos,' ');
		}		
	}
	
	protected void appendLocalName(StringBuffer buffer, String localName) {
		if (localName != null) {
			buffer.append(localName);
			
		} else {
			buffer.append(this.localName);
		}
		
		buffer.append(' ');
	}

	public String createSyslogHeader(int facility, int level, String localName, boolean sendLocalTimestamp, boolean sendLocalName) {
		StringBuffer buffer = new StringBuffer();
		
		appendPriority(buffer,facility,level);
		
		if (sendLocalTimestamp) {
			appendLocalTimestamp(buffer);
		}
		
		if (sendLocalName) {
			appendLocalName(buffer,localName);
		}
		
		return buffer.toString();
	}
}
