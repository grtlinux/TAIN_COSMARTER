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
import org.hyperic.jni.ArchLoader;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

/**
 * Code Templates > Comments > Types
 *
 * <PRE>
 *   -. FileName   : CpuInfo.java
 *   -. Package    : tain.kr.com.test.sigar.v01
 *   -. Comment    :
 *   -. Author     : taincokr
 *   -. First Date : 2017. 3. 17. {time}
 * </PRE>
 *
 * @author taincokr
 *
 */
public final class CpuInfo extends SigarCommandBase {

	private static boolean flag = true;

	private static final Logger log = Logger.getLogger(CpuInfo.class);

	///////////////////////////////////////////////////////////////////////////////////////////////

	public boolean displayTimes = true;

	///////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * constructor
	 */
	public CpuInfo(Shell shell) {

		super(shell);

		if (flag)
			log.debug(">>>>> in class " + this.getClass().getSimpleName());
	}

	public CpuInfo() {
		super();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	/* (non-Javadoc)
	 * @see org.hyperic.sigar.cmd.SigarCommandBase#output(java.lang.String[])
	 */
	@Override
	public void output(String[] arg0) throws SigarException {
		org.hyperic.sigar.CpuInfo[] infos =
			this.sigar.getCpuInfoList();

		CpuPerc[] cpus =
			this.sigar.getCpuPercList();

		org.hyperic.sigar.CpuInfo info = infos[0];
		long cacheSize = info.getCacheSize();
		println("Vendor........." + info.getVendor());
		println("Model.........." + info.getModel());
		println("Mhz............" + info.getMhz());
		println("Total CPUs....." + info.getTotalCores());
		if ((info.getTotalCores() != info.getTotalSockets()) ||
			(info.getCoresPerSocket() > info.getTotalCores()))
		{
			println("Physical CPUs.." + info.getTotalSockets());
			println("Cores per CPU.." + info.getCoresPerSocket());
		}

		if (cacheSize != Sigar.FIELD_NOTIMPL) {
			println("Cache size...." + cacheSize);
		}
		println("");

		if (!this.displayTimes) {
			return;
		}

		for (int i=0; i<cpus.length; i++) {
			println("CPU " + i + ".........");
			output(cpus[i]);
		}

		println("Totals........");
		output(this.sigar.getCpuPerc());
	}

	private void output(CpuPerc cpu) {
		println("User Time....." + CpuPerc.format(cpu.getUser()));
		println("Sys Time......" + CpuPerc.format(cpu.getSys()));
		println("Idle Time....." + CpuPerc.format(cpu.getIdle()));
		println("Wait Time....." + CpuPerc.format(cpu.getWait()));
		println("Nice Time....." + CpuPerc.format(cpu.getNice()));
		println("Combined......" + CpuPerc.format(cpu.getCombined()));
		println("Irq Time......" + CpuPerc.format(cpu.getIrq()));
		if (ArchLoader.IS_LINUX) {
			println("SoftIrq Time.." + CpuPerc.format(cpu.getSoftIrq()));
			println("Stolen Time...." + CpuPerc.format(cpu.getStolen()));
		}
		println("");
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public String getUsageShort() {
		return "Display cpu information";
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

		if (flag)
			new CpuInfo();

		if (flag) {
			/*
			 * begin
			 */
			new CpuInfo().processCommand(args);

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
