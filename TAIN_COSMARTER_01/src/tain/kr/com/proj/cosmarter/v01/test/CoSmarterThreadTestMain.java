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
package tain.kr.com.proj.cosmarter.v01.test;

import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;

import tain.kr.com.proj.cosmarter.v01.main.server.CoSmarterThread;

/**
 * Code Templates > Comments > Types
 *
 * <PRE>
 *   -. FileName   : CoSmarterThreadTestMain.java
 *   -. Package    : tain.kr.com.proj.cosmarter.v01.test
 *   -. Comment    :
 *   -. Author     : taincokr
 *   -. First Date : 2016. 4. 14. {time}
 * </PRE>
 *
 * @author taincokr
 *
 */
public class CoSmarterThreadTestMain {

	private static boolean flag = true;

	private static final Logger log = Logger.getLogger(CoSmarterThreadTestMain.class);

	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////

	private static void test01(String[] args) throws Exception {
		
		if (flag) {
			int nListenPort = 7412;
			ServerSocket serverSocket = new ServerSocket(nListenPort);
			if (flag) log.debug(String.format("SERVER : listening by port %d [%s]", nListenPort, serverSocket.toString()));
			
			for (int idxThr=0; idxThr <= 100; idxThr ++) {
				if (idxThr > 100)
					idxThr = 0;
				
				Socket socket = serverSocket.accept();
				if (flag) log.debug(String.format("SERVER : accept the connection [%s]", socket));
					
				Thread thr = new CoSmarterThread(123, socket);
				thr.start();
				if (flag) thr.join();  // waiting for finish of thread
			}
			
			serverSocket.close();
		}
	}
	
	public static void main(String[] args) throws Exception {
		
		if (flag) log.debug(">>>>> " + new Object(){}.getClass().getEnclosingClass().getName());
		
		if (flag) test01(args);
	}
}
