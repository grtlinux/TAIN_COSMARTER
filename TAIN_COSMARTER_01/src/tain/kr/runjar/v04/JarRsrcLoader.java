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

import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarFile;


/**
 * Code Templates > Comments > Types
 *
 * <PRE>
 *   -. FileName   : JarRsrcLoader.java
 *   -. Package    : tain.kr.runjar.v04
 *   -. Comment    :
 *   -. Author     : taincokr
 *   -. First Date : 2017. 3. 4. {time}
 * </PRE>
 *
 * @author taincokr
 *
 */
public final class JarRsrcLoader {

	private static boolean flag = true;

	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * constructor
	 */
	public JarRsrcLoader() {}

	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private static class ManifestInfo {
		String rsrcMainClass;
		String[] rsrcClassPath;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private static ManifestInfo getManifestInfo() throws Exception {
		
		if (flag) System.out.printf("\t 1) JarFile.MANIFEST_NAME = [%s]\n\n", JarFile.MANIFEST_NAME);
		
		Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(JarFile.MANIFEST_NAME);
		while (urls.hasMoreElements()) {
			URL url = (URL) urls.nextElement();
			if (flag) System.out.printf("\t\t 2) url = [%s]\n", url);
		}
		
		return null;
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

	/*
	 * static test method
	 */
	private static void test01(String[] args) throws Exception {

		if (flag) {
			/*
			 * begin
			 */
			ManifestInfo manifestInfo = getManifestInfo();
		}
	}

	/*
	 * main method
	 */
	public static void main(String[] args) throws Exception {

		if (flag)
			test01(args);
	}
}
