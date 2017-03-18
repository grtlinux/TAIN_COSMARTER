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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hyperic.sigar.FileInfo;
import org.hyperic.sigar.FileTail;
import org.hyperic.sigar.FileWatcherThread;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

/**
 * Code Templates > Comments > Types
 *
 * <PRE>
 *   -. FileName   : Tail.java
 *   -. Package    : tain.kr.com.test.sigar.v01
 *   -. Comment    :
 *   -. Author     : taincokr
 *   -. First Date : 2017. 3. 17. {time}
 * </PRE>
 *
 * @author taincokr
 *
 */
public final class Tail {

	private static boolean flag = true;

	private static final Logger log = Logger.getLogger(Tail.class);

	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean follow;
	public int number = 10;
	public List<String> files = new ArrayList<String>();

	///////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * constructor
	 */
	public Tail() {
		if (flag)
			log.debug(">>>>> in class " + this.getClass().getSimpleName());
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public void parseArgs(String args[]) throws SigarException {
		for (int i=0; i<args.length; i++) {
			
			String arg = args[i];
			
			if (arg.charAt(0) != '-') {
				this.files.add(arg);
				continue;
			}
			
			arg = arg.substring(1);
			
			if (arg.equals("f")) {
				this.follow = true;
			} else if (Character.isDigit(arg.charAt(0))) {
				this.number = Integer.parseInt(arg);
			} else {
				throw new SigarException("Unknown argument: " + args[i]);
			}
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
	///////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * static test method
	 */
	private static void test01(String[] args) throws SigarException {

		if (flag)
			new Tail();

		if (flag) {
			/*
			 * begin
			 */
			Sigar sigar = new Sigar();
			
			FileWatcherThread watcherThread = FileWatcherThread.getInstance();
			watcherThread.doStart();
			watcherThread.setInterval(1000);
			
			FileTail watcher = new FileTail(sigar) {
				@Override
				public void tail(FileInfo info, Reader reader) {
					String line;
					BufferedReader br = new BufferedReader(reader);
					
					if (getFiles().size() > 1) {
						System.out.println("===> " + info.getName() + " <===");
					}
					
					try {
						while ((line = br.readLine()) != null) {
							System.out.println(line);
						}
					} catch (IOException e) {
						System.out.println(e);
					}
				}
			};
			
			for (int i=0; i < args.length; i++) {
				watcher.add(args[i]);
			}
			
			watcherThread.add(watcher);
			
			try {
				System.in.read();
			} catch (IOException e) {}
			
			watcherThread.doStop();
		}
	}

	/*
	 * main method
	 */
	public static void main(String[] args) throws Exception {

		if (flag)
			log.debug(">>>>> " + new Object() {
			}.getClass().getEnclosingClass().getName());

		if (flag) {
			//test01(new String[] { "N:/PROG/hyperic-sigar-1.6.4/bindings/java/examples/Tail.java" });
			test01(args);
		}
	}
}
