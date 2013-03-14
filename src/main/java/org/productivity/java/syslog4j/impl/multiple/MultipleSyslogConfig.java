package org.productivity.java.syslog4j.impl.multiple;

import java.util.ArrayList;
import java.util.List;

import org.productivity.java.syslog4j.SyslogBackLogHandlerIF;
import org.productivity.java.syslog4j.SyslogConfigIF;
import org.productivity.java.syslog4j.SyslogConstants;
import org.productivity.java.syslog4j.SyslogMessageModifierIF;
import org.productivity.java.syslog4j.SyslogRuntimeException;

/**
* MultipleSyslogConfig is a configuration Object for allowing a single
* Syslog call to send to multiple Syslog implementations.
* 
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
* 
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: MultipleSyslogConfig.java,v 1.8 2010/11/28 04:15:18 cvs Exp $
*/
public class MultipleSyslogConfig implements SyslogConfigIF {
	private static final long serialVersionUID = 753704522364959612L;
	
	protected List syslogProtocols = null;
	
	public MultipleSyslogConfig() {
		this.syslogProtocols = new ArrayList();
	}

	public MultipleSyslogConfig(List protocols) {
		if (protocols != null) {
			this.syslogProtocols = protocols;
			
		} else {
			this.syslogProtocols = new ArrayList();
		}
	}

	public MultipleSyslogConfig(String[] protocols) {
		if (protocols != null) {
			this.syslogProtocols = new ArrayList(protocols.length);
			
			for(int i=0; i<protocols.length; i++) {
				this.syslogProtocols.add(protocols[i]);
			}
			
		} else {
			this.syslogProtocols = new ArrayList();
		}
	}
	
	public List getProtocols() {
		return this.syslogProtocols;
	}

	public void addProtocol(String protocol) {
		this.syslogProtocols.add(protocol);
	}

	public void insertProtocol(int index, String protocol) {
		this.syslogProtocols.add(index,protocol);
	}

	public void removeProtocol(String protocol) {
		this.syslogProtocols.remove(protocol);
	}

	public void removeAllProtocols() {
		this.syslogProtocols.clear();
	}

	public void addBackLogHandler(SyslogBackLogHandlerIF backLogHandler) {
		throw new SyslogRuntimeException("MultipleSyslog is an aggregator; please set the individual protocols");
	}

	public void addMessageModifier(SyslogMessageModifierIF messageModifier) {
		throw new SyslogRuntimeException("MultipleSyslog is an aggregator; please set the individual protocols");
	}

	public Class getSyslogClass() {
		return MultipleSyslog.class;
	}

	public String getCharSet() {
		return SyslogConstants.CHAR_SET_DEFAULT;
	}

	public int getFacility() {
		return SyslogConstants.SYSLOG_FACILITY_DEFAULT;
	}

	public String getHost() {
		return SyslogConstants.SYSLOG_HOST_DEFAULT;
	}

	public String getIdent() {
		return null;
	}
	
	public String getLocalName() {
		return null;
	}

	public int getPort() {
		return SyslogConstants.SYSLOG_PORT_DEFAULT;
	}
	
	public int getMaxShutdownWait() {
		return SyslogConstants.MAX_SHUTDOWN_WAIT_DEFAULT;
	}

	public void setMaxShutdownWait(int maxShutdownWait) {
		throw new SyslogRuntimeException("MultipleSyslog is an aggregator; please set the individual protocols");
	}

	public void insertBackLogHandler(int index, SyslogBackLogHandlerIF backLogHandler) {
		throw new SyslogRuntimeException("MultipleSyslog is an aggregator; please set the individual protocols");
	}

	public void insertMessageModifier(int index, SyslogMessageModifierIF messageModifier) {
		throw new SyslogRuntimeException("MultipleSyslog is an aggregator; please set the individual protocols");
	}

	public boolean isCacheHostAddress() {
		return SyslogConstants.CACHE_HOST_ADDRESS_DEFAULT;
	}

	public boolean isIncludeIdentInMessageModifier() {
		return SyslogConstants.INCLUDE_IDENT_IN_MESSAGE_MODIFIER_DEFAULT;
	}

	public boolean isSendLocalName() {
		return SyslogConstants.SEND_LOCAL_NAME_DEFAULT;
	}

	public boolean isSendLocalTimestamp() {
		return SyslogConstants.SEND_LOCAL_TIMESTAMP_DEFAULT;
	}

	public boolean isThrowExceptionOnInitialize() {
		return SyslogConstants.THROW_EXCEPTION_ON_INITIALIZE_DEFAULT;
	}

	public boolean isThrowExceptionOnWrite() {
		return SyslogConstants.THROW_EXCEPTION_ON_WRITE_DEFAULT;
	}

	public void removeAllBackLogHandlers() {
		throw new SyslogRuntimeException("MultipleSyslog is an aggregator; please set the individual protocols");
	}

	public void removeAllMessageModifiers() {
		throw new SyslogRuntimeException("MultipleSyslog is an aggregator; please set the individual protocols");
	}

	public void removeBackLogHandler(SyslogBackLogHandlerIF backLogHandler) {
		throw new SyslogRuntimeException("MultipleSyslog is an aggregator; please set the individual protocols");
	}

	public void removeMessageModifier(SyslogMessageModifierIF messageModifier) {
		throw new SyslogRuntimeException("MultipleSyslog is an aggregator; please set the individual protocols");
	}

	public void setCacheHostAddress(boolean cacheHostAddress) {
		throw new SyslogRuntimeException("MultipleSyslog is an aggregator; please set the individual protocols");
	}

	public void setCharSet(String charSet) {
		throw new SyslogRuntimeException("MultipleSyslog is an aggregator; please set the individual protocols");
	}

	public void setFacility(int facility) {
		throw new SyslogRuntimeException("MultipleSyslog is an aggregator; please set the individual protocols");
	}

	public void setFacility(String facilityName) {
		throw new SyslogRuntimeException("MultipleSyslog is an aggregator; please set the individual protocols");
	}

	public void setHost(String host) throws SyslogRuntimeException {
		throw new SyslogRuntimeException("MultipleSyslog is an aggregator; please set the individual protocols");
	}

	public void setIdent(String ident) {
		throw new SyslogRuntimeException("MultipleSyslog is an aggregator; please set the individual protocols");
	}

	public void setLocalName(String localName) {
		throw new SyslogRuntimeException("MultipleSyslog is an aggregator; please set the individual protocols");
	}

	public void setIncludeIdentInMessageModifier(boolean throwExceptionOnInitialize) {
		throw new SyslogRuntimeException("MultipleSyslog is an aggregator; please set the individual protocols");
	}

	public void setPort(int port) throws SyslogRuntimeException {
		throw new SyslogRuntimeException("MultipleSyslog is an aggregator; please set the individual protocols");
	}

	public void setSendLocalName(boolean sendLocalName) {
		throw new SyslogRuntimeException("MultipleSyslog is an aggregator; please set the individual protocols");
	}

	public void setSendLocalTimestamp(boolean sendLocalTimestamp) {
		throw new SyslogRuntimeException("MultipleSyslog is an aggregator; please set the individual protocols");
	}

	public void setThrowExceptionOnInitialize(boolean throwExceptionOnInitialize) {
		throw new SyslogRuntimeException("MultipleSyslog is an aggregator; please set the individual protocols");
	}

	public void setThrowExceptionOnWrite(boolean throwExceptionOnWrite) {
		throw new SyslogRuntimeException("MultipleSyslog is an aggregator; please set the individual protocols");
	}

	public int getMaxMessageLength() {
		return SyslogConstants.MAX_MESSAGE_LENGTH_DEFAULT;
	}

	public void setMaxMessageLength(int maxMessageLength) {
		throw new SyslogRuntimeException("MultipleSyslog is an aggregator; please set the individual protocols");
	}

	public boolean isTruncateMessage() {
		return SyslogConstants.TRUNCATE_MESSAGE_DEFAULT;
	}

	public void setTruncateMessage(boolean truncateMessage) {
		throw new SyslogRuntimeException("MultipleSyslog is an aggregator; please set the individual protocols");
	}

	public boolean isUseStructuredData() {
		return SyslogConstants.USE_STRUCTURED_DATA_DEFAULT;
	}

	public void setUseStructuredData(boolean useStructuredData) {
		throw new SyslogRuntimeException("MultipleSyslog is an aggregator; please set the individual protocols");
	}
}
