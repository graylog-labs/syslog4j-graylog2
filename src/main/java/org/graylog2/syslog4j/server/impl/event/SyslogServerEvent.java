package org.graylog2.syslog4j.server.impl.event;

import org.graylog2.syslog4j.SyslogConstants;
import org.graylog2.syslog4j.server.SyslogServerEventIF;
import org.graylog2.syslog4j.util.SyslogUtility;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.ISODateTimeFormat;

import java.net.InetAddress;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * SyslogServerEvent provides an implementation of the SyslogServerEventIF interface.
 * <p/>
 * <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
 * of the LGPL license is available in the META-INF folder in all
 * distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
 *
 * @author &lt;syslog4j@productivity.org&gt;
 * @version $Id: SyslogServerEvent.java,v 1.9 2011/01/11 06:21:15 cvs Exp $
 */
public class SyslogServerEvent implements SyslogServerEventIF {
    private static final long serialVersionUID = 6136043067089899962L;

    public static final String DATE_FORMAT = "MMM dd HH:mm:ss yyyy";
    public static final String DATE_FORMAT_S = "MMM d HH:mm:ss yyyy";

    protected String charSet = SyslogConstants.CHAR_SET_DEFAULT;
    protected String rawString = null;
    protected byte[] rawBytes = null;
    protected int rawLength = -1;
    protected Date date = null;
    protected int level = -1;
    protected int facility = -1;
    protected String host = null;
    protected boolean isHostStrippedFromMessage = false;
    protected String message = null;
    protected InetAddress inetAddress = null;
    protected DateTimeZone sysLogServerTimeZone;

    protected SyslogServerEvent() {
    }

    public SyslogServerEvent(final String message, InetAddress inetAddress) {
        initialize(message, inetAddress, null);

        parse();
    }

    public SyslogServerEvent(final String message, InetAddress inetAddress, DateTimeZone sysLogServerTimeZone) {
        initialize(message, inetAddress, sysLogServerTimeZone);

        parse();
    }

    public SyslogServerEvent(final byte[] message, int length, InetAddress inetAddress) {
        initialize(message, length, inetAddress, null);

        parse();
    }

    public SyslogServerEvent(final byte[] message, int length, InetAddress inetAddress, DateTimeZone sysLogServerTimeZone) {
        initialize(message, length, inetAddress, sysLogServerTimeZone);

        parse();
    }

    protected void initialize(final String message, InetAddress inetAddress, DateTimeZone sysLogServerTimeZone) {
        this.rawString = message;
        this.rawLength = message.length();
        this.inetAddress = inetAddress;
        this.message = message;
        this.sysLogServerTimeZone = sysLogServerTimeZone;
    }


    protected void initialize(final byte[] message, int length, InetAddress inetAddress, DateTimeZone sysLogServerTimeZone) {
        this.rawBytes = message;
        this.rawLength = length;
        this.inetAddress = inetAddress;
        this.sysLogServerTimeZone = sysLogServerTimeZone;
    }

    protected void parseHost() {
        int i = this.message.indexOf(' ');

        if (i > -1) {
            this.host = this.message.substring(0, i).trim();
        }
    }

    protected void parseDate() {
        int datelength = 16;
        String dateFormatS = DATE_FORMAT;
        boolean isDate8601 = false;

        if (this.message.length() > datelength) {

            // http://jira.graylog2.org/browse/SERVER-287
            if (this.message.charAt(5) == ' ') {
                datelength = 15;
                dateFormatS = DATE_FORMAT_S;
            }

            if (Character.isDigit(this.message.charAt(0))) {
                datelength = this.message.indexOf(' ') + 1;
                isDate8601 = true;
            }

            String originalDate = this.message.substring(0, datelength - 1);

            try {
                if (!isDate8601) {
                    this.date = parseDateBasedOnFormat(originalDate, datelength, dateFormatS);
                } else {
                    this.date = parse8601Date(originalDate).toDate();
                }

                this.message = this.message.substring(datelength);

            } catch (ParseException pe) {
                this.date = new Date();
            }
        }

        parseHost();
    }

    private Date parseDateBasedOnFormat(String originalDate, int dateLength, String format) throws ParseException {
        String year = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
        String modifiedDate = originalDate + " " + year;
        DateFormat dateFormat = new SimpleDateFormat(format, Locale.ENGLISH);

        if (Objects.nonNull(sysLogServerTimeZone)) {
            dateFormat.setTimeZone(sysLogServerTimeZone.toTimeZone());
        }

        return dateFormat.parse(modifiedDate);
    }

    private DateTime parse8601Date(String date) {
        boolean hasTimezone = date.substring(date.length() - 6).matches(".*[Z+-].*");

        if (!hasTimezone && Objects.nonNull(sysLogServerTimeZone)) {
            return DateTime.parse(date, ISODateTimeFormat.dateTimeParser().withZone(sysLogServerTimeZone));
        }

        return DateTime.parse(date);
    }

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

                    parseDate();

                } catch (NumberFormatException nfe) {
                    //
                }

                parseHost();
            }
        }
    }

    protected void parse() {
        if (this.message == null) {
            this.message = SyslogUtility.newString(this, this.rawBytes, this.rawLength);
        }

        parsePriority();
    }

    public int getFacility() {
        return this.facility;
    }

    public void setFacility(int facility) {
        this.facility = facility;
    }

    public byte[] getRaw() {
        if (this.rawString != null) {
            byte[] rawStringBytes = SyslogUtility.getBytes(this, this.rawString);

            return rawStringBytes;

        } else if (this.rawBytes.length == this.rawLength) {
            return this.rawBytes;

        } else {
            byte[] newRawBytes = new byte[this.rawLength];
            System.arraycopy(this.rawBytes, 0, newRawBytes, 0, this.rawLength);

            return newRawBytes;
        }
    }

    protected ZoneId getDefaultServerZoneId() {
        return Objects.isNull(sysLogServerTimeZone) ? ZoneOffset.UTC : sysLogServerTimeZone.toTimeZone().toZoneId();
    }

    public int getRawLength() {
        return this.rawLength;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getHost() {
        return this.host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public boolean isHostStrippedFromMessage() {
        return isHostStrippedFromMessage;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCharSet() {
        return this.charSet;
    }

    public void setCharSet(String charSet) {
        this.charSet = charSet;
    }
}
