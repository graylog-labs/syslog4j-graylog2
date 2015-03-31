package org.graylog2.syslog4j.server.impl.event;

import org.joda.time.DateTime;
import org.junit.Test;

import java.net.InetAddress;
import java.net.InetSocketAddress;

import static org.junit.Assert.assertEquals;

public class SyslogServerEventTest {
    private final InetAddress INET_ADDR = new InetSocketAddress(514).getAddress();

    private SyslogServerEvent buildEvent(String message) {
        return new SyslogServerEvent(message, INET_ADDR);
    }

    @Test
    public void test1() throws Exception {
        // Message from: https://tools.ietf.org/html/rfc3164#section-5.4
        final String message = "<34>Oct 11 22:14:15 mymachine su: 'su root' failed for lonvick on /dev/pts/8";

        final SyslogServerEvent event = buildEvent(message);

        assertEquals(new DateTime(new DateTime().getYear() + "-10-11T22:14:15").toDate(), event.getDate());
        assertEquals(4, event.getFacility());
        assertEquals("mymachine", event.getHost());
        assertEquals(2, event.getLevel());
        assertEquals("mymachine su: 'su root' failed for lonvick on /dev/pts/8", event.getMessage());
    }

    @Test
    public void test2() throws Exception {
        // Message from: https://tools.ietf.org/html/rfc3164#section-5.4
        final String message = "<13>Feb  5 17:32:18 10.0.0.99 Use the BFG!";

        final SyslogServerEvent event = buildEvent(message);

        assertEquals(new DateTime(new DateTime().getYear() + "-02-05T17:32:18").toDate(), event.getDate());
        assertEquals(1, event.getFacility());
        assertEquals("10.0.0.99", event.getHost());
        assertEquals(5, event.getLevel());
        assertEquals("10.0.0.99 Use the BFG!", event.getMessage());
    }

    @Test
    public void test3() throws Exception {
        // Message from: https://github.com/spotify/flume-syslog-source2/blob/master/src/test/java/com/spotify/flume/syslog2/SyslogParserTest.java
        // Not really valid, but want to check double-digit day. (day 05)
        final String message = "<11>Oct 05 12:23:34 hostname tag: hello world";

        final SyslogServerEvent event = buildEvent(message);

        assertEquals(new DateTime(new DateTime().getYear() + "-10-05T12:23:34").toDate(), event.getDate());
        assertEquals(1, event.getFacility());
        assertEquals("hostname", event.getHost());
        assertEquals(3, event.getLevel());
        assertEquals("hostname tag: hello world", event.getMessage());
    }

    @Test
    public void test4() throws Exception {
        // Message from: https://tools.ietf.org/html/rfc3164#section-5.4
        // Added pid to program.
        final String message = "<34>Oct 11 22:14:15 mymachine su[123]: 'su root' failed for lonvick on /dev/pts/8";

        final SyslogServerEvent event = buildEvent(message);

        assertEquals(new DateTime(new DateTime().getYear() + "-10-11T22:14:15").toDate(), event.getDate());
        assertEquals(4, event.getFacility());
        assertEquals("mymachine", event.getHost());
        assertEquals(2, event.getLevel());
        assertEquals("mymachine su[123]: 'su root' failed for lonvick on /dev/pts/8", event.getMessage());
    }

    @Test
    public void testWithSingleDigitDayAndNoExtraSpace() throws Exception {
        // Message from: https://tools.ietf.org/html/rfc3164#section-5.4
        // Changed "Feb  5" to "Feb 5" (notice the missing extra space)
        //
        // This variation wasn't supported by the original syslog4j and has beed fixed by Lennart in
        // org.graylog2.syslog4j.server.impl.event.SyslogServerEvent#parseDate().
        // There has been no commit for that but the following code has been added to the parseDate() method.
        //
        //     // http://jira.graylog2.org/browse/SERVER-287
        //     if (this.message.charAt(5) == ' ') {
        //         datelength = 15;
        //         dateFormatS = DATE_FORMAT_S;
        //     }
        //
        // The commit where the modified syslog4j jar has been added.
        // https://github.com/Graylog2/graylog2-server/commit/85f94ae7c8572843c291cceebcbf08dc57b08f09
        final String message = "<13>Feb 5 17:32:18 10.0.0.99 Use the BFG!";

        final SyslogServerEvent event = buildEvent(message);

        assertEquals(new DateTime(new DateTime().getYear() + "-02-05T17:32:18").toDate(), event.getDate());
        assertEquals(1, event.getFacility());
        assertEquals("10.0.0.99", event.getHost());
        assertEquals(5, event.getLevel());
        assertEquals("10.0.0.99 Use the BFG!", event.getMessage());
    }

    @Test
    public void testFacility12() throws Exception {
        // Message from: https://tools.ietf.org/html/rfc3164#section-5.4
        // Added pid to program.
        // changed facility to 12
        final String message = "<98>Oct 11 22:14:15 mymachine su[123]: 'su root' failed for lonvick on /dev/pts/8";

        final SyslogServerEvent event = buildEvent(message);

        assertEquals(new DateTime(new DateTime().getYear() + "-10-11T22:14:15").toDate(), event.getDate());
        assertEquals(12, event.getFacility());
        assertEquals("mymachine", event.getHost());
        assertEquals(2, event.getLevel());
        assertEquals("mymachine su[123]: 'su root' failed for lonvick on /dev/pts/8", event.getMessage());
    }

    @Test
    public void testFacility13() throws Exception {
        // Message from: https://tools.ietf.org/html/rfc3164#section-5.4
        // Added pid to program.
        // changed facility to 13
        final String message = "<106>Oct 11 22:14:15 mymachine su[123]: 'su root' failed for lonvick on /dev/pts/8";

        final SyslogServerEvent event = buildEvent(message);

        assertEquals(new DateTime(new DateTime().getYear() + "-10-11T22:14:15").toDate(), event.getDate());
        assertEquals(13, event.getFacility());
        assertEquals("mymachine", event.getHost());
        assertEquals(2, event.getLevel());
        assertEquals("mymachine su[123]: 'su root' failed for lonvick on /dev/pts/8", event.getMessage());
    }

    @Test
    public void testFacility14() throws Exception {
        // Message from: https://tools.ietf.org/html/rfc3164#section-5.4
        // Added pid to program.
        // changed facility to 14
        final String message = "<114>Oct 11 22:14:15 mymachine su[123]: 'su root' failed for lonvick on /dev/pts/8";

        final SyslogServerEvent event = buildEvent(message);

        assertEquals(new DateTime(new DateTime().getYear() + "-10-11T22:14:15").toDate(), event.getDate());
        assertEquals(14, event.getFacility());
        assertEquals("mymachine", event.getHost());
        assertEquals(2, event.getLevel());
        assertEquals("mymachine su[123]: 'su root' failed for lonvick on /dev/pts/8", event.getMessage());
    }

    @Test
    public void testFacility15() throws Exception {
        // Message from: https://tools.ietf.org/html/rfc3164#section-5.4
        // Added pid to program.
        // changed facility to 15
        final String message = "<122>Oct 11 22:14:15 mymachine su[123]: 'su root' failed for lonvick on /dev/pts/8";

        final SyslogServerEvent event = buildEvent(message);

        assertEquals(new DateTime(new DateTime().getYear() + "-10-11T22:14:15").toDate(), event.getDate());
        assertEquals(15, event.getFacility());
        assertEquals("mymachine", event.getHost());
        assertEquals(2, event.getLevel());
        assertEquals("mymachine su[123]: 'su root' failed for lonvick on /dev/pts/8", event.getMessage());
    }

}