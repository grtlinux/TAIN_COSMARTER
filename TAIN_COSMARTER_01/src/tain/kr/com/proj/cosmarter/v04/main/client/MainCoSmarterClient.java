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
package tain.kr.com.proj.cosmarter.v04.main.client;

import org.apache.log4j.Logger;

import tain.kr.com.proj.cosmarter.v04.bean.BeanCommand;

/**
 * Code Templates > Comments > Types
 *
 * <PRE>
 *   -. FileName   : MainCoSmarterClient.java
 *   -. Package    : tain.kr.com.proj.cosmarter.v04.main.client
 *   -. Comment    :
 *   -. Author     : taincokr
 *   -. First Date : 2017. 3. 17. {time}
 * </PRE>
 *
 * @author taincokr
 *
 */
public final class MainCoSmarterClient {

	private static boolean flag = true;

	private static final Logger log = Logger
			.getLogger(MainCoSmarterClient.class);

	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * constructor
	 */
	public MainCoSmarterClient() throws Exception {
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
	///////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * static test method
	 */
	private static void test01(String[] args) throws Exception {

		if (flag) {
			if (flag) {
				/*
				 * set BeanCommand object
				 */
				BeanCommand bean = new BeanCommand();
				
				bean.setName("testCommand");
				bean.setDesc("test command");
				
				bean.setHost("127.0.0.1");
				bean.setPort("7412");
				
				bean.setCmd(new String[] { "cmd", "/c", "dir" });
				bean.setEnv(new String[] { "PARAM1=hello", "PARAM2=world" });
				bean.setDir("./");
				bean.setArgs(null);
				
				bean.setSkipCmd(new String[] { "W", "L2", "L10", "R3-7", "Y오전", "Y오후" });
				bean.setFldName(new String[] { "일자", "구분", "시간", "정보" });
				if (flag) bean.print();
				
				/*
				 * do business
				 */
				BeanClient.getInstance().process(bean);
				
				if (flag) log.debug(String.format(">>>>> result <<<<<\n%s\n", bean.getResult()));
			}
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
