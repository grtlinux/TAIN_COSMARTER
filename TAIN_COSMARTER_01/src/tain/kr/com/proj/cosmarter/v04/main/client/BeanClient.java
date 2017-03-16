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
package tain.kr.com.proj.cosmarter.v04.main.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.Charset;

import org.apache.log4j.Logger;

import tain.kr.com.proj.cosmarter.v04.bean.BeanCommand;
import tain.kr.com.proj.cosmarter.v04.condition.AbsCondition;
import tain.kr.com.proj.cosmarter.v04.util.CheckSystem;
import tain.kr.com.proj.cosmarter.v04.util.Param;

import com.google.gson.Gson;

/**
 * Code Templates > Comments > Types
 *
 * <PRE>
 *   -. FileName   : BeanClient.java
 *   -. Package    : tain.kr.com.proj.cosmarter.v04.main.client
 *   -. Comment    :
 *   -. Author     : taincokr
 *   -. First Date : 2017. 3. 17. {time}
 * </PRE>
 *
 * @author taincokr
 *
 */
public final class BeanClient {

	private static boolean flag = true;

	private static final Logger log = Logger.getLogger(BeanClient.class);

	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final String KEY_CLIENT_CHARSET = "tain.cosmarter.v04.client.charset";
	private static final String KEY_CLIENT_LOGFLAG = "tain.cosmarter.v04.client.log.flag";
	private static final String DEF_CLIENT_CHARSET = "euc-kr";
	//private static final String DEF_CLIENT_LOGFLAG = "false";
	private static final String DEF_CLIENT_LOGFLAG = "true";

	private final String charSet;
	private final boolean flgLog;
	
	///////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * constructor
	 */
	private BeanClient() throws Exception {
		
		this.charSet = Param.getInstance().getString(KEY_CLIENT_CHARSET, DEF_CLIENT_CHARSET);
		
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
		String strFlgLog = Param.getInstance().getString(KEY_CLIENT_LOGFLAG, DEF_CLIENT_LOGFLAG);
		this.flgLog = Boolean.valueOf(strFlgLog).booleanValue();
		
		if (flag) {
			if (flag) log.debug(String.format(">>>>> [%s] = [%s]", KEY_CLIENT_CHARSET, this.charSet));
			if (flag) log.debug(String.format(">>>>> [%s] = [%s]", KEY_CLIENT_LOGFLAG, this.flgLog));
		}

		if (flag)
			log.debug(">>>>> in class " + this.getClass().getSimpleName());
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	public void process(BeanCommand bean) throws Exception {

		if (flag) {
			/*
			 * print CoBean info
			 */
			if (!flag) bean.print();
		}
		
		if (flag) {
			/*
			 * do the job of process in this class
			 */
			Socket socket = new Socket(bean.getHost(), Integer.parseInt(bean.getPort()));
			
			PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), Charset.forName(this.charSet)));
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), Charset.forName(this.charSet)));
			
			if (flag) {
				/*
				 * send the command to the CoSmarter server
				 */
				Gson gson = new Gson();
				String strGson = gson.toJson(bean);
				
				writer.println(strGson);
				writer.flush();
			}
			
			if (flag) {
				/*
				 * recv the result from the CoSmarter server
				 */
				getResult(reader, bean);
			}
			
			reader.close();
			writer.close();
			socket.close();
		}
	}
	
	private void getResult(final BufferedReader reader, final BeanCommand bean) throws Exception {
		
		if (flag) {
			/*
			 * make conditions from skipCmd
			 */
			AbsCondition.setConditions(bean.getSkipCmd());
		}
		
		if (flag) {
			/*
			 * reader
			 */
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
				int lineNum = 0;
				String[] strFldName = bean.getFldName();
				int sizeColumns = strFldName.length;

				while ((line = reader.readLine()) != null) {
					++lineNum;
					
					if (flag && this.flgLog) System.out.printf("B%04d) [%s]\n", lineNum, line);
					
					/*
					 * trim the line
					 */
					line = line.trim();
					
					/*
					 * check the line about the conditions
					 */
					if (!AbsCondition.scanConditions(lineNum, line))
						continue;
					
					if (!flag && this.flgLog) System.out.printf("A%04d) [%s]\n", lineNum, line);
					
					/*
					 * split
					 */
					String[] columns = line.split("\\s+", sizeColumns);
					
					/*
					 * line Json
					 */
					StringBuffer sb = new StringBuffer();
					String strFldValue;
					
					if (flag) sb.append("\t");
					if (!flag) sb.append(String.format("%03d", lineNum));
					sb.append("{");

					for (int i=0; i < sizeColumns; i++) {
						
						if (i < columns.length) {
							strFldValue = columns[i].replace('"', '`').trim();
						} else {
							strFldValue = "";
						}
						
						sb.append("\"").append(strFldName[i]).append("\"").append(":").append("\"").append(strFldValue).append("\"").append(",");
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
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private static BeanClient instance = null;
	
	public static synchronized BeanClient getInstance() throws Exception {
		
		if (BeanClient.instance == null) {
			BeanClient.instance = new BeanClient();
		}
		
		return BeanClient.instance;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * static test method
	 */
	private static void test01(String[] args) throws Exception {

		if (flag) {
			/*
			 * set BeanCommand object
			 */
			BeanCommand bean = new BeanCommand();
			
			bean.setName("testCommand");
			bean.setDesc("test command");
			
			bean.setHost("127.0.0.1");
			bean.setPort("7412");
			
			bean.setCmd(new String[] { "cmd", "/c", "dir" });
			bean.setEnv(new String[] { "PARAM1=hello", "PARAM2=world" });
			bean.setDir("./");
			bean.setArgs(null);
			
			bean.setSkipCmd(new String[] { "W", "L2", "L10", "R3-7", "N오후", "Y오후" });
			bean.setFldName(new String[] { "일자", "구분", "시간", "정보" });
			if (flag) bean.print();
			
			/*
			 * 
			 */
			BeanClient.getInstance().process(bean);
			
			if (flag) log.debug(String.format(">>>>> result <<<<<\n%s\n", bean.getResult()));
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
