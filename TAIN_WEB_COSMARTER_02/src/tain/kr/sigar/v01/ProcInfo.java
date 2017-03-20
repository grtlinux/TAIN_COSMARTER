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

import org.apache.log4j.Logger;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.SigarPermissionDeniedException;

/**
 * Code Templates > Comments > Types
 *
 * <PRE>
 *   -. FileName   : ProcInfo.java
 *   -. Package    : tain.kr.com.test.sigar.v01
 *   -. Comment    :
 *   -. Author     : taincokr
 *   -. First Date : 2017. 3. 17. {time}
 * </PRE>
 *
 * @author taincokr
 *
 */
public final class ProcInfo extends SigarCommandBase {

	private static boolean flag = true;

	private static final Logger log = Logger.getLogger(ProcInfo.class);

	///////////////////////////////////////////////////////////////////////////////////////////////

	private boolean isSingleProcess;
	
	///////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * constructor
	 */
	public ProcInfo(Shell shell) {
		super(shell);
		if (!flag)
			log.debug(">>>>> in class " + this.getClass().getSimpleName());
	}

	public ProcInfo() {
		super();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////

	protected boolean validateArgs(String[] args) {
		return true;
	}

	public String getUsageShort() {
		return "Display all process info";
	}

	public boolean isPidCompleter() {
		return true;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	public void output(String[] args) throws SigarException {
		
		this.isSingleProcess = false;

		if ((args.length != 0) && args[0].startsWith("-s")) {
			this.isSingleProcess = true;
		}

		if (this.isSingleProcess) {
			for (int i=1; i<args.length; i++) {
				try {
					output(args[i]);
				} catch (SigarException e) {
					println("(" + e.getMessage() + ")");
				}
				
				println("\n------------------------\n");
			}
		} else {
			long[] pids = this.shell.findPids(args);

			for (int i=0; i<pids.length; i++) {
				try {
					output(String.valueOf(pids[i]));
				} catch (SigarPermissionDeniedException e) {
					println(this.shell.getUserDeniedMessage(pids[i]));
				} catch (SigarException e) {
					println("(" + e.getMessage() + ")");
				}
				
				println("\n------------------------\n");
			}
		}
	}

	public void output(String pid) throws SigarException {
		
		println("pid=" + pid);
		
		try {
			println("state=" + sigar.getProcState(pid));
		} catch (SigarException e) {
			if (this.isSingleProcess) {
				println(e.getMessage());
			}
		}
		
		try {
			println("mem=" + sigar.getProcMem(pid));
		} catch (SigarException e) {}
		
		try {
			println("cpu=" + sigar.getProcCpu(pid));
		} catch (SigarException e) {}
		
		try {
			println("cred=" + sigar.getProcCred(pid));
		} catch (SigarException e) {}
		
		try {
			println("credname=" + sigar.getProcCredName(pid));
		} catch (SigarException e) {}
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
			new ProcInfo();

		if (flag) {
			/*
			 * begin
			 */
			new ProcInfo().processCommand(args);
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
