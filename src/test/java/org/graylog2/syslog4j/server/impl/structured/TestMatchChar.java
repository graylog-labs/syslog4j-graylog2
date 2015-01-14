/**
 *
 * (C) Copyright 2008-2011 syslog4j.org
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 2.1 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-2.1.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 */
package org.graylog2.syslog4j.server.impl.structured;

import org.graylog2.syslog4j.impl.message.structured.StructuredSyslogMessage;
import org.junit.Assert;
import org.junit.Test;

public class TestMatchChar
{
    @Test
    public void testMatchChar() throws Exception
    {
        Assert.assertEquals(2, StructuredSyslogMessage.matchChar("hello", 0, 'l'));
        Assert.assertEquals(2, StructuredSyslogMessage.matchChar("hello", 2, 'l'));
        Assert.assertEquals(3, StructuredSyslogMessage.matchChar("hello", 3, 'l'));
        Assert.assertEquals(-1, StructuredSyslogMessage.matchChar("hello", 4, 'l'));
        Assert.assertEquals(-1, StructuredSyslogMessage.matchChar("hello", 10, 'l'));

        Assert.assertEquals(2, StructuredSyslogMessage.matchChar("\\ll", 0, 'l'));
        Assert.assertEquals(-1, StructuredSyslogMessage.matchChar("\\l\\l", 0, 'l'));
        Assert.assertEquals(-1, StructuredSyslogMessage.matchChar("\\", 0, 'x'));
        Assert.assertEquals(-1, StructuredSyslogMessage.matchChar("foo\\", 0, 'x'));

        Assert.assertEquals(-1, StructuredSyslogMessage.matchChar("this\\\"is\\ a\\ test.", 0, ' '));
    }

}
