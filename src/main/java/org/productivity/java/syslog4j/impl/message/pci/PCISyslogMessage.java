package org.productivity.java.syslog4j.impl.message.pci;

import java.util.Date;
import java.util.Map;

import org.productivity.java.syslog4j.impl.message.AbstractSyslogMessage;

/**
* PCISyslogMessage provides support for audit trails defined by section
* 10.3 of the PCI Data Security Standard (PCI DSS) versions 1.1 and 1.2.
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
* @version $Id: PCISyslogMessage.java,v 1.3 2008/11/14 04:32:00 cvs Exp $
*/
public class PCISyslogMessage extends AbstractSyslogMessage implements PCISyslogMessageIF {
	private static final long serialVersionUID = 3571696218386879119L;
	
	public static final String USER_ID				= "userId";
	public static final String EVENT_TYPE			= "eventType";
	public static final String DATE					= "date";
	public static final String TIME					= "time";
	public static final String STATUS				= "status";
	public static final String ORIGINATION			= "origination";
	public static final String AFFECTED_RESOURCE	= "affectedResource";
	
	protected String userId = UNDEFINED;			// 10.3.1 "User Identification"
	protected String eventType = UNDEFINED;			// 10.3.2 "Type of event"
	protected String date = null;					// 10.3.3 "Date and time" (date)
	protected String time = null;					// 10.3.3 "Date and time" (time)
	protected String status = UNDEFINED;			// 10.3.4 "Success or failure indication"
	protected String origination = null;			// 10.3.5 "Origination of Event"
	protected String affectedResource = UNDEFINED;	// 10.3.6 "Identity or name of affected data, system component, or resource"

	public PCISyslogMessage() {
		//
	}

	public PCISyslogMessage(PCISyslogMessageIF message) {
		init(message);
	}
	
	public PCISyslogMessage(Map fields) {
		init(fields);
	}
	
	protected void init(PCISyslogMessageIF message) {
		this.userId = message.getUserId();
		this.eventType = message.getEventType();
		this.date = message.getDate();
		this.time = message.getTime();
		this.status = message.getStatus();
		this.origination = message.getOrigination();
		this.affectedResource = message.getAffectedResource();
	}

	protected void init(Map fields) {
		if (fields.containsKey(USER_ID)) { this.userId = (String) fields.get(USER_ID); };
		if (fields.containsKey(EVENT_TYPE)) { this.eventType = (String) fields.get(EVENT_TYPE); };
		if (fields.containsKey(DATE) && fields.get(DATE) instanceof String) { this.date = (String) fields.get(DATE); };
		if (fields.containsKey(DATE) && fields.get(DATE) instanceof Date) { setDate((Date) fields.get(DATE)); };
		if (fields.containsKey(TIME)) { this.time = (String) fields.get(TIME); };
		if (fields.containsKey(STATUS)) { this.status = (String) fields.get(STATUS); };
		if (fields.containsKey(ORIGINATION)) { this.origination = (String) fields.get(ORIGINATION); };
		if (fields.containsKey(AFFECTED_RESOURCE)) { this.affectedResource = (String) fields.get(AFFECTED_RESOURCE); };
	}

	public PCISyslogMessage(String userId, String eventType, String status, String affectedResource) {
		this.userId = userId;
		this.eventType = eventType;
		this.status = status;
		this.affectedResource = affectedResource;
	}

	public PCISyslogMessage(String userId, String eventType, String status, String origination, String affectedResource) {
		this.userId = userId;
		this.eventType = eventType;
		this.status = status;
		this.origination = origination;
		this.affectedResource = affectedResource;
	}

	public PCISyslogMessage(String userId, String eventType, String date, String time, String status, String affectedResource) {
		this.userId = userId;
		this.eventType = eventType;
		this.date = date;
		this.time = time;
		this.status = status;
		this.affectedResource = affectedResource;
	}

	public PCISyslogMessage(String userId, String eventType, String date, String time, String status, String origination, String affectedResource) {
		this.userId = userId;
		this.eventType = eventType;
		this.date = date;
		this.time = time;
		this.status = status;
		this.origination = origination;
		this.affectedResource = affectedResource;
	}

	public PCISyslogMessage(String userId, String eventType, Date date, String status, String affectedResource) {
		this.userId = userId;
		this.eventType = eventType;
		
		String[] dateAndTime = generateDateAndTime(date);
		this.date = dateAndTime[0];
		this.time = dateAndTime[1];
		
		this.status = status;
		this.affectedResource = affectedResource;
	}
	
	public PCISyslogMessage(String userId, String eventType, Date date, String status, String origination, String affectedResource) {
		this.userId = userId;
		this.eventType = eventType;
		
		String[] dateAndTime = generateDateAndTime(date);
		this.date = dateAndTime[0];
		this.time = dateAndTime[1];
		
		this.status = status;
		this.origination = origination;
		this.affectedResource = affectedResource;
	}

	public String getUserId() {
		if (nullOrEmpty(this.userId)) {
			return UNDEFINED;
		}
		
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEventType() {
		if (nullOrEmpty(this.eventType)) {
			return UNDEFINED;
		}
		
		return this.eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getDate() {
		if (nullOrEmpty(this.date)) {
			String dateNow = generateDate();
			
			return dateNow;
		}
		
		return this.date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setDate(Date date) {
		String[] d = generateDateAndTime(date);
		
		this.date = d[0];
		this.time = d[1];
	}

	public String getTime() {
		if (nullOrEmpty(this.time)) {
			String timeNow = generateTime();
			
			return timeNow;
		}
		
		return this.time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getStatus() {
		if (nullOrEmpty(this.status)) {
			return UNDEFINED;
		}
		
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOrigination() {
		if (nullOrEmpty(this.origination)) {
			String originationHere = generateLocalHostName();
			
			return originationHere;
		}
		
		return this.origination;
	}

	public void setOrigination(String origination) {
		this.origination = origination;
	}

	public String getAffectedResource() {
		if (nullOrEmpty(this.affectedResource)) {
			return UNDEFINED;
		}
		
		return this.affectedResource;
	}

	public void setAffectedResource(String affectedResource) {
		this.affectedResource = affectedResource;
	}
	
	public String createMessage() {
		StringBuffer buffer = new StringBuffer();
		
		char delimiter = getDelimiter();
		String replaceDelimiter = getReplaceDelimiter();
		
		buffer.append(replaceDelimiter(USER_ID,getUserId(),delimiter,replaceDelimiter));
		buffer.append(delimiter);
		buffer.append(replaceDelimiter(EVENT_TYPE,getEventType(),delimiter,replaceDelimiter));
		buffer.append(delimiter);
		buffer.append(replaceDelimiter(DATE,getDate(),delimiter,replaceDelimiter));
		buffer.append(delimiter);
		buffer.append(replaceDelimiter(TIME,getTime(),delimiter,replaceDelimiter));
		buffer.append(delimiter);
		buffer.append(replaceDelimiter(STATUS,getStatus(),delimiter,replaceDelimiter));
		buffer.append(delimiter);
		buffer.append(replaceDelimiter(ORIGINATION,getOrigination(),delimiter,replaceDelimiter));
		buffer.append(delimiter);
		buffer.append(replaceDelimiter(AFFECTED_RESOURCE,getAffectedResource(),delimiter,replaceDelimiter));
		
		return buffer.toString();
	}
}
