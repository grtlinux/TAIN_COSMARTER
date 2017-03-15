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
package tain.kr.com.proj.cosmarter.v03.main.client;

import org.apache.log4j.Logger;

import tain.kr.com.proj.cosmarter.v03.bean.SimpleBean;
import tain.kr.com.proj.cosmarter.v03.util.Param;

/**
 * Code Templates > Comments > Types
 *
 * <PRE>
 *   -. FileName   : CoBeanClient.java
 *   -. Package    : tain.kr.com.proj.cosmarter.v03.main.client
 *   -. Comment    :
 *   -. Author     : taincokr
 *   -. First Date : 2017. 3. 15. {time}
 * </PRE>
 *
 * @author taincokr
 *
 */
public final class CoBeanClient {

	private static boolean flag = true;

	private static final Logger log = Logger.getLogger(CoBeanClient.class);

	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final String KEY_CLIENT_HOST = "tain.cosmarter.v03.client.host";
	private static final String KEY_CLIENT_PORT = "tain.cosmarter.v03.client.port";
	
	private final String host;
	private final String port;
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final String KEY_CLIENT_CHARSET = "tain.cosmarter.v03.client.charset";
	private final String charSet;
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * constructor
	 */
	private CoBeanClient() throws Exception {
		
		this.host = Param.getInstance().getString(KEY_CLIENT_HOST, "NO_HOST");
		this.port = Param.getInstance().getString(KEY_CLIENT_PORT, "NO_PORT");
		
		this.charSet = Param.getInstance().getString(KEY_CLIENT_CHARSET, "NO_CHARSET");
		
		if (flag)
			log.debug(">>>>> in class " + this.getClass().getSimpleName());
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	public void process() throws Exception {
		
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
	
	private static CoBeanClient instance = null;
	
	public static CoBeanClient getInstance() throws Exception {
		
		if (CoBeanClient.instance == null) {
			CoBeanClient.instance = new CoBeanClient();
		}
		
		return CoBeanClient.instance;
	}
	
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
			SimpleBean bean = new SimpleBean();
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
