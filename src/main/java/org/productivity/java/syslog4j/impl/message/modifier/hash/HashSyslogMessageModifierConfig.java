package org.productivity.java.syslog4j.impl.message.modifier.hash;

import org.productivity.java.syslog4j.impl.message.modifier.AbstractSyslogMessageModifierConfig;

/**
* HashSyslogMessageModifierConfig is an implementation of AbstractSyslogMessageModifierConfig
* that provides configuration for HashSyslogMessageModifier.
* 
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
* 
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: HashSyslogMessageModifierConfig.java,v 1.1 2008/11/10 04:38:37 cvs Exp $
*/
public class HashSyslogMessageModifierConfig extends AbstractSyslogMessageModifierConfig {
	private static final long serialVersionUID = -3148300281439874231L;
	
	protected String hashAlgorithm = null;
	
	public static final HashSyslogMessageModifierConfig createMD5() {
		HashSyslogMessageModifierConfig md5 = new HashSyslogMessageModifierConfig("MD5");
		
		return md5;
	}
	
	public static final HashSyslogMessageModifierConfig createSHA1() {
		HashSyslogMessageModifierConfig sha1 = new HashSyslogMessageModifierConfig("SHA1");
		
		return sha1;
	}
	
	public static final HashSyslogMessageModifierConfig createSHA160() {
		 return createSHA1();
	}
	
	public static final HashSyslogMessageModifierConfig createSHA256() {
		HashSyslogMessageModifierConfig sha256 = new HashSyslogMessageModifierConfig("SHA-256");
		
		return sha256;
	}
	
	public static final HashSyslogMessageModifierConfig createSHA384() {
		HashSyslogMessageModifierConfig sha384 = new HashSyslogMessageModifierConfig("SHA-384");
		
		return sha384;
	}
	
	public static final HashSyslogMessageModifierConfig createSHA512() {
		HashSyslogMessageModifierConfig sha512 = new HashSyslogMessageModifierConfig("SHA-512");
		
		return sha512;
	}
	
	public HashSyslogMessageModifierConfig(String hashAlgorithm) {
		this.hashAlgorithm = hashAlgorithm;
	}

	public String getHashAlgorithm() {
		return this.hashAlgorithm;
	}
	
	public void setHashAlgorithm(String hashAlgorithm) {
		this.hashAlgorithm = hashAlgorithm;
	}
}
