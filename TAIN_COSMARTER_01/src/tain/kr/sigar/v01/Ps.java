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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hyperic.sigar.ProcCredName;
import org.hyperic.sigar.ProcMem;
import org.hyperic.sigar.ProcState;
import org.hyperic.sigar.ProcTime;
import org.hyperic.sigar.ProcUtil;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.SigarProxy;

/**
 * Code Templates > Comments > Types
 *
 * <PRE>
 *   -. FileName   : Ps.java
 *   -. Package    : tain.kr.com.test.sigar.v01
 *   -. Comment    :
 *   -. Author     : taincokr
 *   -. First Date : 2017. 3. 17. {time}
 * </PRE>
 *
 * @author taincokr
 *
 */
public final class Ps extends SigarCommandBase {

	private static boolean flag = true;

	private static final Logger log = Logger.getLogger(Ps.class);

	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * constructor
	 */
	public Ps(Shell shell) {
		super(shell);
		if (!flag)
			log.debug(">>>>> in class " + this.getClass().getSimpleName());
	}
	
	public Ps() {
		super();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	
	protected boolean validateArgs(String[] args) {
		return true;
	}
	
	public String getSyntaxArgs() {
		return "[pid|query]";
	}
	
	public String getUsageShort() {
		return "Show process status";
	}
	
	public boolean isPidCompleter() {
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public void output(String[] args) throws SigarException {
		
		long[] pids;
		if (args.length == 0) {
			pids = this.proxy.getProcList();
		} else {
			pids = this.shell.findPids(args);
		}

		for (int i=0; i < pids.length; i++) {
			long pid = pids[i];
			try {
				output(pid);
			} catch (SigarException e) {
				this.err.println("Exception getting process info for " + pid + ": " + e.getMessage());
			}
		}
	}
	
	public void output(long pid) throws SigarException {
		
		println(join(getInfo(this.proxy, pid)));
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////

	public static String join(List<String> infos) {
		
		StringBuffer sb = new StringBuffer();
		
		int i = 0;
		for (String info : infos) {
			if (i != 0)
				sb.append("\t");
			sb.append(info);
			i++;
		}
		
		return sb.toString();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	@SuppressWarnings("deprecation")
	public static List<String> getInfo(SigarProxy sigar, long pid) throws SigarException {
		
		ProcState state = sigar.getProcState(pid);
		ProcTime time = null;
		String unknown = "???";

		List<String> info = new ArrayList<String>();
		info.add(String.valueOf(pid));

		try {
			ProcCredName cred = sigar.getProcCredName(pid);
			info.add(cred.getUser());
		} catch (SigarException e) {
			info.add(unknown);
		}

		try {
			time = sigar.getProcTime(pid);
			info.add(getStartTime(time.getStartTime()));
		} catch (SigarException e) {
			info.add(unknown);
		}

		try {
			ProcMem mem = sigar.getProcMem(pid);
			info.add(Sigar.formatSize(mem.getSize()));
			info.add(Sigar.formatSize(mem.getRss()));
			info.add(Sigar.formatSize(mem.getShare()));
		} catch (SigarException e) {
			info.add(unknown);
		}

		info.add(String.valueOf(state.getState()));

		if (time != null) {
			info.add(getCpuTime(time));
		} else {
			info.add(unknown);
		}

		String name = ProcUtil.getDescription(sigar, pid);
		info.add(name);

		return info;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public static String getCpuTime(long total) {
		
		long t = total / 1000;
		return String.format("%02d:%02d", t / 60, t % 60);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public static String getCpuTime(ProcTime time) {
		
		return getCpuTime(time.getTotal());
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private static String getStartTime(long time) {
		
		if (time == 0) {
			return "00:00";
		}
		
		long timeNow = System.currentTimeMillis();
		String fmt = "MMMd";
		
		if ((timeNow - time) < ((60 * 60 * 24) * 1000)) {
			fmt = "HH:mm";
		}
		
		return new SimpleDateFormat(fmt).format(new Date(time));
	}
	
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
			new Ps();

		if (flag) {
			/*
			 * begin
			 */
			new Ps().processCommand(args);
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
