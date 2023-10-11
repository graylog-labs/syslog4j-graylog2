package org.graylog2.syslog4j.server.impl.event;

import org.graylog2.syslog4j.server.SyslogServerEventIF;
import org.joda.time.DateTimeZone;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Objects.requireNonNull;

/**
 * FortiGateSyslogEvent provides an implementation of the
 * SyslogServerEventIF interface that supports receiving of
 * FortiGate/FortiOS syslog messages.
 *
 * @see <a href="http://help.fortinet.com/fos50hlp/54/Content/FortiOS/fortigate-logging-reporting-54/logs.htm#Log_messages">FortiGate logging and reporting overview</a>
 */
public class FortiGateSyslogEvent implements SyslogServerEventIF {
    private static final Pattern PRI_PATTERN = Pattern.compile("^<(\\d{1,3})>(.*)$");
    private static final Pattern KV_PATTERN = Pattern.compile("(\\w+)=(?:\"([^\"]*)\"|([^\\s\"]*))");

    private String rawEvent;
    private ZoneId defaultZoneId;
    private Date date;
    private int facility;
    private int level;
    private String host;
    private String message;
    private Charset charSet = StandardCharsets.UTF_8;
    private Map<String, String> fields = Collections.emptyMap();

    public FortiGateSyslogEvent(final String rawEvent) {
        initialize(rawEvent, null);
    }

    public FortiGateSyslogEvent(final String rawEvent, DateTimeZone sysLogServerTimeZone) {
        initialize(rawEvent, sysLogServerTimeZone);
    }

    private void initialize(final String rawEvent, DateTimeZone sysLogServerTimeZone) {
        this.rawEvent = requireNonNull(rawEvent, "rawEvent");
        this.defaultZoneId = Objects.isNull(sysLogServerTimeZone) ? ZoneOffset.UTC : sysLogServerTimeZone.toTimeZone().toZoneId();
        parse(rawEvent);
    }

    private void parse(String event) {
        final Matcher matcher = PRI_PATTERN.matcher(event);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Invalid Fortigate syslog message");
        } else {
            final String priority = matcher.group(1);
            final String message = matcher.group(2);

            parsePriority(priority);
            setMessage(message);
            parseFields(message);
            parseDate(fields.get("date"), fields.get("time"), fields.get("tz"));
            setHost(fields.get("devname"));
        }
    }

    private void parsePriority(String priorityString) {
        try {
            final int priority = Integer.parseInt(priorityString);
            setFacility(priority / 8);
            setLevel(priority % 8);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Couldn't parse message priority", e);
        }
    }

    private void parseFields(String event) {
        final Map<String, String> fields = new HashMap<>();
        final Matcher matcher = KV_PATTERN.matcher(event);
        while (matcher.find()) {
            fields.put(matcher.group(1), matcher.group(2) != null ? matcher.group(2) : matcher.group(3));
        }
        setFields(fields);
    }

    private void parseDate(String date, String time, String timeZone) {
        if (date != null && time != null) {
            ZoneId zone = defaultZoneId;

            if (timeZone != null) {
              zone =   ZoneOffset.of(timeZone);
            }

            final ZonedDateTime dateTime = ZonedDateTime.of(
                    LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE.withZone(zone)),
                    LocalTime.parse(time, DateTimeFormatter.ISO_LOCAL_TIME.withZone(zone)),
                    zone);
            setDate(Date.from(dateTime.toInstant()));

        } else {
            setDate(new Date());
        }
    }

    @Override
    public byte[] getRaw() {
        return rawEvent.getBytes(charSet);
    }

    @Override
    public int getFacility() {
        return facility;
    }

    @Override
    public void setFacility(int facility) {
        this.facility = requireNonNull(facility, "facility");
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public void setDate(Date date) {
        this.date = requireNonNull(date, "date");
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public void setLevel(int level) {
        this.level = requireNonNull(level, "level");
    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public boolean isHostStrippedFromMessage() {
        return false;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = requireNonNull(message, "message");
    }

    @Override
    public String getCharSet() {
        return charSet.name();
    }

    @Override
    public void setCharSet(String charSet) {
        this.charSet = Charset.forName(charSet);
    }

    public Map<String, String> getFields() {
        return Collections.unmodifiableMap(fields);
    }

    public void setFields(Map<String, String> fields) {
        this.fields = requireNonNull(fields, "fields");
    }
}
