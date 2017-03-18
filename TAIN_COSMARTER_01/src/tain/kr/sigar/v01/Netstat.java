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

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.hyperic.sigar.NetConnection;
import org.hyperic.sigar.NetFlags;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.Tcp;

/**
 * Code Templates > Comments > Types
 *
 * <PRE>
 *   -. FileName   : Netstat.java
 *   -. Package    : tain.kr.com.test.sigar.v01
 *   -. Comment    :
 *   -. Author     : taincokr
 *   -. First Date : 2017. 3. 17. {time}
 * </PRE>
 *
 * @author taincokr
 *
 */
public final class Netstat extends SigarCommandBase {

	private static boolean flag = true;

	private static final Logger log = Logger.getLogger(Netstat.class);

	///////////////////////////////////////////////////////////////////////////////////////////////

	private static final int LADDR_LEN = 20;
	private static final int RADDR_LEN = 35;

	private static final String[] HEADER = new String[] {
		"Proto",
		"Local Address",
		"Foreign Address",
		"State",
		""
	};

	private static boolean isNumeric, wantPid, isStat;

	///////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * constructor
	 */
	public Netstat(Shell shell) {
		super(shell);
		if (!flag)
			log.debug(">>>>> in class " + this.getClass().getSimpleName());
	}

	public Netstat() {
		super();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////

	protected boolean validateArgs(String[] args) {
		return true;
	}

	public String getUsageShort() {
		return "Display network connections";
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	//poor mans getopt.
	public static int getFlags(String[] args, int flags) {
		
		int proto_flags = 0;
		isNumeric = false;
		wantPid = false;
		isStat = false;

		for (int i=0; i<args.length; i++) {
			String arg = args[i];
			int j = 0;

			while (j<arg.length()) {
				switch (arg.charAt(j++)) {
				case '-':
					continue;
				case 'l':
					flags &= ~NetFlags.CONN_CLIENT;
					flags |= NetFlags.CONN_SERVER;
					break;
				case 'a':
					flags |= NetFlags.CONN_SERVER | NetFlags.CONN_CLIENT;
					break;
				case 'n':
					isNumeric = true;
					break;
				case 'p':
					wantPid = true;
					break;
				case 's':
					isStat = true;
					break;
				case 't':
					proto_flags |= NetFlags.CONN_TCP;
					break;
				case 'u':
					proto_flags |= NetFlags.CONN_UDP;
					break;
				case 'w':
					proto_flags |= NetFlags.CONN_RAW;
					break;
				case 'x':
					proto_flags |= NetFlags.CONN_UNIX;
					break;
				default:
					System.err.println("unknown option");
				}
			}
		}

		if (proto_flags != 0) {
			flags &= ~NetFlags.CONN_PROTOCOLS;
			flags |= proto_flags;
		}

		return flags;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	private String formatPort(int proto, long port) {
		
		if (port == 0) {
			return "*";
		}
		
		if (!isNumeric) {
			String service = this.sigar.getNetServicesName(proto, port);
			if (service != null) {
				return service;
			}
		}
		
		return String.valueOf(port);
	}

	private String formatAddress(int proto, String ip, long portnum, int max) {

		String port = formatPort(proto, portnum);
		String address;

		if (NetFlags.isAnyAddress(ip)) {
			address = "*";
		} else if (isNumeric) {
			address = ip;
		} else {
			try {
				address = InetAddress.getByName(ip).getHostName();
			} catch (UnknownHostException e) {
				address = ip;
			}
		}

		max -= port.length() + 1;
		if (address.length() > max) {
			address = address.substring(0, max);
		}

		return address + ":" + port;
	}

	private void outputTcpStats() throws SigarException {
		
		Tcp stat = this.sigar.getTcp();
		final String dnt = "    ";
		
		println(dnt + stat.getActiveOpens() + " active connections openings");
		println(dnt + stat.getPassiveOpens() + " passive connection openings");
		println(dnt + stat.getAttemptFails() + " failed connection attempts");
		println(dnt + stat.getEstabResets() + " connection resets received");
		println(dnt + stat.getCurrEstab() + " connections established");
		println(dnt + stat.getInSegs() + " segments received");
		println(dnt + stat.getOutSegs() + " segments send out");
		println(dnt + stat.getRetransSegs() + " segments retransmited");
		println(dnt + stat.getInErrs() + " bad segments received.");
		println(dnt + stat.getOutRsts() + " resets sent");
	}

	private void outputStats(int flags) throws SigarException {
		
		if ((flags & NetFlags.CONN_TCP) != 0) {
			println("Tcp:");
			try {
				outputTcpStats();
			} catch (SigarException e) {
				println("    " + e);
			}
		}
	}

	// XXX currently weak sauce.  should end up like netstat command.
	public void output(String[] args) throws SigarException {
		
		//default
		int flags = NetFlags.CONN_CLIENT | NetFlags.CONN_PROTOCOLS;

		if (args.length > 0) {
			flags = getFlags(args, flags);
			if (isStat) {
				outputStats(flags);
				return;
			}
		}

		NetConnection[] connections = this.sigar.getNetConnectionList(flags);
		printf(HEADER);

		for (int i=0; i<connections.length; i++) {
			NetConnection conn = connections[i];
			String proto = conn.getTypeString();
			String state;

			if (conn.getType() == NetFlags.CONN_UDP) {
				state = "";
			} else {
				state = conn.getStateString();
			}

			ArrayList<String> items = new ArrayList<String>();
			items.add(proto);
			items.add(formatAddress(conn.getType(),
									conn.getLocalAddress(),
									conn.getLocalPort(),
									LADDR_LEN));
			items.add(formatAddress(conn.getType(),
									conn.getRemoteAddress(),
									conn.getRemotePort(),
									RADDR_LEN));
			items.add(state);

			String process = null;
			if (wantPid &&
				// XXX only works w/ listen ports
				(conn.getState() == NetFlags.TCP_LISTEN))
			{
				try {
					long pid = this.sigar.getProcPort(conn.getType(), conn.getLocalPort());
					if (pid != 0) { // XXX another bug
						String name = this.sigar.getProcState(pid).getName();
						process = pid + "/" + name;
					}
				} catch (SigarException e) {
				}
			}

			if (process == null) {
				process = "";
			}

			items.add(process);

			printf(items);
		}
		
		flush();
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

	/*
	 * static test method
	 */
	private static void test01(String[] args) throws Exception {

		if (flag) {
			/*
			 * begin
			 */
			new Netstat().processCommand(args);
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
