package org.graylog2.syslog4j.server.impl.event.structured;

import org.joda.time.DateTime;
import org.testng.annotations.Test;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;

public class StructuredSyslogServerEventTest {
    private final InetAddress INET_ADDR = new InetSocketAddress(514).getAddress();

    private StructuredSyslogServerEvent buildEvent(String message) {
        return new StructuredSyslogServerEvent(message, INET_ADDR);
    }

    @Test
    public void testStructured1() throws Exception {
        // Message from: https://tools.ietf.org/html/rfc5424#section-6.5
        final String message = "<165>1 2012-12-25T22:14:15.003Z mymachine.example.com evntslog - ID47 [exampleSDID@32473 iut=\"3\" eventSource=\"Application\" eventID=\"1011\"] BOMAn application event log entry";

        final StructuredSyslogServerEvent event = buildEvent(message);

        Map<String, Object> structuredData = new HashMap<String, Object>() {
            {
                put("exampleSDID@32473", new HashMap<String, String>() {
                    {
                        put("eventSource", "Application");
                        put("eventID", "1011");
                        put("iut", "3");
                    }
                });
            }
        };

        assertEquals(event.getApplicationName(), "evntslog");
        assertEquals(event.getDateTime(), new DateTime("2012-12-25T22:14:15.003Z"));
        assertEquals(event.getFacility(), 20);
        assertEquals(event.getHost(), "mymachine.example.com");
        assertEquals(event.getLevel(), 5);
        assertEquals(event.getMessage(), "ID47 [exampleSDID@32473 iut=\"3\" eventSource=\"Application\" eventID=\"1011\"] BOMAn application event log entry");
        assertEquals(event.getProcessId(), null);

        assertEquals(event.getStructuredMessage().getStructuredData(), structuredData);
        assertEquals(event.getStructuredMessage().getMessageId(), "ID47");
        assertEquals(event.getStructuredMessage().getMessage(), "BOMAn application event log entry");
    }

    @Test
    public void testStructured2() throws Exception {
        // Message from: https://github.com/Graylog2/graylog2-server/issues/845
        final String message = "<190>1 2015-01-06T20:56:33.287Z app-1 app - - [mdc@18060 ip=\"::ffff:132.213.51.30\" logger=\"{c.corp.Handler}\" session=\"4ot7\" user=\"user@example.com\" user-agent=\"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_5) AppleWebKit/600.2.5 (KHTML, like Gecko) Version/7.1.2 Safari/537.85.11\"] User page 13 requested";

        final StructuredSyslogServerEvent event = buildEvent(message);

        Map<String, Object> structuredData = new HashMap<String, Object>() {
            {
                put("mdc@18060", new HashMap<String, String>() {
                    {
                        put("ip", "::ffff:132.213.51.30");
                        put("logger", "{c.corp.Handler}");
                        put("session", "4ot7");
                        put("user", "user@example.com");
                        put("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_5) AppleWebKit/600.2.5 (KHTML, like Gecko) Version/7.1.2 Safari/537.85.11");
                    }
                });
            }
        };

        assertEquals(event.getApplicationName(), "app");
        assertEquals(event.getDateTime(), new DateTime("2015-01-06T20:56:33.287Z"));
        assertEquals(event.getFacility(), 23);
        assertEquals(event.getHost(), "app-1");
        assertEquals(event.getLevel(), 6);
        assertEquals(event.getMessage(), "- [mdc@18060 ip=\"::ffff:132.213.51.30\" logger=\"{c.corp.Handler}\" session=\"4ot7\" user=\"user@example.com\" user-agent=\"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_5) AppleWebKit/600.2.5 (KHTML, like Gecko) Version/7.1.2 Safari/537.85.11\"] User page 13 requested");
        assertEquals(event.getProcessId(), null);

        assertEquals(event.getStructuredMessage().getStructuredData(), structuredData);
        assertEquals(event.getStructuredMessage().getMessageId(), null);
        assertEquals(event.getStructuredMessage().getMessage(), "User page 13 requested");
    }

    @Test
    public void testStructured3() throws Exception {
        // Message from: https://github.com/Graylog2/graylog2-server/issues/845
        final String message = "<128>1 2015-01-11T16:35:21.335797+01:00 s000000.example.com - - - - tralala";

        final StructuredSyslogServerEvent event = buildEvent(message);

        assertEquals(event.getApplicationName(), null);
        assertEquals(event.getDateTime(), new DateTime("2015-01-11T15:35:21.335797Z"));
        assertEquals(event.getFacility(), 16);
        assertEquals(event.getHost(), "s000000.example.com");
        assertEquals(event.getLevel(), 0);
        assertEquals(event.getMessage(), "- - tralala");
        assertEquals(event.getProcessId(), null);

        assertEquals(event.getStructuredMessage().getStructuredData(), null);
        assertEquals(event.getStructuredMessage().getMessageId(), null);
        assertEquals(event.getStructuredMessage().getMessage(), "- - tralala");
    }

    @Test
    public void testStructuredWithOnlyStructuredData() throws Exception {
        // Message from: https://tools.ietf.org/html/rfc5424#section-6.5
        final String message = "<165>1 2003-10-11T22:14:15.003Z mymachine.example.com evntslog - ID47 [exampleSDID@32473 iut=\"3\" eventSource=\"Application\" eventID=\"1011\"][examplePriority@32473 class=\"high\"]";

        final StructuredSyslogServerEvent event = buildEvent(message);

        Map<String, Object> structuredData = new HashMap<String, Object>() {
            {
                put("exampleSDID@32473", new HashMap<String, String>() {
                    {
                        put("eventSource", "Application");
                        put("eventID", "1011");
                        put("iut", "3");
                    }
                });

                put("examplePriority@32473", new HashMap<String, String>() {
                    {
                        put("class", "high");
                    }
                });
            }
        };

        assertEquals(event.getApplicationName(), "evntslog");
        assertEquals(event.getDateTime(), new DateTime("2003-10-11T22:14:15.003Z"));
        assertEquals(event.getFacility(), 20);
        assertEquals(event.getHost(), "mymachine.example.com");
        assertEquals(event.getLevel(), 5);
        assertEquals(event.getMessage(), "ID47 [exampleSDID@32473 iut=\"3\" eventSource=\"Application\" eventID=\"1011\"][examplePriority@32473 class=\"high\"]");
        assertEquals(event.getProcessId(), null);

        assertEquals(event.getStructuredMessage().getStructuredData(), structuredData);
        assertEquals(event.getStructuredMessage().getMessageId(), "ID47");
        assertEquals(event.getStructuredMessage().getMessage(), "");
    }


    @Test
    public void testStructuredWithoutStructuredData() throws Exception {
        // Message from: https://tools.ietf.org/html/rfc5424#section-6.5
        final String message = "<165>1 2003-08-24T05:14:15.000003-07:00 192.0.2.1 myproc 8710 - - %% It's time to make the do-nuts.";

        final StructuredSyslogServerEvent event = buildEvent(message);

        assertEquals(event.getApplicationName(), "myproc");
        assertEquals(event.getDateTime(), new DateTime("2003-08-24T05:14:15.000003-07:00"));
        assertEquals(event.getFacility(), 20);
        assertEquals(event.getHost(), "192.0.2.1");
        assertEquals(event.getLevel(), 5);
        assertEquals(event.getMessage(), "- - %% It's time to make the do-nuts.");
        assertEquals(event.getProcessId(), "8710");

        assertEquals(event.getStructuredMessage().getStructuredData(), null);
        assertEquals(event.getStructuredMessage().getMessageId(), null);
        assertEquals(event.getStructuredMessage().getMessage(), "- - %% It's time to make the do-nuts.");
    }

    @Test
    public void testStructuredSyslogNg1() throws Exception {
        // Message from: syslog-ng-core 3.5.3-1 package in Ubuntu 14.04 (default config)
        // Manually added ".000" to timestamp!
        final String message = "<45>1 2014-10-21T10:21:09.000+00:00 c4dc57ba1ebb syslog-ng 7120 - [meta sequenceId=\"1\"] syslog-ng starting up; version='3.5.3'";

        final StructuredSyslogServerEvent event = buildEvent(message);

        Map<String, Object> structuredData = new HashMap<String, Object>() {
            {
                put("meta", new HashMap<String, String>() {
                    {
                        put("sequenceId", "1");
                    }
                });
            }
        };

        assertEquals(event.getApplicationName(), "syslog-ng");
        assertEquals(event.getDateTime(), new DateTime("2014-10-21T10:21:09.000Z"));
        assertEquals(event.getFacility(), 5);
        assertEquals(event.getHost(), "c4dc57ba1ebb");
        assertEquals(event.getLevel(), 5);
        assertEquals(event.getMessage(), "- [meta sequenceId=\"1\"] syslog-ng starting up; version='3.5.3'");
        assertEquals(event.getProcessId(), "7120");

        assertEquals(event.getStructuredMessage().getStructuredData(), structuredData);
        assertEquals(event.getStructuredMessage().getMessageId(), null);
        assertEquals(event.getStructuredMessage().getMessage(), "syslog-ng starting up; version='3.5.3'");
    }

    @Test
    public void testStructuredSyslogNgNoMillisecTimestamp() throws Exception {
        // Message from: syslog-ng-core 3.5.3-1 package in Ubuntu 14.04 (default config)
        final String message = "<45>1 2014-10-21T10:21:09+00:00 c4dc57ba1ebb syslog-ng 7120 - [meta sequenceId=\"1\"] syslog-ng starting up; version='3.5.3'";

        final StructuredSyslogServerEvent event = buildEvent(message);

        Map<String, Object> structuredData = new HashMap<String, Object>() {
            {
                put("meta", new HashMap<String, String>() {
                    {
                        put("sequenceId", "1");
                    }
                });
            }
        };

        assertEquals(event.getApplicationName(), "syslog-ng");
        assertEquals(event.getDateTime(), new DateTime("2014-10-21T10:21:09.000Z"));
        assertEquals(event.getFacility(), 5);
        assertEquals(event.getHost(), "c4dc57ba1ebb");
        assertEquals(event.getLevel(), 5);
        assertEquals(event.getMessage(), "- [meta sequenceId=\"1\"] syslog-ng starting up; version='3.5.3'");
        assertEquals(event.getProcessId(), "7120");

        assertEquals(event.getStructuredMessage().getStructuredData(), structuredData);
        assertEquals(event.getStructuredMessage().getMessageId(), null);
        assertEquals(event.getStructuredMessage().getMessage(), "syslog-ng starting up; version='3.5.3'");
    }
}