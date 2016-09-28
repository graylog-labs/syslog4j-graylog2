package org.graylog2.syslog4j.impl.unix;

import org.graylog2.syslog4j.SyslogConstants;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class UnixSyslogTest {
    @Test
    public void writeUsesCorrectSyslogPriority() throws Exception {
        final UnixSyslog.CLibrary mockLibrary = mock(UnixSyslog.CLibrary.class);
        UnixSyslog.libraryInstance = mockLibrary;
        UnixSyslog.currentFacility = SyslogConstants.FACILITY_LOCAL4;

        final UnixSyslogConfig config = new UnixSyslogConfig();
        config.setFacility(SyslogConstants.FACILITY_LOCAL4);

        UnixSyslog.write(SyslogConstants.LEVEL_NOTICE, "Test", config);

        verify(mockLibrary, times(1)).syslog(165, "%s", "Test");
    }

}