package org.graylog2.syslog4j.server.impl.event;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.junit.Test;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class SyslogServerEventTest {
    private final InetAddress INET_ADDR = new InetSocketAddress(514).getAddress();
    private final DateTimeZone mstZone = DateTimeZone.forID("MST");
    private final DateTimeZone cet = DateTimeZone.forID("CET");

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

    @Test
    public void testIssue16() throws Exception {
        // Message from: https://github.com/Graylog2/syslog4j-graylog2/issues/16
        final String message = "<6>2016-10-12T14:10:18Z hostname testmsg[20]: Test";

        final SyslogServerEvent event = buildEvent(message);

        assertEquals(new DateTime(2016, 10, 12, 14, 10, 18, DateTimeZone.UTC).toDate(), event.getDate());
        assertEquals(0, event.getFacility());
        assertEquals("hostname", event.getHost());
        assertEquals(6, event.getLevel());
        assertEquals("hostname testmsg[20]: Test", event.getMessage());
    }

    @Test
    public void testRFC5424Timestamps() throws Exception {
        // https://tools.ietf.org/html/rfc5424#section-6.2.3.1
        final String example1 = "<0>1985-04-12T23:20:50.52Z hostname test[42]: Test";
        final SyslogServerEvent event1 = buildEvent(example1);

        assertEquals(new DateTime(1985, 4, 12, 23, 20, 50, 520, DateTimeZone.UTC).toDate(), event1.getDate());
        assertEquals(0, event1.getFacility());
        assertEquals("hostname", event1.getHost());
        assertEquals(0, event1.getLevel());
        assertEquals("hostname test[42]: Test", event1.getMessage());

        final String example2 = "<0>1985-04-12T19:20:50.52-04:00 hostname test[42]: Test";
        final SyslogServerEvent event2 = buildEvent(example2);
        assertEquals(new DateTime(1985, 4, 12, 19, 20, 50, 520, DateTimeZone.forOffsetHours(-4)).toDate(), event2.getDate());
        assertEquals(0, event2.getFacility());
        assertEquals("hostname", event2.getHost());
        assertEquals(0, event2.getLevel());
        assertEquals("hostname test[42]: Test", event2.getMessage());

        final String example3 = "<0>2003-10-11T22:14:15.003Z hostname test[42]: Test";
        final SyslogServerEvent event3 = buildEvent(example3);
        assertEquals(new DateTime(2003, 10, 11, 22, 14, 15, 3, DateTimeZone.UTC).toDate(), event3.getDate());
        assertEquals(0, event3.getFacility());
        assertEquals("hostname", event3.getHost());
        assertEquals(0, event3.getLevel());
        assertEquals("hostname test[42]: Test", event3.getMessage());

        final String example4 = "<0>2003-08-24T05:14:15.000003-07:00 hostname test[42]: Test";
        final SyslogServerEvent event4 = buildEvent(example4);
        assertEquals(new DateTime(2003, 8, 24, 5, 14, 15, 0, DateTimeZone.forOffsetHours(-7)).toDate(), event4.getDate());
        assertEquals(0, event4.getFacility());
        assertEquals("hostname", event4.getHost());
        assertEquals(0, event4.getLevel());
        assertEquals("hostname test[42]: Test", event4.getMessage());

        final String example5 = "<0>2003-08-24T05:14:15.000000003-07:00 hostname test[42]: Test";
        final SyslogServerEvent event5 = buildEvent(example5);
        // This *should* fail but the date/time parser seems to be too lenient. ;-)
        assertEquals(new DateTime(2003, 8, 24, 5, 14, 15, 0, DateTimeZone.forOffsetHours(-7)).toDate(), event5.getDate());
        assertEquals(0, event5.getFacility());
        assertEquals("hostname", event5.getHost());
        assertEquals(0, event5.getLevel());
        assertEquals("hostname test[42]: Test", event5.getMessage());
    }

    @Test
    public void testDefaultTimeZoneForRFC5424() {
        final String withUtcOffset = "<0>1985-04-12T23:20:50.52Z hostname test[42]: Test";
        DateTime utcDateTime = new DateTime(1985, 4, 12, 23, 20, 50, 520, DateTimeZone.UTC);

        assertEquals(utcDateTime.toDate(), new SyslogServerEvent(withUtcOffset, INET_ADDR).getDate());
        assertEquals(utcDateTime.toDate(), new SyslogServerEvent(withUtcOffset.getBytes(), withUtcOffset.length(), INET_ADDR).getDate());

        final String withMstOffset = "<0>1985-04-12T23:20:50.52-07:00 hostname test[42]: Test";
        DateTime cetDateTime = new DateTime(1985, 4, 12, 23, 20, 50, 520, cet);
        DateTime mstDateTime = new DateTime(1985, 4, 12, 23, 20, 50, 520, mstZone);
        SyslogServerEvent syslogServerEventMstWithDefaultCet = new SyslogServerEvent(withMstOffset, INET_ADDR, cet);

        assertEquals(mstDateTime.toDate(), new SyslogServerEvent(withMstOffset, INET_ADDR).getDate());
        assertEquals(mstDateTime.toDate(), syslogServerEventMstWithDefaultCet.getDate());
        assertNotEquals(cetDateTime.toDate(), syslogServerEventMstWithDefaultCet.getDate());

        final String withoutOffset = "<0>1985-04-12T23:20:50.52 hostname test[42]: Test";
        SyslogServerEvent syslogServerEventCet = new SyslogServerEvent(withoutOffset, INET_ADDR, cet);

        assertEquals(cetDateTime.toDate(), syslogServerEventCet.getDate());
        assertNotEquals(mstDateTime.toDate(), syslogServerEventCet.getDate());
        assertEquals(mstDateTime.toDate(), new SyslogServerEvent(withoutOffset, INET_ADDR, mstZone).getDate());
    }

    @Test
    public void testDefaultTimeZones() {
        final String message = "<34>Oct 11 17:14:15 mymachine su: 'su root' failed for lonvick on /dev/pts/8";
        final SyslogServerEvent testWithDefaultConf = new SyslogServerEvent(message, INET_ADDR, mstZone);
        final SyslogServerEvent test = new SyslogServerEvent(message, INET_ADDR);

        assertEquals(new DateTime(new DateTime().getYear() + "-10-11T17:14:15", mstZone).toDate(), testWithDefaultConf.getDate());
        assertEquals(new DateTime(new DateTime().getYear() + "-10-11T17:14:15").toDate(), test.getDate());

    }
}