package org.graylog2.syslog4j.server.impl.event;

import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class FortiGateSyslogEventTest {
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

}