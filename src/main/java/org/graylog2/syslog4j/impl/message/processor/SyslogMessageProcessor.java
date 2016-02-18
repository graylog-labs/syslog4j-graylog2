package org.graylog2.syslog4j.impl.message.processor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * SyslogMessageProcessor wraps AbstractSyslogMessageProcessor.
 * <p/>
 * <p>Those wishing to replace (or improve upon) this implementation
 * can write a custom SyslogMessageProcessorIF and set it per
 * instance via the SyslogIF.setMessageProcessor(..) method or set it globally
 * via the SyslogMessageProcessor.setDefault(..) method.</p>
 * <p/>
 * <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
 * of the LGPL license is available in the META-INF folder in all
 * distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
 *
 * @author &lt;syslog4j@productivity.org&gt;
 * @version $Id: SyslogMessageProcessor.java,v 1.7 2010/02/04 03:41:37 cvs Exp $
 */
public class SyslogMessageProcessor extends AbstractSyslogMessageProcessor {
    private static final long serialVersionUID = -4232803978024990353L;

    private static final SyslogMessageProcessor INSTANCE = new SyslogMessageProcessor();

    protected static SyslogMessageProcessor defaultInstance = INSTANCE;

    public static void setDefault(SyslogMessageProcessor messageProcessor) {
        if (messageProcessor != null) {
            defaultInstance = messageProcessor;
        }
    }

    public static SyslogMessageProcessor getDefault() {
        return defaultInstance;
    }

    /* (non-Javadoc)
     * @see org.graylog2.syslog4j.impl.message.processor.AbstractSyslogMessageProcessor#appendTimestamp(java.lang.StringBuffer, java.util.Date)
     * 
     * This is compatible with BSD protocol
     */
    @Override
    public void appendTimestamp(StringBuffer buffer, Date datetime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(SYSLOG_DATEFORMAT, Locale.ENGLISH);

        String datePrefix = dateFormat.format(datetime);

        int pos = buffer.length() + 4;

        buffer.append(datePrefix);

        //  RFC 3164 requires leading space for days 1-9
        if (buffer.charAt(pos) == '0') {
            buffer.setCharAt(pos, ' ');
        }

    }

    /* (non-Javadoc)
     * @see org.graylog2.syslog4j.SyslogMessageProcessorIF#createSyslogHeader(int, int, java.lang.String, boolean, boolean)
     * 
     * This is compatible with BSD protocol
     */
    public String createSyslogHeader(int facility, int level, String localName, boolean sendLocalTimestamp, boolean sendLocalName) {
        StringBuffer buffer = new StringBuffer();

        appendPriority(buffer, facility, level);

        if (sendLocalTimestamp) {
            appendTimestamp(buffer, new Date());
        }

        if (sendLocalName) {
            appendLocalName(buffer, localName);
        }

        return buffer.toString();
    }

    /* (non-Javadoc)
     * @see org.graylog2.syslog4j.SyslogMessageProcessorIF#createSyslogHeader(int, int, java.lang.String, boolean, java.util.Date)
     * 
     * This is compatible with BSD protocol
     */
    public String createSyslogHeader(int facility, int level, String localName, boolean sendLocalName, Date datetime) {
        StringBuffer buffer = new StringBuffer();

        appendPriority(buffer, facility, level);

        appendTimestamp(buffer, datetime);

        if (sendLocalName) {
            appendLocalName(buffer, localName);
        }

        return buffer.toString();
    }

}
