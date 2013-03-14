package org.productivity.java.syslog4j.util;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.productivity.java.syslog4j.SyslogCharSetIF;
import org.productivity.java.syslog4j.SyslogConstants;
import org.productivity.java.syslog4j.SyslogRuntimeException;

/**
* SyslogUtility provides several common utility methods used within
* Syslog4j.
* 
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
* 
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: SyslogUtility.java,v 1.21 2010/11/28 01:38:08 cvs Exp $
*/
public final class SyslogUtility implements SyslogConstants {
	private static final long serialVersionUID = 915031554586613648L;
	
	private SyslogUtility() {
		//
	}
	
	public static final InetAddress getInetAddress(String host) throws SyslogRuntimeException {
		InetAddress address = null;
		
		try {
			address = InetAddress.getByName(host);
			
		} catch (UnknownHostException uhe) {
			throw new SyslogRuntimeException(uhe);
		}
		
		return address;
	}
	
	public static final String getFacilityString(int syslogFacility) {
		switch(syslogFacility) {
			case FACILITY_KERN:			return "kern";
			case FACILITY_USER:			return "user";
			case FACILITY_MAIL:			return "mail";
			case FACILITY_DAEMON:		return "daemon";
			case FACILITY_AUTH:			return "auth";
			case FACILITY_SYSLOG:		return "syslog";
			case FACILITY_LPR:			return "lpr";
			case FACILITY_NEWS:			return "news";
			case FACILITY_UUCP:			return "uucp";
			case FACILITY_CRON:			return "cron";
			case FACILITY_AUTHPRIV:		return "authpriv";
			case FACILITY_FTP:			return "ftp";
			case FACILITY_LOCAL0:		return "local0";
			case FACILITY_LOCAL1:		return "local1";
			case FACILITY_LOCAL2:		return "local2";
			case FACILITY_LOCAL3:		return "local3";
			case FACILITY_LOCAL4:		return "local4";
			case FACILITY_LOCAL5:		return "local5";
			case FACILITY_LOCAL6:		return "local6";
			case FACILITY_LOCAL7:		return "local7";
			
			default:					return "UNKNOWN";
		}
	}
	  
	public static final int getFacility(String facilityName) {
		String _facilityName = facilityName;
		
		if (facilityName == null) {
			return -1;
			
		} else {
			_facilityName = facilityName.trim();
		}
		
		if("KERN".equalsIgnoreCase(_facilityName)) {				return FACILITY_KERN;
		} else if("USER".equalsIgnoreCase(facilityName)) {		return FACILITY_USER;
		} else if("MAIL".equalsIgnoreCase(facilityName)) {		return FACILITY_MAIL;
		} else if("DAEMON".equalsIgnoreCase(facilityName)) {	return FACILITY_DAEMON;
		} else if("AUTH".equalsIgnoreCase(facilityName)) {		return FACILITY_AUTH;
		} else if("SYSLOG".equalsIgnoreCase(facilityName)) {	return FACILITY_SYSLOG;
		} else if("LPR".equalsIgnoreCase(facilityName)) {		return FACILITY_LPR;
		} else if("NEWS".equalsIgnoreCase(facilityName)) {		return FACILITY_NEWS;
		} else if("UUCP".equalsIgnoreCase(facilityName)) {		return FACILITY_UUCP;
		} else if("CRON".equalsIgnoreCase(facilityName)) {		return FACILITY_CRON;
		} else if("AUTHPRIV".equalsIgnoreCase(facilityName)) {	return FACILITY_AUTHPRIV;
		} else if("FTP".equalsIgnoreCase(facilityName)) {		return FACILITY_FTP;
		} else if("LOCAL0".equalsIgnoreCase(facilityName)) {	return FACILITY_LOCAL0;
		} else if("LOCAL1".equalsIgnoreCase(facilityName)) {	return FACILITY_LOCAL1;
		} else if("LOCAL2".equalsIgnoreCase(facilityName)) {	return FACILITY_LOCAL2;
		} else if("LOCAL3".equalsIgnoreCase(facilityName)) {	return FACILITY_LOCAL3;
		} else if("LOCAL4".equalsIgnoreCase(facilityName)) {	return FACILITY_LOCAL4;
		} else if("LOCAL5".equalsIgnoreCase(facilityName)) {	return FACILITY_LOCAL5;
		} else if("LOCAL6".equalsIgnoreCase(facilityName)) {	return FACILITY_LOCAL6;
		} else if("LOCAL7".equalsIgnoreCase(facilityName)) {	return FACILITY_LOCAL7;
		} else {												return -1;
		}
	}
	
	public static final int getLevel(String levelName) {
		String _levelName = levelName;
		
		if (levelName == null) {
			return -1;
			
		} else {
			_levelName = levelName.trim();
		}
		
		if("DEBUG".equalsIgnoreCase(_levelName)) {				return LEVEL_DEBUG;
		} else if("INFO".equalsIgnoreCase(_levelName)) {		return LEVEL_INFO;
		} else if("NOTICE".equalsIgnoreCase(_levelName)) {		return LEVEL_NOTICE;
		} else if("WARN".equalsIgnoreCase(_levelName)) {		return LEVEL_WARN;
		} else if("ERROR".equalsIgnoreCase(_levelName)) {		return LEVEL_ERROR;
		} else if("CRITICAL".equalsIgnoreCase(_levelName)) {	return LEVEL_CRITICAL;
		} else if("ALERT".equalsIgnoreCase(_levelName)) {		return LEVEL_ALERT;
		} else if("EMERGENCY".equalsIgnoreCase(_levelName)) {	return LEVEL_EMERGENCY;
		} else {												return -1;
		}
	}

	public static final boolean isClassExists(String className) {
		try {
			Class.forName(className);
			return true;
			
		} catch (ClassNotFoundException cnfe) {
			//
		}
		
		return false;
	}
	
	public static final String getLocalName() {
		String localName = SEND_LOCAL_NAME_DEFAULT_VALUE;
		
        try {
        	InetAddress addr = InetAddress.getLocalHost();
        	localName = addr.getHostName();
            
        } catch (UnknownHostException uhe) {
        	//
        }
        
        return localName;
	}
	
	public static final byte[] getBytes(SyslogCharSetIF syslogCharSet, String data) {
		byte[] dataBytes = null;
		
		try {
			dataBytes = data.getBytes(syslogCharSet.getCharSet());
			
		} catch (UnsupportedEncodingException uee) {
			dataBytes = data.getBytes();
		}
		
		return dataBytes;
	}
	
	public static final String newString(SyslogCharSetIF syslogCharSet, byte[] dataBytes) {
		String data = newString(syslogCharSet,dataBytes,dataBytes.length);
		
		return data;
	}

	public static final String newString(SyslogCharSetIF syslogCharSet, byte[] dataBytes, int dataLength) {
		String data = null;
		
		try {
			data = new String(dataBytes,0,dataLength,syslogCharSet.getCharSet());
			
		} catch (UnsupportedEncodingException uee) {
			data = new String(dataBytes);
		}
		
		return data;
	}

	public static final String getLevelString(int level) {
		switch(level) {
			case SyslogConstants.LEVEL_DEBUG: return "DEBUG";
			case SyslogConstants.LEVEL_INFO: return "INFO";
			case SyslogConstants.LEVEL_NOTICE: return "NOTICE";
			case SyslogConstants.LEVEL_WARN: return "WARN";
			case SyslogConstants.LEVEL_ERROR: return "ERROR";
			case SyslogConstants.LEVEL_CRITICAL: return "CRITICAL";
			case SyslogConstants.LEVEL_ALERT: return "ALERT";
			case SyslogConstants.LEVEL_EMERGENCY: return "EMERGENCY";
			
			default:
				return "UNKNOWN";
		}
	}
	
	
	public static void sleep(long duration) {
		try {
			Thread.sleep(duration);
			
		} catch (InterruptedException ie) {
			//
		}
	}	
}
