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

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

/**
 * Code Templates > Comments > Types
 *
 * <PRE>
 *   -. FileName   : License.java
 *   -. Package    : tain.kr.com.proj.pos51.v02.license
 *   -. Comment    :
 *   -. Author     : taincokr
 *   -. First Date : 2016. 3. 22. {time}
 * </PRE>
 *
 * @author taincokr
 *
 */
public class License {

	private static boolean flag = true;

	private static final Logger log = Logger.getLogger(License.class);

	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final String KEY_EXPIRE_DAYS = "tain.license.expire.days";
	
	private String clsName = null;
	
	private String expDays = null;
	private String expDate = null;
	
	///////////////////////////////////////////////////////////////////////////////////////////////

	private License() throws Exception {
		
		if (flag) {
			this.clsName = this.getClass().getName();
			
			ResourceBundle rb = ResourceBundle.getBundle(clsName.replace('.', '/'));
			
			this.expDays = rb.getString(KEY_EXPIRE_DAYS);
			if (!flag) log.debug(">>>>> " + this.expDays);

			long days = Long.parseLong(this.expDays);
			long expireDays = new Date().getTime() + (1000 * 60 * 60 * 24 * days);
			
			String expireDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.KOREA).format(new Date(expireDays));
			
			this.expDate = new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date(expireDays));
			
			if (!flag) log.debug(String.format("expire date [%d:%s] -> [%s]", days, expireDate, this.expDate));
		}
	}
	
	public String get(String expMonth) throws Exception {
		
		StringBuffer sb = new StringBuffer();
		
		if (flag) {
			/*
			 * chech expire date
			 */
		}
		
		if (flag) {
			/*
			 * make license
			 *     orgLicense
			 *     encLicense
			 *     decLicense
			 */
			
			String orgLicense = InetAddr.getInstance().getInfo(expMonth);
			if (flag) log.debug(">>>>> orgLicense : " + orgLicense);
			
			String strItem1 = orgLicense.substring( 0, 15);
			String strItem2 = orgLicense.substring(15, 18);
			String strItem3 = orgLicense.substring(18, 20);
			String strItem4 = orgLicense.substring(20);
			if (!flag) log.debug(String.format("[%s] [%s] [%s] [%s]", strItem1, strItem2, strItem3, strItem4));
			
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
				
				if (!flag) log.debug("strItem1 [" + sb.toString() + "]");
			}
			
			if (flag) {
				/*
				 * strItem2
				 */
				sb.append("K");
				
				if (!flag) log.debug("strItem2 [" + sb.toString() + "]");
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
				
				if (!flag) log.debug("strItem3 [" + sb.toString() + "]");
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

				if (!flag) log.debug("strItem4 [" + sb.toString() + "]");
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

				if (!flag) log.debug("crc      [" + sb.toString() + "]");
			}
		}

		return sb.toString();
	}
	
	public String get() throws Exception {
		
		return get(this.expDate.substring(0, 6));
	}
	
	public String getLicense(String encLicense) throws Exception {
		
		StringBuffer sbInfo = new StringBuffer();

		if (flag) {
			
			/*
			 * ############################
			 *  license key
			 */
			String license = encLicense;
			//license = "918X942YAAAZAA2K94C8";    // correct [192.168.000.008-201612] 48 8
			//license = "AAAX111YAAAZ111K94C9";    // correct : ignore ip check
			//license = "918X942YAAAZAA2K94C84";   // size error
			//license = "918X942YAAAZAA2K94T8";    // wrong license key
			//license = "918X942YAAAZAA1K94C7";    // wrong ip address
			//license = "918X942YAAAZAA2K94C9";    // crc error
			//license = "918X942YAAAZAA2K95C9";    // expire date error
			
			if (flag) {
				/*
				 * 1. check size of license
				 */
				if (license.length() != 20) {
					throw new Exception("ERROR : error for checking the size of license");
				}
			}
			
			String key = license.substring( 0, 18);
			String mon = license.substring(18, 19);
			String crc = license.substring(19);
			if (!flag) log.debug(String.format("[%s] = [%s] [%s] [%s]", license, key, mon, crc));

			int crcDigit = 0;
			
			if (flag) {
				/*
				 * 3. check characters of license key
				 */
				
				char[] chs = key.toCharArray();
				
				for (char ch : chs) {
					String strKey = "" + ch;
					String strVal = LicenseMap.getInstance().get(strKey);
					if (strVal == null) {
						throw new Exception("ERROR : error because of wrong license key");
					}
					
					if (!flag) log.debug(String.format("[%s] => [%s]", strKey, strVal));
					
					if (Character.isDigit(ch)) {
						crcDigit += Integer.parseInt(strKey);
					}
					
					sbInfo.append(strVal);
				}
				
				switch (mon) {
				case "A": sbInfo.append("10"); break;
				case "B": sbInfo.append("11"); break;
				case "C": sbInfo.append("12"); break;
				default:
					sbInfo.append(String.format("%02d", Integer.parseInt(mon)));
					break;
				}
				
				if (!flag) log.debug(String.format("[%s] %d %d", sbInfo.toString(), crcDigit, crcDigit % 10));
			}

			if (flag) {
				/*
				 * 4. check CRC value
				 */
				if (!crc.equals(String.valueOf(crcDigit % 10))) {
					throw new Exception("ERROR : error for crc check.");
				}
			}
			
			StringBuffer sbIp = new StringBuffer();
			
			if (flag) {
				/*
				 * 5. get local ip address
				 */
				InetAddress inetAddr = null;
				
				try {
					inetAddr = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					throw e;
				}

				byte[] ip = inetAddr.getAddress();
				for (int i=0; i < ip.length; i++) {
					sbIp.append(String.format("%03d", (int) ip[i] & 0xFF));
					if (i != ip.length - 1)
						sbIp.append(".");
				}
				
				if (!flag) log.debug("real ip : " + sbIp.toString());
			}
			
			if (flag) {
				/*
				 * 6. check ip address
				 *    if sbInfo is started with 000, then skip
				 */
				
				if (!flag) log.debug(String.format("[%s] [%s]", sbInfo.substring(0, 15), sbIp));
				
				if (!"000".equals(sbInfo.substring(0, 3))) {
					// license key does not start with AAA
					if (!sbIp.toString().equals(sbInfo.substring(0, 15))) {
						throw new Exception("ERROR : error because of wrong ip address.");
					}
				}
			}
			
			if (flag) {
				/*
				 * 7. check expire date
				 */
				String[] str = sbInfo.toString().split("-");
				String yyyymm = new SimpleDateFormat("yyyyMM").format(new Date());

				if (!flag) log.debug(String.format("[%s] [%s] [%s]", sbInfo.toString(), str[1], yyyymm));
				
				if (yyyymm.compareTo(str[1]) > 0) {
					throw new Exception("ERROR : error out of expire date");
				}
			}
		}
		
		return sbInfo.toString();
	}
	
	public boolean check(String encLicense) throws Exception {
		
		getLicense(encLicense);
		
		if (flag) log.debug("SUCCESS : success to check license");
		
		return true;
	}
	
	public String getIpAddr(String encLicense) throws Exception {
		
		StringBuffer sb = new StringBuffer();
		
		String license = getLicense(encLicense);
		if (!flag) log.debug("> " + license);
		
		String[] infos = license.split("-");
		if (!flag) log.debug("> " + infos[0]);
		
		String[] nums = infos[0].split("\\.");
		
		for (String num : nums) {
			sb.append(".");
			sb.append(String.valueOf(Integer.parseInt(num)));
		}
		
		sb.deleteCharAt(0);
		
		return sb.toString();
	}
	
	public String getExpireDate(String encLicense) throws Exception {
		
		String license = getLicense(encLicense);
		
		String[] infos = license.split("-");
		
		return infos[1];
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private static License instance = null;
	
	public static synchronized License getInstance() throws Exception {
		
		if (instance == null) {
			instance = new License();
		}
		
		return instance;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////

	private static void test01(String[] args) throws Exception {
		
		if (flag) {
			/*
			 * create license and check
			 */
			String license = License.getInstance().get();
			if (flag) log.debug("LICENSE [" + license + "]");
			
			License.getInstance().check(license);
		}
		
		if (!flag) {
			/*
			 * check license, and fire error.
			 */
			License.getInstance().check("918Y942XA54XAA9K94A3");
		}
		
		if (flag) {
			
			/*
			 * get real IP address
			 */
			if (flag) log.debug("Real IP address > " + InetAddr.getInstance().getIpAddr());
			/*
			 * get IP address
			 */
			if (flag) log.debug("IP address > " + License.getInstance().getIpAddr("918Y942XA54XAA9K94A4"));
			
			/*
			 * get expire date
			 */
			if (flag) log.debug("Expire Date > " + License.getInstance().getExpireDate("918Y942XA54XAA9K94A4"));
		}
	}
	
	public static void main(String[] args) throws Exception {
		
		if (flag) log.debug(">>>>> " + new Object(){}.getClass().getEnclosingClass().getName());
		
		if (flag) test01(args);
	}
}
