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
package org.graylog2.syslog4j.test.message.structured;

//
// Cleversafe open-source code header - Version 1.2 - February 15, 2008
//
// Cleversafe Dispersed Storage(TM) is software for secure, private and
// reliable storage of the world's data using information dispersal.
//
// Copyright (C) 2005-2008 Cleversafe, Inc.
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// as published by the Free Software Foundation; either version 2
// of the License, or (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301,
// USA.
//
// Contact Information: Cleversafe, 224 North Desplaines Street, Suite 500
// Chicago IL 60661
// email licensing@cleversafe.org
//
// END-OF-HEADER
// -----------------------
// @author: mmotwani
//
// Date: Jul 15, 2009
// ---------------------

import com.google.common.collect.Maps;
import junit.framework.TestCase;
import org.graylog2.syslog4j.impl.message.structured.StructuredSyslogMessage;
import org.graylog2.syslog4j.server.impl.event.structured.StructuredSyslogServerEvent;
import org.junit.Assert;

import java.net.InetAddress;
import java.util.Map;

public class StructuredSyslogMessageTest extends TestCase
{
   public void testFromString1()
   {
      final String messageStr = "msgId1 [0@0] my message!!";

      final StructuredSyslogMessage message = StructuredSyslogMessage.fromString(messageStr);

      assertEquals("msgId1 [0@0] my message!!",message.toString());
      assertEquals(-108931075,message.hashCode());

      assertEquals("my message!!", message.getMessage());
      assertEquals("msgId1", message.getMessageId());
      assertTrue(message.getStructuredData().size() == 0);
   }
   
   
   public void testFromString1a()
   {
      final String messageStr = "msgId1 [type a=\"[xx\\] xx\"] [first] my message!!";

      final StructuredSyslogMessage message = StructuredSyslogMessage.fromString(messageStr);

      assertEquals("msgId1 [type a=\"[xx\\] xx\"] [first] my message!!",message.toString());


      assertEquals("[first] my message!!", message.getMessage());
      assertEquals("msgId1", message.getMessageId());
      assertEquals("[xx] xx", (message.getStructuredData().get("type")).get("a"));
   }
   
   public void testFromString1b()
   {
      final String messageStr = "msgId1 [type a=\"[xx\\] xx\"] my [second] message!!";

      final StructuredSyslogMessage message = StructuredSyslogMessage.fromString(messageStr);

      assertEquals("msgId1 [type a=\"[xx\\] xx\"] my [second] message!!",message.toString());


      assertEquals("my [second] message!!", message.getMessage());
      assertEquals("msgId1", message.getMessageId());
      assertEquals("[xx] xx", (message.getStructuredData().get("type")).get("a"));
   }
   
   public void testFromString1c()
   {
      final String messageStr = "msgId1 [type a=\"[xx\\] xx\"][value b=\"c\"] my message!! [last]";

      final StructuredSyslogMessage message = StructuredSyslogMessage.fromString(messageStr);

      assertEquals("my message!! [last]", message.getMessage());
      assertEquals("msgId1", message.getMessageId());
      assertEquals("[xx] xx", (message.getStructuredData().get("type")).get("a"));
      assertEquals("c", (message.getStructuredData().get("value")).get("b"));
   }
   
   public void testFromString2()
   {
      final String messageStr = "msgId1 [invalid SD] my message!!";

      try {
          StructuredSyslogMessage.fromString(messageStr);
          fail();

      } catch (IllegalArgumentException iae) {
          //
      }
   }

   public void testFromString3()
   {
      final String messageStr = "msgId1 [data1 a=b] my message!!";

      try {
          StructuredSyslogMessage.fromString(messageStr);
          fail();

       } catch (IllegalArgumentException iae) {
           //
       }
   }

   public void testFromString4()
   {
      final String messageStr = "msgId1 [data1 a=\"b] my message!!";

      try {
          StructuredSyslogMessage.fromString(messageStr);
          fail();

       } catch (IllegalArgumentException iae) {
           //
       }
   }

   public void testFromString5()
   {
      final String messageStr = "msgId1 [data1 a=b\"] my message!!";

      try {
          StructuredSyslogMessage.fromString(messageStr);
          fail();

      } catch (IllegalArgumentException iae) {
          //
      }

   }

   public void testFromString6()
   {
      final String messageStr = "msgId1 [data1 a=\"b\"] my message!!";

      final StructuredSyslogMessage message = StructuredSyslogMessage.fromString(messageStr);

      assertEquals("my message!!", message.getMessage());
      assertEquals("msgId1", message.getMessageId());
      assertTrue(message.getStructuredData().size() == 1);
      assertTrue((message.getStructuredData().get("data1")).size() == 1);
      assertEquals("b", (message.getStructuredData().get("data1")).get("a"));
   }

   public void testFromString7()
   {
      final String messageStr =
            "msgId1 [data1 a=\"b\"][data2 a=\"b\" x1=\"c1\" n2=\"f5\"] my message!!";

      final StructuredSyslogMessage message = StructuredSyslogMessage.fromString(messageStr);

      assertEquals("my message!!", message.getMessage());
      assertEquals("msgId1", message.getMessageId());
      assertTrue(message.getStructuredData().size() == 2);
      assertTrue((message.getStructuredData().get("data1")).size() == 1);
      assertTrue((message.getStructuredData().get("data2")).size() == 3);
      assertEquals("b", (message.getStructuredData().get("data1")).get("a"));
      assertEquals("b", (message.getStructuredData().get("data2")).get("a"));
      assertEquals("c1", (message.getStructuredData().get("data2")).get("x1"));
      assertEquals("f5", (message.getStructuredData().get("data2")).get("n2"));
   }

   public void testCreateMessage1()
   {
      final StructuredSyslogMessage message = new StructuredSyslogMessage("msgId", null, null, null);
      assertEquals("msgId [0@0]", message.createMessage());
   }

   public void testCreateMessage2()
   {
      final StructuredSyslogMessage message =
            new StructuredSyslogMessage("msgId", null, null, "my message");
      assertEquals("msgId [0@0] my message", message.createMessage());
   }

   public void testCreateMessage3()
   {
      final StructuredSyslogMessage message =
            new StructuredSyslogMessage("msgId", null, Maps.<String, Map<String, String>>newHashMap(), "my message");
      assertEquals("msgId [0@0] my message", message.createMessage());
   }

   public void testCreateMessage4()
   {
      final Map<String, Map<String, String>> map = Maps.newHashMap();
      final StructuredSyslogMessage message =
            new StructuredSyslogMessage("msgId", null, map, "my message");
      assertEquals("msgId [0@0] my message", message.createMessage());
   }

   public void testMessageWithNulls() throws Exception
   {
       final String message = "<134>1 2012-07-25T21:32:08.887+00:00 some-server.some.domain noprog qtp583592918-80437 95d42b22c48e4eadb59e61a182c102d4 [l@2 si=\"some-server-s4\" sc=\"/a/b-c/d\" ip=\"1.2.3.4\" m=\"GET\" u=\"http://1.2.3.4:8081/path/PATH:12345/path\" q=\"source=SERVICE\" rc=\"200\" t=\"12\"][co@2 auth-cookie=\"jskldjskldjasskljlaskjas\"][rs@2 some-header=\"4054630f-8d31-457c-b1ff-2f2b465d69ef\"] nomsg";
       final StructuredSyslogServerEvent ev = new StructuredSyslogServerEvent(message, InetAddress.getLocalHost());
       final StructuredSyslogMessage msg = ev.getStructuredMessage();
       Assert.assertEquals("95d42b22c48e4eadb59e61a182c102d4", msg.getMessageId());
       Assert.assertNotNull(msg.getStructuredData());
       Assert.assertNotNull(msg.getStructuredData().get("l@2"));
       Assert.assertEquals("/a/b-c/d", msg.getStructuredData().get("l@2").get("sc"));
   }

   public void testMessageWithEmptyStruct() throws Exception
   {
       final String message = "<134>1 2012-07-25T21:32:08.887+00:00 some-server.some.domain noprog qtp583592918-80437 95d42b22c48e4eadb59e61a182c102d4 [l@2][a@3 a=\"b\\\"c\"]";
       final StructuredSyslogServerEvent ev = new StructuredSyslogServerEvent(message, InetAddress.getLocalHost());
       final StructuredSyslogMessage msg = ev.getStructuredMessage();
       Assert.assertEquals("95d42b22c48e4eadb59e61a182c102d4", msg.getMessageId());
       Assert.assertNotNull(msg.getStructuredData());
       Assert.assertNotNull(msg.getStructuredData().get("l@2"));
       Assert.assertNotNull(msg.getStructuredData().get("a@3"));
       Assert.assertEquals("b\"c", msg.getStructuredData().get("a@3").get("a"));
   }

    public void testMessageWithSpace() throws Exception
    {
    final String message = "<134>1 2012-07-25T21:32:08.887+00:00 some-server.some.domain noprog qtp583592918-80437 95d42b22c48e4eadb59e61a182c102d4 [rh@12345 xxx=\"hell0 7|133454|00022f444ad7fe10ef5d0d536ae879f1\"]'";
       final StructuredSyslogServerEvent ev = new StructuredSyslogServerEvent(message, InetAddress.getLocalHost());
       final StructuredSyslogMessage msg = ev.getStructuredMessage();
       Assert.assertNotNull(msg.getStructuredData().get("rh@12345"));
       Assert.assertEquals("hell0 7|133454|00022f444ad7fe10ef5d0d536ae879f1", msg.getStructuredData().get("rh@12345").get("xxx"));
    }

    public void testMessageWithTwoDashes()
    {
        final String messageStr = "msgId1 [data1 a=\"b\"] - - TEST";
        final StructuredSyslogMessage message = StructuredSyslogMessage.fromString(messageStr);

        assertEquals("- - TEST", message.getMessage());
        assertEquals("msgId1", message.getMessageId());
        assertTrue(message.getStructuredData().size() == 1);
        assertTrue((message.getStructuredData().get("data1")).size() == 1);
        assertEquals("b", message.getStructuredData().get("data1").get("a"));
    }
}
