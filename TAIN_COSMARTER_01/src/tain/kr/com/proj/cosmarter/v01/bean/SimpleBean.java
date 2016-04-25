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
package tain.kr.com.proj.cosmarter.v01.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

/**
 * Code Templates > Comments > Types
 *
 * <PRE>
 *   -. FileName   : SimpleBean.java
 *   -. Package    : tain.kr.com.proj.cosmarter.v01.bean
 *   -. Comment    :
 *   -. Author     : taincokr
 *   -. First Date : 2016. 4. 25. {time}
 * </PRE>
 *
 * @author taincokr
 *
 */

public class SimpleBean {

	private static boolean flag = true;

	private static final Logger log = Logger.getLogger(SimpleBean.class);

	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final String KEY_IPADDR  = "tain.cosmarter.bean.simple.ipaddr";
	private static final String KEY_PORT    = "tain.cosmarter.bean.simple.port";
	private static final String KEY_NAME    = "tain.cosmarter.bean.simple.name";
	private static final String KEY_CMD     = "tain.cosmarter.bean.simple.cmd";
	private static final String KEY_RETINFO = "tain.cosmarter.bean.simple.ret.info";

	private static final String KEY_SKIP    = "tain.cosmarter.bean.simple.skip.lines";
	
	private String ipAddr = null;
	private String port = null;
	
	private String name = null;
	private String cmd = null;
	private String retInfo = null;
	private String skip = null;
	
	private List<SimpleInfo> lstInfo = null;
	
	private String result = null;
	
	private class SimpleInfo {
		
		public String name;
		public boolean usable;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public SimpleBean() throws Exception {
		
		if (flag) {
			String clsName = this.getClass().getName();
			
			ResourceBundle rb = ResourceBundle.getBundle(clsName.replace('.', '/'));
			
			this.ipAddr = rb.getString(KEY_IPADDR);
			this.port = rb.getString(KEY_PORT);
			this.name = rb.getString(KEY_NAME);
			this.cmd = rb.getString(KEY_CMD);
			this.retInfo = rb.getString(KEY_RETINFO);
			this.skip = rb.getString(KEY_SKIP);
			
			if (!flag) print();
		}
		
		if (flag) {
			lstInfo = new ArrayList<SimpleInfo>();
			
			String[] infos = this.retInfo.split(",");
			
			for (String info : infos) {
				String[] items = info.split(":");
				if (items.length != 2)
					continue;
				
				SimpleInfo simpleInfo = new SimpleInfo();
				simpleInfo.name = items[0].trim();
				simpleInfo.usable = "1".equals(items[1].trim()) ? true : false;
				
				lstInfo.add(simpleInfo);
			}
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * @return the ipAddr
	 */
	public String getIpAddr() {
		return ipAddr;
	}

	/**
	 * @return the port
	 */
	public String getPort() {
		return port;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the cmd
	 */
	public String getCmd() {
		return cmd;
	}

	/**
	 * @return the retInfo
	 */
	public String getRetInfo() {
		return retInfo;
	}

	/**
	 * @param ipAddr the ipAddr to set
	 */
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(String port) {
		this.port = port;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param cmd the cmd to set
	 */
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	/**
	 * @param retInfo the retInfo to set
	 */
	public void setRetInfo(String retInfo) {
		this.retInfo = retInfo;
	}

	/**
	 * @return the skip
	 */
	public String getSkip() {
		return skip;
	}

	/**
	 * @param skip the skip to set
	 */
	public void setSkip(String skip) {
		this.skip = skip;
	}

	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}

	public String getInfoName(int idx) throws Exception {
		
		SimpleInfo info = lstInfo.get(idx);
		
		if (info.usable)
			return info.name;
		
		return null;
	}

	public int getInfoCnt() throws Exception {
		return lstInfo.size();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public void print() throws Exception {
		
		if (flag) {

			log.debug(" ipAddr  = " + this.getIpAddr());
			log.debug(" port    = " + this.getPort());
			log.debug(" name    = " + this.getName());
			log.debug(" cmd     = " + this.getCmd());
			log.debug(" retInfo = " + this.getRetInfo());
			log.debug(" skip    = " + this.getSkip());
			
			log.debug(" result  = " + this.getResult());
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private static void test01(String[] args) throws Exception {
		
		if (flag) {
			
			SimpleBean bean = new SimpleBean();
			
			bean.print();
		}
	}
	
	public static void main(String[] args) throws Exception {
		
		if (flag) log.debug(">>>>> " + new Object(){}.getClass().getEnclosingClass().getName());
		
		if (flag) test01(args);
	}
}
