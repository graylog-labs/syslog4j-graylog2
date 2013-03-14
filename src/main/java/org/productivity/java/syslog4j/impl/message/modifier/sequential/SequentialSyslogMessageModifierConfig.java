package org.productivity.java.syslog4j.impl.message.modifier.sequential;

import org.productivity.java.syslog4j.impl.message.modifier.AbstractSyslogMessageModifierConfig;

/**
* SequentialSyslogMessageModifierConfig is an implementation of AbstractSyslogMessageModifierConfig
* that provides configuration for SequentialSyslogMessageModifier.
* 
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
* 
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: SequentialSyslogMessageModifierConfig.java,v 1.4 2009/03/29 17:38:58 cvs Exp $
*/
public class SequentialSyslogMessageModifierConfig extends AbstractSyslogMessageModifierConfig {
	private static final long serialVersionUID = 1570930406228960303L;
	
	protected long firstNumber = SYSLOG_SEQUENTIAL_MESSAGE_MODIFIER_FIRST_NUMBER_DEFAULT;
	protected long lastNumber = SYSLOG_SEQUENTIAL_MESSAGE_MODIFIER_LAST_NUMBER_DEFAULT;
	protected char padChar = SYSLOG_SEQUENTIAL_MESSAGE_MODIFIER_PAD_CHAR_DEFAULT;
	protected boolean usePadding = SYSLOG_SEQUENTIAL_MESSAGE_MODIFIER_USE_PADDING_DEFAULT;
	
	public static final SequentialSyslogMessageModifierConfig createDefault() {
		SequentialSyslogMessageModifierConfig modifierConfig = new SequentialSyslogMessageModifierConfig();
		
		return modifierConfig;
	}

	public SequentialSyslogMessageModifierConfig() {
		setPrefix(SYSLOG_SEQUENTIAL_MESSAGE_MODIFIER_PREFIX_DEFAULT);
		setSuffix(SYSLOG_SEQUENTIAL_MESSAGE_MODIFIER_SUFFIX_DEFAULT);
	}
	
	public long getLastNumberDigits() {
		return Long.toString(this.lastNumber).length();
	}
	
	public long getFirstNumber() {
		return this.firstNumber;
	}
	
	public void setFirstNumber(long firstNumber) {
		if (firstNumber < this.lastNumber) {
			this.firstNumber = firstNumber;
		}
	}
	
	public long getLastNumber() {
		return this.lastNumber;
	}
	
	public void setLastNumber(long lastNumber) {
		if (lastNumber > this.firstNumber) {
			this.lastNumber = lastNumber;
		}
	}
	
	public boolean isUsePadding() {
		return this.usePadding;
	}

	public void setUsePadding(boolean usePadding) {
		this.usePadding = usePadding;
	}

	public char getPadChar() {
		return this.padChar;
	}
	
	public void setPadChar(char padChar) {
		this.padChar = padChar;
	}
}
