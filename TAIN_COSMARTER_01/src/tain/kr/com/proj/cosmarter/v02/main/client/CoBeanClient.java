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
package tain.kr.com.proj.cosmarter.v02.main.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import tain.kr.com.proj.cosmarter.v01.bean.SimpleBean;
import tain.kr.com.proj.cosmarter.v01.util.CheckSystem;

/**
 * Code Templates > Comments > Types
 *
 * <PRE>
 *   -. FileName   : CoBeanClient.java
 *   -. Package    : tain.kr.com.proj.cosmarter.v01.main.client
 *   -. Comment    :
 *   -. Author     : taincokr
 *   -. First Date : 2016. 4. 25. {time}
 * </PRE>
 *
 * @author taincokr
 *
 */
public class CoBeanClient {

	private static boolean flag = true;

	private static final Logger log = Logger.getLogger(CoBeanClient.class);

	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final String KEY_DESC = "tain.cobean.client.desc";
	
	private String strDesc = null;
	
	public CoBeanClient() throws Exception {
		
		if (flag) {
			String clsName = this.getClass().getName();
			
			ResourceBundle rb = ResourceBundle.getBundle(clsName.replace('.', '/'));
			
			this.strDesc = rb.getString(KEY_DESC);
			
			if (flag) this.print();
		}
	}
	
	public String getDesc() throws Exception {
		
		return this.strDesc;
	}
	
	public void print() throws Exception {
		
		if (flag) {
			log.debug(">>>>> desc = " + this.getDesc());
		}
	}
	
	public void process(SimpleBean bean) throws Exception {
		
		if (flag) {
			PrintWriter pw = null;
			BufferedReader br = null;
			String line = null;
			
			Socket socket = new Socket(bean.getIpAddr(), Integer.parseInt(bean.getPort()));
			
			pw = new PrintWriter(socket.getOutputStream());
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			if (flag) log.info(">>>>> CMD : " + bean.getCmd());
			pw.println(bean.getCmd());
			pw.flush();
			
			int noSkip = Integer.parseInt(bean.getSkip());
			int noLine = 0;
			int limit = bean.getInfoCnt();
			
			StringBuffer sbJson = new StringBuffer();

			sbJson.append("{").append("\"").append(bean.getName()).append("\"").append(":").append("[");
			if (flag) sbJson.append(CheckSystem.getInstance().getLineSeparator());

			while ((line = br.readLine()) != null) {
				++noLine;
				if (noLine <= noSkip)
					continue;
				
				if (!flag) log.debug(String.format(">>>>> (%04d) [%s]", noLine, line));
				
				String[] words = line.split("\\s+", limit);
				if (flag) words[limit-1] = words[limit-1].trim();
				
				// make JSON
				StringBuffer sb = new StringBuffer();
				if (flag) sb.append("\t");
				sb.append("{");
				
				for (int i=0; i < limit; i++) {
					String infoName = bean.getInfoName(i);
					if (infoName == null)
						continue;
					
					sb.append("\"").append(infoName).append("\"");
					sb.append(":");
					sb.append("\"").append(words[i]).append("\"");
					sb.append(",");
				}
				
				sb.append("},");
				if (flag) sb.append(CheckSystem.getInstance().getLineSeparator());
				
				if (flag) sbJson.append(sb.toString());
			}
			
			sbJson.append("]}");
			if (flag) sbJson.append(CheckSystem.getInstance().getLineSeparator());

			bean.setResult(sbJson.toString());
			
			br.close();
			pw.close();
			socket.close();
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private static CoBeanClient instance = null;
	
	public static synchronized CoBeanClient getInstance() throws Exception {
		
		if (instance == null) {
			instance = new CoBeanClient();
		}
		
		return instance;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private static void test01(String[] args) throws Exception {
		
		if (flag) {
			CoBeanClient.getInstance();
		}
	}
	
	private static void test02(String[] args) throws Exception {
		
		SimpleBean bean = new SimpleBean();

		if (flag) {
			bean.setIpAddr("cosmarter02");
			bean.setSkip("0");
			CoBeanClient.getInstance().process(bean);
			
			if (flag) log.debug(">>>>> result = " + bean.getResult());
		}
		
		if (flag) {
			bean.setIpAddr("cosmarter01");
			bean.setName("lstps2");
			bean.setCmd("ps -ef | grep kang | grep -v grep");
			bean.setRetInfo("uid:1, pid:1, ppid:0, cpu:0, stime:0, tty:0, time:0, cmd:1");
			bean.setSkip("0");
			CoBeanClient.getInstance().process(bean);
			
			if (flag) log.debug(">>>>> result = " + bean.getResult());
		}
	}
	
	public static void main(String[] args) throws Exception {
		
		if (flag) log.debug(">>>>> " + new Object(){}.getClass().getEnclosingClass().getName());
		
		if (!flag) test01(args);
		if (flag) test02(args);
	}
}
