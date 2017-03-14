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
package tain.kr.com.proj.cosmarter.v03.util;

import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * Code Templates > Comments > Types
 *
 * <PRE>
 *   -. FileName   : CheckSystem.java
 *   -. Package    : tain.kr.com.proj.pos51.v02.util
 *   -. Comment    :
 *   -. Author     : taincokr
 *   -. First Date : 2016. 3. 22. {time}
 * </PRE>
 *
 * @author taincokr
 *
 */
public final class CheckSystem {

	private static boolean flag = true;

	private static final Logger log = Logger.getLogger(CheckSystem.class);

	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private Properties prop = null;
	
	private String strLineSeparator = null;
	
	private CheckSystem() throws Exception {
		
		if (flag) {
			this.prop = System.getProperties();
			
			this.strLineSeparator = System.getProperty("line.separator", "\n");
		}
	}
	
	private String get(String key) throws Exception {
		
		String val = null;
		
		if (flag) {
			val = this.prop.getProperty(key);
		}
		
		return val;
	}
	
	private String get(String key, String def) throws Exception {
		
		String val = null;
		
		if (flag) {
			val = this.prop.getProperty(key, def);
		}
		
		return val;
	}
	
	public String printOsName() throws Exception {
		return get("os.name");
	}
	
	public boolean isWindows() throws Exception {
		
		if (!flag) {
			String osName = get("os.name", "");
			if (osName.indexOf("Win", 0) >= 0) {
				return true;
			}
		}
		
		if (flag) {
			String osName = get("os.name");
			if (osName.indexOf("Win", 0) >= 0) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isUnix() throws Exception {
		
		/*
		 * TODO 2016.03.22 : change someday..
		 */
		return isWindows() ? false : true;
	}
	
	public boolean isLinux() throws Exception {
		/*
		 * TODO 2016.03.22 : change someday
		 */
		return isWindows() ? false : true;
	}
	
	public String getLineSeparator() throws Exception {
		return this.strLineSeparator;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private static CheckSystem instance = null;
	
	public static synchronized CheckSystem getInstance() throws Exception {
		
		if (instance == null) {
			instance = new CheckSystem();
		}
		
		return instance;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////

	private static void test01(String[] args) throws Exception {
		
		if (flag) {
			log.debug(">>>>> " + CheckSystem.getInstance().printOsName());
			log.debug(">>>>> " + CheckSystem.getInstance().isWindows());
		}
	}
	
	public static void main(String[] args) throws Exception {
		
		if (flag) log.debug(">>>>> " + new Object(){}.getClass().getEnclosingClass().getName());
		
		if (flag) test01(args);
	}
}
