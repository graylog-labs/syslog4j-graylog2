package org.graylog2.syslog4j.server.impl.event.structured;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;


public class StructuredSyslogServerEventTest {
    private static final DateTimeZone MST = DateTimeZone.forID("MST");
    private final InetAddress INET_ADDR = new InetSocketAddress(514).getAddress();

    private StructuredSyslogServerEvent buildEvent(String message, DateTimeZone defaultZone) {
        return new StructuredSyslogServerEvent(message, INET_ADDR, defaultZone);
    }

    @Test
    public void testStructured1() throws Exception {
        // Message from: https://tools.ietf.org/html/rfc5424#section-6.5
        final String message = "<165>1 2012-12-25T22:14:15.003Z mymachine.example.com evntslog - ID47 [exampleSDID@32473 iut=\"3\" eventSource=\"Application\" eventID=\"1011\"] BOMAn application event log entry";

        final StructuredSyslogServerEvent event = buildEvent(message, null);

        Map<String, Map<String, String>> structuredData = new HashMap<String, Map<String, String>>() {
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

        assertEquals("evntslog", event.getApplicationName());
        assertEquals(new DateTime("2012-12-25T22:14:15.003Z"), event.getDateTime());
        assertEquals(20, event.getFacility());
        assertEquals("mymachine.example.com", event.getHost());
        assertEquals(5, event.getLevel());
        assertEquals("ID47 [exampleSDID@32473 iut=\"3\" eventSource=\"Application\" eventID=\"1011\"] BOMAn application event log entry", event.getMessage());
        assertEquals(null, event.getProcessId());

        assertEquals(structuredData, event.getStructuredMessage().getStructuredData());
        assertEquals("ID47", event.getStructuredMessage().getMessageId());
        assertEquals("BOMAn application event log entry", event.getStructuredMessage().getMessage());
    }

    @Test
    public void testStructured2() throws Exception {
        // Message from: https://github.com/Graylog2/graylog2-server/issues/845
        final String message = "<190>1 2015-01-06T20:56:33.287Z app-1 app - - [mdc@18060 ip=\"::ffff:132.213.51.30\" logger=\"{c.corp.Handler}\" session=\"4ot7\" user=\"user@example.com\" user-agent=\"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_5) AppleWebKit/600.2.5 (KHTML, like Gecko) Version/7.1.2 Safari/537.85.11\"] User page 13 requested";

        final StructuredSyslogServerEvent event = buildEvent(message, null);

        Map<String, Map<String, String>> structuredData = new HashMap<String, Map<String, String>>() {
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

        assertEquals("app", event.getApplicationName());
        assertEquals(new DateTime("2015-01-06T20:56:33.287Z"), event.getDateTime());
        assertEquals(23, event.getFacility());
        assertEquals("app-1", event.getHost());
        assertEquals(6, event.getLevel());
        assertEquals("- [mdc@18060 ip=\"::ffff:132.213.51.30\" logger=\"{c.corp.Handler}\" session=\"4ot7\" user=\"user@example.com\" user-agent=\"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_5) AppleWebKit/600.2.5 (KHTML, like Gecko) Version/7.1.2 Safari/537.85.11\"] User page 13 requested", event.getMessage());
        assertEquals(null, event.getProcessId());

        assertEquals(structuredData, event.getStructuredMessage().getStructuredData());
        assertEquals(null, event.getStructuredMessage().getMessageId());
        assertEquals("User page 13 requested", event.getStructuredMessage().getMessage());
    }

    @Test
    public void testStructured3() throws Exception {
        // Message from: https://github.com/Graylog2/graylog2-server/issues/845
        final String message = "<128>1 2015-01-11T16:35:21.335797+01:00 s000000.example.com - - - - tralala";

        final StructuredSyslogServerEvent event = buildEvent(message, null);

        assertEquals(null, event.getApplicationName());
        assertEquals(new DateTime("2015-01-11T15:35:21.335797Z"), event.getDateTime());
        assertEquals(16, event.getFacility());
        assertEquals("s000000.example.com", event.getHost());
        assertEquals(0, event.getLevel());
        assertEquals("- - tralala", event.getMessage());
        assertEquals(null, event.getProcessId());

        assertEquals(null, event.getStructuredMessage().getStructuredData());
        assertEquals(null, event.getStructuredMessage().getMessageId());
        assertEquals("tralala", event.getStructuredMessage().getMessage());
    }

    @Test
    public void testStructuredWithOnlyStructuredData() throws Exception {
        // Message from: https://tools.ietf.org/html/rfc5424#section-6.5
        final String message = "<165>1 2003-10-11T22:14:15.003Z mymachine.example.com evntslog - ID47 [exampleSDID@32473 iut=\"3\" eventSource=\"Application\" eventID=\"1011\"][examplePriority@32473 class=\"high\"]";

        final StructuredSyslogServerEvent event = buildEvent(message, null);

        Map<String, Map<String, String>> structuredData = new HashMap<String, Map<String, String>>() {
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

        assertEquals("evntslog", event.getApplicationName());
        assertEquals(new DateTime("2003-10-11T22:14:15.003Z"), event.getDateTime());
        assertEquals(20, event.getFacility());
        assertEquals("mymachine.example.com", event.getHost());
        assertEquals(5, event.getLevel());
        assertEquals("ID47 [exampleSDID@32473 iut=\"3\" eventSource=\"Application\" eventID=\"1011\"][examplePriority@32473 class=\"high\"]", event.getMessage());
        assertEquals(null, event.getProcessId());

        assertEquals(structuredData, event.getStructuredMessage().getStructuredData());
        assertEquals("ID47", event.getStructuredMessage().getMessageId());
        assertEquals("", event.getStructuredMessage().getMessage());
    }


    @Test
    public void testStructuredWithoutStructuredData() throws Exception {
        // Message from: https://tools.ietf.org/html/rfc5424#section-6.5
        final String message = "<165>1 2003-08-24T05:14:15.000003-07:00 192.0.2.1 myproc 8710 - - %% It's time to make the do-nuts.";

        final StructuredSyslogServerEvent event = buildEvent(message, null);

        assertEquals("myproc", event.getApplicationName());
        assertEquals(new DateTime("2003-08-24T05:14:15.000003-07:00"), event.getDateTime());
        assertEquals(20, event.getFacility());
        assertEquals("192.0.2.1", event.getHost());
        assertEquals(5, event.getLevel());
        assertEquals("- - %% It's time to make the do-nuts.", event.getMessage());
        assertEquals("8710", event.getProcessId());

        assertEquals(null, event.getStructuredMessage().getStructuredData());
        assertEquals(null, event.getStructuredMessage().getMessageId());
        assertEquals("%% It's time to make the do-nuts.", event.getStructuredMessage().getMessage());
    }

    @Test
    public void testStructuredSyslogNg1() throws Exception {
        // Message from: syslog-ng-core 3.5.3-1 package in Ubuntu 14.04 (default config)
        // Manually added ".000" to timestamp!
        final String message = "<45>1 2014-10-21T10:21:09.000+00:00 c4dc57ba1ebb syslog-ng 7120 - [meta sequenceId=\"1\"] syslog-ng starting up; version='3.5.3'";

        final StructuredSyslogServerEvent event = buildEvent(message, null);

        Map<String, Map<String, String>> structuredData = new HashMap<String, Map<String, String>>() {
            {
                put("meta", new HashMap<String, String>() {
                    {
                        put("sequenceId", "1");
                    }
                });
            }
        };

        assertEquals("syslog-ng", event.getApplicationName());
        assertEquals(new DateTime("2014-10-21T10:21:09.000Z"), event.getDateTime());
        assertEquals(5, event.getFacility());
        assertEquals("c4dc57ba1ebb", event.getHost());
        assertEquals(5, event.getLevel());
        assertEquals("- [meta sequenceId=\"1\"] syslog-ng starting up; version='3.5.3'", event.getMessage());
        assertEquals("7120", event.getProcessId());

        assertEquals(structuredData, event.getStructuredMessage().getStructuredData());
        assertEquals(null, event.getStructuredMessage().getMessageId());
        assertEquals("syslog-ng starting up; version='3.5.3'", event.getStructuredMessage().getMessage());
    }

    @Test
    public void testStructuredSyslogNgNoMillisecTimestamp() throws Exception {
        // Message from: syslog-ng-core 3.5.3-1 package in Ubuntu 14.04 (default config)
        final String message = "<45>1 2014-10-21T10:21:09+00:00 c4dc57ba1ebb syslog-ng 7120 - [meta sequenceId=\"1\"] syslog-ng starting up; version='3.5.3'";

        final StructuredSyslogServerEvent event = buildEvent(message, null);

        Map<String, Map<String, String>> structuredData = new HashMap<String, Map<String, String>>() {
            {
                put("meta", new HashMap<String, String>() {
                    {
                        put("sequenceId", "1");
                    }
                });
            }
        };

        assertEquals("syslog-ng", event.getApplicationName());
        assertEquals(new DateTime("2014-10-21T10:21:09.000Z"), event.getDateTime());
        assertEquals(5, event.getFacility());
        assertEquals("c4dc57ba1ebb", event.getHost());
        assertEquals(5, event.getLevel());
        assertEquals("- [meta sequenceId=\"1\"] syslog-ng starting up; version='3.5.3'", event.getMessage());
        assertEquals("7120", event.getProcessId());

        assertEquals(structuredData, event.getStructuredMessage().getStructuredData());
        assertEquals(null, event.getStructuredMessage().getMessageId());
        assertEquals("syslog-ng starting up; version='3.5.3'", event.getStructuredMessage().getMessage());
    }

    @Test
    public void testDefaultTimeZoneNotSet() throws Exception {
        // Message from: https://github.com/Graylog2/graylog2-server/issues/845
        final String messageWithoutZone = "<45>1 2014-10-21T10:21:09 c4dc57ba1ebb syslog-ng 7120 - [meta sequenceId=\"1\"] syslog-ng starting up; version='3.5.3'";
        final String messageWithZone = "<45>1 2014-10-21T10:21:09-07:00 c4dc57ba1ebb syslog-ng 7120 - [meta sequenceId=\"1\"] syslog-ng starting up; version='3.5.3'";

        assertEquals(new DateTime("2014-10-21T10:21:09.000"), buildEvent(messageWithoutZone, null).getDateTime());
        assertEquals(new DateTime("2014-10-21T10:21:09.000-07:00"), buildEvent(messageWithZone, null).getDateTime());
    }

    @Test
    public void testDefaultTimeZoneSet() throws Exception {
        final String messageWithoutZone = "<45>1 2014-10-21T10:21:09 c4dc57ba1ebb syslog-ng 7120 - [meta sequenceId=\"1\"] syslog-ng starting up; version='3.5.3'";
        final String messageWithZone = "<45>1 2014-10-21T10:21:09+01:00 c4dc57ba1ebb syslog-ng 7120 - [meta sequenceId=\"1\"] syslog-ng starting up; version='3.5.3'";

        assertEquals(new DateTime("2014-10-21T10:21:09.000-07:00", MST), buildEvent(messageWithoutZone,  MST).getDateTime());
        assertEquals(new DateTime("2014-10-21T10:21:09.000+01:00"), buildEvent(messageWithZone, MST).getDateTime());
    }
}
