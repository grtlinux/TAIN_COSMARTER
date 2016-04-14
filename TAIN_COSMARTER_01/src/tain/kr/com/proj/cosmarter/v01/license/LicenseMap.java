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
package tain.kr.com.proj.cosmarter.v01.license;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * Code Templates > Comments > Types
 *
 * <PRE>
 *   -. FileName   : LicenseMap.java
 *   -. Package    : tain.kr.com.proj.pos51.v02.license
 *   -. Comment    :
 *   -. Author     : taincokr
 *   -. First Date : 2016. 3. 22. {time}
 * </PRE>
 *
 * @author taincokr
 *
 */
public class LicenseMap {

	private static boolean flag = true;

	private static final Logger log = Logger.getLogger(LicenseMap.class);

	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private Map<String, String> map = null;
	
	///////////////////////////////////////////////////////////////////////////////////////////////

	private LicenseMap() throws Exception {
		
		if (flag) {
			this.map = new HashMap<String, String>();
			
			for (LicenseType type : LicenseType.values()) {
				this.map.put(type.getKey(), type.getVal());
			}
		}
	}
	
	public void print() throws Exception {
		
		if (flag) {
			for (Map.Entry<String, String> entry : map.entrySet()) {
				log.debug(String.format("[%s] => [%s]", entry.getKey(), entry.getValue()));
			}
		}
	}
	
	public String get(String key) throws Exception {
		
		String val = null;
		
		if (flag) {
			val = this.map.get(key);
		}
		
		return val;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private static LicenseMap instance = null;
	
	public static synchronized LicenseMap getInstance() throws Exception {
		
		if (instance == null) {
			instance = new LicenseMap();
			if (!flag) instance.print();
		}
		
		return instance;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////

	private static void test01(String[] args) throws Exception {
		
		if (flag) {
			LicenseMap.getInstance().print();
		}
		
		if (flag) {
			log.debug(">>>>> " + LicenseMap.getInstance().get(("K")));
		}
	}
	
	public static void main(String[] args) throws Exception {
		
		if (flag) log.debug(">>>>> " + new Object(){}.getClass().getEnclosingClass().getName());
		
		if (flag) test01(args);
	}
}
