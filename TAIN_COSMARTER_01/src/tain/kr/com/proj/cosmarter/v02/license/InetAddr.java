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
package tain.kr.com.proj.cosmarter.v02.license;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

/**
 * Code Templates > Comments > Types
 *
 * <PRE>
 *   -. FileName   : InetAddr.java
 *   -. Package    : tain.kr.com.proj.pos51.v02.license
 *   -. Comment    :
 *   -. Author     : taincokr
 *   -. First Date : 2016. 3. 22. {time}
 * </PRE>
 *
 * @author taincokr
 *
 */
public class InetAddr {

	private static boolean flag = true;

	private static final Logger log = Logger.getLogger(InetAddr.class);

	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private String clsName = null;
	
	private ResourceBundle resource = null;
	
	//private String info = null;
	
	///////////////////////////////////////////////////////////////////////////////////////////////

	private InetAddr() {
		if (!flag) log.debug(">>>>> " + this.getClass().getName());
		
		if (flag) {
			this.clsName = this.getClass().getName();
			
			this.resource = ResourceBundle.getBundle(this.clsName.replace('.','/'));
		}
	}
	
	public String getInfo(String expDate) throws Exception {
		
		String info = null;

		if (flag) {
			/*
			 * validation of expDate
			 */
			if (expDate == null || expDate.length() != 6 || expDate.length() != expDate.getBytes().length)
				return null;
		}
		
		if (flag) {
			
			// InetAddress
			InetAddress inetAddress = null;
			
			try {
				inetAddress = InetAddress.getLocalHost();
			} catch (Exception e) {
				// e.printStackTrace();
				throw e;
			}
			
			if (!flag) log.debug(String.format("%s, %s", inetAddress.getHostName(), inetAddress.getHostAddress()));
			
			if (flag) {
				/*
				 * format : 999.999.999.999
				 */
				StringBuffer sb = new StringBuffer();
				
				byte[] ip = inetAddress.getAddress();
				for (int i=0; i < ip.length; i++) {
					sb.append(String.format("%03d", (int) ip[i] & 0xFF));
					if (i != ip.length - 1)
						sb.append('.');
				}
				
				info = String.format("%s-%s", sb.toString(), expDate);
				
				if (!flag) log.debug("[" + info + "]");
			}
		}
		
		return info;
	}
	
	public String getInfo() throws Exception {
		
		String expireMonth = "205012";
		
		if (flag) {
			long days = Long.parseLong(this.resource.getString("tain.license.expire.days"));
			long expireDays = new Date().getTime() + (1000 * 60 * 60 * 24 * days);
			
			String expireDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.KOREA).format(new Date(expireDays));
			
			expireMonth = new SimpleDateFormat("yyyyMM", Locale.KOREA).format(new Date(expireDays));
			
			if (!flag) log.debug(String.format("expire date [%d:%s] -> [%s]", days, expireDate, expireMonth));
		}

		return getInfo(expireMonth);
	}
	
	public String getIpAddr() throws Exception {

		StringBuffer sb = new StringBuffer();
		
		if (flag) {
			
			// InetAddress
			InetAddress inetAddress = null;
			
			try {
				inetAddress = InetAddress.getLocalHost();
			} catch (Exception e) {
				// e.printStackTrace();
				throw e;
			}
			
			if (!flag) log.debug(String.format("%s, %s", inetAddress.getHostName(), inetAddress.getHostAddress()));
			
			if (flag) {
				/*
				 * format : 127.0.0.l
				 */
				byte[] ip = inetAddress.getAddress();
				for (int i=0; i < ip.length; i++) {
					sb.append(String.format("%d", (int) ip[i] & 0xFF));
					if (i != ip.length - 1)
						sb.append('.');
				}
			}
		}
		
		return sb.toString();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private static InetAddr instance = null;
	
	public static synchronized InetAddr getInstance() throws Exception {
		
		if (instance == null) {
			instance = new InetAddr();
		}
		
		return instance;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////

	private static void test01(String[] args) throws Exception {
		
		if (flag) {
			/*
			 * check validation
			 */
			if (flag) log.debug(">>>>> " + InetAddr.getInstance().getInfo());           // default license
			if (flag) log.debug(">>>>> " + InetAddr.getInstance().getInfo("201612"));
		}
	}
	
	public static void main(String[] args) throws Exception {
		
		if (flag) log.debug(">>>>> " + new Object(){}.getClass().getEnclosingClass().getName());
		
		if (flag) test01(args);
	}
}
