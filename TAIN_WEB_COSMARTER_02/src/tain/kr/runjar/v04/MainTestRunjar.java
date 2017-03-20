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

import java.lang.reflect.Method;
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
	
	private static final String KEY_SYSTEM_DESC = "tain.kr.desc";
	private static final String KEY_RUNJAR_DESC = "tain.kr.runjar.desc";
	private static final String KEY_MAIN_CLASS = "tain.kr.runjar.main.class";

	private final Properties prop;
	private final ResourceBundle resourceBundle;
	
	///////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * constructor
	 */
	public MainTestRunjar() {
		
		this.prop = System.getProperties();
		this.resourceBundle = ResourceBundle.getBundle(this.getClass().getName().replace('.', '/'));
		
		if (flag) System.out.println(String.format("System.getProperties -> [%s] = [%s]", KEY_SYSTEM_DESC, this.prop.getProperty(KEY_SYSTEM_DESC)));
		if (flag) System.out.println(String.format("ResourceBundle.getBundle -> [%s] = [%s]", KEY_RUNJAR_DESC, this.resourceBundle.getString(KEY_RUNJAR_DESC)));
		if (flag) System.out.println(String.format("ResourceBundle.getBundle -> [%s] = [%s]", KEY_MAIN_CLASS, this.resourceBundle.getString(KEY_MAIN_CLASS)));
		
		if (flag)
			log.debug(">>>>> in class " + this.getClass().getSimpleName());
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public String getMainClass() throws Exception {
		return this.resourceBundle.getString(KEY_MAIN_CLASS);
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
			 * start classLoader
			 */
			String mainClass = new MainTestRunjar().getMainClass();
			if (flag) System.out.println();
			
			Class<?> cls = Class.forName(mainClass);
			Method main = cls.getMethod("main", new Class[] { args.getClass() });
			main.invoke((Object) null, new Object[] { args });
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
