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
package tain.kr.runjar.v02;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import org.apache.log4j.Logger;

/**
 * Code Templates > Comments > Types
 *
 * <PRE>
 *   -. FileName   : JarRsrcLoader.java
 *   -. Package    : tain.kr.com.test.runJar.v02
 *   -. Comment    :
 *   -. Author     : taincokr
 *   -. First Date : 2016. 4. 15. {time}
 * </PRE>
 *
 * @author taincokr
 *
 */
public class JarRsrcLoader {

	private static boolean flag = true;

	private static final Logger log = Logger.getLogger(JarRsrcLoader.class);

	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private static class ManifestInfo {
		String rsrcMainClass;
		String[] rsrcClassPath;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private static String[] splitSpaces(String line) throws Exception {
		
		if (line == null)
			return null;
		
		if (!flag) log.debug("line = [" + line + "]");
		
		List<String> result = new ArrayList<String>();
		
		int firstPos = 0;
		while (firstPos < line.length()) {
			int lastPos = line.indexOf(' ', firstPos);
			if (lastPos == -1) {
				lastPos = line.length();
			}
			
			if (lastPos > firstPos) {
				result.add(line.substring(firstPos, lastPos));
			}
			
			firstPos = lastPos + 1;
		}
		
		return (String[]) result.toArray(new String[result.size()]);
	}
	
	private static ManifestInfo getManifestInfo() throws Exception {
		
		Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(JarFile.MANIFEST_NAME);
		while (urls.hasMoreElements()) {
			URL url = (URL) urls.nextElement();
			
			if (!flag) log.debug(">>>>> " + url.toString());
			
			InputStream is = url.openStream();
			if (is != null) {
				Manifest manifest = new Manifest(is);
				Attributes attributes = manifest.getMainAttributes();
				
				if (!flag) {
					for (Map.Entry<Object, Object> entry : attributes.entrySet()) {
						String key = String.valueOf(entry.getKey());
						String val = String.valueOf(entry.getValue());
						
						if (flag) log.debug("[" + key + "] = [" + val + "]");
					}
				}

				// return value
				ManifestInfo manifestInfo = new ManifestInfo();
				
				manifestInfo.rsrcMainClass = attributes.getValue(JIJConstants.REDIRECTED_MAIN_CLASS_MANIFEST_NAME);
				
				String rsrcClassPath = attributes.getValue(JIJConstants.REDIRECTED_CLASS_PATH_MANIFEST_NAME);
				if (rsrcClassPath == null)
					rsrcClassPath = JIJConstants.DEFAULT_REDIRECTED_CLASSPATH;
				manifestInfo.rsrcClassPath = splitSpaces(rsrcClassPath);
				
				if ((manifestInfo.rsrcMainClass != null) && !manifestInfo.rsrcMainClass.trim().equals(""))
					return manifestInfo;
			}
			
			break;
		}
		
		System.err.println("Missing attributes for JarRsrcLoader in Manifest (" + JIJConstants.REDIRECTED_MAIN_CLASS_MANIFEST_NAME + ", " + JIJConstants.REDIRECTED_CLASS_PATH_MANIFEST_NAME + ")");
		
		return null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////

	private static void test01(String[] args) throws Exception {
		
		if (flag) {
			Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(JarFile.MANIFEST_NAME);
			while (urls.hasMoreElements()) {
				URL url = (URL) urls.nextElement();

				if (flag) log.debug(">>>>> ");
				if (flag) log.debug(">>>>> " + url.toString());
				
				InputStream is = url.openStream();
				if (is != null) {
					Manifest manifest = new Manifest(is);
					Attributes attributes = manifest.getMainAttributes();
					
					if (flag) {
						for (Map.Entry<Object, Object> entry : attributes.entrySet()) {
							String key = String.valueOf(entry.getKey());
							String val = String.valueOf(entry.getValue());
							
							if (flag) log.debug("[" + key + "] = [" + val + "]");
						}
					}
				}
			}
			
			if (flag) log.debug(">>>>> ");
		}
	}
	
	private static void test02(String[] args) throws Exception {
		
		if (!flag) {
			ManifestInfo manifestInfo = getManifestInfo();
			if (manifestInfo != null) {
				if (flag) log.debug("rsrcMainClass >>> " + manifestInfo.rsrcMainClass);
				
				for (String classPath : manifestInfo.rsrcClassPath) {
					if (flag) log.debug("rsrcClassPath >>> " + classPath);
				}
			}
		}
		
		if (flag) {
			
			if (!flag) args = new String[]{ "one", "two", "three", "four" };
			
			ManifestInfo manifestInfo = getManifestInfo();
			
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			URL.setURLStreamHandlerFactory(new RsrcURLStreamHandlerFactory(classLoader));
			
			URL[] rsrcUrls = new URL[manifestInfo.rsrcClassPath.length];
			
			for (int i=0; i < manifestInfo.rsrcClassPath.length; i++) {
				
				String rsrcPath = manifestInfo.rsrcClassPath[i];
				
				if (rsrcPath.endsWith(JIJConstants.PATH_SEPARATOR))
					rsrcUrls[i] = new URL(JIJConstants.INTERNAL_URL_PROTOCOL_WITH_COLON + rsrcPath);
				else
					rsrcUrls[i] = new URL(JIJConstants.JAR_INTERNAL_URL_PROTOCOL_WITH_COLON + rsrcPath + JIJConstants.JAR_INTERNAL_SEPARATOR);
			}
			
			ClassLoader jceClassLoader = new URLClassLoader(rsrcUrls, null);
			Thread.currentThread().setContextClassLoader(jceClassLoader);
			
			Class<?> cls = Class.forName(manifestInfo.rsrcMainClass, true, jceClassLoader);
			Method main = cls.getMethod(JIJConstants.MAIN_METHOD_NAME, new Class[] {args.getClass()});
			main.invoke((Object) null, new Object[]{args});
		}
	}
	
	public static void main(String[] args) throws Exception {
		
		if (flag) log.debug(">>>>> " + new Object(){}.getClass().getEnclosingClass().getName());
		
		if (!flag) test01(args);
		if (flag) test02(args);
	}
}
