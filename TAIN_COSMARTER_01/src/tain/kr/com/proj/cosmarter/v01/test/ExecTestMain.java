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
package tain.kr.com.proj.cosmarter.v01.test;

import org.apache.log4j.Logger;

import tain.kr.com.proj.cosmarter.v01.util.CheckSystem;
import tain.kr.com.proj.cosmarter.v01.util.Exec;

/**
 * Code Templates > Comments > Types
 *
 * <PRE>
 *   -. FileName   : ExecTestMain.java
 *   -. Package    : tain.kr.com.proj.cosmarter.v01.test
 *   -. Comment    :
 *   -. Author     : taincokr
 *   -. First Date : 2016. 4. 14. {time}
 * </PRE>
 *
 * @author taincokr
 *
 */
public class ExecTestMain {

	private static boolean flag = true;

	private static final Logger log = Logger.getLogger(ExecTestMain.class);

	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	/*
	 * Windows
	 */
	private static void test01(String[] args) throws Exception {
		
		if (!CheckSystem.getInstance().isWindows()) {
			log.debug(">>>>> This is not windows system.");
			return;
		}

		if (flag) {
			// String
			String cmd = "cmd /c dir";
			
			log.debug(">>>>> String");
			
			log.debug(">>>>> ret = " + Exec.run(cmd, false));
		}

		if (flag) {
			// String[]
			String[] cmd = { "cmd", "/c", "dir" };
			
			log.debug(">>>>> String[]");
			
			log.debug(">>>>> ret = " + Exec.run(cmd, false));
		}
	}
	
	/*
	 * Linux
	 */
	private static void test02(String[] args) throws Exception {
		
		if (!CheckSystem.getInstance().isLinux()) {
			log.debug(">>>>> This is not linux system.");
			return;
		}

		if (flag) {
			// String
			Exec.run("/bin/ksh -x /home/kang/abc.sh");
		}
	}
	
	public static void main(String[] args) throws Exception {
		
		if (flag) log.debug(">>>>> " + new Object(){}.getClass().getEnclosingClass().getName());
		
		if (flag) test01(args);
		if (flag) test02(args);
	}
}



