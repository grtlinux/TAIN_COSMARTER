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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

/**
 * Code Templates > Comments > Types
 *
 * <PRE>
 *   -. FileName   : DateTime.java
 *   -. Package    : tain.kr.com.proj.pos51.v02.util
 *   -. Comment    :
 *   -. Author     : taincokr
 *   -. First Date : 2016. 3. 22. {time}
 * </PRE>
 *
 * @author taincokr
 *
 */
public final class DateTime {

	private static boolean flag = true;

	private static final Logger log = Logger.getLogger(DateTime.class);

	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final String FORMAT_DEFAULT = "[ yyyy/MM/dd HH:mm:ss ]";
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private DateTime() throws Exception {}
	
	public String get(String format) throws Exception {
		
		return new SimpleDateFormat(format, Locale.KOREA).format(new Date());
	}
	
	public String get() throws Exception {
		
		return get(DateTime.FORMAT_DEFAULT);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private static DateTime instance = null;
	
	public static synchronized DateTime getInstance() throws Exception {
		
		if (DateTime.instance == null) {
			DateTime.instance = new DateTime();
		}
		
		return DateTime.instance;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////

	private static void test01(String[] args) throws Exception {
		
		if (flag) {
			log.debug(String.format(">>>>> %s", DateTime.getInstance().get()));
			log.debug(String.format(">>>>> %s", DateTime.getInstance().get("yyyyMMddHHMMss")));
		}
	}
	
	public static void main(String[] args) throws Exception {
		
		if (flag) log.debug(">>>>> " + new Object(){}.getClass().getEnclosingClass().getName());
		
		if (flag) test01(args);
	}
}
