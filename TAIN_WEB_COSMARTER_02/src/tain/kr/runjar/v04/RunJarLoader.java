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
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;


/**
 * Code Templates > Comments > Types
 *
 * <PRE>
 *   -. FileName   : RunJarLoader.java
 *   -. Package    : tain.kr.runjar.v04
 *   -. Comment    :
 *   -. Author     : taincokr
 *   -. First Date : 2017. 3. 4. {time}
 * </PRE>
 *
 * @author taincokr
 *
 */
public final class RunJarLoader {

	private static boolean flag = true;

	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * constructor
	 */
	public RunJarLoader() {}

	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private static class ManifestInfo {
		String rsrcMainClass;
		String[] rsrcClassPath;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private static String[] splitSpaces(String line) throws Exception {
	
		if (line != null) {
			List<String> list = new ArrayList<String>();
			
			String[] arr = line.split("\\s+");
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
		
		Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(JarFile.MANIFEST_NAME);
		while (urls.hasMoreElements()) {
			URL url = (URL) urls.nextElement();
			InputStream is = url.openStream();
			if (is != null) {
				Manifest manifest = new Manifest(is);
				Attributes attributes = manifest.getMainAttributes();
				
				ManifestInfo manifestInfo = new ManifestInfo();
				
				manifestInfo.rsrcMainClass = attributes.getValue("Rsrc-Main-Class").trim();
				manifestInfo.rsrcClassPath = splitSpaces(attributes.getValue("Rsrc-Class-Path"));
				
				if (manifestInfo.rsrcMainClass != null)
					return manifestInfo;
			}
			
			if (flag) break;
		}

		if (flag) throw new Exception("Missing attributes for JarRsrcLoader in Manifest (Rsrc-Main-Class, Rsrc-Class-Path)");
		
		return null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final String KEY_RSRC_MAIN_CLASS = "rsrcMainClass";
	
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
			
			if (flag) {
				/*
				 * change the rsrcMainClass value
				 */
				manifestInfo.rsrcMainClass = System.getProperty(KEY_RSRC_MAIN_CLASS, manifestInfo.rsrcMainClass);
			}
			
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			URL.setURLStreamHandlerFactory(new RsrcURLStreamHandlerFactory(classLoader));
			
			URL[] rsrcUrls = new URL[manifestInfo.rsrcClassPath.length];
			for (int i=0; i < manifestInfo.rsrcClassPath.length; i++) {
				String rsrcPath = manifestInfo.rsrcClassPath[i];
				
				if (rsrcPath.endsWith("/"))
					rsrcUrls[i] = new URL("rsrc:" + rsrcPath);
				else
					rsrcUrls[i] = new URL("jar:rsrc:" + rsrcPath + "!/");
			}
			
			if (flag) {
				/*
				 * start classLoader
				 */
				ClassLoader jceClassLoader = new URLClassLoader(rsrcUrls, null);
				Thread.currentThread().setContextClassLoader(jceClassLoader);
				
				Class<?> cls = Class.forName(manifestInfo.rsrcMainClass, true, jceClassLoader);
				Method main = cls.getMethod("main", new Class[] { args.getClass() });
				main.invoke((Object) null, new Object[] { args });
			}
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
