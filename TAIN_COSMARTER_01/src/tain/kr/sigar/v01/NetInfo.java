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
import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.SigarException;

/**
 * Code Templates > Comments > Types
 *
 * <PRE>
 *   -. FileName   : NetInfo.java
 *   -. Package    : tain.kr.com.test.sigar.v01
 *   -. Comment    :
 *   -. Author     : taincokr
 *   -. First Date : 2017. 3. 17. {time}
 * </PRE>
 *
 * @author taincokr
 *
 */
public final class NetInfo extends SigarCommandBase {

	private static boolean flag = true;

	private static final Logger log = Logger.getLogger(NetInfo.class);

	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * constructor
	 */
	public NetInfo(Shell shell) {
		super(shell);
		if (!flag)
			log.debug(">>>>> in class " + this.getClass().getSimpleName());
	}

	public NetInfo() {
		super();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public String getUsageShort() {
		return "Display network info";
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public void output(String[] args) throws SigarException {
		
		NetInterfaceConfig config = this.sigar.getNetInterfaceConfig(null);
		
		println("primary interface....." + config.getName());

		println("primary ip address...." + config.getAddress());

		println("primary mac address..." + config.getHwaddr());

		println("primary netmask......." + config.getNetmask());

		org.hyperic.sigar.NetInfo info = this.sigar.getNetInfo();

		println("host name............." + info.getHostName());

		println("domain name..........." + info.getDomainName());

		println("default gateway......." + info.getDefaultGateway());

		println("primary dns..........." + info.getPrimaryDns());

		println("secondary dns........." + info.getSecondaryDns());
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
			new NetInfo().processCommand(args);
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
