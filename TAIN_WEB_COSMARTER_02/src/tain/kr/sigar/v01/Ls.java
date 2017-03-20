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
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.hyperic.sigar.FileInfo;
import org.hyperic.sigar.SigarException;

/**
 * Code Templates > Comments > Types
 *
 * <PRE>
 *   -. FileName   : Ls.java
 *   -. Package    : tain.kr.com.test.sigar.v01
 *   -. Comment    :
 *   -. Author     : taincokr
 *   -. First Date : 2017. 3. 17. {time}
 * </PRE>
 *
 * @author taincokr
 *
 */
public final class Ls extends SigarCommandBase {

	private static boolean flag = true;

	private static final Logger log = Logger.getLogger(Ls.class);

	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * constructor
	 */
	public Ls(Shell shell) {
		super(shell);
		if (!flag)
			log.debug(">>>>> in class " + this.getClass().getSimpleName());
	}
	
	public Ls() {
		super();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public String getUsageShort() {
		return "simple FileInfo test at the moment (like ls -l)";
	}
	
	protected boolean validateArgs(String[] args) {
		return args.length == 1;
	}
	
	private String getDate(long mtime) {
		final String fmt = "MMM dd  yyyy";
		
		return new SimpleDateFormat(fmt).format(new Date(mtime));
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public void output(String[] args) throws SigarException {
		
		String file = args[0];
		
		FileInfo link = this.sigar.getLinkInfo(file);
		FileInfo info = this.sigar.getFileInfo(file);
		
		if (link.getType() == FileInfo.TYPE_LNK) {
			try {
				file = file + " -> " + new File(file).getCanonicalPath();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		println(link.getTypeChar()
				+ info.getPermissionsString() + "\t"
				+ info.getUid() + "\t" + info.getGid() + "\t" + info.getSize() + "\t"
				+ getDate(info.getMtime()) + "\t"
				+ file
		);
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
			new Ls().processCommand(new String[] { "N:/DOC" });
			new Ls().processCommand(new String[] { "N:/DOC/import github.20160215.pptx" });
			//new Ls().processCommand(args);
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
