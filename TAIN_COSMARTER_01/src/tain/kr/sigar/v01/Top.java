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

import java.util.List;

import org.apache.log4j.Logger;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.ProcCpu;
import org.hyperic.sigar.ProcStat;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.SigarProxy;
import org.hyperic.sigar.SigarProxyCache;

/**
 * Code Templates > Comments > Types
 *
 * <PRE>
 *   -. FileName   : Top.java
 *   -. Package    : tain.kr.com.test.sigar.v01
 *   -. Comment    :
 *   -. Author     : taincokr
 *   -. First Date : 2017. 3. 17. {time}
 * </PRE>
 *
 * @author taincokr
 *
 */
public final class Top {

	private static boolean flag = true;

	private static final Logger log = Logger.getLogger(Top.class);

	///////////////////////////////////////////////////////////////////////////////////////////////

	private static final int SLEEP_TIME = 1000 * 5;

	private static final String HEADER =
		"PID\tUSER\tSTIME\tSIZE\tRSS\tSHARE\tSTATE\tTIME\t%CPU\tCOMMAND";

	///////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * constructor
	 */
	public Top() {
		if (flag)
			log.debug(">>>>> in class " + this.getClass().getSimpleName());
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	private static String toString(ProcStat stat) {
		return
			stat.getTotal()    + " processes: " +
			stat.getSleeping() + " sleeping, " +
			stat.getRunning()  + " running, " +
			stat.getZombie()   + " zombie, " +
			stat.getStopped()  + " stopped... " + stat.getThreads() + " threads";
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

	/*
	 * static test method
	 */
	private static void test01(String[] args) throws Exception {

		if (flag) {
			/*
			 * begin
			 */
			Sigar sigarImpl = new Sigar();

			SigarProxy sigar = SigarProxyCache.newInstance(sigarImpl, SLEEP_TIME);

			while (true) {
				Shell.clearScreen();

				System.out.println(Uptime.getInfo(sigar));

				System.out.println(toString(sigar.getProcStat()));

				System.out.println(sigar.getCpuPerc());

				System.out.println(sigar.getMem());

				System.out.println(sigar.getSwap());

				System.out.println();

				System.out.println(HEADER);

				long[] pids = Shell.getPids(sigar, args);

				for (int i=0; i<pids.length; i++) {
					long pid = pids[i];

					String cpuPerc = "?";

					List<String> info;
					
					try {
						info = Ps.getInfo(sigar, pid);
					} catch (SigarException e) {
						continue; //process may have gone away
					}
					
					try {
						ProcCpu cpu = sigar.getProcCpu(pid);
						cpuPerc = CpuPerc.format(cpu.getPercent());
					} catch (SigarException e) {
					}

					info.add(info.size()-1, cpuPerc);

					System.out.println(Ps.join(info));
				}

				Thread.sleep(SLEEP_TIME);
				SigarProxyCache.clear(sigar);
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
