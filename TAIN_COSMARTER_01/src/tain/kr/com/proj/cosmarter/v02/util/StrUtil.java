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
package tain.kr.com.proj.cosmarter.v02.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Code Templates > Comments > Types
 *
 * <PRE>
 *   -. FileName   : StrUtil.java
 *   -. Package    : tain.kr.com.proj.cosmarter.v01.util
 *   -. Comment    :
 *   -. Author     : taincokr
 *   -. First Date : 2016. 4. 18. {time}
 * </PRE>
 *
 * @author taincokr
 *
 */
public class StrUtil {

	private static boolean flag = true;

	private static final Logger log = Logger.getLogger(StrUtil.class);

	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public static String list2str(List<String> lst) throws Exception {
		
		if (lst == null) {
			return "";
		}
		
		StringBuffer sb = new StringBuffer();
		
		for (int i=0; i < lst.size(); i++) {
			if (i > 0)
				sb.append("|");
			sb.append(lst.get(i));
		}
		
		return sb.toString();
	}
	
	public static String arr2str(String[] arr) throws Exception {
		
		if (arr == null) {
			return "";
		}
		
		StringBuffer sb = new StringBuffer();
		
		for (int i=0; i < arr.length; i++) {
			if (i > 0)
				sb.append("|");
			sb.append(arr[i]);
		}
		
		return sb.toString();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	private static void test01(String[] args) throws Exception {
		
		if (flag) {
			List<String> lst = new ArrayList<String>();
			
			lst.add("1234");
			lst.add("3456");
			
			log.debug(">>> " + StrUtil.list2str(lst));
		}
	}
	
	private static void test02(String[] args) throws Exception {
		
		if (flag) {
			String line = "tcp6       0      0 :::7412                 :::*                    LISTEN      1476/java       ";
			String[] words = line.split(" ");
			if (!flag) words = line.split("\\s+");
			
			for (String word : words) {
				log.debug("[" + word + "]");
			}
			
			log.debug("---------------------------------------------------");
		}
		
		if (flag) {
			String line = "tcp6       0      0 :::7412                 :::*                    LISTEN      1476/java       ";
			String[] words = line.split("\\s+");
			
			for (String word : words) {
				log.debug("[" + word + "]");
			}
			
			log.debug("---------------------------------------------------");
		}
	}
	
	public static void main(String[] args) throws Exception {
		
		if (flag) log.debug(">>>>> " + new Object(){}.getClass().getEnclosingClass().getName());
		
		if (flag) test01(args);
		if (flag) test02(args);
	}
}
