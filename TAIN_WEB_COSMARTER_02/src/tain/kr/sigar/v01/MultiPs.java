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
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.MultiProcCpu;
import org.hyperic.sigar.ProcMem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

/**
 * Code Templates > Comments > Types
 *
 * <PRE>
 *   -. FileName   : MultiPs.java
 *   -. Package    : tain.kr.com.test.sigar.v01
 *   -. Comment    :
 *   -. Author     : taincokr
 *   -. First Date : 2017. 3. 17. {time}
 * </PRE>
 *
 * @author taincokr
 *
 */
public final class MultiPs extends SigarCommandBase {

	private static boolean flag = true;

	private static final Logger log = Logger.getLogger(MultiPs.class);

	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * constructor
	 */
	public MultiPs(Shell shell) {
		super(shell);
		if (!flag)
			log.debug(">>>>> in class " + this.getClass().getSimpleName());
	}

	public MultiPs() {
		super();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	protected boolean validateArgs(String[] args) {
		return args.length == 1;
	}
	
	public String getSyntaxArgs() {
		return "query";
	}
	
	public String getUsageShort() {
		return "Show multi process status";
	}
	
	public boolean isPidCompleter() {
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public void output(String[] args) throws SigarException {
		
		String query = args[0];
		MultiProcCpu cpu = this.proxy.getMultiProcCpu(query);
		
		println("Number of processes: " + cpu.getProcesses());
		println("Cpu usage: " + CpuPerc.format(cpu.getPercent()));
		println("Cpu time: " + Ps.getCpuTime(cpu.getTotal()));
		
		ProcMem mem = this.proxy.getMultiProcMem(query);
		println("Size: " + Sigar.formatSize(mem.getSize()));
		println("Resident: " + Sigar.formatSize(mem.getResident()));
		println("Share: " + Sigar.formatSize(mem.getShare()));
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
	 * PTQL (Process Table Query Language)
	 * 
	 * Hyperic SIGAR provides a mechanism to identify processes called Process Table Query Language.
	 * All operating systems assign a unique id (PID) to each running process. However, the PID is
	 * a random number that may also change at any point in time when a process is restarted. PTQL
	 * uses process attributes that will persist over time to identify a process.
	 * 
	 * Class.Attribute.operator=value
	 * 
	 *     e.g.) Exe.Name.ct=Program Files
	 * 
	 * Class.Attribute
	 * 
	 *     Pid.Pid        - The process ID
	 *     Pid.PidFile    - File containing the process ID
	 *     Pid.Service    - Windows Service name used to pid from the service manager
	 *     State.Name     - Base name of the process executable
	 *     CredName.User  - User Name of the process owner
	 *     CredName.Group - Group Name of the process owner
	 *     Cred.Uid       - User ID of the process owner
	 *     Cred.Gid       - Group ID of the process owner
	 *     Cred.Euid      - Effective User ID of the process owner
	 *     Cred.Egid      - Effective Group ID of the process owner
	 *     Exe.Name       - Full path name of the process executable
	 *     Exe.Cwd        - Current Working Directory of the process
	 *     Args.*         - Command line argument passed to the process
	 *     Env.*          - Environment variable within the process
	 *     Modules.*      - Shared library loaded within the process
	 *     
	 * operator for String values
	 * 
	 *     eq - equal to value
	 *     ne - not equal to value
	 *     ew - ends with value
	 *     sw - start with value
	 *     ct - contains value (substring)
	 *     re - regular expression value matches
	 *     Pne - Parent process not equal to value
	 *     
	 * operator for Numeric values
	 * 
	 *     eq - equal to value
	 *     ne - not equal to value
	 *     gt - greater than value
	 *     ge - greater than or equal value
	 *     lt - less than value
	 *     le - less than or equal value
	 *     
	 * e.g.) do not make a space in the below
	 * 
	 *     State.Name.eq = crond
	 *     
	 *     State.Name.eq = sshd
	 *     
	 *     Pid.PidFile.eq = /var/run/sshd.pid
	 *     
	 *     Pid.Service.eq = Eventlog
	 *     
	 *     Pid.Service.eq = sshd
	 *     
	 *     State.Name.re = ^(https?d.*|[Aa]pache2?)$
	 *     
	 *     State.Name.eq = httpd, State.Name.Pne = httpd
	 *     
	 *     State.Name.eq = httpd, State.Name.Pne = $1   <- $1 is the return value of the attribute (State.Name)
	 *                                                     in the first branch of the query
	 *     
	 *     CredName.User.eq = dougm, State.Name.eq = httpd, State.Name.Pne = $2 <- the return value of State.Name
	 *     
	 *     State.Name.eq = java
	 *     
	 *     State.Name.sw = java
	 *     
	 *     State.Name.eq = java, Args.7.eq = org.jboss.Main
	 *     
	 *     State.Name.eq = java, Args.-1.eq = org.jboss.Main
	 *     
	 *     State.Name.eq = java, Args.*.eq = org.jboss.Main
	 *     
	 *     
	 */
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
			// new MultiPs().processCommand(args);
			new MultiPs().processCommand(new String[] { "Exe.Name.ct=Program Files" });
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
