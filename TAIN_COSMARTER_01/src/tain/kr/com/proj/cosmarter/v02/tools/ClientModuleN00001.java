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
package tain.kr.com.proj.cosmarter.v02.tools;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import tain.kr.com.proj.cosmarter.v01.util.StrUtil;

/**
 * Code Templates > Comments > Types
 *
 * <PRE>
 *   -. FileName   : ClientModuleN00001.java
 *   -. Package    : tain.kr.com.proj.cosmarter.v01.tools
 *   -. Comment    :
 *   -. Author     : taincokr
 *   -. First Date : 2016. 4. 18. {time}
 * </PRE>
 *
 * @author taincokr
 *
 */
public class ClientModuleN00001 {

	private static boolean flag = true;

	private static final Logger log = Logger.getLogger(ClientModuleN00001.class);

	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final String KEY_CLIENT_DESC = "tain.cosmarter.client.desc";
	private static final String KEY_CLIENT_CONNECT_HOST = "tain.cosmarter.client.connect.host";
	private static final String KEY_CLIENT_CONNECT_PORT = "tain.cosmarter.client.connect.port";
	private static final String KEY_CLIENT_COMMAND_01 = "tain.cosmarter.client.command.01";
	private static final String KEY_CLIENT_COMMAND_02 = "tain.cosmarter.client.command.02";
	
	private String strClientDesc = null;
	private String strConnectHost = null;
	private int nConnectPort = -1;
	private String strCommand01 = null;
	private String strCommand02 = null;

	///////////////////////////////////////////////////////////////////////////////////////////////

	private ClientModuleN00001() throws Exception {
		
		if (flag) {
			String clsName = this.getClass().getName();
			
			ResourceBundle rb = ResourceBundle.getBundle(clsName.replace('.', '/'));
			
			this.strClientDesc = rb.getString(KEY_CLIENT_DESC);
			this.strConnectHost = rb.getString(KEY_CLIENT_CONNECT_HOST);
			this.nConnectPort = Integer.parseInt(rb.getString(KEY_CLIENT_CONNECT_PORT));
			this.strCommand01 = rb.getString(KEY_CLIENT_COMMAND_01);
			this.strCommand02 = rb.getString(KEY_CLIENT_COMMAND_02);
		}
		
		if (flag) {
			log.info(">>>>> DESC         : " + this.strClientDesc);
			log.info(">>>>> CONNECT HOST : " + this.strConnectHost);
			log.info(">>>>> CONNECT PORT : " + this.nConnectPort);
		}
	}
	
	public void execute01() throws Exception {
		
		List<String> lstPids = new ArrayList<String>();
		
		if (flag) {
			/*
			 * Single command
			 */
			PrintWriter pw = null;
			BufferedReader br = null;
			String line = null;
			
			Socket socket = new Socket(this.strConnectHost, this.nConnectPort);
			
			pw = new PrintWriter(socket.getOutputStream());
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			log.info(">>>>> COMMAND_01   : " + this.strCommand01);
			pw.println(this.strCommand01);
			pw.flush();
			
			while ((line = br.readLine()) != null) {
				if (flag) log.debug(">>>>> [" + line + "]");
				
				if (flag) {
					String[] words = line.split("\\s+");
					for (String word : words) {
						int idx = word.indexOf("java");
						if (idx > 0) {
							lstPids.add(word.substring(0, idx-1));
							break;
						}
					}
				}
			}
			
			br.close();
			pw.close();
			socket.close();
		}
		
		if (flag) {
			/*
			 * Single command
			 */
			PrintWriter pw = null;
			BufferedReader br = null;
			String line = null;
			
			Socket socket = new Socket(this.strConnectHost, this.nConnectPort);
			
			pw = new PrintWriter(socket.getOutputStream());
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			String strPids = StrUtil.list2str(lstPids);

			this.strCommand02 = this.strCommand02.replaceAll("\\[PID\\]", strPids);
			log.info(">>>>> COMMAND_02   : " + this.strCommand02);
			pw.println(this.strCommand02);
			pw.flush();
			
			while ((line = br.readLine()) != null) {
				if (flag) log.debug(">>>>> [" + line + "]");
				
				if (flag) {
					StringBuffer sb = new StringBuffer();
					String[] words = line.split("\\s+", 8);
					for (String word : words) {
						sb.append("[").append(word).append("]");
					}
					
					log.debug("==> " + sb.toString());
				}
			}
			
			br.close();
			pw.close();
			socket.close();
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private static ClientModuleN00001 instance = null;
	
	public static synchronized ClientModuleN00001 getInstance() throws Exception {
		
		if (instance == null) {
			instance = new ClientModuleN00001();
		}
		
		return instance;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////

	private static void test01(String[] args) throws Exception {
		
		if (flag) {
			ClientModuleN00001.getInstance().execute01();
		}
	}
	
	public static void main(String[] args) throws Exception {
		
		if (flag) log.debug(">>>>> " + new Object(){}.getClass().getEnclosingClass().getName());
		
		if (flag) test01(args);
	}
}
