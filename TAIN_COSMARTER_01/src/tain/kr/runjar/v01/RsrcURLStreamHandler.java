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
package tain.kr.runjar.v01;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

/**
 * Code Templates > Comments > Types
 *
 * <PRE>
 *   -. FileName   : RsrcURLStreamHandler.java
 *   -. Package    : tain.kr.com.test.runJar.v01
 *   -. Comment    :
 *                   This class will be compiled into the binary jar-in-jar-loader.zip. This ZIP is used for the "Runnable JAR File Exporter"
 *                   
 *                   Handle URLs with protocol "rsrc". "rsrc:path/file.ext" identifies the contents accessible as 
 *                   classLoader.getResourceAsStream("path/file.ext"). "rsrc:path/" identifies a base-path for
 *                   resources to be searched. The spec "file.ext" is combined to "rsrc:path/file.ext."
 *   -. Author     : taincokr
 *   -. First Date : 2016. 3. 28. {time}
 * </PRE>
 *
 * @author taincokr
 *
 */
public class RsrcURLStreamHandler extends URLStreamHandler {

	//private static boolean flag = true;

	//private static final Logger log = Logger.getLogger(RsrcURLStreamHandler.class);

	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private ClassLoader classLoader;
	
	public RsrcURLStreamHandler(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}
	
	protected URLConnection openConnection(URL u) throws IOException {
		return new RsrcURLConnection(u, this.classLoader);
	}
	
	protected void parseURL(URL url, String spec, int start, int limit) {
		String file;
		
		if (spec.startsWith(JIJConstants.INTERNAL_URL_PROTOCOL_WITH_COLON)) {
			file = spec.substring(5);
		} else if (url.getFile().equals(JIJConstants.CURRENT_DIR)) {
			file = spec;
		} else if (url.getFile().endsWith(JIJConstants.PATH_SEPARATOR)) {
			file = url.getFile() + spec;
		} else {
			file = spec;
		}
		
		setURL(url, JIJConstants.INTERNAL_URL_PROTOCOL, "", -1, null, null, file, null, null);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////

}
