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

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.hyperic.sigar.NetFlags;
import org.hyperic.sigar.NetRoute;
import org.hyperic.sigar.SigarException;

/**
 * Code Templates > Comments > Types
 *
 * <PRE>
 *   -. FileName   : Route.java
 *   -. Package    : tain.kr.com.test.sigar.v01
 *   -. Comment    :
 *   -. Author     : taincokr
 *   -. First Date : 2017. 3. 17. {time}
 * </PRE>
 *
 * @author taincokr
 *
 */
public final class Route extends SigarCommandBase {

	private static boolean flag = true;

	private static final Logger log = Logger.getLogger(Route.class);

	///////////////////////////////////////////////////////////////////////////////////////////////

	private static final String OUTPUT_FORMAT =
		"%-15s %-15s %-15s %-5s %-6s %-3s %-s";

	//like route -n
	private static final String[] HEADER = new String[] {
		"Destination",
		"Gateway",
		"Genmask",
		"Flags",
		"Metric",
		"Ref",
		"Iface"
	};

	///////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * constructor
	 */
	public Route(Shell shell) {
		super(shell);
		setOutputFormat(OUTPUT_FORMAT);
		
		if (!flag)
			log.debug(">>>>> in class " + this.getClass().getSimpleName());
	}

	public Route() {
		super();
		setOutputFormat(OUTPUT_FORMAT);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	//netstat -r
	public void output(String[] args) throws SigarException {
		NetRoute[] routes = this.sigar.getNetRouteList();

		printf(HEADER);

		for (int i=0; i<routes.length; i++) {
			NetRoute route = routes[i];

			ArrayList<String> items = new ArrayList<String>();
			items.add(route.getDestination());
			items.add(route.getGateway());
			items.add(route.getMask());
			items.add(flags(route.getFlags()));
			items.add(String.valueOf(route.getMetric()));
			items.add(String.valueOf(route.getRefcnt()));
			items.add(route.getIfname());

			printf(items);
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

	private static String flags(long flags) {
		StringBuffer sb = new StringBuffer();
		if ((flags & NetFlags.RTF_UP) != 0) {
			sb.append('U');
		}
		if ((flags & NetFlags.RTF_GATEWAY) != 0) {
			sb.append('G');
		}
		return sb.toString();
	}

	public String getUsageShort() {
		return "Kernel IP routing table";
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
			new Route().processCommand(args);
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
