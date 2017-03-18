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

import org.apache.log4j.Logger;
import org.hyperic.sigar.NetFlags;
import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.NetInterfaceStat;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

/**
 * Code Templates > Comments > Types
 *
 * <PRE>
 *   -. FileName   : Ifconfig.java
 *   -. Package    : tain.kr.com.test.sigar.v01
 *   -. Comment    :
 *   -. Author     : taincokr
 *   -. First Date : 2017. 3. 17. {time}
 * </PRE>
 *
 * @author taincokr
 *
 */
public final class Ifconfig extends SigarCommandBase {

	private static boolean flag = true;

	private static final Logger log = Logger.getLogger(Ifconfig.class);

	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * constructor
	 */
	public Ifconfig(Shell shell) {
		super(shell);
		if (!flag)
			log.debug(">>>>> in class " + this.getClass().getSimpleName());
	}
	
	public Ifconfig() {
		super();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	protected boolean validateArgs(String[] args) {
		return args.length <= 1;
	}
	
	public String getSyntaxArgs() {
		return "[interface]";
	}
	
	public String getUsageShort() {
		return "Network interface information";
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Collection getCompletions() {
		String[] ifNames;
		
		try {
			ifNames = this.proxy.getNetInterfaceList();
		} catch (SigarException e) {
			return super.getCompletions();
		}
		
		return Arrays.asList(ifNames);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////

	/* (non-Javadoc)
	 * @see tain.kr.com.test.sigar.v01.SigarCommandBase#output(java.lang.String[])
	 */
	@Override
	public void output(String[] args) throws SigarException {
		
		String[] ifNames;
		
		if (args.length == 1) {
			ifNames = args;
		} else {
			ifNames = this.proxy.getNetInterfaceList();
		}
		
		for (int i=0; i < ifNames.length; i++) {
			try {
				output(ifNames[i]);
			} catch (SigarException e) {
				println(ifNames[i] + "\t" + e.getMessage());
			}
		}
	}
	
	public void output(String name) throws SigarException {
		
		NetInterfaceConfig ifconfig = this.sigar.getNetInterfaceConfig(name);
		long flags = ifconfig.getFlags();
		
		String hwaddr = "";
		if (!NetFlags.NULL_HWADDR.equals(ifconfig.getHwaddr())) {
			hwaddr = " HWaddr " + ifconfig.getHwaddr();
		}
		
		if (!ifconfig.getName().equals(ifconfig.getDescription())) {
			println(ifconfig.getDescription());
		}
		
		println(ifconfig.getName() + "\t" + "Link encap:" + ifconfig.getType() + hwaddr);
		
		String ptp = "";
		if ((flags & NetFlags.IFF_POINTOPOINT) > 0) {
			ptp = "  P-t-P:" + ifconfig.getDescription();
		}
		
		String bcast = "";
		if ((flags & NetFlags.IFF_BROADCAST) > 0) {
			bcast = "  Bcast:" + ifconfig.getBroadcast();
		}
		
		println("\t" + "inet addr:" + ifconfig.getAddress() + ptp + bcast + "  Mask:" + ifconfig.getNetmask());
		
		println("\t" + NetFlags.getIfFlagsString(flags) + " MTU:" + ifconfig.getMtu() + "  Metric:" + ifconfig.getMetric());
		
		try {
			NetInterfaceStat ifstat = this.sigar.getNetInterfaceStat(name);
			
			println("\t" +
					"RX packets:" + ifstat.getRxPackets() +
					" errors:" + ifstat.getRxErrors() +
					" dropped:" + ifstat.getRxDropped() +
					" overruns:" + ifstat.getRxOverruns() +
					" frame:" + ifstat.getRxFrame());

			println("\t" +
					"TX packets:" + ifstat.getTxPackets() +
					" errors:" + ifstat.getTxErrors() +
					" dropped:" + ifstat.getTxDropped() +
					" overruns:" + ifstat.getTxOverruns() +
					" carrier:" + ifstat.getTxCarrier());
			
			println("\t" + "collisions:" + ifstat.getTxCollisions());
			
			long rxBytes = ifstat.getRxBytes();
			long txBytes = ifstat.getTxBytes();
			
			println("\t" +
					"RX bytes:" + rxBytes +
					" (" + Sigar.formatSize(rxBytes) + ")" +
					"  " +
					"TX bytes:" + txBytes +
					" (" + Sigar.formatSize(txBytes) + ")");
			
		} catch (SigarException e) {
		}
		
		println("");
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

		if (flag) {
			/*
			 * begin
			 */
			new Ifconfig().processCommand(args);
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
