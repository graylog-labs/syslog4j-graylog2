package org.graylog2.syslog4j.impl.message.processor;

import org.graylog2.syslog4j.SyslogConstants;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class AbstractSyslogMessageProcessorTest {
    @Test
    public void appendPriority() throws Exception {
        final AbstractSyslogMessageProcessor messageProcessor = new AbstractSyslogMessageProcessor() {
            @Override
            protected void appendTimestamp(StringBuffer buffer, Date datetime) {
            }

            @Override
            public String createSyslogHeader(int facility, int level, String localName, boolean sendLocalTimestamp, boolean sendLocalName) {
                return null;
            }

            @Override
            public String createSyslogHeader(int facility, int level, String localName, boolean sendLocalName, Date datetime) {
                return null;
            }
        };

        // For example, a kernel message (Facility=0) with a Severity of Emergency (Severity=0)
        // would have a Priority value of 0.
        // -- https://tools.ietf.org/html/rfc5424#section-6.2.1
        final StringBuffer sb1 = new StringBuffer();
        messageProcessor.appendPriority(sb1, SyslogConstants.FACILITY_KERN, SyslogConstants.LEVEL_EMERGENCY);
        assertEquals("<0>", sb1.toString());

        // Also, a "local use 4" message (Facility=20) with a Severity of Notice (Severity=5)
        // would have a Priority value of 165.
        // -- https://tools.ietf.org/html/rfc5424#section-6.2.1
        final StringBuffer sb2 = new StringBuffer();
        messageProcessor.appendPriority(sb2, SyslogConstants.FACILITY_LOCAL4, SyslogConstants.LEVEL_NOTICE);
        assertEquals("<165>", sb2.toString());

        // Upper bound: Facility = 23 (local7), Level = 7 (Debug)
        final StringBuffer sb3 = new StringBuffer();
        messageProcessor.appendPriority(sb3, SyslogConstants.FACILITY_LOCAL7, SyslogConstants.LEVEL_DEBUG);
        assertEquals("<191>", sb3.toString());
    }

}