package org.productivity.java.syslog4j.impl.message.modifier.text;

import org.productivity.java.syslog4j.SyslogIF;
import org.productivity.java.syslog4j.SyslogMessageModifierIF;

/**
* PrefixSyslogMessageModifier is an implementation of SyslogMessageModifierIF
* that provides support for adding static text to the beginning of a Syslog message.
* 
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
* 
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: PrefixSyslogMessageModifier.java,v 1.5 2010/10/28 05:10:57 cvs Exp $
*/
public class PrefixSyslogMessageModifier implements SyslogMessageModifierIF {
	private static final long serialVersionUID = 6718826215583513972L;
	
	protected String prefix = null;
	protected String delimiter = " ";

	public PrefixSyslogMessageModifier() {
		//
	}
	
	public PrefixSyslogMessageModifier(String prefix) {
		this.prefix = prefix;
	}

	public PrefixSyslogMessageModifier(String prefix, String delimiter) {
		this.prefix = prefix;
		if (delimiter != null) {
			this.delimiter = delimiter;
		}
	}

	public String getPrefix() {
		return this.prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String modify(SyslogIF syslog, int facility, int level, String message) {
		if (this.prefix == null || "".equals(this.prefix.trim())) {
			return message;
		}

		return this.prefix + this.delimiter + message;
	}

	public boolean verify(String message) {
		// NO-OP
		
		return true;
	}
}
