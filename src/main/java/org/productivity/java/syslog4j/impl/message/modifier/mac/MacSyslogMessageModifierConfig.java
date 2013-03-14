package org.productivity.java.syslog4j.impl.message.modifier.mac;

import java.security.Key;

import javax.crypto.spec.SecretKeySpec;

import org.productivity.java.syslog4j.SyslogRuntimeException;
import org.productivity.java.syslog4j.impl.message.modifier.AbstractSyslogMessageModifierConfig;
import org.productivity.java.syslog4j.util.Base64;

/**
* MacSyslogMessageModifierConfig is an implementation of AbstractSyslogMessageModifierConfig
* that provides configuration for HashSyslogMessageModifier.
* 
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
* 
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: MacSyslogMessageModifierConfig.java,v 1.3 2009/04/17 02:37:04 cvs Exp $
*/
public class MacSyslogMessageModifierConfig extends AbstractSyslogMessageModifierConfig {
	private static final long serialVersionUID = 4524180892377960695L;
	
	protected String macAlgorithm = null;
	protected String keyAlgorithm = null;
	protected Key key = null;
	
	public MacSyslogMessageModifierConfig(String macAlgorithm, String keyAlgorithm, Key key) {
		this.macAlgorithm = macAlgorithm;
		this.keyAlgorithm = keyAlgorithm;
		this.key = key;
	}

	public MacSyslogMessageModifierConfig(String macAlgorithm, String keyAlgorithm, byte[] keyBytes) {
		this.macAlgorithm = macAlgorithm;
		this.keyAlgorithm = keyAlgorithm;
		
		try {
			this.key = new SecretKeySpec(keyBytes,keyAlgorithm);
			
		} catch (IllegalArgumentException iae) {
			throw new SyslogRuntimeException(iae);
		}		
	}

	public MacSyslogMessageModifierConfig(String macAlgorithm, String keyAlgorithm, String base64Key) {
		this.macAlgorithm = macAlgorithm;
		this.keyAlgorithm = keyAlgorithm;
		
		byte[] keyBytes = Base64.decode(base64Key);
		
		try {
			this.key = new SecretKeySpec(keyBytes,keyAlgorithm);
			
		} catch (IllegalArgumentException iae) {
			throw new SyslogRuntimeException(iae);
		}
	}
	
	public static MacSyslogMessageModifierConfig createHmacSHA1(Key key) {
		return new MacSyslogMessageModifierConfig("HmacSHA1","SHA1",key);
	}

	public static MacSyslogMessageModifierConfig createHmacSHA1(String base64Key) {
		return new MacSyslogMessageModifierConfig("HmacSHA1","SHA1",base64Key);
	}

	public static MacSyslogMessageModifierConfig createHmacSHA256(Key key) {
		return new MacSyslogMessageModifierConfig("HmacSHA256","SHA-256",key);
	}

	public static MacSyslogMessageModifierConfig createHmacSHA256(String base64Key) {
		return new MacSyslogMessageModifierConfig("HmacSHA256","SHA-256",base64Key);
	}

	public static MacSyslogMessageModifierConfig createHmacSHA512(Key key) {
		return new MacSyslogMessageModifierConfig("HmacSHA512","SHA-512",key);
	}

	public static MacSyslogMessageModifierConfig createHmacSHA512(String base64Key) {
		return new MacSyslogMessageModifierConfig("HmacSHA512","SHA-512",base64Key);
	}

	public static MacSyslogMessageModifierConfig createHmacMD5(Key key) {
		return new MacSyslogMessageModifierConfig("HmacMD5","MD5",key);
	}

	public static MacSyslogMessageModifierConfig createHmacMD5(String base64Key) {
		return new MacSyslogMessageModifierConfig("HmacMD5","MD5",base64Key);
	}

	public String getMacAlgorithm() {
		return this.macAlgorithm;
	}
	
	public String getKeyAlgorithm() {
		return this.keyAlgorithm;
	}

	public Key getKey() {
		return this.key;
	}

	public void setKey(Key key) {
		this.key = key;
	}
}
