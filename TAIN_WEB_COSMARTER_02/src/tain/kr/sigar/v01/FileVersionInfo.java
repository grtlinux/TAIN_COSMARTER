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
package tain.kr.sigar.v01;

import java.io.File;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.win32.FileVersion;
import org.hyperic.sigar.win32.Win32;

/**
 * Code Templates > Comments > Types
 *
 * <PRE>
 *   -. FileName   : FileVersionInfo.java
 *   -. Package    : tain.kr.com.test.sigar.v01
 *   -. Comment    :
 *   -. Author     : taincokr
 *   -. First Date : 2017. 3. 17. {time}
 * </PRE>
 *
 * @author taincokr
 *
 */
public final class FileVersionInfo extends SigarCommandBase {

	private static boolean flag = true;

	private static final Logger log = Logger.getLogger(FileVersionInfo.class);

	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * constructor
	 */
	public FileVersionInfo(Shell shell) {
		
		super(shell);
		
		if (!flag)
			log.debug(">>>>> in class " + this.getClass().getSimpleName());
	}

	public FileVersionInfo() {
		super();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	protected boolean validateArgs(String[] args) {
		return args.length >= 1;
	}
	
	@Override
	public String getUsageShort() {
		return "Display file version info";
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////

	/* (non-Javadoc)
	 * @see tain.kr.com.test.sigar.v01.SigarCommandBase#output(java.lang.String[])
	 */
	@Override
	public void output(String[] args) throws SigarException {
		
		for (int i=0; i < args.length; i++) {
			String exe = args[i];
			if (new File(exe).exists()) {
				output(exe);
			} else {
				long[] pids = this.shell.findPids(exe);
				for (int j=0; j < pids.length; j++) {
					try {
						output(sigar.getProcExe(pids[j]).getName());
					} catch (SigarException e) {
						println(exe + ": " + e.getMessage());
					}
				}
			}
		}
	}

	private void output(String key, String val) {
		
		final int max = 20;
		int len = max - key.length();
		
		StringBuffer sb = new StringBuffer();
		sb.append("  ").append(key);
		while (len-- > 0) {
			sb.append('.');
		}
		sb.append(val);
		
		println(sb.toString());
	}
	
	public void output(String exe) throws SigarException {
		
		FileVersion info = Win32.getFileVersion(exe);
		if (info == null) {
			return;
		}
		
		println("Version info for file '" + exe + "':");
		output("FileVersion", info.getFileVersion());
		output("ProductVersion", info.getProductVersion());
		
		for (@SuppressWarnings("unchecked")
		Iterator<Map.Entry<String, String>> it = info.getInfo().entrySet().iterator(); it.hasNext();) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
			output((String) entry.getKey(), (String) entry.getValue());
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

	/*
	 * static test method
	 */
	private static void test01(String[] args) throws Exception {

		if (flag)
			new FileVersionInfo();

		if (flag) {
			/*
			 * begin
			 */
			new FileVersionInfo().processCommand(new String[] { "N:/PROG/jdk1.7.0_79/bin/javaw.exe" });
			//new FileVersionInfo().processCommand(args);
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
