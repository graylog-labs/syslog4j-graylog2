package org.graylog2.syslog4j.impl.message.processor.structured;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.graylog2.syslog4j.SyslogConstants;
import org.graylog2.syslog4j.impl.message.processor.AbstractSyslogMessageProcessor;
import org.graylog2.syslog4j.impl.message.structured.StructuredSyslogMessage;

/**
 * SyslogStructuredMessageProcessor extends SyslogMessageProcessor's ability to
 * split a syslog message into multiple messages when the message is greater
 * than the syslog maximum message length (1024 bytes including the header). It
 * adds support for structured syslog messages as specified by
 * draft-ietf-syslog-protocol-23. More information here:
 * <p/>
 * <p>http://tools.ietf.org/html/draft-ietf-syslog-protocol-23</p>
 * <p/>
 * <p>Those wishing to replace (or improve upon) this implementation
 * can write a custom SyslogMessageProcessorIF and set it per
 * instance via the SyslogIF.setStructuredMessageProcessor(..) method or set it globally
 * via the StructuredSyslogMessageProcessor.setDefault(..) method.</p>
 * <p/>
 * <p>
 * Syslog4j is licensed under the Lesser GNU Public License v2.1. A copy of the
 * LGPL license is available in the META-INF folder in all distributions of
 * Syslog4j and in the base directory of the "doc" ZIP.
 * </p>
 *
 * @author Manish Motwani
 * @version $Id: StructuredSyslogMessageProcessor.java,v 1.4 2011/01/11 05:11:13 cvs Exp $
 */
public class StructuredSyslogMessageProcessor extends AbstractSyslogMessageProcessor {
    private static final long   serialVersionUID = -1563777226913475257L;    
    
    public static String VERSION = "1";
    
    private static final  StructuredSyslogMessageProcessor INSTANCE = new StructuredSyslogMessageProcessor();

    protected static StructuredSyslogMessageProcessor defaultInstance = INSTANCE;

    private String            applicationName   = STRUCTURED_DATA_APP_NAME_DEFAULT_VALUE;
    private String            processId         = STRUCTURED_DATA_PROCESS_ID_DEFAULT_VALUE;
    private DateTimeFormatter dateTimeFormatter = ISODateTimeFormat.dateTime();

    public static void setDefault(StructuredSyslogMessageProcessor messageProcessor) {
        if (messageProcessor != null) {
            defaultInstance = messageProcessor;
        }
    }

    public static StructuredSyslogMessageProcessor getDefault() {
        return defaultInstance;
    }

    public StructuredSyslogMessageProcessor() {
        super();
    }

    public StructuredSyslogMessageProcessor(final String applicationName) {
        super();
        this.applicationName = applicationName;
    }

    public DateTimeFormatter getDateTimeFormatter() {
        return dateTimeFormatter;
    }

    public void setDateTimeFormatter(DateTimeFormatter dateTimeFormatter) {
        this.dateTimeFormatter = dateTimeFormatter;
    }

    public String getApplicationName() {
        return this.applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getProcessId() {
        return this.processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

	/* (non-Javadoc)
	 * @see org.graylog2.syslog4j.impl.message.processor.AbstractSyslogMessageProcessor#appendTimestamp(java.lang.StringBuffer, java.util.Date)
	 * 
	 * This is compatible with RFC5424 protocol.
	 */
	@Override
    public void appendTimestamp(StringBuffer buffer, Date datetime) {
        SimpleDateFormat formatter = new SimpleDateFormat(SyslogConstants.SYSLOG_DATEFORMAT_RFC5424);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(datetime.getTime());
        String formatedTimestamp = formatter.format(calendar.getTime());
        buffer.append(formatedTimestamp);	
        buffer.append(' ');
    }

    /* (non-Javadoc)
     * @see org.graylog2.syslog4j.SyslogMessageProcessorIF#createSyslogHeader(int, int, java.lang.String, boolean, boolean)
     * 
     * This is compatible with RFC5424 protocol.
     * 
     * RFC5424 does not allow flags of sendLocalTimestamp and sendLocalName be off and therefore the incoming flags will not be used in this method.
     * 
     */
    public String createSyslogHeader(int facility, int level, String localName, boolean sendLocalTimestamp, boolean sendLocalName) {
        return createSyslogHeaderInner(facility, level, localName, new Date());
    }

    /* (non-Javadoc)
     * @see org.graylog2.syslog4j.SyslogMessageProcessorIF#createSyslogHeader(int, int, java.lang.String, boolean, java.util.Date)
     * 
     * This is compatible with RFC5424 protocol.
     * 
     * RFC5424 does not allow sendLocalName flag to be off and therefore sendLocalName will not be used in this method.
     */
    public String createSyslogHeader(int facility, int level, String localName, boolean sendLocalName, Date datetime) { 	
    	return createSyslogHeaderInner(facility, level, localName, datetime);
    }
    
    private String createSyslogHeaderInner(int facility, int level, String localName, Date datetime) {
        StringBuffer buffer = new StringBuffer();
        appendPriority(buffer, facility, level);
        buffer.append(VERSION);
        buffer.append(' ');
        appendTimestamp(buffer, datetime);
        appendLocalName(buffer, localName);
        buffer.append(StructuredSyslogMessage.nilProtect(this.applicationName)).append(' ');
        buffer.append(StructuredSyslogMessage.nilProtect(this.processId)).append(' ');
        return buffer.toString();
    }
}
