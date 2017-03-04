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
package tain.kr.runjar.v04;

import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;


/**
 * Code Templates > Comments > Types
 *
 * <PRE>
 *   -. FileName   : MainTestRunjar.java
 *   -. Package    : tain.kr.runjar.v04
 *   -. Comment    :
 *   -. Author     : taincokr
 *   -. First Date : 2017. 3. 4. {time}
 * </PRE>
 *
 * @author taincokr
 *
 */
public class MainTestRunjar {

	private static boolean flag = true;

	private static final Logger log = Logger.getLogger(MainTestRunjar.class);

	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private final Properties prop;
	private final ResourceBundle resourceBundle;
	
	///////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * constructor
	 */
	public MainTestRunjar() {
		
		this.prop = System.getProperties();
		this.resourceBundle = ResourceBundle.getBundle(this.getClass().getName().replace('.', '/'));
		
		if (flag) log.debug(String.format("System.getProperties -> [%s]", this.prop.getProperty("tain.kr.desc")));
		if (flag) log.debug(String.format("ResourceBundle.getBundle -> [%s]", this.resourceBundle.getString("tain.kr.runjar.desc")));
		
		if (flag)
			log.debug(">>>>> in class " + this.getClass().getSimpleName());
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

		if (flag)
			new MainTestRunjar();

		if (flag) {

		}
	}

	/*
	 * main method
	 */
	public static void main(String[] args) throws Exception {

		if (flag)
			log.debug(">>>>> " + new Object() {}.getClass().getEnclosingClass().getName());

		if (flag)
			test01(args);
	}
}
