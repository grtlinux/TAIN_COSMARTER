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
package tain.kr.com.proj.cosmarter.v02.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

import org.apache.log4j.Logger;

/**
 * Code Templates > Comments > Types
 *
 * <PRE>
 *   -. FileName   : Exec.java
 *   -. Package    : tain.kr.com.proj.cosmarter.v02.util
 *   -. Comment    :
 *   -. Author     : taincokr
 *   -. First Date : 2017. 3. 12. {time}
 * </PRE>
 *
 * @author taincokr
 *
 */
public final class Exec {

	private static boolean flag = true;

	private static final Logger log = Logger.getLogger(Exec.class);

	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private final static String KEY_PROCESS_CHARSET = "tain.exec.charset";
	private static String charSet;
	static {
		try {
			Exec.charSet = Param.getInstance().getString(KEY_PROCESS_CHARSET, "NO_TYPE");
			if (flag) System.out.printf(">>>>> [%s] = [%s]\n", KEY_PROCESS_CHARSET, Exec.charSet);
		} catch (Exception e) {}
	}
	
	/*
	 * need a Runtime object for any of these methods in this class
	 */
	private final static Runtime rt = Runtime.getRuntime();
	
	///////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * constructor
	 */
	public Exec() {
		
		/*
		 * nothing to do
		 */
		if (flag)
			log.debug(">>>>> in class " + this.getClass().getSimpleName());
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public static int run(String cmd, Writer writer, boolean flgOsClose) throws IOException {
		
		Process process = rt.exec(cmd);
		
		FileIO.copyFile(new InputStreamReader(process.getInputStream(), Charset.forName(Exec.charSet)), writer, flgOsClose);
		
		try {
			process.waitFor();
		} catch (InterruptedException e) {
			return -1;
		}
		
		return process.exitValue();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public static int run(String cmd, boolean flgOsClose) throws IOException {
		
		return run(cmd, new OutputStreamWriter(System.out, Charset.forName(Exec.charSet)), flgOsClose);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public static int run(String cmd, Writer writer) throws IOException {
		
		return run(cmd, writer, true);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public static int run(String cmd) throws IOException {
		
		return run(cmd, new OutputStreamWriter(System.out, Charset.forName(Exec.charSet)), true);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public static int run(String[] cmd, Writer writer, boolean flgOsClose) throws IOException {
		
		Process process = rt.exec(cmd);
		
		FileIO.copyFile(new InputStreamReader(process.getInputStream(), Charset.forName(Exec.charSet)), writer, flgOsClose);
		
		try {
			process.waitFor();
		} catch (InterruptedException e) {
			return -1;
		}
		
		return process.exitValue();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public static int run(String[] cmd, boolean flgOsClose) throws IOException {
		
		return run(cmd, new OutputStreamWriter(System.out, Charset.forName(Exec.charSet)), flgOsClose);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public static int run(String[] cmd, Writer writer) throws IOException {
		
		return run(cmd, writer, true);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public static int run(String[] cmd) throws IOException {
		
		return run(cmd, new OutputStreamWriter(System.out, Charset.forName(Exec.charSet)), true);
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
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * static test method
	 */
	private static void test01(String[] args) throws Exception {

		if (flag)
			new Exec();

		if (flag) {
			/*
			 * begin
			 */
			if (!CheckSystem.getInstance().isWindows()) {
				log.debug(">>>>> This system is not windows system.");
				return;
			}
			
			if (flag) {
				/*
				 * cmd = String object
				 */
				String cmd = "cmd /c dir";
				log.debug(">>>>> String");
				log.debug(">>>>> ret = " + Exec.run(cmd, false));  // System.out not be closed
				// log.debug(">>>>> ret = " + Exec.run(cmd));  // System.out be closed
			}
			
			if (flag) {
				/*
				 * cmd = String[] object
				 */
				String[] cmd = { "cmd", "/c", "dir" };
				log.debug(">>>>> String[]");
				log.debug(">>>>> ret = " + Exec.run(cmd, false));
				// log.debug(">>>>> ret = " + Exec.run(cmd));
			}
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
