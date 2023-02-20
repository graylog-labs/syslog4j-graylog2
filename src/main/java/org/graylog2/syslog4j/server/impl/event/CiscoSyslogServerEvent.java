package org.graylog2.syslog4j.server.impl.event;

import org.joda.time.DateTimeZone;

import java.net.InetAddress;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * CiscoSyslogServerEvent provides an implementation of the
 * SyslogServerEventIF interface that supports receiving of
 * Cisco syslog messages.
 *
 * @see <a href="http://www.ciscopress.com/articles/article.asp?p=426638">An Overview of the syslog Protocol</a>
 */
public class CiscoSyslogServerEvent extends SyslogServerEvent {
    private static final DateTimeFormatter DEFAULT_FORMATTER =
            DateTimeFormatter
                    .ofPattern("yyyy MMM ppd HH:mm:ss[.SSS][ zzz]", Locale.ROOT);
    private int sequenceNumber = 0;

    public CiscoSyslogServerEvent(final byte[] message, int length, InetAddress inetAddress) {
        super();

        initialize(message, length, inetAddress, null);
        parse();
    }
    public CiscoSyslogServerEvent(final byte[] message, int length, InetAddress inetAddress, DateTimeZone sysLogServerTimeZone) {
        super();

        initialize(message, length, inetAddress, sysLogServerTimeZone);
        parse();
    }

    public CiscoSyslogServerEvent(final String message, InetAddress inetAddress) {
        super();

        initialize(message, inetAddress, null);
        parse();
    }

    public CiscoSyslogServerEvent(final String message, InetAddress inetAddress, DateTimeZone sysLogServerTimeZone) {
        super();

        initialize(message, inetAddress, sysLogServerTimeZone);
        parse();
    }

    @Override
    protected void parsePriority() {
        if (this.message.charAt(0) == '<') {
            int i = this.message.indexOf(">");

            if (i <= 4 && i > -1) {
                String priorityStr = this.message.substring(1, i);

                int priority = 0;
                try {
                    priority = Integer.parseInt(priorityStr);
                    this.facility = priority >> 3;
                    this.level = priority - (this.facility << 3);

                    this.message = this.message.substring(i + 1);

                    parseSequenceNumber();
                    parseDate();
                } catch (NumberFormatException nfe) {
                    //
                }

                parseHost();
            }
        }
    }

    private void parseSequenceNumber() {
        int i = this.message.indexOf(':');
        if (i > -1) {
            String sequenceNumberStr = this.message.substring(0, i);
            if (sequenceNumberStr.isEmpty()) {
                this.message = this.message.substring(i + 1);
            } else {
                try {
                    this.sequenceNumber = Integer.parseInt(sequenceNumberStr);
                    this.message = this.message.substring(i + 1);
                } catch (NumberFormatException nfe) {
                    //
                }
            }
        }
    }

    @Override
    protected void parseHost() {
        if (message.indexOf('%') < 1) {
            this.host = "";
        } else {
            int i = this.message.indexOf(' ');
            if (i > -1) {
                this.host = this.message.substring(0, i).trim();

                // Skip ' ' and ':' characters
                char c = message.charAt(i);
                while (c == ' ' || c == ':') {
                    c = message.charAt(++i);
                }

                this.message = this.message.substring(i);
            }
        }
    }

    @Override
    protected void parseDate() {
        // Skip leading spaces
        while (message.charAt(0) == ' ') {
            message = message.substring(1);
        }

        // Skip leading asterisk
        if (message.charAt(0) == '*') {
            message = message.substring(1);
        }

        int dateLength = message.indexOf(": ");
        if (this.message.length() > dateLength) {
            boolean isYearMissing = Character.isLetter(message.charAt(0));
            String originalDate = this.message.substring(0, dateLength);
            DateTimeFormatter formatter = DEFAULT_FORMATTER.withZone(getDefaultServerZoneId());

            // Hacky override for: "Mar 06 2016 12:53:10 DEVICENAME :"
            if (Character.isDigit(message.charAt(7))
                    && Character.isDigit(message.charAt(8))
                    && Character.isDigit(message.charAt(9))
                    && Character.isDigit(message.charAt(10))) {
                dateLength = 20;
                isYearMissing = false;
                originalDate = this.message.substring(0, dateLength);
                formatter = DateTimeFormatter
                        .ofPattern("MMM ppd yyyy HH:mm:ss", Locale.ROOT)
                        .withZone(getDefaultServerZoneId());
            }

            try {
                if (isYearMissing) {
                    String year = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
                    String modifiedDate = year + " " + originalDate;
                    final ZonedDateTime dateTime = ZonedDateTime.parse(modifiedDate, formatter);
                    this.date = Date.from(dateTime.toInstant());
                } else {
                    final ZonedDateTime dateTime = ZonedDateTime.parse(originalDate, formatter);
                    this.date = Date.from(dateTime.toInstant());
                }

                this.message = this.message.substring(dateLength + 1);

            } catch (DateTimeParseException pe) {
                this.date = new Date();
            }
        }
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

}
