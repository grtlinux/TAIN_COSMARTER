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
public class DateTime {

	private static boolean flag = true;

	private static final Logger log = Logger.getLogger(DateTime.class);

	///////////////////////////////////////////////////////////////////////////////////////////////

	private static final String KEY_FORMAT_YYYYMMDD = "tain.datetime.format.yyyymmdd";
	private static final String KEY_FORMAT_YYYYMMDDHHMMSS = "tain.datetime.format.yyyymmddhhmmss";
	
	private String strFormatYYYYMMDD = null;
	private String strFormatYYYYMMDDHHMMSS = null;
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private DateTime() throws Exception {
		if (flag) {
			String clsName = this.getClass().getName();
			
			ResourceBundle rb = ResourceBundle.getBundle(clsName.replace('.', '/'));
			
			this.strFormatYYYYMMDD = rb.getString(KEY_FORMAT_YYYYMMDD);
			this.strFormatYYYYMMDDHHMMSS = rb.getString(KEY_FORMAT_YYYYMMDDHHMMSS);
		}
	}
	
	public String getYYYYMMDD() throws Exception {
		String ret = null;
		
		if (flag) {
			ret = new SimpleDateFormat(this.strFormatYYYYMMDD, Locale.KOREA).format(new Date());
		}
		
		return ret;
	}
	
	public String getYYYYMMDDHHMMSS() throws Exception {
		String ret = null;
		
		if (flag) {
			ret = new SimpleDateFormat(this.strFormatYYYYMMDDHHMMSS, Locale.KOREA).format(new Date());
		}
		
		return ret;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private static DateTime instance = null;
	
	public static synchronized DateTime getInstance() throws Exception {
		
		if (instance == null) {
			instance = new DateTime();
		}
		
		return instance;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////

	private static void test01(String[] args) throws Exception {
		
		if (flag) {
			log.debug("[" + DateTime.getInstance().getYYYYMMDD() + "]");
			log.debug("[" + DateTime.getInstance().getYYYYMMDDHHMMSS() + "]");
		}
	}
	
	public static void main(String[] args) throws Exception {
		
		if (flag) log.debug(">>>>> " + new Object(){}.getClass().getEnclosingClass().getName());
		
		if (flag) test01(args);
	}
}
