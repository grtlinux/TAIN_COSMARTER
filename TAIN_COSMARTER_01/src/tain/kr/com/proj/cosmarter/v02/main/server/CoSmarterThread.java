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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import tain.kr.com.proj.cosmarter.v01.util.CheckSystem;
import tain.kr.com.proj.cosmarter.v01.util.Exec;
import tain.kr.com.proj.cosmarter.v02.util.Param;

/**
 * Code Templates > Comments > Types
 *
 * <PRE>
 *   -. FileName   : CoSmarterThread.java
 *   -. Package    : tain.kr.com.proj.cosmarter.v01.main.server
 *   -. Comment    :
 *   -. Author     : taincokr
 *   -. First Date : 2016. 4. 14. {time}
 * </PRE>
 *
 * @author taincokr
 *
 */
public final class CoSmarterThread extends Thread {

	private static boolean flag = true;

	private static final Logger log = Logger.getLogger(CoSmarterThread.class);

	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private final int idxThr;
	private final Socket socket;

	private static final String KEY_THREAD_DESC = "tain.cosmarter.thread.desc";
	
	private static final String KEY_READER_CHARSET = "tain.server.reader.charset";
	private static final String KEY_WRITER_CHARSET = "tain.server.writer.charset";
	
	private String strThreadDesc = null;

	private String strReaderCharset = null;
	private String strWriterCharset = null;
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public CoSmarterThread(int idxThr, Socket socket) throws Exception {
		
		/*
		 * initialize
		 */
		this.idxThr = idxThr;
		this.socket = socket;

		if (flag) {
			String clsName = this.getClass().getName();
			
			ResourceBundle rb = ResourceBundle.getBundle(clsName.replace('.', '/'));
			
			this.strThreadDesc = rb.getString(KEY_THREAD_DESC);
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
			if (flag) log.info(">>>>> idxThr : " + this.idxThr);
			if (flag) log.info(">>>>> DESC : " + this.strThreadDesc);
		}
	}
	
	public void run() {
		
		if (flag) {
			BufferedReader br = null;
			String line = null;
			
			try {
				/*
				 * TODO 2017.03.10 : add Charset in InputStreamReader
				 */
				br = new BufferedReader(new InputStreamReader(this.socket.getInputStream(), Charset.forName(this.strReaderCharset)));
				
				line = br.readLine();
				if (flag) log.debug("COMMAND IS [" + line + "]");
				
				String[] cmd = null;
				
				if (CheckSystem.getInstance().isWindows()) {
					/*
					 * Windows
					 */
					cmd = new String[] { "cmd", "/c", line };
				} else {
					/*
					 * Unix or Linux
					 */
					cmd = new String[] { "/bin/sh", "-c", line };
				}
				/*
				 * TODO 2017.03.10 : add Charset in OutputStreamWriter
				 */
				log.debug("RET_VAL = " + Exec.run(cmd, new OutputStreamWriter(this.socket.getOutputStream(), Charset.forName(this.strWriterCharset)), true));

			//} catch (InterruptedException e) {
			//	e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (br != null) try { br.close(); } catch (Exception e) {}
				if (socket != null) try { socket.close(); } catch (Exception e) {}
			}
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////

	private static void test01(String[] args) throws Exception {
		
		if (flag) {
			int nListenPort = 7412;
			ServerSocket serverSocket = new ServerSocket(nListenPort);
			if (flag) log.debug(String.format("SERVER : listening by port %d [%s]", nListenPort, serverSocket.toString()));
			
			for (int idxThr=0; flag; idxThr = ++idxThr % 10000) {
				
				Socket socket = serverSocket.accept();
				if (flag) log.debug(String.format("SERVER : accept the connection [%s]", socket));
					
				Thread thr = new CoSmarterThread(idxThr, socket);
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
