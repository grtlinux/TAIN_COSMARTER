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
package tain.kr.com.proj.cosmarter.v03.util;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.log4j.Logger;

/**
 * Code Templates > Comments > Types
 *
 * <PRE>
 *   -. FileName   : Exec.java
 *   -. Package    : tain.kr.com.proj.cosmarter.v03.util
 *   -. Comment    :
 *   -. Author     : taincokr
 *   -. First Date : 2017. 3. 15. {time}
 * </PRE>
 *
 * @author taincokr
 *
 */
public final class Exec {

	private static boolean flag = true;

	private static final Logger log = Logger.getLogger(Exec.class);

	///////////////////////////////////////////////////////////////////////////////////////////////
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
	
	/*
	 * need a Runtime object for any of these methods in this class
	 */
	private final static Runtime run = Runtime.getRuntime();
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public static int run(String cmd, String[] envp, File dir, OutputStream os, boolean flgOsClose) throws IOException {
		
		Process process = run.exec(cmd, envp, dir);
		
		FileIO.copyFile(process.getInputStream(), os, flgOsClose);
		
		try {
			process.waitFor();
		} catch (InterruptedException e) {
			
			FileIO.copyFile(process.getErrorStream(), os, flgOsClose);
			
			return -1;
		}
		
		return process.exitValue();
	}
	
	public static int run(String cmd, OutputStream os, boolean flaOsClose) throws IOException {
	
		return run(cmd, null, null, os, flaOsClose);
	}
	
	public static int run(String cmd, OutputStream os) throws IOException {
		
		return run(cmd, null, null, os, true);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public static int run(String[] cmd, String[] envp, File dir, OutputStream os, boolean flgOsClose) throws IOException {
		
		Process process = run.exec(cmd, envp, dir);
		
		FileIO.copyFile(process.getInputStream(), os, flgOsClose);
		
		try {
			process.waitFor();
		} catch (InterruptedException e) {
			
			FileIO.copyFile(process.getErrorStream(), os, flgOsClose);
			
			return -1;
		}
		
		return process.exitValue();
	}
	
	public static int run(String[] cmd, OutputStream os, boolean flaOsClose) throws IOException {
		
		return run(cmd, null, null, os, flaOsClose);
	}
	
	public static int run(String[] cmd, OutputStream os) throws IOException {
		
		return run(cmd, null, null, os, true);
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
			String cmd = "cmd /c dir";
			
			int ret = Exec.run(cmd, System.out, false);
			if (flag) log.debug(String.format(">>>>> ret = (%d)", ret));
		}
		
		if (flag) {
			String cmd = "netstat -n";
			
			int ret = Exec.run(cmd, System.out, false);
			if (flag) log.debug(String.format(">>>>> ret = (%d)", ret));
		}
		
		if (flag) {
			/*
			 * working dir
			 */
			File dir = new File("N:/TEMP");
			String cmd = "cmd /c dir";
			
			int ret = Exec.run(cmd, null, dir, System.out, false);
			if (flag) log.debug(String.format(">>>>> ret = (%d)", ret));
		}
		
		if (flag) {
			/*
			 * type command
			 */
			File dir = new File("N:/TEMP");
			String cmd = "cmd /c type TEXT_ECU-KR.txt";
			
			int ret = Exec.run(cmd, null, dir, System.out, false);
			if (flag) log.debug(String.format(">>>>> ret = (%d)", ret));
			
			cmd = "cmd /c type TEXT_UTF-8.txt";
			
			ret = Exec.run(cmd, null, dir, System.out, false);
			if (flag) log.debug(String.format(">>>>> ret = (%d)", ret));
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