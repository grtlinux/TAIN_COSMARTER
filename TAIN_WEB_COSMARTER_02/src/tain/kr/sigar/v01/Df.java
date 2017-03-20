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
import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemMap;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.NfsFileSystem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.shell.FileCompleter;
import org.hyperic.sigar.util.GetlineCompleter;

/**
 * Code Templates > Comments > Types
 *
 * <PRE>
 *   -. FileName   : Df.java
 *   -. Package    : tain.kr.com.test.sigar.v01
 *   -. Comment    :
 *   -. Author     : taincokr
 *   -. First Date : 2017. 3. 17. {time}
 * </PRE>
 *
 * @author taincokr
 *
 */
public final class Df extends SigarCommandBase {

	private static boolean flag = true;

	private static final Logger log = Logger.getLogger(Df.class);

	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final String OUTPUT_FORMAT =
			"%-15s %4s %4s %5s %4s %-15s %s";

	//like df -h -a
	private static final String[] HEADER = new String[] {
		"Filesystem",
		"Size",
		"Used",
		"Avail",
		"Use%",
		"Mounted on",
		"Type"
	};
	//df -i
	private static final String[] IHEADER = new String[] {
		"Filesystem",
		"Inodes",
		"IUsed",
		"IFree",
		"IUse%",
		"Mounted on",
		"Type"
	};

	private GetlineCompleter completer;
	private boolean opt_i;

	///////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * constructor
	 */
	public Df(Shell shell) {
		
		super(shell);
		setOutputFormat(OUTPUT_FORMAT);
		this.completer = new FileCompleter(shell);
		
		if (flag)
			log.debug(">>>>> in class " + this.getClass().getSimpleName());
	}

	public Df() {
		super();
		setOutputFormat(OUTPUT_FORMAT);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public GetlineCompleter getCompleter() {
		return this.completer;
	}

	@Override
	protected boolean validateArgs(String[] args) {
		return true;
	}

	@Override
	public String getSyntaxArgs() {
		return "[filesystem]";
	}

	@Override
	public String getUsageShort() {
		return "Report filesystem disk space usage";
	}

	public void printHeader() {
		printf(this.opt_i ? IHEADER : HEADER);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void output(String[] args) throws SigarException {
		this.opt_i = false;
		ArrayList<FileSystem> sys = new ArrayList<FileSystem>();

		if (args.length > 0) {
			FileSystemMap mounts = this.proxy.getFileSystemMap();
			for (int i=0; i<args.length; i++) {
				String arg = args[i];
				if (arg.equals("-i")) {
					this.opt_i = true;
					continue;
				}
				String name = FileCompleter.expand(arg);
				FileSystem fs = mounts.getMountPoint(name);

				if (fs == null) {
					throw new SigarException(arg +
											 " No such file or directory");
				}
				sys.add(fs);
			}
		}
		if (sys.size() == 0) {
			FileSystem[] fslist = this.proxy.getFileSystemList();
			for (int i=0; i<fslist.length; i++) {
				sys.add(fslist[i]);
			}
		}

		printHeader();
		for (int i=0; i<sys.size(); i++) {
			output(sys.get(i));
		}
	}

	public void output(FileSystem fs) throws SigarException {
		long used, avail, total, pct;

		try {
			FileSystemUsage usage;
			if (fs instanceof NfsFileSystem) {
				NfsFileSystem nfs = (NfsFileSystem)fs;
				if (!nfs.ping()) {
					println(nfs.getUnreachableMessage());
					return;
				}
			}
			usage = this.sigar.getFileSystemUsage(fs.getDirName());
			if (this.opt_i) {
				used  = usage.getFiles() - usage.getFreeFiles();
				avail = usage.getFreeFiles();
				total = usage.getFiles();
				if (total == 0) {
					pct = 0;
				}
				else {
					long u100 = used * 100;
					pct = u100 / total +
						((u100 % total != 0) ? 1 : 0);
				}
			}
			else {
				used = usage.getTotal() - usage.getFree();
				avail = usage.getAvail();
				total = usage.getTotal();

				pct = (long)(usage.getUsePercent() * 100);
			}
		} catch (SigarException e) {
			//e.g. on win32 D:\ fails with "Device not ready"
			//if there is no cd in the drive.
			used = avail = total = pct = 0;
		}

		String usePct;
		if (pct == 0) {
			usePct = "-";
		}
		else {
			usePct = pct + "%";
		}

		ArrayList<String> items = new ArrayList<String>();

		items.add(fs.getDevName());
		items.add(formatSize(total));
		items.add(formatSize(used));
		items.add(formatSize(avail));
		items.add(usePct);
		items.add(fs.getDirName());
		items.add(fs.getSysTypeName() + "/" + fs.getTypeName());

		printf(items);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	private String formatSize(long size) {
		return this.opt_i ? String.valueOf(size) : Sigar.formatSize(size * 1024);
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
			new Df().processCommand(args);
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
