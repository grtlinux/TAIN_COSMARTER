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
package tain.kr.com.proj.cosmarter.v04.bean;

import java.util.ArrayList;
import java.util.Arrays;

import tain.kr.com.proj.cosmarter.v02.util.CheckSystem;

import com.google.gson.Gson;

/**
 * Code Templates > Comments > Types
 *
 * <PRE>
 *   -. FileName   : BeanCommand.java
 *   -. Package    : tain.kr.com.proj.cosmarter.v04.bean
 *   -. Comment    :
 *   -. Author     : taincokr
 *   -. First Date : 2017. 3. 17. {time}
 * </PRE>
 *
 * @author taincokr
 *
 */
public final class BeanCommand {

	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private String name = "cmdName";
	private String desc = "command description";
	
	private String host = "127.0.0.1";
	private String port = "7412";
	
	private String[] cmd;
	private String[] env = null;
	private String dir = "./";
	private String[] args = null;
	
	private String[] skipCmd = new String[] { "W" };
	private String[] fldName = new String[] { "fld1" };
	
	private String result = null;
	
	///////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * constructor
	 */
	public BeanCommand() throws Exception {
		
		this.cmd = CheckSystem.getInstance().isWindows() ? new String[] { "cmd", "/c", "dir" } : new String[] { "/bin/sh", "-c", "ls -al" };
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	/*
	 * getter
	 */
	
	public String getName() {
		return name;
	}

	public String getDesc() {
		return desc;
	}

	public String getHost() {
		return host;
	}

	public String getPort() {
		return port;
	}

	public String[] getCmd() {
		return cmd;
	}

	public String[] getEnv() {
		return env;
	}

	public String getDir() {
		return dir;
	}

	public String[] getArgs() {
		return args;
	}

	public String[] getSkipCmd() {
		return skipCmd;
	}

	public String[] getFldName() {
		return fldName;
	}

	public String getResult() {
		return result;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	/*
	 * setter
	 */
	
	public void setName(String name) {
		this.name = name;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public void setCmd(String[] cmd) {
		this.cmd = cmd;
	}

	public void setEnv(String[] env) {
		this.env = env;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public void setArgs(String[] args) {
		this.args = args;
	}

	public void setSkipCmd(String[] skipCmd) {
		this.skipCmd = skipCmd;
	}

	public void setFldName(String[] fldName) {
		this.fldName = fldName;
	}

	public void setResult(String result) {
		this.result = result;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public void print() {
	
		System.out.println("\t=========================== BeanCommand =================================");
		
		System.out.printf("\t[name]    = [%s]\n", this.name);
		System.out.printf("\t[desc]    = [%s]\n", this.desc);
		System.out.printf("\t[host]    = [%s]\n", this.host);
		System.out.printf("\t[port]    = [%s]\n", this.port);
		System.out.printf("\t[cmd]     = %s\n"  , this.cmd == null ? "<NULL>" : new ArrayList<String>(Arrays.asList(this.cmd)));
		System.out.printf("\t[env]     = %s\n"  , this.env == null ? "<NULL>" : new ArrayList<String>(Arrays.asList(this.env)));
		System.out.printf("\t[dir]     = [%s]\n", this.dir);
		System.out.printf("\t[args]    = %s\n"  , this.args == null ? "<NULL>" : new ArrayList<String>(Arrays.asList(this.args)));
		System.out.printf("\t[skipCmd] = %s\n"  , this.skipCmd == null ? "<NULL>" : new ArrayList<String>(Arrays.asList(this.skipCmd)));
		System.out.printf("\t[fldName] = %s\n"  , this.fldName == null ? "<NULL>" : new ArrayList<String>(Arrays.asList(this.fldName)));
		System.out.printf("\t=> [result] = [%s]\n", this.result);

		System.out.println("\t-------------------------------------------------------------------------");
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
	
	public static void main(String[] args) throws Exception {
		
		Gson gson = new Gson();
		
		BeanCommand beanCmdBefore = new BeanCommand();
		beanCmdBefore.print();
		
		String strGson = gson.toJson(beanCmdBefore);
		
		BeanCommand beanCmdAfter = gson.fromJson(strGson, BeanCommand.class);
		beanCmdAfter.print();
	}
}
