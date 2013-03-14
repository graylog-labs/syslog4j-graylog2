package org.productivity.java.syslog4j;

import java.io.Serializable;

/**
* SyslogConstants provides several global constant values for several
* classes within Syslog4j.
* 
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
* 
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: SyslogConstants.java,v 1.33 2011/01/11 05:11:13 cvs Exp $
*/
public interface SyslogConstants extends Serializable {
	public static final String SYSLOG_PATH_DEFAULT = "/dev/log";
	public static final String SYSLOG_HOST_DEFAULT = "localhost";
	public static final int SYSLOG_PORT_DEFAULT = 514;
	public static final int SYSLOG_BUFFER_SIZE = 1024;
	public static final int SERVER_SOCKET_BACKLOG_DEFAULT = 50;
	
	public static final String SYSLOG_DATEFORMAT = "MMM dd HH:mm:ss ";
	
	public static final String STRUCTURED_DATA_NILVALUE = "-";
	public static final String STRUCTURED_DATA_EMPTY_VALUE = "[0@0]";

	public static final String CHAR_SET_DEFAULT = "UTF-8";

	public static final byte[] LF = "\n".getBytes();
	public static final byte[] CRLF = "\r\n".getBytes();
	
	public static final byte[] TCP_DELIMITER_SEQUENCE_DEFAULT = LF;
	
	public static final boolean THREADED_DEFAULT = true;
	public static final long THREAD_LOOP_INTERVAL_DEFAULT = 500;

	public static final boolean SEND_LOCAL_NAME_DEFAULT = true;
	public static final boolean SEND_LOCAL_TIMESTAMP_DEFAULT = true;
	public static final boolean CACHE_HOST_ADDRESS_DEFAULT = true;
	
	public static final int MAX_MESSAGE_LENGTH_DEFAULT = 1024;
	
	public static final boolean INCLUDE_IDENT_IN_MESSAGE_MODIFIER_DEFAULT = false;
	public static final boolean THROW_EXCEPTION_ON_INITIALIZE_DEFAULT = true;
	public static final boolean THROW_EXCEPTION_ON_WRITE_DEFAULT = false;
	public static final boolean USE_STRUCTURED_DATA_DEFAULT = false;
	public static final String STRUCTURED_DATA_APP_NAME_DEFAULT_VALUE = "unknown";
	public static final String STRUCTURED_DATA_PROCESS_ID_DEFAULT_VALUE = STRUCTURED_DATA_NILVALUE;
	
	public static final boolean USE_DAEMON_THREAD_DEFAULT = true;
	public static final int THREAD_PRIORITY_DEFAULT = -1;
	
	public static final boolean TRUNCATE_MESSAGE_DEFAULT = false;
	
	public static final String SPLIT_MESSAGE_BEGIN_TEXT_DEFAULT = "...";
	public static final String SPLIT_MESSAGE_END_TEXT_DEFAULT = "...";

	public static final String SEND_LOCAL_NAME_DEFAULT_VALUE = "unknown";

	public static final String TCP  = "tcp";
	public static final String UDP  = "udp";
	public static final String UNIX_SYSLOG = "unix_syslog";
	public static final String UNIX_SOCKET = "unix_socket";
	
	public static final boolean TCP_PERSISTENT_CONNECTION_DEFAULT = true;
	public static final boolean TCP_SO_LINGER_DEFAULT = true;
	public static final int TCP_SO_LINGER_SECONDS_DEFAULT = 1;
	public static final boolean TCP_KEEP_ALIVE_DEFAULT = true;
	public static final boolean TCP_REUSE_ADDRESS_DEFAULT = true;
	public static final boolean TCP_SET_BUFFER_SIZE_DEFAULT = true;
	public static final int TCP_FRESH_CONNECTION_INTERVAL_DEFAULT = -1;
	
	public static final int TCP_MAX_ACTIVE_SOCKETS_DEFAULT = 0;
	public static final byte TCP_MAX_ACTIVE_SOCKETS_BEHAVIOR_DEFAULT = 0;

	public static final int FACILITY_KERN     =  0;
	public static final int FACILITY_USER     =  1<<3;
	public static final int FACILITY_MAIL     =  2<<3;
	public static final int FACILITY_DAEMON   =  3<<3;
	public static final int FACILITY_AUTH     =  4<<3;
	public static final int FACILITY_SYSLOG   =  5<<3;

	public static final int FACILITY_LPR      =  6<<3;
	public static final int FACILITY_NEWS     =  7<<3;
	public static final int FACILITY_UUCP     =  8<<3;
	public static final int FACILITY_CRON     =  9<<3;
	public static final int FACILITY_AUTHPRIV = 10<<3;
	public static final int FACILITY_FTP      = 11<<3;

	public static final int FACILITY_LOCAL0   = 16<<3;
	public static final int FACILITY_LOCAL1   = 17<<3;
	public static final int FACILITY_LOCAL2   = 18<<3;
	public static final int FACILITY_LOCAL3   = 19<<3;
	public static final int FACILITY_LOCAL4   = 20<<3;
	public static final int FACILITY_LOCAL5   = 21<<3;
	public static final int FACILITY_LOCAL6   = 22<<3;
	public static final int FACILITY_LOCAL7   = 23<<3;

	public static final int SYSLOG_FACILITY_DEFAULT = FACILITY_USER;

	public static final int LEVEL_DEBUG = 7;
	public static final int LEVEL_INFO = 6;
	public static final int LEVEL_NOTICE = 5;
	public static final int LEVEL_WARN = 4;
	public static final int LEVEL_ERROR = 3;
	public static final int LEVEL_CRITICAL = 2;
	public static final int LEVEL_ALERT = 1;
	public static final int LEVEL_EMERGENCY = 0;
	
	public static final int OPTION_NONE = 0;
	public static final int OPTION_LOG_CONS = 1;
	public static final int OPTION_LOG_NDELAY = 2;
	public static final int OPTION_LOG_NOWAIT = 4;
	public static final int OPTION_LOG_ODELAY = 8;
	public static final int OPTION_LOG_PERROR = 16;
	public static final int OPTION_LOG_PID = 32;

	public static final int SOCK_STREAM = 1;
	public static final int SOCK_DGRAM = 2;

	public static final short AF_UNIX = 1;
	
	public static final int WRITE_RETRIES_DEFAULT = 2;
	public static final int MAX_SHUTDOWN_WAIT_DEFAULT = 30000;
	public static final long SHUTDOWN_INTERVAL = 100;	
	public static final int MAX_QUEUE_SIZE_DEFAULT = -1;
	
	public static final long SERVER_SHUTDOWN_WAIT_DEFAULT = 500;
	
	public static final String SYSLOG_LIBRARY_DEFAULT = "c";
	
	public static final int SYSLOG_SOCKET_TYPE_DEFAULT = SOCK_DGRAM;
	public static final short SYSLOG_SOCKET_FAMILY_DEFAULT = AF_UNIX;
	public static final String SYSLOG_SOCKET_LIBRARY_DEFAULT = "c";
	public static final int SYSLOG_SOCKET_PROTOCOL_DEFAULT = 0;
	public static final String SYSLOG_SOCKET_PATH_DEFAULT = "/dev/log";
	
	public static final String JNA_NATIVE_CLASS = "com.sun.jna.Native";
	
	public static final String IDENT_SUFFIX_DEFAULT = ": ";
	
	public static final String SYSLOG_MESSAGE_MODIFIER_PREFIX_DEFAULT = " {";
	public static final String SYSLOG_MESSAGE_MODIFIER_SUFFIX_DEFAULT = "}";

	public static final String SYSLOG_SEQUENTIAL_MESSAGE_MODIFIER_PREFIX_DEFAULT = " #";
	public static final String SYSLOG_SEQUENTIAL_MESSAGE_MODIFIER_SUFFIX_DEFAULT = "";
	public static final long SYSLOG_SEQUENTIAL_MESSAGE_MODIFIER_FIRST_NUMBER_DEFAULT = 0;
	public static final long SYSLOG_SEQUENTIAL_MESSAGE_MODIFIER_LAST_NUMBER_DEFAULT = 9999;
	public static final char SYSLOG_SEQUENTIAL_MESSAGE_MODIFIER_PAD_CHAR_DEFAULT = '0';
	public static final boolean SYSLOG_SEQUENTIAL_MESSAGE_MODIFIER_USE_PADDING_DEFAULT = true;
	
	public static final int SYSLOG_POOL_CONFIG_MAX_ACTIVE_DEFAULT = 4;
	public static final int SYSLOG_POOL_CONFIG_MAX_IDLE_DEFAULT = 4;
	public static final int SYSLOG_POOL_CONFIG_MAX_WAIT_DEFAULT = 1000;
	public static final int SYSLOG_POOL_CONFIG_NUM_TESTS_PER_EVICTION_RUN_DEFAULT = 0;
	public static final int SYSLOG_POOL_CONFIG_MIN_IDLE_DEFAULT = 4;
	public static final int SYSLOG_POOL_CONFIG_MIN_EVICTABLE_IDLE_TIME_MILLIS_DEFAULT = 0;
	public static final int SYSLOG_POOL_CONFIG_SOFT_MIN_EVICTABLE_IDLE_TIME_MILLIS_DEFAULT = 0;
	public static final int SYSLOG_POOL_CONFIG_TIME_BETWEEN_EVICTION_RUNS_MILLIS_DEFAULT = 0;
	public static final boolean SYSLOG_POOL_CONFIG_TEST_ON_BORROW_DEFAULT = false;
	public static final boolean SYSLOG_POOL_CONFIG_TEST_ON_RETURN_DEFAULT = false;
	public static final boolean SYSLOG_POOL_CONFIG_TEST_WHILE_IDLE_DEFAULT = false;
}
