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
import org.hyperic.sigar.ProcMem;
import org.hyperic.sigar.Sigar;

/**
 * Code Templates > Comments > Types
 *
 * <PRE>
 *   -. FileName   : MemWatch.java
 *   -. Package    : tain.kr.com.test.sigar.v01
 *   -. Comment    :
 *   -. Author     : taincokr
 *   -. First Date : 2017. 3. 17. {time}
 * </PRE>
 *
 * @author taincokr
 *
 */
public final class MemWatch {

	private static boolean flag = true;

	private static final Logger log = Logger.getLogger(MemWatch.class);

	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final int SLEEP_TIME = 1000 * 10;
	
	///////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * constructor
	 */
	public MemWatch() {
		if (flag)
			log.debug(">>>>> in class " + this.getClass().getSimpleName());
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
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private static StringBuffer diff(ProcMem last, ProcMem cur) {
		
		StringBuffer sb = new StringBuffer();
		
		long diff;
		
		diff = cur.getSize() - last.getSize();
		if (diff != 0) {
			sb.append("size=" + diff);
		}
		
		diff = cur.getResident() - last.getResident();
		if (diff != 0) {
			sb.append(", resident=" + diff);
		}
		
		diff = cur.getShare() - last.getShare();
		if (diff != 0) {
			sb.append(", share=" + diff);
		}
		
		return sb;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * static test method
	 */
	private static void test01(String[] args) throws Exception {

		if (flag)
			new MemWatch();

		if (flag) {
			Sigar sigar = new Sigar();
			
			if (args.length != 1) {
				throw new Exception("Usage: MemWatch pid");
			}
			
			long pid = Long.parseLong(args[0]);

			long lastTime = System.currentTimeMillis();

			ProcMem last = sigar.getProcMem(pid);

			while (true) {
				ProcMem cur = sigar.getProcMem(pid);

				StringBuffer diff = diff(last, cur);

				if (diff.length() == 0) {
					System.out.println("no change " + "(size=" + Sigar.formatSize(cur.getSize()) + ")");
				} else {
					long curTime = System.currentTimeMillis();
					long timeDiff = curTime - lastTime;
					lastTime = curTime;
					diff.append(" after " + timeDiff + "ms");
					System.out.println(diff);
				}

				last = cur;
				Thread.sleep(SLEEP_TIME);
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

		if (flag) {
			/*
			 * begin
			 */
			// test01(args);
			test01(new String[] { "1520" });
		}
	}
}
