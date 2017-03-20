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

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.SigarProxy;
import org.hyperic.sigar.pager.PageControl;
import org.hyperic.sigar.pager.PageFetchException;
import org.hyperic.sigar.pager.StaticPageFetcher;
import org.hyperic.sigar.shell.CollectionCompleter;
import org.hyperic.sigar.shell.ProcessQueryCompleter;
import org.hyperic.sigar.shell.ShellCommandBase;
import org.hyperic.sigar.shell.ShellCommandExecException;
import org.hyperic.sigar.shell.ShellCommandUsageException;
import org.hyperic.sigar.util.GetlineCompleter;
import org.hyperic.sigar.util.PrintfFormat;

/**
 * Code Templates > Comments > Types
 *
 * <PRE>
 *   -. FileName   : SigarCommandBase.java
 *   -. Package    : tain.kr.com.test.sigar.v01
 *   -. Comment    :
 *   -. Author     : taincokr
 *   -. First Date : 2017. 3. 17. {time}
 * </PRE>
 *
 * @author taincokr
 *
 */
public abstract class SigarCommandBase extends ShellCommandBase implements GetlineCompleter {

	private static boolean flag = true;

	private static final Logger log = Logger.getLogger(SigarCommandBase.class);

	///////////////////////////////////////////////////////////////////////////////////////////////
	
	protected Shell shell;
	protected PrintStream out = System.out;
	protected PrintStream err = System.err;
	protected Sigar sigar;
	protected SigarProxy proxy;
	protected List<String> output = new ArrayList<String>();
	private CollectionCompleter completer;
	private GetlineCompleter ptqlCompleter;
	private Collection<Object> completions = new ArrayList<Object>();
	private PrintfFormat formatter;
	private ArrayList<Object[]> printfItems = new ArrayList<Object[]>();

	///////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * constructor
	 */
	public SigarCommandBase(Shell shell) {
		
		this.shell = shell;
		this.out = shell.getOutStream();
		this.err = shell.getErrStream();
		this.sigar = shell.getSigar();
		this.proxy = shell.getSigarProxy();
		
		// provide simple way for handlers to implements tab completion
		this.completer = new CollectionCompleter(shell);
		if (isPidCompleter()) {
			this.ptqlCompleter = new ProcessQueryCompleter(shell);
		}
		
		if (!flag)
			log.debug(">>>>> in class " + this.getClass().getSimpleName());
	}
	
	public SigarCommandBase() {
		this(new Shell());
		this.shell.setPageSize(PageControl.SIZE_UNLIMITED);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean isPidCompleter() {
		return false;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public void setOutputFormat(String format) {
		this.formatter = new PrintfFormat(format);
	}
	
	public PrintfFormat getFormatter() {
		return this.formatter;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public String sprintf(String format, Object[] items) {
		return new PrintfFormat(format).sprintf(items);
	}
	
	public void printf(String format, Object[] items) {
		println(sprintf(format, items));
	}
	
	public void printf(List<String> items) {
		printf((Object[]) items.toArray(new Object[0]));
	}
	
	public void printf(Object[] items) {
		PrintfFormat formatter = getFormatter();
		if (formatter == null) {
			// see flushPrintfItems
			this.printfItems.add(items);
		} else {
			println(formatter.sprintf(items));
		}
	}
	
	public void println(String line) {
		if (this.shell.isInteractive()) {
			this.output.add(line);
		} else {
			this.out.println(line);
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private void flushPrintfItems() {
		if (this.printfItems.size() == 0) {
			return;
		}
		
		// no format was specified, just line up the columns
		int[] max = null;
		
		for (Iterator<Object[]> it = this.printfItems.iterator(); it.hasNext();) {
			Object[] items = it.next();
			if (max == null) {
				max = new int[items.length];
				Arrays.fill(max, 0);
			}
			
			for (int i=0; i < items.length; i++) {
				int len = items[i].toString().length();
				if (len > max[i]) {
					max[i] = len;
				}
			}
		}
		
		StringBuffer format = new StringBuffer();
		for (int i=0; i < max.length; i++) {
			format.append("%-" + max[i] + "s");
			if (i < max.length-1) {
				format.append("     ");
			}
		}
		
		for (Iterator<Object[]> it = this.printfItems.iterator(); it.hasNext();) {
			printf(format.toString(), it.next());
		}
		
		this.printfItems.clear();
	}
	
	public void flush() {
		flushPrintfItems();
		
		try {
			this.shell.performPaging(new StaticPageFetcher(this.output));
		} catch (PageFetchException e) {
			this.err.println("Error paging: " + e.getMessage());
		} finally {
			this.output.clear();
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public abstract void output(String[] args) throws SigarException;
	
	protected boolean validateArgs(String[] args) {
		return args.length == 0;
	}
	
	@Override
	public void processCommand(String[] args) throws ShellCommandUsageException, ShellCommandExecException {
		if (!validateArgs(args)) {
			throw new ShellCommandUsageException(getSyntax());
		}
		
		try {
			output(args);
		} catch (SigarException e) {
			throw new ShellCommandExecException(e.getMessage());
		}
	}
	
	public Collection<Object> getCompletions() {
		return this.completions;
	}
	
	public GetlineCompleter getCompleter() {
		return null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public String completePid(String line) {
		if ((line.length() >= 1) && Character.isDigit(line.charAt(0))) {
			return line;
		}
		
		return this.ptqlCompleter.complete(line);
	}
	
	@Override
	public String complete(String line) {
		if (isPidCompleter()) {
			return completePid(line);
		}
		
		GetlineCompleter c = getCompleter();
		if (c != null) {
			return c.complete(line);
		}
		
		this.completer.setCollection(getCompletions());
		
		return this.completer.complete(line);
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
