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
import org.hyperic.sigar.CpuTimer;
import org.hyperic.sigar.SigarException;

/**
 * Code Templates > Comments > Types
 *
 * <PRE>
 *   -. FileName   : Time.java
 *   -. Package    : tain.kr.com.test.sigar.v01
 *   -. Comment    :
 *   -. Author     : taincokr
 *   -. First Date : 2017. 3. 17. {time}
 * </PRE>
 *
 * @author taincokr
 *
 */
public final class Time extends SigarCommandBase {

	private static boolean flag = true;

	private static final Logger log = Logger.getLogger(Time.class);

	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * constructor
	 */
	public Time(Shell shell) {
		super(shell);
		if (!flag)
			log.debug(">>>>> in class " + this.getClass().getSimpleName());
	}
	
	public Time() {
		super();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	protected boolean validateArgs(String[] args) {
		return args.length >= 1;
	}

	public String getSyntaxArgs() {
		return "[command] [...]";
	}

	public String getUsageShort() {
		return "Time command";
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	public void output(String[] args) throws SigarException {
		
		boolean isInteractive = this.shell.isInteractive();
		//turn off paging.
		this.shell.setInteractive(false);
		CpuTimer cpu = new CpuTimer(this.sigar);

		int num;

		if (Character.isDigit(args[0].charAt(0))) {
			num = Integer.parseInt(args[0]);
			String[] xargs = new String[args.length-1];
			System.arraycopy(args, 1, xargs, 0, xargs.length);
			args = xargs;
		} else {
			num = 1;
		}

		cpu.start();

		try {
			for (int i=0; i<num; i++) {
				this.shell.handleCommand("time " + args[0], args);
			}
		} finally {
			this.shell.setInteractive(isInteractive);
		}

		cpu.stop();
		cpu.list(this.out);
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

	/*
	 * static test method
	 */
	private static void test01(String[] args) throws Exception {

		if (flag) {
			/*
			 * begin
			 */
			new Time().processCommand(args);
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
