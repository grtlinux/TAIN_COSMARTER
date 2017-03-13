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
package tain.kr.com.proj.cosmarter.v02.main.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import tain.kr.com.proj.cosmarter.v02.util.Param;

/**
 * Code Templates > Comments > Types
 *
 * <PRE>
 *   -. FileName   : CoSmarterServer.java
 *   -. Package    : tain.kr.com.proj.cosmarter.v01.main.server
 *   -. Comment    :
 *   -. Author     : taincokr
 *   -. First Date : 2016. 4. 14. {time}
 * </PRE>
 *
 * @author taincokr
 *
 */
public final class CoSmarterServer {

	private static boolean flag = true;

	private static final Logger log = Logger.getLogger(CoSmarterServer.class);

	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final String KEY_SERVER_DESC = "tain.cosmarter.server.desc";
	private static final String KEY_SERVER_LISTEN_PORT = "tain.cosmarter.server.listen.port";
	
	private String strServerDesc = null;
	private int nListenPort = -1;
	
	///////////////////////////////////////////////////////////////////////////////////////////////

	private CoSmarterServer() throws Exception {
		
		if (flag) {
			String clsName = this.getClass().getName();
			
			ResourceBundle rb = ResourceBundle.getBundle(clsName.replace('.', '/'));
			
			this.strServerDesc = rb.getString(KEY_SERVER_DESC);
			this.nListenPort = Integer.parseInt(rb.getString(KEY_SERVER_LISTEN_PORT));
		}
		
		if (flag) {
			log.info(">>>>> DESC : " + this.strServerDesc);
			log.info(">>>>> LISTEN PORT : " + this.nListenPort);
		}
	}
	
	public void execute() throws Exception {
		
		if (flag) {
			/*
			 * 1st socket program
			 */
			@SuppressWarnings("resource")
			ServerSocket serverSocket = new ServerSocket(this.nListenPort);
			if (flag) log.debug(String.format("SERVER : listening by port %d [%s]", nListenPort, serverSocket.toString()));
			
			for (int idxThr = 0; ; idxThr = ++idxThr % 10000) {
				
				Socket socket = serverSocket.accept();
				if (!flag) socket.setSoLinger(true, 1);
				if (flag) log.debug(String.format("SERVER : accept the connection(%d)", idxThr));
				
				Thread thr = new CoSmarterThread(idxThr, socket);
				thr.start();
				
				if (flag) thr.join();  // waiting for finish of thread
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
	
	private static CoSmarterServer instance = null;
	
	public static synchronized CoSmarterServer getInstance() throws Exception {
		
		if (CoSmarterServer.instance == null) {
			CoSmarterServer.instance = new CoSmarterServer();
		}
		
		return CoSmarterServer.instance;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////

	private static void test01(String[] args) throws Exception {
		
		if (flag) {
			int i = 0;
			for (String arg : args) {
				if (flag) log.debug(String.format("(%d) [%s]", i++, arg));
			}
		}
		
		if (flag) {
			String name = System.getProperty("tain.name", "NO NAME");
			if (flag) log.debug(String.format("[tain.name] = [%s]", name));
			
			if (flag) log.debug(String.format(">>>>> [%s] = [%s]", "tain.reader.charset", Param.getInstance().getString("tain.reader.charset")));
			if (flag) log.debug(String.format(">>>>> [%s] = [%s]", "tain.writer.charset", Param.getInstance().getString("tain.writer.charset")));
		}
		
		if (!flag) {
			CoSmarterServer.getInstance().execute();
		}
	}
	
	public static void main(String[] args) throws Exception {
		
		if (flag) log.debug(">>>>> " + new Object(){}.getClass().getEnclosingClass().getName());
		
		if (flag) test01(args);
	}
}
