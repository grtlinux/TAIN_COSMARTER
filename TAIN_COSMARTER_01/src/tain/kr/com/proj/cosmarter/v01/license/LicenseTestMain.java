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

import java.util.Date;
import java.util.Random;

import org.apache.log4j.Logger;

/**
 * Code Templates > Comments > Types
 *
 * <PRE>
 *   -. FileName   : LicenseTestMain.java
 *   -. Package    : tain.kr.com.proj.pos51.v02.license
 *   -. Comment    :
 *   -. Author     : taincokr
 *   -. First Date : 2016. 3. 22. {time}
 * </PRE>
 *
 * @author taincokr
 *
 */
public class LicenseTestMain {

	private static boolean flag = true;

	private static final Logger log = Logger.getLogger(LicenseTestMain.class);

	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////

	private static void test01(String[] args) throws Exception {
		
		if (flag) {
			/*
			 * make license
			 *     orgLicense
			 *     encLicense
			 *     decLicense
			 */
			
			String orgLicense = InetAddr.getInstance().getInfo();
			if (flag) log.debug(">>>>> orgLicense : " + orgLicense);
			
			String strItem1 = orgLicense.substring( 0, 15);
			String strItem2 = orgLicense.substring(15, 18);
			String strItem3 = orgLicense.substring(18, 20);
			String strItem4 = orgLicense.substring(20);
			if (flag) log.debug(String.format("[%s] [%s] [%s] [%s]", strItem1, strItem2, strItem3, strItem4));
			
			StringBuffer sb = new StringBuffer();
			
			if (flag) {
				/*
				 * strItem1
				 */

				Random rand = new Random(new Date().getTime());
				
				byte[] xyz = "XYZ".getBytes();
				
				char[] chs = strItem1.toCharArray();
				
				for (char ch : chs) {
					String strKey = "" + ch;
					if (!".".equals(strKey)) {
						String strVal = LicenseMap.getInstance().get(strKey);
						if (!flag) log.debug(String.format("[%s] => [%s]", strKey, strVal));
						
						if (strVal == null) {
							throw new Exception("ERROR : error of wrong key..");
						}
						
						sb.append(strVal);
					} else {
						String strVal = "" + (char) xyz[rand.nextInt(xyz.length)];
						if (!flag) log.debug(String.format("[%s] => [%s]", strKey, strVal));
						
						sb.append(strVal);
					}
				}
				
				if (flag) log.debug("strItem1 [" + sb.toString() + "]");
			}
			
			if (flag) {
				/*
				 * strItem2
				 */
				sb.append("K");
				
				if (flag) log.debug("strItem2 [" + sb.toString() + "]");
			}
			
			if (flag) {
				/*
				 * strItem3
				 */
				char[] chs = strItem3.toCharArray();

				for (char ch : chs) {
					String strKey = "" + ch;
					String strVal = LicenseMap.getInstance().get(strKey);
					if (!flag) log.debug(String.format("[%s] => [%s]", strKey, strVal));
					
					if (strVal == null) {
						throw new Exception("ERROR : error of wrong key..");
					}
						
					sb.append(strVal);
				}
				
				if (flag) log.debug("strItem3 [" + sb.toString() + "]");
			}
			
			if (flag) {
				/*
				 * strItem4
				 */
				switch (strItem4) {
				case "10": sb.append("A"); break;
				case "11": sb.append("B"); break;
				case "12": sb.append("C"); break;
				default:
					sb.append(strItem4.substring(1));
					break;
				}

				if (flag) log.debug("strItem4 [" + sb.toString() + "]");
			}
			
			if (flag) {
				/*
				 * CRC
				 */
				char[] chs = sb.toString().toCharArray();
				
				int crc = 0;
				for (char ch : chs) {
					if (Character.isDigit(ch)) {
						crc += Integer.parseInt("" + ch);
					}
				}
				
				sb.append(String.valueOf(crc % 10));

				if (flag) log.debug("crc      [" + sb.toString() + "]");
			}
		}
	}
	
	private static void test02(String[] args) throws Exception {
		if (flag) {
			/*
			 * check license
			 */
		}
	}
	
	public static void main(String[] args) throws Exception {
		
		if (flag) log.debug(">>>>> " + new Object(){}.getClass().getEnclosingClass().getName());
		
		if (flag) test01(args);
		if (flag) test02(args);
	}
}
