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

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.win32.Service;
import org.hyperic.sigar.win32.Win32Exception;

/**
 * Code Templates > Comments > Types
 *
 * <PRE>
 *   -. FileName   : Win32Service.java
 *   -. Package    : tain.kr.com.test.sigar.v01
 *   -. Comment    :
 *   -. Author     : taincokr
 *   -. First Date : 2017. 3. 17. {time}
 * </PRE>
 *
 * @author taincokr
 *
 */
public final class Win32Service extends SigarCommandBase {

	private static boolean flag = true;

	private static final Logger log = Logger.getLogger(Win32Service.class);

	///////////////////////////////////////////////////////////////////////////////////////////////

	private static final List<String> COMMANDS =
		Arrays.asList(new String[] {
			"state",
			"start",
			"stop",
			"pause",
			"resume",
			"restart",
		});

	///////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * constructor
	 */
	public Win32Service(Shell shell) {
		super(shell);
		if (!flag)
			log.debug(">>>>> in class " + this.getClass().getSimpleName());
	}

	public Win32Service() {
		super();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////

	public String getSyntaxArgs() {
		return "[name] [action]";
	}

	public String getUsageShort() {
		return "Windows service commands";
	}

	protected boolean validateArgs(String[] args) {
		return (args.length == 1) || (args.length == 2);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Collection getCompletions() {
		
		try {
			return Service.getServiceNames();
		} catch (Win32Exception e) {
			return null;
		}
	}

	public void output(String[] args) throws SigarException {
		
		Service service = null;
		String name = args[0];
		String cmd = null;

		if (args.length == 2) {
			cmd = args[1];
		}

		try {
			service = new Service(name);

			if ((cmd == null) || cmd.equals("state")) {
				service.list(this.out);
			} else if (cmd.equals("start")) {
				service.start();
			} else if (cmd.equals("stop")) {
				service.stop();
			} else if (cmd.equals("pause")) {
				service.pause();
			} else if (cmd.equals("resume")) {
				service.resume();
			} else if (cmd.equals("delete")) {
				service.delete();
			} else if (cmd.equals("restart")) {
				service.stop(0);
				service.start();
			} else {
				println("Unsupported service command: " + args[1]);
				println("Valid commands: " + COMMANDS);
			}
		} finally {
			if (service != null) {
				service.close();
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
	///////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * static test method
	 */
	private static void test01(String[] args) throws Exception {

		if (flag) {
			/*
			 * begin
			 */
			new Win32Service().processCommand(args);
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
