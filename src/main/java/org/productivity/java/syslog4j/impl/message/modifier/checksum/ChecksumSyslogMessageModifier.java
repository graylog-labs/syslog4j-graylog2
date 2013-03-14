package org.productivity.java.syslog4j.impl.message.modifier.checksum;

import org.productivity.java.syslog4j.SyslogIF;
import org.productivity.java.syslog4j.SyslogRuntimeException;
import org.productivity.java.syslog4j.impl.message.modifier.AbstractSyslogMessageModifier;
import org.productivity.java.syslog4j.util.SyslogUtility;

/**
* ChecksumSyslogMessageModifier is an implementation of SyslogMessageModifierIF
* that provides support for Java Checksum algorithms (java.util.zip.Checksum).
* 
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
* 
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: ChecksumSyslogMessageModifier.java,v 1.5 2010/10/28 05:10:57 cvs Exp $
*/
public class ChecksumSyslogMessageModifier extends AbstractSyslogMessageModifier {
	private static final long serialVersionUID = -3268914290497005065L;
	
	protected ChecksumSyslogMessageModifierConfig config = null;
	
	public static final ChecksumSyslogMessageModifier createCRC32() {
		ChecksumSyslogMessageModifier crc32 = new ChecksumSyslogMessageModifier(ChecksumSyslogMessageModifierConfig.createCRC32());
		
		return crc32;
	}
	public static final ChecksumSyslogMessageModifier createADLER32() {
		ChecksumSyslogMessageModifier adler32 = new ChecksumSyslogMessageModifier(ChecksumSyslogMessageModifierConfig.createADLER32());
		
		return adler32;
	}
	
	public ChecksumSyslogMessageModifier(ChecksumSyslogMessageModifierConfig config) {
		super(config);
		
		this.config = config;
		
		if (this.config == null) {
			throw new SyslogRuntimeException("Checksum config object cannot be null");
		}
		
		if (this.config.getChecksum() == null) {
			throw new SyslogRuntimeException("Checksum object cannot be null");
		}
	}
	
	public ChecksumSyslogMessageModifierConfig getConfig() {
		return this.config;
	}
	
	protected void continuousCheckForVerify() {
		if (this.config.isContinuous()) {
			throw new SyslogRuntimeException(this.getClass().getName() + ".verify(..) does not work with isContinuous() returning true");
		}
		
	}
	
	public boolean verify(String message, String hexChecksum) {
		continuousCheckForVerify();

		long checksum = Long.parseLong(hexChecksum,16);
		
		return verify(message,checksum);
	}
	
	public boolean verify(String message, long checksum) {
		continuousCheckForVerify();
		
		synchronized(this.config.getChecksum()) {
			this.config.getChecksum().reset();
			
			byte[] messageBytes = SyslogUtility.getBytes(this.config,message);
			
			this.config.getChecksum().update(messageBytes,0,message.length());
			
			return this.config.getChecksum().getValue() == checksum;
		}
	}

	public String modify(SyslogIF syslog, int facility, int level, String message) {
		synchronized(this.config.getChecksum()) {
			StringBuffer messageBuffer = new StringBuffer(message);
			
			byte[] messageBytes = SyslogUtility.getBytes(syslog.getConfig(),message);
			
			if (!this.config.isContinuous()) {
				this.config.getChecksum().reset();
			}
			
			this.config.getChecksum().update(messageBytes,0,message.length());
			
			messageBuffer.append(this.config.getPrefix());
			messageBuffer.append(Long.toHexString(this.config.getChecksum().getValue()).toUpperCase());
			messageBuffer.append(this.config.getSuffix());
			
			return messageBuffer.toString();
		}
	}
}
