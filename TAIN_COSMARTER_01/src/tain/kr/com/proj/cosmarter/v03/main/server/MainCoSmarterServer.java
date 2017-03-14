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

import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;

import tain.kr.com.proj.cosmarter.v02.util.Param;

/**
 * Code Templates > Comments > Types
 *
 * <PRE>
 *   -. FileName   : MainCoSmarterServer.java
 *   -. Package    : tain.kr.com.proj.cosmarter.v03.main.server
 *   -. Comment    :
 *   -. Author     : taincokr
 *   -. First Date : 2017. 3. 15. {time}
 * </PRE>
 *
 * @author taincokr
 *
 */
public final class MainCoSmarterServer {

	private static boolean flag = true;

	private static final Logger log = Logger
			.getLogger(MainCoSmarterServer.class);

	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final String KEY_SERVER_PORT = "tain.cosmarter.v03.server.port";
	
	private final String port;
	
	///////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * constructor
	 */
	private MainCoSmarterServer() throws Exception {
		
		this.port = Param.getInstance().getString(KEY_SERVER_PORT, "NO_SERVER_PORT");
		
		if (flag)
			log.debug(">>>>> in class " + this.getClass().getSimpleName());
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public void execute() throws Exception {
		
		if (flag) {
			/*
			 * listening server port
			 */
			@SuppressWarnings("resource")
			ServerSocket serverSocket = new ServerSocket(Integer.parseInt(this.port));
			if (flag) log.debug(String.format(">>>>> SERVER: listening by server port '%s' [%s]", this.port, serverSocket.toString()));
			
			for (int idxThr = 0; ; idxThr = ++idxThr % 10000) {
				
				Socket socket = serverSocket.accept();
				if (flag) log.debug(String.format(">>>>> SERVER: accept the connection (%d) from client", idxThr));
				
				Thread thread = new ThrCoSmarter(idxThr, socket);
				thread.start();
				
				if (flag) thread.join();   // waiting for the finish of the thread
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
	
	private static MainCoSmarterServer instance = null;
	
	public static synchronized MainCoSmarterServer getInstance() throws Exception {
		
		if (MainCoSmarterServer.instance == null) {
			MainCoSmarterServer.instance = new MainCoSmarterServer();
		}
		
		return MainCoSmarterServer.instance;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * static test method
	 */
	private static void test01(String[] args) throws Exception {

		if (flag)
			new MainCoSmarterServer();

		if (flag) {
			/*
			 * begin
			 */
			MainCoSmarterServer.getInstance().execute();
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
