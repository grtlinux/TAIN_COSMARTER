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

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;

import org.apache.log4j.Logger;
import org.hyperic.jni.ArchLoader;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.SigarLoader;
import org.hyperic.sigar.SigarPermissionDeniedException;
import org.hyperic.sigar.SigarProxy;
import org.hyperic.sigar.SigarProxyCache;
import org.hyperic.sigar.ptql.ProcessFinder;
import org.hyperic.sigar.shell.ShellBase;
import org.hyperic.sigar.shell.ShellCommandExecException;
import org.hyperic.sigar.shell.ShellCommandHandler;
import org.hyperic.sigar.shell.ShellCommandInitException;
import org.hyperic.sigar.shell.ShellCommandUsageException;
import org.hyperic.sigar.util.Getline;

/**
 * Code Templates > Comments > Types
 *
 * <PRE>
 *   -. FileName   : Shell.java
 *   -. Package    : tain.kr.com.test.sigar.v01
 *   -. Comment    :
 *   -. Author     : taincokr
 *   -. First Date : 2017. 3. 17. {time}
 * </PRE>
 *
 * @author taincokr
 *
 */
public class Shell extends ShellBase {

	private static boolean flag = true;

	private static final Logger log = Logger.getLogger(Shell.class);

	///////////////////////////////////////////////////////////////////////////////////////////////

	public static final String RCFILE_NAME = ".sigar_shellrc";
	private static final String CLEAR_SCREEN = "\033[2j";

	private Sigar sigar = new Sigar();
	private SigarProxy proxy = SigarProxyCache.newInstance(this.sigar);
	private long[] foundPids = new long[0];
	private boolean isInteractive = false;

	///////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * constructor
	 */
	public Shell() {
		if (!flag)
			log.debug(">>>>> in class " + this.getClass().getSimpleName());
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	public SigarProxy getSigarProxy() {
		return this.proxy;
	}

	public Sigar getSigar() {
		return this.sigar;
	}

	public boolean isInteractive() {
		return this.isInteractive;
	}

	public void setInteractive(boolean isInteractive) {
		this.isInteractive = isInteractive;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	public void registerCommands() throws ShellCommandInitException {
		registerCommandHandler("cpuinfo", new CpuInfo(this));
		registerCommandHandler("df", new Df(this));
		registerCommandHandler("du", new Du(this));
		registerCommandHandler("ls", new Ls(this));
		registerCommandHandler("iostat", new Iostat(this));
		registerCommandHandler("free", new Free(this));
		registerCommandHandler("pargs", new ShowArgs(this));
		registerCommandHandler("penv", new ShowEnv(this));
		registerCommandHandler("pfile", new ProcFileInfo(this));
		registerCommandHandler("pmodules", new ProcModuleInfo(this));
		registerCommandHandler("pinfo", new ProcInfo(this));
		registerCommandHandler("ifconfig", new Ifconfig(this));
		registerCommandHandler("uptime", new Uptime(this));
		registerCommandHandler("ps", new Ps(this));
		registerCommandHandler("pidof", new Pidof(this));
		registerCommandHandler("kill", new Kill(this));
		registerCommandHandler("netstat", new Netstat(this));
		registerCommandHandler("netinfo", new NetInfo(this));
		registerCommandHandler("nfsstat", new Nfsstat(this));
		registerCommandHandler("route", new Route(this));
		registerCommandHandler("version", new Version(this));
		registerCommandHandler("mps", new MultiPs(this));
		registerCommandHandler("sysinfo", new SysInfo(this));
		registerCommandHandler("time", new Time(this));
		registerCommandHandler("ulimit", new Ulimit(this));
		registerCommandHandler("who", new Who(this));

		if (ArchLoader.IS_WIN32) {
			registerCommandHandler("service", new Win32Service(this));
			registerCommandHandler("fversion", new FileVersionInfo(this));
		}

		try {
			// requires junit.jar
			registerCommandHandler("test", "org.hyperic.sigar.test.SigarTestRunner");  // Runner
		} catch (NoClassDefFoundError e) {
		} catch (Exception e) {}
	}

	private void registerCommandHandler(String name, String className) throws Exception {
		Class<?> cls = Class.forName(className);
		Constructor<?> con = cls.getConstructor(new Class[] { this.getClass() });
		registerCommandHandler(name, (ShellCommandHandler) con.newInstance(new Object[] { this }));
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void processCommand(ShellCommandHandler handler, String[] args) throws ShellCommandUsageException, ShellCommandExecException {
		try {
			super.processCommand(handler, args);
			if (handler instanceof SigarCommandBase) {
				((SigarCommandBase) handler).flush();
			}
		} finally {
			SigarProxyCache.clear(this.proxy);
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public long[] findPids(String[] args) throws SigarException {
		if ((args.length == 1) && args[0].equals("-")) {
			return this.foundPids;
		}
		
		this.foundPids = getPids(this.proxy, args);
		
		return this.foundPids;
	}
	
	public long[] findPids(String query) throws SigarException {
		return findPids(new String[] { query });
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public void readCommandFile(String dir) {
		try {
			File rc = new File(dir, RCFILE_NAME);
			readRCFile(rc, false);
			if (this.isInteractive && Getline.isTTY()) {
				this.out.println("Loaded rc file: " + rc);
			}
		} catch (IOException e) {
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public String getUserDeniedMessage(long pid) {
		return SigarPermissionDeniedException.getUserDeniedMessage(this.proxy, pid);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public void shutdown() {
		this.sigar.close();
		
		// cleanup for dmalloc
		// using reflection incase junit.jar is not present
		
		try {
			// SigarTestCase.closeSigar();
			Class.forName("org.hyperic.sigar.test.SigarTestCase").getMethod("closeSigar", new Class[0]).invoke(null, new Object[0]);
		} catch (ClassNotFoundException e) {
			// SigarTestCase.java not compiled w/o junit.jar
		} catch (Exception e) {
			e.printStackTrace();
		} catch (NoClassDefFoundError e) {
			// avoiding possible Class Not Found: junit/framework/TestCase
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////

	public static void clearScreen() {
		System.out.print(CLEAR_SCREEN);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public static long[] getPids(SigarProxy sigar, String[] args) throws SigarException {
		long[] pids;
		
		switch (args.length) {
		case 0:
			pids = new long[] { sigar.getPid() };
			break;
		case 1:
			if (args[0].indexOf("=") > 0) {
				pids = ProcessFinder.find(sigar,  args[0]);
			} else if (args[0].equals("$$")) {
				pids = new long[] { sigar.getPid() };
			} else {
				pids = new long[] { Long.parseLong(args[0]) };
			}
			break;
		default:
			pids = new long[args.length];
			for (int i=0; i < args.length; i++) {
				pids[i] = Long.parseLong(args[i]);
			}
			break;
		}
		
		return pids;
	}
	
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
			Shell shell = new Shell();
			
			try {
				if (args.length == 0) {
					shell.isInteractive = true;
				}
				
				shell.init("sigar",  System.out, System.err);
				shell.registerCommands();
				
				shell.readCommandFile(System.getProperty("user.home"));
				shell.readCommandFile(".");
				shell.readCommandFile(SigarLoader.getLocation());
				
				if (shell.isInteractive) {
					shell.initHistory();
					Getline.setCompleter(shell);
					shell.run();
				} else {
					shell.handleCommand(null, args);
				}
			} catch (Exception e) {
				System.err.println("Unexpected exception: " + e);
			} finally {
				shell.shutdown();
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
