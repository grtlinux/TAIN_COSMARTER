/**
 * Copyright 2014, 2015, 2016 TAIN, Inc. all rights reserved.
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
 * Copyright 2014, 2015, 2016 TAIN, Inc.
 *
 */
package tain.kr.com.proj.cosmarter.v01.common.program;

import java.util.ResourceBundle;

import org.apache.log4j.Logger;

/**
 * Code Templates > Comments > Types
 *
 * <PRE>
 *   -. FileName   : Program.java
 *   -. Package    : tain.kr.com.proj.cosmarter.v01.common.program
 *   -. Comment    :
 *   -. Author     : taincokr
 *   -. First Date : 2016. 4. 15. {time}
 * </PRE>
 *
 * @author taincokr
 *
 */
public class Program {

	private static boolean flag = true;

	private static final Logger log = Logger.getLogger(Program.class);

	///////////////////////////////////////////////////////////////////////////////////////////////

	private static final String KEY_PROGRAM_DESC = "tain.program.desc";
	
	private String strProgramDesc = null;
	
	///////////////////////////////////////////////////////////////////////////////////////////////

	private Program() throws Exception {
		
		if (flag) {
			String clsName = this.getClass().getName();
			
			ResourceBundle rb = ResourceBundle.getBundle(clsName.replace('.', '/'));
			
			this.strProgramDesc = rb.getString(KEY_PROGRAM_DESC);
		}
	}
	
	public String getDesc() throws Exception {
		return this.strProgramDesc;
	}
	
	public void print() throws Exception {
		System.out.println("DESC > " + this.getDesc());
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private static Program instance = null;
	
	public static synchronized Program getInstance() throws Exception {
		
		if (instance == null) {
			instance = new Program();
		}
		
		return instance;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private static void test01(String[] args) throws Exception {
		
		if (flag) {
			Program.getInstance().print();
			
			log.debug(">>>>> " + Program.getInstance().getDesc());
		}
	}
	
	public static void main(String[] args) throws Exception {
		
		if (flag) log.debug(">>>>> " + new Object(){}.getClass().getEnclosingClass().getName());
		
		if (flag) test01(args);
	}
}
