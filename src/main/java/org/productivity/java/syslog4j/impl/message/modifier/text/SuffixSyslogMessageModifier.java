package org.productivity.java.syslog4j.impl.message.modifier.text;

import org.productivity.java.syslog4j.SyslogIF;
import org.productivity.java.syslog4j.SyslogMessageModifierIF;

/**
* SuffixSyslogMessageModifier is an implementation of SyslogMessageModifierIF
* that provides support for adding static text to the end of a Syslog message.
* 
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
* 
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: SuffixSyslogMessageModifier.java,v 1.5 2010/10/28 05:10:57 cvs Exp $
*/
public class SuffixSyslogMessageModifier implements SyslogMessageModifierIF {
	private static final long serialVersionUID = 7160593302741507576L;
	
	protected String suffix = null;
	protected String delimiter = " ";

	public SuffixSyslogMessageModifier() {
		//
	}

	public SuffixSyslogMessageModifier(String suffix) {
		this.suffix = suffix;
	}

	public SuffixSyslogMessageModifier(String suffix, String delimiter) {
		this.suffix = suffix;
		if (delimiter != null) {
			this.delimiter = delimiter;
		}
	}

	public String getSuffix() {
		return this.suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String modify(SyslogIF syslog, int facility, int level, String message) {
		if (this.suffix == null || "".equals(this.suffix.trim())) {
			return message;
		}

		return message + this.delimiter + this.suffix;
	}

	public boolean verify(String message) {
		// NO-OP
		
		return true;
	}
}
