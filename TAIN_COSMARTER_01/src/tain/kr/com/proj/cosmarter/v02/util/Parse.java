/**
 * Copyright 2014, 2015, 2016 TAIN, Inc. all rights reserved.
 *
 * Licensed under the GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.gnu.org/licenses/
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * -----------------------------------------------------------------
 * Copyright 2014, 2015, 2016 TAIN, Inc.
 *
 */
package tain.kr.com.proj.cosmarter.v02.util;

import java.util.ResourceBundle;

import org.apache.log4j.Logger;

/**
 * Code Templates > Comments > Types
 *
 * <PRE>
 *   -. FileName   : Parse.java
 *   -. Package    : tain.kr.com.proj.cosmarter.v01.util
 *   -. Comment    :
 *   -. Author     : taincokr
 *   -. First Date : 2016. 4. 25. {time}
 * </PRE>
 *
 * @author taincokr
 *
 */
public class Parse {

	private static boolean flag = true;

	private static final Logger log = Logger.getLogger(Parse.class);

	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final String KEY_DESC = "tain.parse.desc";
	
	private String desc = null;
	
	///////////////////////////////////////////////////////////////////////////////////////////////

	private Parse() throws Exception {
		
		if (flag) {
			String className = this.getClass().getName();
			
			ResourceBundle rb = ResourceBundle.getBundle(className.replace('.', '/'));
			
			this.desc = rb.getString(KEY_DESC);
			
			this.print();
		}
	}
	
	public String getDesc() throws Exception {
		return this.desc;
	}
	
	public void print() throws Exception {
		
		if (flag) {
			log.debug("desc = [" + this.desc + "]");
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private static Parse instance = null;
	
	public static synchronized Parse getInstance() throws Exception {
		
		if (instance == null) {
			instance = new Parse();
		}
		
		return instance;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////

	private static void test01(String[] args) throws Exception {
		
		if (flag) {
			log.debug(">>>>> 1 " + Parse.getInstance().getDesc());
			log.debug(">>>>> 2 " + Parse.getInstance().getDesc());
		}
	}
	
	/*
	 * netstat -naplt
	 */
	@SuppressWarnings("unused")
	private static void test02(String[] args) throws Exception {
		
		if (flag) {
			String name = "lstps";
			String cmd = "ps -ef";
			String lineItems = "";
			String[] items = { "proto", "recvq", "sendq", "local", "foreign", "state", "program" };  // count = 7
			int limit = items.length;
			String[] lines = {
					"tcp        0      0 0.0.0.0:22              0.0.0.0:*               LISTEN      -               ",
					"tcp        0      0 192.168.56.103:22       192.168.56.1:49209      ESTABLISHED -               ",
					"tcp        0      0 192.168.56.103:22       192.168.56.1:49200      ESTABLISHED -               ",
					"tcp6       0      0 :::7412                 :::*                    LISTEN      1256/java       ",
					"tcp6       0      0 :::22                   :::*                    LISTEN      -               ",
					"tcp6       0      0 192.168.56.103:7412     192.168.56.1:49210      TIME_WAIT   -               ",
					"tcp6       0      0 192.168.56.103:7412     192.168.56.1:49211      TIME_WAIT   -               ",
			};
			
			if (!flag) {
				for (String title : items) {
					log.debug("TITLE [" + title + "]");
				}
				
				for (String line : lines) {
					log.debug("LINE [" + line + "]");
				}
			}
			
			if (flag) {
				
				StringBuffer sbJson = new StringBuffer();
				
				sbJson.append("{").append("\"").append("lstps").append("\"").append(":").append("[").append(CheckSystem.getInstance().getLineSeparator());
				
				for (String line : lines) {
					
					// split
					String[] words = line.split("\\s+", limit);
					if (flag) words[limit-1] = words[limit-1].trim();
					
					// make JSON
					StringBuffer sb = new StringBuffer();
					if (flag) sb.append("\t");
					sb.append("{");
					
					for (int i=0; i < limit; i++) {
						sb.append("\"").append(items[i]).append("\"");
						sb.append(":");
						sb.append("\"").append(words[i]).append("\"");
						sb.append(",");
					}
					
					sb.append("},");
					
					if (flag) sb.append(CheckSystem.getInstance().getLineSeparator());
					
					if (!flag) log.debug(">>> " + sb.toString());
					if (flag) sbJson.append(sb.toString());
				}
				
				sbJson.append("]}").append(CheckSystem.getInstance().getLineSeparator());

				if (flag) log.debug("JSON = " + sbJson.toString());
			}
		}
	}
	
	/*
	 * ps -ef
	 */
	private static void test03(String[] args) throws Exception {
		
		if (flag) {
			String[] items = { "uid", "pid", "ppid", "cpu", "stime", "tty", "time", "cmd", };  // count = 8
			int limit = items.length;
			String[] lines = {
					"root         1     0  0 11:22 ?        00:00:00 /sbin/init",
					"root         2     0  0 11:22 ?        00:00:00 [kthreadd]",
					"root         3     2  0 11:22 ?        00:00:00 [ksoftirqd/0]",
					"root         5     2  0 11:22 ?        00:00:00 [kworker/0:0H]",
					"root         6     2  0 11:22 ?        00:00:00 [kworker/u2:0]",
					"root         7     2  0 11:22 ?        00:00:00 [migration/0]",
					"root         8     2  0 11:22 ?        00:00:00 [rcu_bh]",
					"root         9     2  0 11:22 ?        00:00:00 [rcu_sched]",
					"root        10     2  0 11:22 ?        00:00:00 [watchdog/0]",
					"root        11     2  0 11:22 ?        00:00:00 [khelper]",
					"root        12     2  0 11:22 ?        00:00:00 [kdevtmpfs]",
					"root        13     2  0 11:22 ?        00:00:00 [netns]",
					"root        14     2  0 11:22 ?        00:00:00 [writeback]",
					"root        15     2  0 11:22 ?        00:00:00 [kintegrityd]",
					"root        16     2  0 11:22 ?        00:00:00 [bioset]",
					"root        17     2  0 11:22 ?        00:00:00 [kworker/u3:0]",
					"root        18     2  0 11:22 ?        00:00:00 [kblockd]",
					"root        19     2  0 11:22 ?        00:00:00 [ata_sff]",
					"root        20     2  0 11:22 ?        00:00:00 [khubd]",
					"root        21     2  0 11:22 ?        00:00:00 [md]",
					"root        22     2  0 11:22 ?        00:00:00 [devfreq_wq]",
					"root        23     2  0 11:22 ?        00:00:04 [kworker/0:1]",
					"root        25     2  0 11:22 ?        00:00:00 [khungtaskd]",
					"root        26     2  0 11:22 ?        00:00:00 [kswapd0]",
					"root        27     2  0 11:22 ?        00:00:00 [ksmd]",
					"root        28     2  0 11:22 ?        00:00:00 [fsnotify_mark]",
					"root        29     2  0 11:22 ?        00:00:00 [ecryptfs-kthrea]",
					"root        30     2  0 11:22 ?        00:00:00 [crypto]",
					"root        42     2  0 11:22 ?        00:00:00 [kthrotld]",
					"root        44     2  0 11:22 ?        00:00:00 [scsi_eh_0]",
					"root        45     2  0 11:22 ?        00:00:00 [scsi_eh_1]",
					"root        46     2  0 11:22 ?        00:00:01 [kworker/u2:2]",
					"root        67     2  0 11:22 ?        00:00:00 [deferwq]",
					"root        68     2  0 11:22 ?        00:00:00 [charger_manager]",
					"root       114     2  0 11:22 ?        00:00:00 [kworker/u3:1]",
					"root       115     2  0 11:22 ?        00:00:00 [scsi_eh_2]",
					"root       124     2  0 11:22 ?        00:00:00 [jbd2/sda1-8]",
					"root       125     2  0 11:22 ?        00:00:00 [ext4-rsv-conver]",
					"root       126     2  0 11:22 ?        00:00:00 [ext4-unrsv-conv]",
					"root       252     1  0 11:22 ?        00:00:00 upstart-udev-bridge --daemon",
					"root       256     1  0 11:22 ?        00:00:00 /lib/systemd/systemd-udevd --daemon",
					"102        314     1  0 11:22 ?        00:00:00 dbus-daemon --system --fork",
					"root       338     2  0 11:22 ?        00:00:00 [kpsmoused]",
					"root       355     1  0 11:22 ?        00:00:00 /lib/systemd/systemd-logind",
					"syslog     364     1  0 11:22 ?        00:00:00 rsyslogd -c5",
					"root       672     1  0 11:22 ?        00:00:00 upstart-file-bridge --daemon",
					"root       682     1  0 11:22 ?        00:00:00 upstart-socket-bridge --daemon",
					"root       711     1  0 11:22 tty4     00:00:00 /sbin/getty -8 38400 tty4",
					"root       718     1  0 11:22 tty5     00:00:00 /sbin/getty -8 38400 tty5",
					"root       727     1  0 11:22 tty2     00:00:00 /sbin/getty -8 38400 tty2",
					"root       730     1  0 11:22 tty3     00:00:00 /sbin/getty -8 38400 tty3",
					"root       734     1  0 11:22 tty6     00:00:00 /sbin/getty -8 38400 tty6",
					"root       783     1  0 11:22 ?        00:00:00 /usr/sbin/sshd -D",
					"root       789     1  0 11:22 ?        00:00:00 acpid -c /etc/acpi/events -s /var/run/acpid.socket",
					"root       790     1  0 11:22 ?        00:00:00 cron",
					"daemon     791     1  0 11:22 ?        00:00:00 atd",
					"whoopsie   804     1  0 11:22 ?        00:00:00 whoopsie",
					"root       855     1  0 11:22 tty1     00:00:00 /sbin/getty -8 38400 tty1",
					"root       930     2  0 11:25 ?        00:00:00 [kauditd]",
					"root      1090   783  0 11:33 ?        00:00:00 sshd: kang [priv]",
					"kang      1136  1090  0 11:34 ?        00:00:02 sshd: kang@pts/1",
					"kang      1137  1136  0 11:34 pts/1    00:00:23 -bash",
					"kang      1255  1137  0 11:40 pts/1    00:00:00 /bin/bash ./start.sh",
					"kang      1256  1255  0 11:40 pts/1    00:00:14 /home/kang/TAIN/WEB_TEST/jdk1.7.0_79_32/bin/java -Dtain.name=CoSmarter -jar ./tain-cosmarter-1.0.jar One Two Three Four",
					"root      1319   783  0 13:04 ?        00:00:00 sshd: kang [priv]",
					"kang      1398  1319  0 13:04 ?        00:00:00 sshd: kang@pts/0",
					"kang      1399  1398  0 13:04 pts/0    00:00:00 -bash",
					"root      1555     2  0 13:34 ?        00:00:00 [kworker/0:2]",
					"kang      1610  1399  0 13:46 pts/0    00:00:00 ps -ef",
			};
			
			if (!flag) {
				for (String title : items) {
					log.debug("TITLE [" + title + "]");
				}
				
				for (String line : lines) {
					log.debug("LINE [" + line + "]");
				}
			}
			
			if (flag) {
				
				StringBuffer sb = null;
				
				for (String line : lines) {
					
					// split
					String[] words = line.split("\\s+", limit);
					if (flag) words[limit-1] = words[limit-1].trim();
					
					// make JSON
					sb = new StringBuffer();
					sb.append("{");
					
					for (int i=0; i < limit; i++) {
						sb.append("\"").append(items[i]).append("\"");
						sb.append(":");
						sb.append("\"").append(words[i]).append("\"");
						sb.append(",");
					}
					
					sb.append("},");
					
					if (flag) sb.append(CheckSystem.getInstance().getLineSeparator());
					
					if (flag) log.debug(">>> " + sb.toString());
				}
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		
		if (flag) log.debug(">>>>> " + new Object(){}.getClass().getEnclosingClass().getName());
		
		if (!flag) test01(args);
		if (flag) test02(args);
		if (!flag) test03(args);
	}
}
