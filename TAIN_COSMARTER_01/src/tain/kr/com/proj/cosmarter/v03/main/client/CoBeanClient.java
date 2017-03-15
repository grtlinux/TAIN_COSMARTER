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
package tain.kr.com.proj.cosmarter.v03.main.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.Charset;

import org.apache.log4j.Logger;

import tain.kr.com.proj.cosmarter.v03.bean.SimpleBean;
import tain.kr.com.proj.cosmarter.v03.util.CheckSystem;
import tain.kr.com.proj.cosmarter.v03.util.Param;

/**
 * Code Templates > Comments > Types
 *
 * <PRE>
 *   -. FileName   : CoBeanClient.java
 *   -. Package    : tain.kr.com.proj.cosmarter.v03.main.client
 *   -. Comment    :
 *   -. Author     : taincokr
 *   -. First Date : 2017. 3. 15. {time}
 * </PRE>
 *
 * @author taincokr
 *
 */
public final class CoBeanClient {

	private static boolean flag = true;

	private static final Logger log = Logger.getLogger(CoBeanClient.class);

	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final String KEY_CLIENT_HOST = "tain.cosmarter.v03.client.host";
	private static final String KEY_CLIENT_PORT = "tain.cosmarter.v03.client.port";
	
	@SuppressWarnings("unused")
	private final String host;
	@SuppressWarnings("unused")
	private final String port;
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final String KEY_CLIENT_CHARSET = "tain.cosmarter.v03.client.charset";
	private static final String KEY_CLIENT_LOGFLAG = "tain.cosmarter.v03.client.log.flag";

	private final String charSet;
	private final boolean flgLog;
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * constructor
	 */
	private CoBeanClient() throws Exception {
		
		this.host = Param.getInstance().getString(KEY_CLIENT_HOST, "NO_HOST");
		this.port = Param.getInstance().getString(KEY_CLIENT_PORT, "NO_PORT");
		
		this.charSet = Param.getInstance().getString(KEY_CLIENT_CHARSET, "NO_CHARSET");
		
		/*
		 * TODO 2017.03.15 : information of methods of Boolean
		 * 
		 * Boolean.getBoolean("true") -> false
		 * 
		 * tain.flag = true
		 * Boolean.getBoolean("tain.flag") -> true
		 * 
		 * Boolean.valueOf("true").booleanValue() -> true
		 * 
		 */
		String strFlgLog = Param.getInstance().getString(KEY_CLIENT_LOGFLAG, "false");
		this.flgLog = Boolean.valueOf(strFlgLog).booleanValue();
		
		if (flag)
			log.debug(">>>>> in class " + this.getClass().getSimpleName() + " " + this.flgLog);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	public void process(SimpleBean bean) throws Exception {

		if (flag) {
			/*
			 * print CoBean info
			 */
			if (flag) bean.print();
			
			if (flag) log.debug(String.format(">>>>> [%s] = [%s]", KEY_CLIENT_CHARSET, this.charSet));
			if (flag) log.debug(String.format(">>>>> [%s] = [%s]", KEY_CLIENT_LOGFLAG, this.flgLog));
		}
		
		if (flag) {
			/*
			 * do the job of process in this class
			 */
			Socket socket = new Socket(bean.getIpAddr(), Integer.parseInt(bean.getPort()));
			
			PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), Charset.forName(this.charSet)));
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), Charset.forName(this.charSet)));
			
			if (flag) {
				/*
				 * send the command to the CoSmarter server
				 */
				writer.println(bean.getCmd());
				writer.flush();
			}
			
			if (flag) {
				/*
				 * recv the result from the CoSmarter server
				 */
				int skip = Integer.parseInt(bean.getSkip());
				int lineNum = 0;
				int numColumn = bean.getInfoCnt();
				
				StringBuffer sbJson = new StringBuffer();
				
				if (flag) {
					/*
					 * Json header
					 */

					sbJson.append("{").append("\"").append(bean.getName()).append("\"").append(":").append("[");
					if (flag) sbJson.append(CheckSystem.getInstance().getLineSeparator());
				}
				
				if (flag) {
					/*
					 * Json body
					 */
					String line;
					while ((line = reader.readLine()) != null) {
						++lineNum;
						if (lineNum <= skip)
							continue;
						
						if (flag && this.flgLog) System.out.printf("%04d) [%s]\n", lineNum, line);
						
						/*
						 * split
						 */
						String[] columns = line.split("\\s+", numColumn);
						
						/*
						 * line Json
						 */
						StringBuffer sb = new StringBuffer();
						String strKey, strVal;
						
						if (flag) sb.append("\t");
						sb.append("{");

						for (int i=0; i < numColumn; i++) {
							strKey = bean.getInfoName(i);
							if (strKey == null)
								continue;
							
							if (i < columns.length) {
								strVal = columns[i].replace('"', '`').trim();
							} else {
								strVal = "";
							}
							
							sb.append("\"").append(strKey).append("\"").append(":").append("\"").append(strVal).append("\"").append(",");
						}
						
						sb.append("},");
						if (flag) sb.append(CheckSystem.getInstance().getLineSeparator());
						
						if (flag) sbJson.append(sb.toString());
					}
				}
				
				if (flag) {
					/*
					 * Json tail
					 */
					sbJson.append("]}");
					if (flag) sbJson.append(CheckSystem.getInstance().getLineSeparator());
				}
				
				if (flag) {
					/*
					 * transfer Json to the result
					 */
					bean.setResult(sbJson.toString());
				}
			}
			
			reader.close();
			writer.close();
			socket.close();
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
	
	private static CoBeanClient instance = null;
	
	public static CoBeanClient getInstance() throws Exception {
		
		if (CoBeanClient.instance == null) {
			CoBeanClient.instance = new CoBeanClient();
		}
		
		return CoBeanClient.instance;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * static test method
	 */
	private static void test01(String[] args) throws Exception {

		if (flag) {
			/*
			 * begin
			 */
			SimpleBean bean = new SimpleBean();
			if (!flag) bean.print();
			
			bean.setIpAddr("127.0.0.1");
			//bean.setIpAddr("192.168.0.19");
			bean.setPort("7412");
			bean.setName("dirName");
			bean.setCmd("netstat -n | findstr EST");
			bean.setRetInfo("content:1");
			bean.setSkip("0");
			if (!flag) bean.print();
			
			CoBeanClient.getInstance().process(bean);
			
			if (flag) log.debug(String.format(">>>>> result <<<<<\n%s", bean.getResult()));
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
