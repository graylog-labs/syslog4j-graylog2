package org.productivity.java.syslog4j.impl.pool.generic;

import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.productivity.java.syslog4j.SyslogPoolConfigIF;
import org.productivity.java.syslog4j.SyslogRuntimeException;
import org.productivity.java.syslog4j.impl.pool.AbstractSyslogPoolFactory;

/**
* GenericSyslogPoolFactory is an implementation of the Apache Commons Pool
* BasePoolableObjectFactory using a GenericObjectPool.
* 
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
* 
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: GenericSyslogPoolFactory.java,v 1.5 2008/12/10 04:15:10 cvs Exp $
*/
public class GenericSyslogPoolFactory extends AbstractSyslogPoolFactory {
	protected void configureGenericObjectPool(GenericObjectPool genericObjectPool) throws SyslogRuntimeException {
		SyslogPoolConfigIF poolConfig = null;
		
		try {
			poolConfig = (SyslogPoolConfigIF) this.syslog.getConfig();
			
		} catch (ClassCastException cce) {
			throw new SyslogRuntimeException("config must implement interface SyslogPoolConfigIF");
		}
		
		genericObjectPool.setMaxActive(poolConfig.getMaxActive());
		genericObjectPool.setMaxIdle(poolConfig.getMaxIdle());
		genericObjectPool.setMaxWait(poolConfig.getMaxWait());
		genericObjectPool.setMinEvictableIdleTimeMillis(poolConfig.getMinEvictableIdleTimeMillis());
		genericObjectPool.setMinIdle(poolConfig.getMinIdle());
		genericObjectPool.setNumTestsPerEvictionRun(poolConfig.getNumTestsPerEvictionRun());
		genericObjectPool.setSoftMinEvictableIdleTimeMillis(poolConfig.getSoftMinEvictableIdleTimeMillis());
		genericObjectPool.setTestOnBorrow(poolConfig.isTestOnBorrow());
		genericObjectPool.setTestOnReturn(poolConfig.isTestOnReturn());
		genericObjectPool.setTestWhileIdle(poolConfig.isTestWhileIdle());
		genericObjectPool.setTimeBetweenEvictionRunsMillis(poolConfig.getTimeBetweenEvictionRunsMillis());
		genericObjectPool.setWhenExhaustedAction(poolConfig.getWhenExhaustedAction());
	}
	
	public ObjectPool createPool() throws SyslogRuntimeException {
		GenericObjectPool genericPool = new GenericObjectPool(this);
		
		configureGenericObjectPool(genericPool);
		
		return genericPool;
	}
}
