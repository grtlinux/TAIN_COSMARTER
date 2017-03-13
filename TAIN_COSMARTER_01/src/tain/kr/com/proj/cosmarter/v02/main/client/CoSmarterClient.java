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
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import tain.kr.com.proj.cosmarter.v02.util.Param;

/**
 * Code Templates > Comments > Types
 *
 * <PRE>
 *   -. FileName   : CoSmarterClient.java
 *   -. Package    : tain.kr.com.proj.cosmarter.v01.main.client
 *   -. Comment    :
 *   -. Author     : taincokr
 *   -. First Date : 2016. 4. 14. {time}
 * </PRE>
 *
 * @author taincokr
 *
 */
public final class CoSmarterClient {

	private static boolean flag = true;

	private static final Logger log = Logger.getLogger(CoSmarterClient.class);

	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final String KEY_CLIENT_DESC = "tain.cosmarter.client.desc";
	private static final String KEY_CLIENT_CONNECT_HOST = "tain.cosmarter.client.connect.host";
	private static final String KEY_CLIENT_CONNECT_PORT = "tain.cosmarter.client.connect.port";
	private static final String KEY_CLIENT_COMMAND = "tain.cosmarter.client.command";
	
	private static final String KEY_READER_CHARSET = "tain.client.reader.charset";
	private static final String KEY_WRITER_CHARSET = "tain.client.writer.charset";

	private String strClientDesc = null;
	private String strConnectHost = null;
	private int nConnectPort = -1;
	private String strCommand = null;
	
	private String strReaderCharset = null;
	private String strWriterCharset = null;

	///////////////////////////////////////////////////////////////////////////////////////////////

	private CoSmarterClient() throws Exception {
		
		if (flag) {
			String clsName = this.getClass().getName();
			
			ResourceBundle rb = ResourceBundle.getBundle(clsName.replace('.', '/'));
			
			this.strClientDesc = rb.getString(KEY_CLIENT_DESC);
			this.strConnectHost = rb.getString(KEY_CLIENT_CONNECT_HOST);
			this.nConnectPort = Integer.parseInt(rb.getString(KEY_CLIENT_CONNECT_PORT));
			this.strCommand = rb.getString(KEY_CLIENT_COMMAND);
		}
		
		if (flag) {
			/*
			 * TODO 2017.03.13 : add charsets of reader and writer
			 */
			this.strReaderCharset = Param.getInstance().getString(KEY_READER_CHARSET, "NO_TYPE");
			this.strWriterCharset = Param.getInstance().getString(KEY_WRITER_CHARSET, "NO_TYPE");
			
			log.info(String.format(">>>>> [%s] = [%s]", KEY_READER_CHARSET, this.strReaderCharset));
			log.info(String.format(">>>>> [%s] = [%s]", KEY_WRITER_CHARSET, this.strWriterCharset));
		}

		if (flag) {
			log.info(">>>>> DESC         : " + this.strClientDesc);
			log.info(">>>>> CONNECT HOST : " + this.strConnectHost);
			log.info(">>>>> CONNECT PORT : " + this.nConnectPort);
			log.info(">>>>> COMMAND      : " + this.strCommand);
		}
	}
	
	public void execute01() throws Exception {
		
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
			
			pw.println("cmd /c dir");
			pw.flush();
			
			while ((line = br.readLine()) != null) {
				if (flag) log.debug(">>>>> [" + line + "]");
			}
			
			socket.close();
		}
		
		if (flag) {
			/*
			 * Single other command
			 */
			PrintWriter pw = null;
			BufferedReader br = null;
			String line = null;
			
			Socket socket = new Socket(this.strConnectHost, this.nConnectPort);
			
			pw = new PrintWriter(socket.getOutputStream());
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			pw.println("cmd /c dir /w");
			pw.flush();
			
			while ((line = br.readLine()) != null) {
				if (flag) log.debug(">>>>> [" + line + "]");
			}
			
			socket.close();
		}
		
		if (flag) {
			/*
			 * Single error command
			 */
			PrintWriter pw = null;
			BufferedReader br = null;
			String line = null;
			
			Socket socket = new Socket(this.strConnectHost, this.nConnectPort);
			
			pw = new PrintWriter(socket.getOutputStream());
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			pw.println("cmd /c ls");
			pw.flush();
			
			while ((line = br.readLine()) != null) {
				if (flag) log.debug(">>>>> [" + line + "]");
			}
			
			socket.close();
		}
		
		if (!flag) {
			/*
			 * Rush Test
			 */
			PrintWriter pw = null;
			BufferedReader br = null;
			String line = null;
			
			for (int idx=0; idx < 1000; idx++) {
				Socket socket = new Socket(this.strConnectHost, this.nConnectPort);
				
				pw = new PrintWriter(socket.getOutputStream());
				br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				
				pw.println("cmd /c dir");
				pw.flush();
				
				while ((line = br.readLine()) != null) {
					if (flag) log.debug(">>>>> [" + line + "]");
				}
				
				socket.close();
				
				if (flag) try { Thread.sleep(200); } catch (InterruptedException e) {}
			}
		}
	}
	
	public void execute02() throws Exception {
		
		if (flag) {
			PrintWriter pw = null;
			BufferedReader br = null;
			String line = null;
			
			Socket socket = new Socket(this.strConnectHost, this.nConnectPort);
			
			pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), Charset.forName(this.strWriterCharset)));
			br = new BufferedReader(new InputStreamReader(socket.getInputStream(), Charset.forName(this.strReaderCharset)));
			
			pw.println(this.strCommand);
			pw.flush();
			
			while ((line = br.readLine()) != null) {
				if (flag) log.debug(">>>>> [" + line + "]");
			}
			
			br.close();
			pw.close();
			socket.close();
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private static CoSmarterClient instance = null;
	
	public static synchronized CoSmarterClient getInstance() throws Exception {
		
		if (CoSmarterClient.instance == null) {
			CoSmarterClient.instance = new CoSmarterClient();
		}
		
		return CoSmarterClient.instance;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////

	private static void test01(String[] args) throws Exception {

		if (flag) {
			CoSmarterClient.getInstance().execute02();
		}
	}
	
	public static void main(String[] args) throws Exception {
		
		if (flag) log.debug(">>>>> " + new Object(){}.getClass().getEnclosingClass().getName());
		
		if (flag) test01(args);
	}
}
