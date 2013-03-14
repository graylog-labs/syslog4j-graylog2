package org.productivity.java.syslog4j.server.impl.event.structured;

import java.net.InetAddress;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.productivity.java.syslog4j.SyslogConstants;
import org.productivity.java.syslog4j.impl.message.structured.StructuredSyslogMessage;
import org.productivity.java.syslog4j.server.impl.event.SyslogServerEvent;

/**
 * SyslogServerStructuredEvent provides an implementation of the
 * SyslogServerEventIF interface that supports receiving of structured syslog
 * messages, as defined in:
 * 
 * <p>
 * http://tools.ietf.org/html/draft-ietf-syslog-protocol-23#section-6
 * </p>
 * 
 * <p>
 * Syslog4j is licensed under the Lesser GNU Public License v2.1. A copy of the
 * LGPL license is available in the META-INF folder in all distributions of
 * Syslog4j and in the base directory of the "doc" ZIP.
 * </p>
 * 
 * @author Manish Motwani
 * @version $Id: StructuredSyslogServerEvent.java,v 1.6 2011/01/11 05:11:13 cvs Exp $
 */
public class StructuredSyslogServerEvent extends SyslogServerEvent {
	private static final long serialVersionUID = 1676499796406044315L;

	protected String applicationName = SyslogConstants.STRUCTURED_DATA_APP_NAME_DEFAULT_VALUE;
	protected String processId = null;
	protected DateTime dateTime = null;
	protected DateTimeFormatter dateTimeFormatter = null;
	
	public StructuredSyslogServerEvent(final byte[] message, int length, InetAddress inetAddress) {
		super();
		
		initialize(message,length,inetAddress);
		parse();
	}

	public StructuredSyslogServerEvent(final String message, InetAddress inetAddress) {
		super();
		
		initialize(message,inetAddress);
		parse();
	}

	public DateTimeFormatter getDateTimeFormatter() {
		if (dateTimeFormatter == null) {
			this.dateTimeFormatter = ISODateTimeFormat.dateTime();
		}
		
		return dateTimeFormatter;
	}

	public void setDateTimeFormatter(Object dateTimeFormatter) {
		this.dateTimeFormatter = (DateTimeFormatter) dateTimeFormatter;
	}

	protected void parseApplicationName() {
		int i = this.message.indexOf(' ');

		if (i > -1) {
			this.applicationName = this.message.substring(0, i).trim();
			this.message = this.message.substring(i + 1);
			parseProcessId();
		}

		if (SyslogConstants.STRUCTURED_DATA_NILVALUE.equals(this.applicationName)) {
			this.applicationName = null;
		}
	}

	protected void parseProcessId() {
		int i = this.message.indexOf(' ');

		if (i > -1) {
			this.processId = this.message.substring(0, i).trim();
			this.message = this.message.substring(i + 1);
		}

		if (SyslogConstants.STRUCTURED_DATA_NILVALUE.equals(this.processId)) {
			this.processId = null;
		}
	}

	protected void parseDate() {
		// skip VERSION field
		int i = this.message.indexOf(' ');
		this.message = this.message.substring(i + 1);

		// parse the date
		i = this.message.indexOf(' ');

		if (i > -1) {
			String dateString = this.message.substring(0, i).trim();
			
			try {
				DateTimeFormatter formatter = getDateTimeFormatter();
				
				this.dateTime = formatter.parseDateTime(dateString);
				this.date = this.dateTime.toDate();
				
				this.message = this.message.substring(dateString.length() + 1);
				
			} catch (Exception e) {
				// Not structured date format, try super one
				super.parseDate();
			} 
		}
	}

	protected void parseHost() {
		int i = this.message.indexOf(' ');
		
		if (i > -1) {
			this.host = this.message.substring(0,i).trim();
			this.message = this.message.substring(i+1);
			
			parseApplicationName();
		}	
	}

	public String getApplicationName() {
		return this.applicationName;
	}

	public String getProcessId() {
		return this.processId;
	}
	
	public DateTime getDateTime() {
		return this.dateTime;
	}

	public StructuredSyslogMessage getStructuredMessage() {
		try {
			return StructuredSyslogMessage.fromString(getMessage());

		} catch (IllegalArgumentException e) {
			// throw new SyslogRuntimeException(
			// "Message received is not a valid structured message: "
			// + getMessage(), e);
			return new StructuredSyslogMessage(null,null,getMessage());
		}
	}
}
