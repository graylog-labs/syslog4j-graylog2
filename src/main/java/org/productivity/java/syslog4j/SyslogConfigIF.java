package org.productivity.java.syslog4j;

/**
* SyslogConfigIF provides a common, extensible configuration interface for all
* implementations of SyslogIF.
* 
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
* 
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: SyslogConfigIF.java,v 1.19 2010/11/28 04:15:18 cvs Exp $
*/
public interface SyslogConfigIF extends SyslogConstants, SyslogCharSetIF {
	public Class getSyslogClass();
	
	public int getFacility();
	public void setFacility(int facility);
	public void setFacility(String facilityName);
	
	public int getPort();
	public void setPort(int port) throws SyslogRuntimeException;

	public String getLocalName();
	public void setLocalName(String localName) throws SyslogRuntimeException;

	public String getHost();
	public void setHost(String host) throws SyslogRuntimeException;
	
	public String getIdent();
	public void setIdent(String ident);
	
	public String getCharSet();
	public void setCharSet(String charSet);
	
	public boolean isIncludeIdentInMessageModifier();
	public void setIncludeIdentInMessageModifier(boolean throwExceptionOnInitialize);

	public boolean isThrowExceptionOnInitialize();
	public void setThrowExceptionOnInitialize(boolean throwExceptionOnInitialize);

	public boolean isThrowExceptionOnWrite();
	public void setThrowExceptionOnWrite(boolean throwExceptionOnWrite);

	public boolean isSendLocalTimestamp();
	public void setSendLocalTimestamp(boolean sendLocalTimestamp);
	
	public boolean isSendLocalName();
	public void setSendLocalName(boolean sendLocalName);
	
	public boolean isTruncateMessage();
	public void setTruncateMessage(boolean truncateMessage);
	
	public boolean isUseStructuredData();
	public void setUseStructuredData(boolean useStructuredData);
	
	public int getMaxMessageLength();
	public void setMaxMessageLength(int maxMessageLength);
	
	public void addMessageModifier(SyslogMessageModifierIF messageModifier);
	public void insertMessageModifier(int index, SyslogMessageModifierIF messageModifier);
	public void removeMessageModifier(SyslogMessageModifierIF messageModifier);
	public void removeAllMessageModifiers();

	public void addBackLogHandler(SyslogBackLogHandlerIF backLogHandler);
	public void insertBackLogHandler(int index, SyslogBackLogHandlerIF backLogHandler);
	public void removeBackLogHandler(SyslogBackLogHandlerIF backLogHandler);
	public void removeAllBackLogHandlers();
}
