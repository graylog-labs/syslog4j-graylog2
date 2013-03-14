package org.productivity.java.syslog4j;

import java.io.Serializable;

/**
* SyslogPoolConfigIF is an interface which provides configuration support
* for the Apache Commons Pool.
* 
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
* 
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: SyslogPoolConfigIF.java,v 1.2 2009/03/29 17:38:58 cvs Exp $
*/
public interface SyslogPoolConfigIF extends Serializable {
	public int getMaxActive();
	public void setMaxActive(int maxActive);
	
	public int getMaxIdle();
	public void setMaxIdle(int maxIdle);
	
	public long getMaxWait();
	public void setMaxWait(long maxWait);
	
	public long getMinEvictableIdleTimeMillis();
	public void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis);
	
	public int getMinIdle();
	public void setMinIdle(int minIdle);
	
	public int getNumTestsPerEvictionRun();
	public void setNumTestsPerEvictionRun(int numTestsPerEvictionRun);
	
	public long getSoftMinEvictableIdleTimeMillis();
	public void setSoftMinEvictableIdleTimeMillis(long softMinEvictableIdleTimeMillis);
	
	public boolean isTestOnBorrow();
	public void setTestOnBorrow(boolean testOnBorrow);
	
	public boolean isTestOnReturn();
	public void setTestOnReturn(boolean testOnReturn);
	
	public boolean isTestWhileIdle();
	public void setTestWhileIdle(boolean testWhileIdle);
	
	public long getTimeBetweenEvictionRunsMillis();
	public void setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis);
	
	public byte getWhenExhaustedAction();
	public void setWhenExhaustedAction(byte whenExhaustedAction);
}
