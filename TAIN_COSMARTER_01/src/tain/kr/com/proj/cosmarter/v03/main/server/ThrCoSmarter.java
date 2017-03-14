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
package tain.kr.com.proj.cosmarter.v03.main.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.Charset;

import org.apache.log4j.Logger;

import tain.kr.com.proj.cosmarter.v03.util.CheckSystem;
import tain.kr.com.proj.cosmarter.v03.util.Exec;
import tain.kr.com.proj.cosmarter.v03.util.Param;

/**
 * Code Templates > Comments > Types
 *
 * <PRE>
 *   -. FileName   : ThrCoSmarter.java
 *   -. Package    : tain.kr.com.proj.cosmarter.v03.main.server
 *   -. Comment    :
 *   -. Author     : taincokr
 *   -. First Date : 2017. 3. 15. {time}
 * </PRE>
 *
 * @author taincokr
 *
 */
public final class ThrCoSmarter extends Thread {

	private static boolean flag = true;

	private static final Logger log = Logger.getLogger(ThrCoSmarter.class);

	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final String THR_NAME_FORMAT = "THREAD_COSMARTER_%d";
	
	private final int idxThr;
	private final Socket socket;
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final String KEY_SERVER_CHARSET = "tain.cosmarter.v03.server.charset";
	private final String charSet;
	
	///////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * constructor
	 */
	public ThrCoSmarter(int idxThr, Socket socket) throws Exception {
		
		super(String.format(THR_NAME_FORMAT, idxThr));
		
		this.idxThr = idxThr;
		this.socket = socket;
		
		if (flag) log.debug(String.format(">>>>> in class %s (idxThr=%d) [%s]"
				, this.getClass().getSimpleName(), this.idxThr, this.socket.toString()));
		
		this.charSet = Param.getInstance().getString(KEY_SERVER_CHARSET, "NO_CHARSET");
		if (flag) log.debug(String.format(">>>>> [%s] = [%s]", KEY_SERVER_CHARSET, this.charSet));
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public void run() {
		
		if (flag) {
			/*
			 * thread begin
			 */
			BufferedReader reader = null;
			String line = null;
			
			try {
				reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream(), Charset.forName(this.charSet)));
				
				line = reader.readLine();
				if (flag) log.debug(String.format(">>>>> cmd = [%s].", line));
				
				String[] cmd;
				if (CheckSystem.getInstance().isWindows()) {
					/*
					 * windows
					 */
					cmd = new String[] { "cmd", "/c", line };
				} else {
					/*
					 * linux
					 */
					cmd = new String[] { "/bin/sh", "-c", line };
				}
				
				int ret = Exec.run(cmd, this.socket.getOutputStream());
				if (flag) log.debug(String.format(">>>>> ret = (%d)", ret));
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (this.socket != null) try { this.socket.close(); } catch (IOException e) {}
				if (reader != null) try { reader.close(); } catch (IOException e) {}
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

		if (flag) {

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
