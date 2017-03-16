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
	
	private static AbsCondition[] conditions;
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public static void setConditions(String[] skipCmd) throws Exception {
		
		conditions = new AbsCondition[skipCmd.length];
		
		for (int i=0; i < skipCmd.length; i++) {
			if (flag) {
				/*
				 * arrange skipCmd
				 */
				skipCmd[i] = skipCmd[i].replaceAll("\\s+", "").toUpperCase();
			}
			
			if (skipCmd[i].startsWith("W")) {
				/*
				 * skip in white space line
				 */
				conditions[i] = new WCondition(skipCmd[i]);
			} else if (skipCmd[i].startsWith("L")) {
				/*
				 * skip in line number
				 */
				conditions[i] = new LCondition(skipCmd[i]);
			} else if (skipCmd[i].startsWith("R")) {
				/*
				 * skip in range between from and to.
				 */
				conditions[i] = new RCondition(skipCmd[i]);
			} else if (skipCmd[i].startsWith("N")) {
				/*
				 * skip with the word in the line
				 */
				conditions[i] = new NCondition(skipCmd[i]);
			} else if (skipCmd[i].startsWith("Y")) {
				/*
				 * choose the line with the word only.
				 */
				conditions = new AbsCondition[1];
				conditions[0] = new YCondition(skipCmd[i]);
				return;
			} else {
				throw new Exception(String.format("couldn't be parsing '%s'.", skipCmd[i]));
			}
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////

	public static boolean scanConditions(int lineNo, String line) throws Exception {
		
		for (int i=0; i < conditions.length; i++) {
			if (conditions[i].check(lineNo, line))
				return true;
		}
		
		return false;
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
