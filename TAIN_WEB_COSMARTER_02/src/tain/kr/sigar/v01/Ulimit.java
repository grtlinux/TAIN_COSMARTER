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
import org.hyperic.sigar.ResourceLimit;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.jmx.SigarInvokerJMX;

/**
 * Code Templates > Comments > Types
 *
 * <PRE>
 *   -. FileName   : Ulimit.java
 *   -. Package    : tain.kr.com.test.sigar.v01
 *   -. Comment    :
 *   -. Author     : taincokr
 *   -. First Date : 2017. 3. 17. {time}
 * </PRE>
 *
 * @author taincokr
 *
 */
public final class Ulimit extends SigarCommandBase {

	private static boolean flag = true;

	private static final Logger log = Logger.getLogger(Ulimit.class);

	///////////////////////////////////////////////////////////////////////////////////////////////

	private SigarInvokerJMX invoker;
	private String mode;

	///////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * constructor
	 */
	public Ulimit(Shell shell) {
		super(shell);
		if (!flag)
			log.debug(">>>>> in class " + this.getClass().getSimpleName());
	}
	
	public Ulimit() {
		super();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	public String getUsageShort() {
		return "Display system resource limits";
	}

	protected boolean validateArgs(String[] args) {
		return true;
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

	private static String format(long val) {
		
		if (val == ResourceLimit.INFINITY()) {
			return "unlimited";
		} else {
			return String.valueOf(val);
		}
	}

	private String getValue(String attr) throws SigarException {
		
		Long val = (Long)this.invoker.invoke(attr + this.mode);
		return format(val.longValue());
	}

	public void output(String[] args) throws SigarException {

		this.mode = "Cur";
		this.invoker = SigarInvokerJMX.getInstance(this.proxy, "Type=ResourceLimit");

		for (int i=0; i<args.length; i++) {
			String arg = args[i];
			if (arg.equals("-H")) {
				this.mode = "Max";
			} else if (arg.equals("-S")) {
				this.mode = "Cur";
			} else {
				throw new SigarException("Unknown argument: " + arg);
			}
		}

		println("core file size......." + getValue("Core"));
		println("data seg size........" + getValue("Data"));
		println("file size............" + getValue("FileSize"));
		println("pipe size............" + getValue("PipeSize"));
		println("max memory size......" + getValue("Memory"));
		println("open files..........." + getValue("OpenFiles"));
		println("stack size..........." + getValue("Stack"));
		println("cpu time............." + getValue("Cpu"));
		println("max user processes..." + getValue("Processes"));
		println("virtual memory......." + getValue("VirtualMemory"));
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * static test method
	 */
	private static void test01(String[] args) throws Exception {

		if (flag) {
			/*
			 * begin
			 */
			new Ulimit().processCommand(args);
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
