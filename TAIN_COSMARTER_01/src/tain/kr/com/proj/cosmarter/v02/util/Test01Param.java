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
package tain.kr.com.proj.cosmarter.v02.util;

import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

/**
 * Code Templates > Comments > Types
 *
 * <PRE>
 *   -. FileName   : Test01Param.java
 *   -. Package    : tain.kr.com.proj.cosmarter.v02.util
 *   -. Comment    :
 *   -. Author     : taincokr
 *   -. First Date : 2017. 3. 11. {time}
 * </PRE>
 *
 * @author taincokr
 *
 */
public final class Test01Param {

	private static boolean flag = true;

	private static final Logger log = Logger.getLogger(Test01Param.class);

	///////////////////////////////////////////////////////////////////////////////////////////////
	
	protected static final String FILE_NAME_RESOURCES = "resources/resources";

	protected final Properties prop;
	protected final ResourceBundle resourceBundle;

	///////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * constructor
	 */
	private Test01Param() {
		
		this.prop = System.getProperties();
		this.resourceBundle = ResourceBundle.getBundle(FILE_NAME_RESOURCES);

		if (flag)
			log.debug(">>>>> in class " + this.getClass().getSimpleName());
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private String getStringFromSystem(String key) {
		return this.prop.getProperty(key);
	}
	
	private String getStringFromResourceBundle(String key) {
		
		String strValue = null;
		
		try {
			strValue = this.resourceBundle.getString(key);
		} catch (MissingResourceException e) {}
		
		return strValue;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public String getString(String key) {
		
		String value;
		
		value = getStringFromSystem(key);
		if (value != null)
			return value;
		
		value = getStringFromResourceBundle(key);
		
		return value;
	}
	
	public String getString(String key, String defaultValue) {
		
		String value = getString(key);
		if (value == null)
			return defaultValue;
		
		return value;
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
	
	private static Test01Param instance = null;
	
	public static synchronized Test01Param getInstance() throws Exception {
		
		if (Test01Param.instance == null)
			Test01Param.instance = new Test01Param();
		
		return Test01Param.instance;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * static test method
	 */
	private static void test01(String[] args) throws Exception {

		if (flag) {
			/*
			 * System.getProperties
			 * resources/resources.properties
			 */
			String strKey = "tain.project";
			
			if (flag) System.out.printf("Param : [%s] = [%s]\n", strKey, Test01Param.getInstance().getString(strKey));
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
