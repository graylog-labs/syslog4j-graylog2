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

import junit.framework.TestCase;
import org.graylog2.syslog4j.impl.message.structured.StructuredSyslogMessage;
import org.graylog2.syslog4j.server.impl.event.SyslogServerEvent;
import org.graylog2.syslog4j.server.impl.event.structured.StructuredSyslogServerEvent;
import org.junit.Assert;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
          assertEquals("Invalid structured data in syslog message 'msgId1 [invalid SD] my message!!'", iae.getMessage());
      }
   }

   public void testFromString3()
   {
      final String messageStr = "msgId1 [data1 a=b] my message!!";

      try {
          StructuredSyslogMessage.fromString(messageStr);
          fail();

       } catch (IllegalArgumentException iae) {
          assertEquals("Invalid structured data in syslog message 'msgId1 [data1 a=b] my message!!'", iae.getMessage());
       }
   }

   public void testFromString4()
   {
      final String messageStr = "msgId1 [data1 a=\"b] my message!!";

      try {
          StructuredSyslogMessage.fromString(messageStr);
          fail();

       } catch (IllegalArgumentException iae) {
          assertEquals("Invalid structured data in syslog message 'msgId1 [data1 a=\"b] my message!!'", iae.getMessage());
       }
   }

   public void testFromString5()
   {
      final String messageStr = "msgId1 [data1 a=b\"] my message!!";

      try {
          StructuredSyslogMessage.fromString(messageStr);
          fail();

      } catch (IllegalArgumentException iae) {
          assertEquals("Invalid structured data in syslog message 'msgId1 [data1 a=b\"] my message!!'", iae.getMessage());
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
            new StructuredSyslogMessage("msgId", null, new HashMap<String, Map<String, String>>(), "my message");
      assertEquals("msgId [0@0] my message", message.createMessage());
   }

   public void testCreateMessage4()
   {
      final Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
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

    public void testMessageWithoutIdOrStructuredData()
    {
        final String messageStr = "- - TEST";
        final StructuredSyslogMessage message = StructuredSyslogMessage.fromString(messageStr);

        assertEquals("TEST", message.getMessage());
        assertNull(message.getMessageId());
        assertNull(message.getStructuredData());
    }


    public void testMessagesIssue13() throws Exception
    {
        final List<String> rfc3164Events = new ArrayList<String>();
        rfc3164Events.add("<4>Jun 20 11:59:57 xiaoleidouglas kernel: [ 357.266774] [UFW BLOCK] IN=enp9s0 OUT=enp9s0 MAC=01:2e:12:49:87:2b:01:36:1b:38:ad:80:08:50 SRC=289.15.121.109 DST=110.67.112.10 LEN=52 TOS=0x00 PREC=0x00 TTL=47 ID=43803 DF PROTO=TCP SPT=39693 DPT=23");
        rfc3164Events.add("<30>Jun 21 00:35:33 xiaoleidouglas dhclient[7445]: bound to 289.15.121.109 -- renewal in 1189 seconds.");
        rfc3164Events.add("<29>Jun 21 00:35:33 xiaoleidouglas dbus[775]: [system] Successfully activated service 'org.freedesktop.nm_dispatcher'");
        rfc3164Events.add("<86>Jun 21 00:35:39 xiaoleidouglas compiz: gkr-pam: unlocked login keyring");

        final List<String> rfc5424Events = new ArrayList<String>();
        rfc5424Events.add("<4>1 2016-06-21T15:27:15.771223+08:00 xiaoleidouglas kernel - - - [ 3132.531409] [UFW BLOCK] IN=enp9s0 OUT= MAC=01:2e:12:49:87:2b:01:36:1b:38:ad:80:08:50 SRC=219.15.121.109 DST=219.15.121.109 LEN=32 TOS=0x00 PREC=0x00 TTL=1 ID=0 PROTO=2");
        rfc5424Events.add("<30>1 2016-06-21T15:27:27.627057+08:00 xiaoleidouglas dhclient 1641 - -  DHCPREQUEST of 219.15.121.109 on wlp3s0 to 219.15.121.109 port 67 (xid=0x327a7f27)");
        rfc5424Events.add("<6>1 2016-06-21T15:27:27.672963+08:00 xiaoleidouglas NetworkManager 810 - -  <info>  [1466494047.6728]   address 219.15.121.109");

        for (String message : rfc3164Events) {
            final SyslogServerEvent event = new SyslogServerEvent(message, InetAddress.getLocalHost());
            assertEquals("xiaoleidouglas", event.getHost());
        }

        for (String message : rfc5424Events) {
            final StructuredSyslogServerEvent event = new StructuredSyslogServerEvent(message, InetAddress.getLocalHost());
            assertEquals("xiaoleidouglas", event.getHost());
            final StructuredSyslogMessage msg = event.getStructuredMessage();
            assertNull(msg.getStructuredData());
        }
    }
}
