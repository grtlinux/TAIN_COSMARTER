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
package tain.kr.com.proj.cosmarter.v02.test;

import java.lang.reflect.Method;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

/**
 * Code Templates > Comments > Types
 *
 * <PRE>
 *   -. FileName   : MainTestCoSmarter.java
 *   -. Package    : tain.kr.com.proj.cosmarter.v02.test
 *   -. Comment    :
 *   -. Author     : taincokr
 *   -. First Date : 2017. 3. 11. {time}
 * </PRE>
 *
 * @author taincokr
 *
 */
public class MainTestCoSmarter {

	private static boolean flag = true;

	private static final Logger log = Logger.getLogger(MainTestCoSmarter.class);

	///////////////////////////////////////////////////////////////////////////////////////////////

	private static final String KEY_COSMARTER_MAIN_CLASS = "tain.cosmarter.main.class";
	
	private String mainClassName = null;
	
	///////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * constructor
	 */
	private MainTestCoSmarter() throws Exception {

		if (flag) {
			String clsName = this.getClass().getName();
			
			ResourceBundle rb = ResourceBundle.getBundle(clsName.replace('.', '/'));
			
			this.mainClassName = rb.getString(KEY_COSMARTER_MAIN_CLASS);
		}

		if (flag)
			log.debug(">>>>> in class " + this.getClass().getSimpleName());
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public void load() throws Exception {

		if (flag) {
			Class<?> cls = Class.forName(this.mainClassName);
			Method method = cls.getDeclaredMethod("main", new Class[] { String[].class });
			
			String[] arg = { "One", "Two", "Three" };
			method.invoke(cls.newInstance(), new Object[] { arg }); 
			
			//String ret = (String) method.invoke(cls.newInstance(), new Object[] { arg }); 
			//String ret = (String) method.invoke(cls.newInstance(), new Object[] { new String[] {"One", "Two", "Three"} });   // CORRECT
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private static MainTestCoSmarter instance = null;
	
	public static synchronized MainTestCoSmarter getInstance() throws Exception {
		
		if (instance == null) {
			instance = new MainTestCoSmarter();
		}
		
		return instance;
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

		if (flag) {
			MainTestCoSmarter.getInstance().load();
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
