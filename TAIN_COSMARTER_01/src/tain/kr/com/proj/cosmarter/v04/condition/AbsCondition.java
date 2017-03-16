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
package tain.kr.com.proj.cosmarter.v04.condition;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Code Templates > Comments > Types
 *
 * <PRE>
 *   -. FileName   : AbsCondition.java
 *   -. Package    : tain.kr.com.proj.cosmarter.v04.condition
 *   -. Comment    :
 *   -. Author     : taincokr
 *   -. First Date : 2017. 3. 17. {time}
 * </PRE>
 *
 * @author taincokr
 *
 */
public abstract class AbsCondition {

	private static boolean flag = true;

	private static final Logger log = Logger.getLogger(AbsCondition.class);

	///////////////////////////////////////////////////////////////////////////////////////////////
	
	protected final String skipCmd;
	
	///////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * constructor
	 */
	public AbsCondition(String skipCmd) {
		
		this.skipCmd = skipCmd;
		
		if (!flag)
			log.debug(">>>>> in class " + this.getClass().getSimpleName());
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public abstract boolean check(int lineNo, String line) throws Exception;
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private static boolean flagY = false;
	private static List<AbsCondition> lstConditions;
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public static void setConditions(String[] skipCmd) throws Exception {
		
		lstConditions = new ArrayList<AbsCondition>();
		
		for (int i=0; i < skipCmd.length; i++) {
			if (flag) {
				/*
				 * arrange skipCmd
				 */
				skipCmd[i] = skipCmd[i].replaceAll("\\s+", "");
			}
			
			if (!flagY && skipCmd[i].startsWith("W")) {
				/*
				 * skip in white space line
				 */
				lstConditions.add(new WCondition(skipCmd[i]));
			} else if (!flagY && skipCmd[i].startsWith("L")) {
				/*
				 * skip in line number
				 */
				lstConditions.add(new LCondition(skipCmd[i]));
			} else if (!flagY && skipCmd[i].startsWith("R")) {
				/*
				 * skip in range between from and to.
				 */
				lstConditions.add(new RCondition(skipCmd[i]));
			} else if (!flagY && skipCmd[i].startsWith("N")) {
				/*
				 * skip with the word in the line
				 */
				lstConditions.add(new NCondition(skipCmd[i]));
			} else if (skipCmd[i].startsWith("Y")) {
				/*
				 * choose the line with the word only.
				 */
				if (!flagY) lstConditions.clear();
				flagY = true;
				
				lstConditions.add(new YCondition(skipCmd[i]));
			}
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////

	public static boolean scanConditions(int lineNo, String line) throws Exception {
		
		for (AbsCondition condition : lstConditions) {
			/*
			 * if return value of condition is false, then skip
			 */
			if (!condition.check(lineNo, line))
				return false;
		}
		
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * static test method
	 */
	private static void test01(String[] args) throws Exception {

		if (flag) {

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
