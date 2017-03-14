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
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.Charset;

import org.apache.log4j.Logger;

import tain.kr.com.proj.cosmarter.v03.util.Param;

/**
 * Code Templates > Comments > Types
 *
 * <PRE>
 *   -. FileName   : MainCoSmarterClient.java
 *   -. Package    : tain.kr.com.proj.cosmarter.v03.main.client
 *   -. Comment    :
 *   -. Author     : taincokr
 *   -. First Date : 2017. 3. 15. {time}
 * </PRE>
 *
 * @author taincokr
 *
 */
public final class MainCoSmarterClient {

	private static boolean flag = true;

	private static final Logger log = Logger
			.getLogger(MainCoSmarterClient.class);

	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final String KEY_CLIENT_HOST = "tain.cosmarter.v03.client.host";
	private static final String KEY_CLIENT_PORT = "tain.cosmarter.v03.client.port";
	
	private final String host;
	private final String port;
	
	private final Socket socket;
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final String KEY_CLIENT_CHARSET = "tain.cosmarter.v03.client.charset";
	private final String charSet;
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * constructor
	 */
	public MainCoSmarterClient() throws Exception {
		
		/*
		 * connection using the host and the port
		 */
		this.host = Param.getInstance().getString(KEY_CLIENT_HOST, "NO_HOST");
		this.port = Param.getInstance().getString(KEY_CLIENT_PORT, "NO_PORT");
		
		this.socket = new Socket(this.host, Integer.parseInt(this.port));
		if (flag) log.debug(String.format(">>>>> [%s:%s] -> connection [%s].", this.host, this.port, this.socket.toString()));
		
		/*
		 * get the charset of client
		 */
		this.charSet = Param.getInstance().getString(KEY_CLIENT_CHARSET, "NO_CHARSET");
		if (flag) log.debug(String.format(">>>>> [%s] = [%s]", KEY_CLIENT_CHARSET, this.charSet));
		
		if (flag)
			log.debug(">>>>> in class " + this.getClass().getSimpleName());
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public void execute01() throws Exception {
		
		if (flag) {
			/*
			 * begin - 1
			 */
			PrintWriter writer = null;
			BufferedReader reader = null;
			String line = null;
			
			try {
				if (flag) {
					/*
					 * create IO objects
					 */
					writer = new PrintWriter(new OutputStreamWriter(this.socket.getOutputStream(), Charset.forName(this.charSet)));
					reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream(), Charset.forName(this.charSet)));
				}
				
				if (flag) {
					/*
					 * send command
					 */
					writer.println("dir");
					writer.flush();
				}
				
				if (flag) {
					/*
					 * recv result
					 */
					while ((line = reader.readLine()) != null) {
						if (flag) System.out.printf("SERVER: %s\n", line);
					}
				}
			} finally {
				if (this.socket != null) try { this.socket.close(); } catch (IOException e) {}
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
	///////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * static test method
	 */
	private static void test01(String[] args) throws Exception {

		if (flag) new MainCoSmarterClient().execute01();
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
