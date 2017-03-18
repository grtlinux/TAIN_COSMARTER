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

import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hyperic.sigar.SigarException;

/**
 * Code Templates > Comments > Types
 *
 * <PRE>
 *   -. FileName   : ShowEnv.java
 *   -. Package    : tain.kr.com.test.sigar.v01
 *   -. Comment    :
 *   -. Author     : taincokr
 *   -. First Date : 2017. 3. 17. {time}
 * </PRE>
 *
 * @author taincokr
 *
 */
public final class ShowEnv extends SigarCommandBase {

	private static boolean flag = true;

	private static final Logger log = Logger.getLogger(ShowEnv.class);

	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * constructor
	 */
	public ShowEnv(Shell shell) {
		super(shell);
		if (!flag)
			log.debug(">>>>> in class " + this.getClass().getSimpleName());
	}

	public ShowEnv() {
		super();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	protected boolean validateArgs(String[] args) {
		return true;
	}

	public String getUsageShort() {
		return "Show process environment";
	}

	public boolean isPidCompleter() {
		return true;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void output(String[] args) throws SigarException {
		long[] pids = this.shell.findPids(args);

		for (int i=0; i<pids.length; i++) {
			try {
				println("pid=" + pids[i]);
				output(pids[i]);
			} catch (SigarException e) {
				println(e.getMessage());
			}
			println("\n------------------------\n");
		}
	}

	public void output(long pid) throws SigarException {
		
		@SuppressWarnings("unchecked")
		Map<String, String> env = this.proxy.getProcEnv(pid);

		for (Iterator<Map.Entry<String, String>> it = env.entrySet().iterator(); it.hasNext();)
		{
			Map.Entry<String, String> ent = (Map.Entry<String, String>) it.next();

			println(ent.getKey() + "=" + ent.getValue());
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
			new ShowEnv().processCommand(args);
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
