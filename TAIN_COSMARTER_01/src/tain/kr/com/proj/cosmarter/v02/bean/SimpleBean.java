/**
 * Copyright 2014, 2015, 2016, 2017 TAIN, Inc. all rights reserved.
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
 * Copyright 2014, 2015, 2016, 2017 TAIN, Inc.
 *
 */
package tain.kr.com.proj.cosmarter.v02.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

/**
 * Code Templates > Comments > Types
 *
 * <PRE>
 *   -. FileName   : SimpleBean.java
 *   -. Package    : tain.kr.com.proj.cosmarter.v02.bean
 *   -. Comment    :
 *   -. Author     : taincokr
 *   -. First Date : 2017. 3. 11. {time}
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
	
	///////////////////////////////////////////////////////////////////////////////////////////////

	private class SimpleInfo {
		
		public String name;
		public boolean usable;
	}
	
	private List<SimpleInfo> lstInfo = null;
	
	private String result = null;
	
	///////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * constructor
	 */
	public SimpleBean() throws Exception {
		
		if (flag) {
			/*
			 * set-1
			 */
			String clsName = this.getClass().getName();
			
			ResourceBundle rb = ResourceBundle.getBundle(clsName.replace('.', '/'));
			
			this.ipAddr = rb.getString(KEY_IPADDR);
			this.port = rb.getString(KEY_PORT);
			this.name = rb.getString(KEY_NAME);
			this.cmd = rb.getString(KEY_CMD);
			this.retInfo = rb.getString(KEY_RETINFO);
			this.skip = rb.getString(KEY_SKIP);
		}
		
		if (flag) {
			/*
			 * set-2
			 */
			setLstInfo();
		}
		
		if (flag)
			log.debug(">>>>> in class " + this.getClass().getSimpleName());
	}

	private boolean setLstInfo() throws Exception {
		
		if (flag) {
			this.lstInfo = new ArrayList<SimpleInfo>();
			
			String[] infos = this.retInfo.split("\\s*,\\s*");
			
			for (String info : infos) {
				String[] items = info.split("\\s*:\\s*");
				if (items.length != 2)
					continue;
				
				SimpleInfo simpleInfo = new SimpleInfo();
				simpleInfo.name = items[0].trim();
				simpleInfo.usable = "1".equals(items[1].trim()) ? true : false;
				
				this.lstInfo.add(simpleInfo);
			}
		}
		
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////

	public String getIpAddr() {
		return ipAddr;
	}

	public String getPort() {
		return port;
	}

	public String getName() {
		return name;
	}

	public String getCmd() {
		return cmd;
	}

	public String getRetInfo() {
		return retInfo;
	}

	public String getSkip() {
		return skip;
	}

	public List<SimpleInfo> getLstInfo() {
		return lstInfo;
	}

	public String getResult() {
		return result;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public void setRetInfo(String retInfo) {
		this.retInfo = retInfo;
		
		try {
			setLstInfo();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setSkip(String skip) {
		this.skip = skip;
	}

	public void setLstInfo(List<SimpleInfo> lstInfo) {
		this.lstInfo = lstInfo;
	}

	public void setResult(String result) {
		this.result = result;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public String getInfoName(int idx) throws Exception {
		
		SimpleInfo info = this.lstInfo.get(idx);
		
		if (info.usable)
			return info.name;
		
		return null;
	}
	
	public int getInfoCnt() throws Exception {
		return this.lstInfo.size();
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
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	/*
	 * static test method
	 */
	private static void test01(String[] args) throws Exception {

		if (flag) {
			SimpleBean bean = new SimpleBean();
			bean.print();
		}
	}

	/*
	 * main method
	 */
	public static void main(String[] args) throws Exception {

		if (flag)
			log.debug(">>>>> " + new Object() {
			}.getClass().getEnclosingClass().getName());

		if (flag)
			test01(args);
	}
}
