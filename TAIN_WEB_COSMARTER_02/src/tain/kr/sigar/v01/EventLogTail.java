/**
 * Copyright 2014, 2015, 2016, 2017 TAIN, Inc. all rights reserved.
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
 * Copyright 2014, 2015, 2016, 2017 TAIN, Inc.
 *
 */
package tain.kr.sigar.v01;

import org.apache.log4j.Logger;
import org.hyperic.sigar.win32.EventLog;
import org.hyperic.sigar.win32.EventLogNotification;
import org.hyperic.sigar.win32.EventLogRecord;
import org.hyperic.sigar.win32.EventLogThread;
import org.hyperic.sigar.win32.Win32Exception;

/**
 * Code Templates > Comments > Types
 *
 * <PRE>
 *   -. FileName   : EventLogTail.java
 *   -. Package    : tain.kr.com.test.sigar.v01
 *   -. Comment    :
 *   -. Author     : taincokr
 *   -. First Date : 2017. 3. 17. {time}
 * </PRE>
 *
 * @author taincokr
 *
 */
public final class EventLogTail {

	private static boolean flag = true;

	private static final Logger log = Logger.getLogger(EventLogTail.class);

	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * constructor
	 */
	public EventLogTail() {
		if (flag)
			log.debug(">>>>> in class " + this.getClass().getSimpleName());
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private static void tail(String name, Tail tail) throws Win32Exception {
		
		EventLog log = new EventLog();
		log.open(name);
		int max = log.getNumberOfRecords();
		if (tail.number < max) {
			max = tail.number;
		}
		
		int last = log.getNewestRecord() + 1;
		int first = last - max;
		
		for (int i=first; i < last; i++) {
			EventLogRecord record = log.read(i);
			System.out.println(record);
		}
		
		log.close();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private static class TailNotification implements EventLogNotification {

		/* (non-Javadoc)
		 * @see org.hyperic.sigar.win32.EventLogNotification#handleNotification(org.hyperic.sigar.win32.EventLogRecord)
		 */
		@Override
		public void handleNotification(EventLogRecord event) {
			System.out.println(event);
		}

		/* (non-Javadoc)
		 * @see org.hyperic.sigar.win32.EventLogNotification#matches(org.hyperic.sigar.win32.EventLogRecord)
		 */
		@Override
		public boolean matches(EventLogRecord event) {
			return true;
		}
		
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * static test method
	 */
	private static void test01(String[] args) throws Exception {

		if (flag) {
			/*
			 * begin
			 */
			Tail tail = new Tail();
			tail.parseArgs(args);
			
			if (tail.files.size() == 0) {
				tail.files.add(EventLog.SYSTEM);
			}
			
			for (int i=0; i < tail.files.size(); i++) {
				String name = (String) tail.files.get(i);
				tail(name, tail);
				
				if (tail.follow) {
					TailNotification notifier = new TailNotification();
					EventLogThread thread = EventLogThread.getInstance(name);
					thread.add(notifier);;
					thread.doStart();
				}
			}
			
			if (tail.follow) {
				System.out.println("pause");
				System.in.read();
			}
		}
	}

	/*
	 * main method
	 */
	public static void main(String[] args) throws Exception {

		if (flag)
			log.debug(">>>>> " + new Object() {
			}.getClass().getEnclosingClass().getName());

		if (flag)
			test01(args);
	}
}
