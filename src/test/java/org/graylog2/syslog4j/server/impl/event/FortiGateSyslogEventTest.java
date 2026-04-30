package org.graylog2.syslog4j.server.impl.event;

import org.joda.time.DateTimeZone;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class FortiGateSyslogEventTest {

    public static final DateTimeZone MST_TIMEZONE = DateTimeZone.forID("MST");
    public static final ZoneId MST = MST_TIMEZONE.toTimeZone().toZoneId();

    @Test
    public void testFortiGateMessage() {
        final String rawMessage = "<45>date=2017-03-06 time=12:53:10 devname=DEVICENAME devid=DEVICEID logid=0000000013 type=traffic subtype=forward level=notice vd=ALIAS srcip=IP srcport=45748 srcintf=\"IF\" dstip=IP dstport=443 dstintf=\"IF\" sessionid=1122686199 status=close policyid=77 dstcountry=\"COUNTRY\" srccountry=\"COUNTRY\" trandisp=dnat tranip=IP tranport=443 service=HTTPS proto=6 appid=41540 app=\"SSL_TLSv1.2\" appcat=\"Network.Service\" applist=\"ACLNAME\" appact=detected duration=1 sentbyte=2313 rcvdbyte=14883 sentpkt=19 rcvdpkt=19 utmaction=passthrough utmevent=app-ctrl attack=\"SSL\" hostname=\"HOSTNAME\" custom=\"white space\"";
        final FortiGateSyslogEvent event = new FortiGateSyslogEvent(rawMessage);

        assertThat(event).isNotNull();
        assertThat(event.getFacility()).isEqualTo(5);
        assertThat(event.getLevel()).isEqualTo(5);
        assertThat(event.getHost()).isEqualTo("DEVICENAME");
        assertThat(ZonedDateTime.ofInstant(event.getDate().toInstant(), ZoneOffset.UTC))
                .isEqualTo(ZonedDateTime.of(2017, 3, 6, 12, 53, 10, 0, ZoneOffset.UTC));
        assertThat(event.getRaw()).isEqualTo(rawMessage.getBytes(StandardCharsets.UTF_8));
        assertThat(event.getMessage()).isEqualTo("date=2017-03-06 time=12:53:10 devname=DEVICENAME devid=DEVICEID logid=0000000013 type=traffic subtype=forward level=notice vd=ALIAS srcip=IP srcport=45748 srcintf=\"IF\" dstip=IP dstport=443 dstintf=\"IF\" sessionid=1122686199 status=close policyid=77 dstcountry=\"COUNTRY\" srccountry=\"COUNTRY\" trandisp=dnat tranip=IP tranport=443 service=HTTPS proto=6 appid=41540 app=\"SSL_TLSv1.2\" appcat=\"Network.Service\" applist=\"ACLNAME\" appact=detected duration=1 sentbyte=2313 rcvdbyte=14883 sentpkt=19 rcvdpkt=19 utmaction=passthrough utmevent=app-ctrl attack=\"SSL\" hostname=\"HOSTNAME\" custom=\"white space\"");
        assertThat(event.getFields())
                .containsEntry("date", "2017-03-06")
                .containsEntry("time", "12:53:10")
                .containsEntry("devname", "DEVICENAME")
                .containsEntry("devid", "DEVICEID")
                .containsEntry("hostname", "HOSTNAME")
                .containsEntry("custom", "white space");
    }

    @Test
    public void testDefaultTimezoneDetected() {
        final String rawMessage = "<45>date=2017-03-06 time=12:53:10 tz=-0700 devname=DEVICENAME devid=DEVICEID logid=0000000013 type=traffic subtype=forward level=notice vd=ALIAS srcip=IP srcport=45748 srcintf=\"IF\" dstip=IP dstport=443 dstintf=\"IF\" sessionid=1122686199 status=close policyid=77 dstcountry=\"COUNTRY\" srccountry=\"COUNTRY\" trandisp=dnat tranip=IP tranport=443 service=HTTPS proto=6 appid=41540 app=\"SSL_TLSv1.2\" appcat=\"Network.Service\" applist=\"ACLNAME\" appact=detected duration=1 sentbyte=2313 rcvdbyte=14883 sentpkt=19 rcvdpkt=19 utmaction=passthrough utmevent=app-ctrl attack=\"SSL\" hostname=\"HOSTNAME\" custom=\"white space\"";
        final FortiGateSyslogEvent event = new FortiGateSyslogEvent(rawMessage);

        ZonedDateTime of = ZonedDateTime.of(2017, 3, 6, 12, 53, 10, 0, MST);

        assertThat(ZonedDateTime.ofInstant(event.getDate().toInstant(), MST))
                .isEqualTo(of);
    }


    @Test
    public void testDefaultTimezoneConfigIgnored() {
        final String rawMessage = "<45>date=2017-03-06 time=12:53:10 tz=+0000 devname=DEVICENAME devid=DEVICEID logid=0000000013 type=traffic subtype=forward level=notice vd=ALIAS srcip=IP srcport=45748 srcintf=\"IF\" dstip=IP dstport=443 dstintf=\"IF\" sessionid=1122686199 status=close policyid=77 dstcountry=\"COUNTRY\" srccountry=\"COUNTRY\" trandisp=dnat tranip=IP tranport=443 service=HTTPS proto=6 appid=41540 app=\"SSL_TLSv1.2\" appcat=\"Network.Service\" applist=\"ACLNAME\" appact=detected duration=1 sentbyte=2313 rcvdbyte=14883 sentpkt=19 rcvdpkt=19 utmaction=passthrough utmevent=app-ctrl attack=\"SSL\" hostname=\"HOSTNAME\" custom=\"white space\"";
        final FortiGateSyslogEvent event = new FortiGateSyslogEvent(rawMessage, MST_TIMEZONE);

        ZonedDateTime of = ZonedDateTime.of(2017, 3, 6, 12, 53, 10, 0, ZoneOffset.UTC);

        assertThat(ZonedDateTime.ofInstant(event.getDate().toInstant(), ZoneOffset.UTC))
                .isEqualTo(of);
    }

    @Test
    public void testDefaultTimezoneConfigured() {
        final String rawMessage = "<45>date=2017-03-06 time=12:53:10 devname=DEVICENAME devid=DEVICEID logid=0000000013 type=traffic subtype=forward level=notice vd=ALIAS srcip=IP srcport=45748 srcintf=\"IF\" dstip=IP dstport=443 dstintf=\"IF\" sessionid=1122686199 status=close policyid=77 dstcountry=\"COUNTRY\" srccountry=\"COUNTRY\" trandisp=dnat tranip=IP tranport=443 service=HTTPS proto=6 appid=41540 app=\"SSL_TLSv1.2\" appcat=\"Network.Service\" applist=\"ACLNAME\" appact=detected duration=1 sentbyte=2313 rcvdbyte=14883 sentpkt=19 rcvdpkt=19 utmaction=passthrough utmevent=app-ctrl attack=\"SSL\" hostname=\"HOSTNAME\" custom=\"white space\"";
        final FortiGateSyslogEvent event = new FortiGateSyslogEvent(rawMessage, MST_TIMEZONE);

        ZoneId mst = MST_TIMEZONE.toTimeZone().toZoneId();
        ZonedDateTime of = ZonedDateTime.of(2017, 3, 6, 12, 53, 10, 0, mst);

        assertThat(ZonedDateTime.ofInstant(event.getDate().toInstant(), mst))
                .isEqualTo(of);
    }

    @Test
    public void quotedValueWithEqualsDoesNotProducePhantomFields() {
        final String rawMessage = "<99>date=2025-06-12 time=13:17:12 devname=\"FW1\" tz=\"+00:00\" url=\"/p/AF1QipMv0l3HYdRc5_YuViCfIIbOZz6ipH4Jb6QV6ngM=w92-h92-n-k-no\"";
        final FortiGateSyslogEvent event = new FortiGateSyslogEvent(rawMessage);

        assertThat(event.getFields())
                .containsEntry("url", "/p/AF1QipMv0l3HYdRc5_YuViCfIIbOZz6ipH4Jb6QV6ngM=w92-h92-n-k-no")
                .doesNotContainKey("AF1QipMv0l3HYdRc5_YuViCfIIbOZz6ipH4Jb6QV6ngM")
                .doesNotContainValue("w92-h92-n-k-no");
    }

    @Test
    public void quotedValueDoesNotOverwriteSiblingFields() {
        final String rawMessage = "<99>date=2025-06-12 time=13:17:12 devname=\"FW1\" tz=\"+00:00\" srcip=10.31.110.152 dstip=192.178.50.65 url=\"/whatever.php?srcip=127.0.0.1&dstip=127.0.0.1\"";
        final FortiGateSyslogEvent event = new FortiGateSyslogEvent(rawMessage);

        assertThat(event.getFields())
                .containsEntry("srcip", "10.31.110.152")
                .containsEntry("dstip", "192.178.50.65")
                .containsEntry("url", "/whatever.php?srcip=127.0.0.1&dstip=127.0.0.1");
    }

    @Test
    public void backslashEscapedQuoteInsideQuotedValueIsConsumedAtomically() {
        final String rawMessage = "<99>date=2025-06-12 time=13:17:12 srcip=10.0.0.1 url=\"/test?\\\"srcip=127.0.0.1\"";
        final FortiGateSyslogEvent event = new FortiGateSyslogEvent(rawMessage);

        assertThat(event.getFields())
                .containsEntry("srcip", "10.0.0.1")
                .containsEntry("url", "/test?\\\"srcip=127.0.0.1");
    }

    @Test
    public void quotedValueDoesNotCorruptParseTimeFields() {
        final String rawMessage = "<99>date=2025-06-12 time=13:17:12 devname=\"FW1\" tz=\"+00:00\" url=\"/whatever.php?time=1\"";
        final FortiGateSyslogEvent event = new FortiGateSyslogEvent(rawMessage);

        assertThat(event.getFields())
                .containsEntry("time", "13:17:12")
                .containsEntry("url", "/whatever.php?time=1");
        assertThat(ZonedDateTime.ofInstant(event.getDate().toInstant(), ZoneOffset.UTC))
                .isEqualTo(ZonedDateTime.of(2025, 6, 12, 13, 17, 12, 0, ZoneOffset.UTC));
    }
}
