package org.graylog2.syslog4j.server.impl.event;

import org.joda.time.DateTimeZone;
import org.junit.Test;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import static java.time.ZoneOffset.UTC;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;


public class CiscoSyslogServerEventTest {
    public static final DateTimeZone MST = DateTimeZone.forID("MST");
    public static final ZoneId MST_ZONE_ID = MST.toTimeZone().toZoneId();
    private static final InetAddress INET_ADDR = new InetSocketAddress(514).getAddress();
    private static final ZoneId CET = ZoneId.of("CET");
    private static final int YEAR = ZonedDateTime.now().getYear();

    private CiscoSyslogServerEvent buildEvent(String message) {
        return new CiscoSyslogServerEvent(message, INET_ADDR);
    }

    @Test
    public void testCisco1() throws Exception {
        final String message = "<166>Mar 06 2016 12:53:10 DEVICENAME : %ASA-6-302013: Built inbound TCP connection 723494125 for FRONTEND:IP/11288 (IP/11288) to BACKEND:IP/27180 (IP/27180)";

        final CiscoSyslogServerEvent event = buildEvent(message);

        assertThat(toZonedDateTime(event.getDate(), UTC)).isEqualTo(ZonedDateTime.of(2016, 3, 6, 12, 53, 10, 0, UTC));
        assertThat(event.getFacility()).isEqualTo(20);
        assertThat(event.getLevel()).isEqualTo(6);
        assertThat(event.getSequenceNumber()).isEqualTo(0);
        assertThat(event.getHost()).isEqualTo("DEVICENAME");
        assertThat(event.getMessage()).isEqualTo("%ASA-6-302013: Built inbound TCP connection 723494125 for FRONTEND:IP/11288 (IP/11288) to BACKEND:IP/27180 (IP/27180)");
    }

    @Test
    public void testCisco2() throws Exception {
        final String message = "<186>1541800: Feb 27 06:08:59.485: %HARDWARE-2-FAN_ERROR: Fan Failure";

        final CiscoSyslogServerEvent event = buildEvent(message);

        assertThat(toZonedDateTime(event.getDate(), UTC)).isEqualTo(ZonedDateTime.of(YEAR, 2, 27, 6, 8, 59, 485_000_000, UTC));
        assertThat(event.getFacility()).isEqualTo(23);
        assertThat(event.getLevel()).isEqualTo(2);
        assertThat(event.getSequenceNumber()).isEqualTo(1541800);
        assertThat(event.getHost()).isEmpty();
        assertThat(event.getMessage()).isEqualTo("%HARDWARE-2-FAN_ERROR: Fan Failure");
    }

    @Test
    public void testCisco3() throws Exception {
        final String message = "<187>148094: Feb 27 06:07:29.716: %LINK-3-UPDOWN: Interface GigabitEthernet1/0/15, changed state to down";

        final CiscoSyslogServerEvent event = buildEvent(message);

        assertThat(toZonedDateTime(event.getDate(), UTC)).isEqualTo(ZonedDateTime.of(YEAR, 2, 27, 6, 7, 29, 716_000_000, UTC));
        assertThat(event.getFacility()).isEqualTo(23);
        assertThat(event.getLevel()).isEqualTo(3);
        assertThat(event.getSequenceNumber()).isEqualTo(148094);
        assertThat(event.getHost()).isEmpty();
        assertEquals("%LINK-3-UPDOWN: Interface GigabitEthernet1/0/15, changed state to down", event.getMessage());
    }

    @Test
    public void testCisco4() throws Exception {
        final String message = "<190>530470: *Sep 28 17:13:35.098: %SEC-6-IPACCESSLOGP: list MGMT_IN denied udp IP(49964) -> IP(161), 11 packets";

        final CiscoSyslogServerEvent event = buildEvent(message);

        assertThat(toZonedDateTime(event.getDate(), UTC)).isEqualTo(ZonedDateTime.of(YEAR, 9, 28, 17, 13, 35, 98_000_000, UTC));
        assertThat(event.getFacility()).isEqualTo(23);
        assertThat(event.getLevel()).isEqualTo(6);
        assertThat(event.getSequenceNumber()).isEqualTo(530470);
        assertThat(event.getHost()).isEmpty();
        assertEquals("%SEC-6-IPACCESSLOGP: list MGMT_IN denied udp IP(49964) -> IP(161), 11 packets", event.getMessage());
    }

    @Test
    public void testCisco5() throws Exception {
        final String message = "<190>: 2016 Mar 06 09:22:34 CET: %AUTHPRIV-6-SYSTEM_MSG: START: rsync pid=4311 from=::ffff:IP - xinetd[6219]";

        final CiscoSyslogServerEvent event = buildEvent(message);

        assertThat(toZonedDateTime(event.getDate(), CET)).isEqualTo(ZonedDateTime.of(2016, 3, 6, 9, 22, 34, 0, CET));
        assertThat(event.getFacility()).isEqualTo(23);
        assertThat(event.getLevel()).isEqualTo(6);
        assertThat(event.getSequenceNumber()).isEqualTo(0);
        assertThat(event.getHost()).isEmpty();
        assertThat(event.getMessage()).isEqualTo("%AUTHPRIV-6-SYSTEM_MSG: START: rsync pid=4311 from=::ffff:IP - xinetd[6219]");
    }

    @Test
    public void testCisco6() throws Exception {
        final String message = "<134>: 2016 Mar  6 12:53:10 UTC: %POLICY_ENGINE-6-POLICY_LOOKUP_EVENT: policy=POLICYNAME rule=RULENAME action=Permit direction=egress src.net.ip-address=IP src.net.port=38321 dst.net.ip-address=IP dst.net.port=5666 net.protocol=6 net.ethertype=800 net.service=\"protocol 6 port 5666\"";

        final CiscoSyslogServerEvent event = buildEvent(message);

        assertThat(toZonedDateTime(event.getDate(), UTC)).isEqualTo(ZonedDateTime.of(2016, 3, 6, 12, 53, 10, 0, UTC));
        assertThat(event.getFacility()).isEqualTo(16);
        assertThat(event.getLevel()).isEqualTo(6);
        assertThat(event.getSequenceNumber()).isEqualTo(0);
        assertThat(event.getHost()).isEmpty();
        assertThat(event.getMessage()).isEqualTo("%POLICY_ENGINE-6-POLICY_LOOKUP_EVENT: policy=POLICYNAME rule=RULENAME action=Permit direction=egress src.net.ip-address=IP src.net.port=38321 dst.net.ip-address=IP dst.net.port=5666 net.protocol=6 net.ethertype=800 net.service=\"protocol 6 port 5666\"");
    }

    @Test
    public void testCisco7() throws Exception {
        final String message = "<166>%ASA-6-302015: Built inbound UDP connection 23631055 for inside:192.168.19.91/44764 (192.168.19.91/44764) to identity:192.168.249.33/161 (192.168.249.33/161)";

        final CiscoSyslogServerEvent event = buildEvent(message);

        assertThat(event.getDate())
                .isInThePast()
                .isInSameMinuteWindowAs(new Date());
        assertThat(event.getFacility()).isEqualTo(20);
        assertThat(event.getLevel()).isEqualTo(6);
        assertThat(event.getSequenceNumber()).isEqualTo(0);
        assertThat(event.getHost()).isEmpty();
        assertThat(event.getMessage()).isEqualTo("%ASA-6-302015: Built inbound UDP connection 23631055 for inside:192.168.19.91/44764 (192.168.19.91/44764) to identity:192.168.249.33/161 (192.168.249.33/161)");
    }

    @Test
    public void testDefaultTimeZoneUtcIfNotConfigured() throws Exception {
        final String message = "<190>: 2016 Mar 06 09:22:34: %AUTHPRIV-6-SYSTEM_MSG: START: rsync pid=4311 from=::ffff:IP - xinetd[6219]";
        final CiscoSyslogServerEvent event = buildEvent(message);

        assertThat(toZonedDateTime(event.getDate(), UTC)).isEqualTo(ZonedDateTime.of(2016, 3, 6, 9, 22, 34, 0, UTC));
    }

    @Test
    public void testDefaultTimeZoneConfigured() throws Exception {
        final String message = "<190>: 2016 Mar 06 09:22:34: %AUTHPRIV-6-SYSTEM_MSG: START: rsync pid=4311 from=::ffff:IP - xinetd[6219]";
        final CiscoSyslogServerEvent event = new CiscoSyslogServerEvent(message, INET_ADDR, MST);

        assertThat(toZonedDateTime(event.getDate(), MST_ZONE_ID)).isEqualTo(ZonedDateTime.of(2016, 3, 6, 9, 22, 34, 0, MST_ZONE_ID));
    }

    @Test
    public void testDefaultTimeZoneIgnoredSinceZoneDetected() throws Exception {
        final String message = "<190>: 2016 Mar 06 09:22:34 CET: %AUTHPRIV-6-SYSTEM_MSG: START: rsync pid=4311 from=::ffff:IP - xinetd[6219]";
        DateTimeZone mst = DateTimeZone.forID("MST");
        final CiscoSyslogServerEvent event = new CiscoSyslogServerEvent(message, INET_ADDR, mst);

        assertThat(toZonedDateTime(event.getDate(), CET)).isEqualTo(ZonedDateTime.of(2016, 3, 6, 9, 22, 34, 0, CET));
    }

    private ZonedDateTime toZonedDateTime(Date date, ZoneId zoneId) {
        return ZonedDateTime.ofInstant(date.toInstant(), zoneId);
    }
}