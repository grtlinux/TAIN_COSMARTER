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

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;


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
	
	private static String[] splitSpaces(String line) throws Exception {
	
		if (line != null) {
			List<String> list = new ArrayList<String>();
			
			String[] arr = line.split("\\s");
			for (String str : arr) {
				if (!"".equals(str = str.trim()))
					list.add(str);
			}
			
			return (String[]) list.toArray(new String[list.size()]);
		}
		
		return null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private static ManifestInfo getManifestInfo() throws Exception {
		
		if (flag) System.out.printf("\t 1) JarFile.MANIFEST_NAME = [%s]\n\n", JarFile.MANIFEST_NAME);
		
		Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(JarFile.MANIFEST_NAME);
		while (urls.hasMoreElements()) {
			URL url = (URL) urls.nextElement();
			if (flag) System.out.printf("\t\t 2) url = [%s]\n", url);
			
			InputStream is = url.openStream();
			if (is != null) {
				Manifest manifest = new Manifest(is);
				Attributes attributes = manifest.getMainAttributes();
				
				if (flag) {
					/*
					 * information of attributes
					 */
					for (Map.Entry<Object, Object> entry : attributes.entrySet()) {
						String key = String.valueOf(entry.getKey());
						String val = String.valueOf(entry.getValue());
						if (flag) System.out.printf("\t\t\t 3) [%s] = [%s]\n", key, val);
					}
					if (flag) System.out.println();
				}
				
				ManifestInfo manifestInfo = new ManifestInfo();
				
				manifestInfo.rsrcMainClass = attributes.getValue("Rsrc-Main-Class").trim();
				manifestInfo.rsrcClassPath = splitSpaces(attributes.getValue("Rsrc-Class-Path"));
				
				if (flag && (manifestInfo.rsrcMainClass != null))
					return manifestInfo;
			}
			
			if (flag) break;
		}
		
		if (flag) System.err.printf("Missing attributes for JarRsrcLoader in Manifest (%s, %s)\n", "Rsrc-Main-Class", "Rsrc-Class-Path");
		
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
			if (flag) System.out.printf("\t\t\t 4) [%s] = [%s]\n", manifestInfo.rsrcMainClass, new ArrayList<String>(Arrays.asList(manifestInfo.rsrcClassPath)));
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
