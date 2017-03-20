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
package tain.kr.com.proj.cosmarter.v04.base;

import org.apache.log4j.Logger;

import tain.kr.com.proj.cosmarter.v04.util.Param;

/**
 * Code Templates > Comments > Types
 *
 * <PRE>
 *   -. FileName   : Version.java
 *   -. Package    : tain.kr.com.proj.cosmarter.v01.common
 *   -. Comment    :
 *   -. Author     : taincokr
 *   -. First Date : 2016. 4. 11. {time}
 * </PRE>
 *
 * @author taincokr
 *
 */
public final class Version {

	private static boolean flag = true;

	private static final Logger log = Logger.getLogger(Version.class);

	///////////////////////////////////////////////////////////////////////////////////////////////

	private static final String KEY_PROJECT = "tain.project";
	private static final String KEY_VERSION = "tain.version";
	
	private String strProject = null;
	private String strVersion = null;
	
	///////////////////////////////////////////////////////////////////////////////////////////////

	private Version() throws Exception {
		
		if (flag) {
			this.strProject = Param.getInstance().getString(KEY_PROJECT, "NO_PROJECT");
			this.strVersion = Param.getInstance().getString(KEY_VERSION, "NO_VERSION");
		}
	}
	
	public String getProject() throws Exception {
		return this.strProject;
	}
	
	public String getVersion() throws Exception {
		return this.strVersion;
	}
	
	public void print() throws Exception {
		System.out.println("PROJECT > " + this.getProject());
		System.out.println("VERSION > " + this.getVersion());
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private static Version instance = null;
	
	public static synchronized Version getInstance() throws Exception {
		
		if (instance == null) {
			instance = new Version();
		}
		
		return instance;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private static void test01(String[] args) throws Exception {
		
		if (flag) {
			Version.getInstance().print();
			
			log.debug(">>>>> " + Version.getInstance().getProject());
			log.debug(">>>>> " + Version.getInstance().getVersion());
		}
	}
	
	public static void main(String[] args) throws Exception {
		
		if (flag) log.debug(">>>>> " + new Object(){}.getClass().getEnclosingClass().getName());
		
		if (flag) test01(args);
	}
}
