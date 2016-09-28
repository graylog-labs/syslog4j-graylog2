package org.graylog2.syslog4j.server.impl.net.udp;

import org.graylog2.syslog4j.SyslogConstants;
import org.graylog2.syslog4j.server.impl.net.AbstractNetSyslogServerConfig;

/**
 * UDPNetSyslogServerConfig provides configuration for UDPNetSyslogServer.
 * <p/>
 * <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
 * of the LGPL license is available in the META-INF folder in all
 * distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
 *
 * @author &lt;syslog4j@productivity.org&gt;
 * @version $Id: UDPNetSyslogServerConfig.java,v 1.6 2010/10/28 05:10:57 cvs Exp $
 */
public class UDPNetSyslogServerConfig extends AbstractNetSyslogServerConfig {
    private static final long serialVersionUID = -2005919161187055486L;

    private int maxMessageSize = SyslogConstants.SYSLOG_BUFFER_SIZE;

    public UDPNetSyslogServerConfig() {
        //
    }

    public UDPNetSyslogServerConfig(int port) {
        this.port = port;
    }

    public UDPNetSyslogServerConfig(String host) {
        this.host = host;
    }

    public UDPNetSyslogServerConfig(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public UDPNetSyslogServerConfig(String host, int port, int maxMessageSize) {
        this.host = host;
        this.port = port;
        this.maxMessageSize = maxMessageSize;
    }

    public int getMaxMessageSize() {
        return maxMessageSize;
    }

    public void setMaxMessageSize(int maxMessageSize) {
        this.maxMessageSize = maxMessageSize;
    }

    public Class getSyslogServerClass() {
        return UDPNetSyslogServer.class;
    }
}
