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
import org.hyperic.sigar.DiskUsage;
import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemMap;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.shell.FileCompleter;
import org.hyperic.sigar.util.GetlineCompleter;

/**
 * Code Templates > Comments > Types
 *
 * <PRE>
 *   -. FileName   : Iostat.java
 *   -. Package    : tain.kr.com.test.sigar.v01
 *   -. Comment    :
 *   -. Author     : taincokr
 *   -. First Date : 2017. 3. 17. {time}
 * </PRE>
 *
 * @author taincokr
 *
 */
public final class Iostat extends SigarCommandBase {

	private static boolean flag = true;

	private static final Logger log = Logger.getLogger(Iostat.class);

	///////////////////////////////////////////////////////////////////////////////////////////////

	private static final String OUTPUT_FORMAT =
			"%-15s %-15s %10s %10s %7s %7s %5s %5s";

		private static final String[] HEADER = new String[] {
			"Filesystem",
			"Mounted on",
			"Reads",
			"Writes",
			"R-bytes",
			"W-bytes",
			"Queue",
			"Svctm",
		};

		private GetlineCompleter completer;

	///////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * constructor
	 */
	public Iostat(Shell shell) {
		
		super(shell);
		setOutputFormat(OUTPUT_FORMAT);
		this.completer = new FileCompleter(shell);
		
		if (!flag)
			log.debug(">>>>> in class " + this.getClass().getSimpleName());
	}

	public Iostat() {
		super();
		setOutputFormat(OUTPUT_FORMAT);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////

	public GetlineCompleter getCompleter() {
		return this.completer;
	}

	protected boolean validateArgs(String[] args) {
		return args.length <= 1;
	}

	public String getSyntaxArgs() {
		return "[filesystem]";
	}

	public String getUsageShort() {
		return "Report filesystem disk i/o";
	}

	public void printHeader() {
		printf(HEADER);
	}

	private String svctm(double val) {
		return sprintf("%3.2f", new Object[] { new Double(val) });
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	/* (non-Javadoc)
	 * @see tain.kr.com.test.sigar.v01.SigarCommandBase#output(java.lang.String[])
	 */
	@Override
	public void output(String[] args) throws SigarException {

		if (args.length == 1) {
			String arg = args[0];
			if ((arg.indexOf('/') != -1) || (arg.indexOf('\\') != -1)) {
				outputFileSystem(arg);
			} else {
				outputDisk(arg);
			}
		} else {
			FileSystem[] fslist = this.proxy.getFileSystemList();
			printHeader();
			for (int i=0; i<fslist.length; i++) {
				if (fslist[i].getType() == FileSystem.TYPE_LOCAL_DISK) {
					output(fslist[i]);
				}
			}
		}
	}
	
	public void outputFileSystem(String arg) throws SigarException {
		
		FileSystemMap mounts = this.proxy.getFileSystemMap();
		String name = FileCompleter.expand(arg);
		FileSystem fs = mounts.getMountPoint(name);

		if (fs != null) {
			printHeader();
			output(fs);
			return;
		}

		throw new SigarException(arg +
								 " No such file or directory");
	}
	
	public void outputDisk(String name) throws SigarException {
		
		DiskUsage disk = this.sigar.getDiskUsage(name);

		ArrayList<String> items = new ArrayList<String>();
		printHeader();
		items.add(name);
		items.add("-");
		items.add(String.valueOf(disk.getReads()));
		items.add(String.valueOf(disk.getWrites()));

		if (disk.getReadBytes() == Sigar.FIELD_NOTIMPL) {
			items.add("-");
			items.add("-");
		} else {
			items.add(Sigar.formatSize(disk.getReadBytes()));
			items.add(Sigar.formatSize(disk.getWriteBytes()));
		}

		if (disk.getQueue() == Sigar.FIELD_NOTIMPL) {
			items.add("-");
		} else {
			items.add(svctm(disk.getQueue()));
		}

		if (disk.getServiceTime() == Sigar.FIELD_NOTIMPL) {
			items.add("-");
		} else {
			items.add(svctm(disk.getServiceTime()));
		}

		printf(items);
	}
	
	public void output(FileSystem fs) throws SigarException {
		
		FileSystemUsage usage = this.sigar.getFileSystemUsage(fs.getDirName());

		ArrayList<String> items = new ArrayList<String>();

		items.add(fs.getDevName());
		items.add(fs.getDirName());
		items.add(String.valueOf(usage.getDiskReads()));
		items.add(String.valueOf(usage.getDiskWrites()));

		if (usage.getDiskReadBytes() == Sigar.FIELD_NOTIMPL) {
			items.add("-");
			items.add("-");
		} else {
			items.add(Sigar.formatSize(usage.getDiskReadBytes()));
			items.add(Sigar.formatSize(usage.getDiskWriteBytes()));
		}

		if (usage.getDiskQueue() == Sigar.FIELD_NOTIMPL) {
			items.add("-");
		} else {
			items.add(svctm(usage.getDiskQueue()));
		}
		
		if (usage.getDiskServiceTime() == Sigar.FIELD_NOTIMPL) {
			items.add("-");
		} else {
			items.add(svctm(usage.getDiskServiceTime()));
		}

		printf(items);
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
			new Iostat();

		if (flag) {
			/*
			 * begin
			 */
			new Iostat().processCommand(args);
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
