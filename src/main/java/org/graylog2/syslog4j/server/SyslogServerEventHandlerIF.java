package org.graylog2.syslog4j.server;

import java.io.Serializable;

public abstract interface SyslogServerEventHandlerIF extends Serializable {
    public void initialize(SyslogServerIF syslogServer);

    public void destroy(SyslogServerIF syslogServer);
}
