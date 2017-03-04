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

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

/**
 * Code Templates > Comments > Types
 *
 * <PRE>
 *   -. FileName   : RsrcURLStreamHandler.java
 *   -. Package    : tain.kr.runjar.v04
 *   -. Comment    :
 *   -. Author     : taincokr
 *   -. First Date : 2017. 3. 4. {time}
 * </PRE>
 *
 * @author taincokr
 *
 */
public final class RsrcURLStreamHandler extends URLStreamHandler {

	private static boolean flag = true;

	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private ClassLoader classLoader = null;
	
	///////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * constructor
	 */
	public RsrcURLStreamHandler(ClassLoader classLoader) {
		
		this.classLoader = classLoader;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	protected void parseURL(URL url, String spec, int start, int limit) {

		if (!flag) System.out.printf("%s >>>>> (URL=%s, spec=%s, start=%d, limit=%d)\n"
				, this.getClass().getName(), url, spec, start, limit);
		
		String file;
		
		if (spec.startsWith("rsrc:"))
			file = spec.substring(5);
		else if (url.getFile().equals("./"))
			file = spec;
		else if (url.getFile().endsWith("/"))
			file = url.getFile() + spec;
		else
			file = spec;
		
		setURL(url, "rsrc", "", -1, null, null, file, null, null);

		if (!flag) System.out.printf("%s >>>>> file=[%s], url.getFile()=[%s]\n"
				, this.getClass().getName(), file, url.getFile());
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////

	/* (non-Javadoc)
	 * @see java.net.URLStreamHandler#openConnection(java.net.URL)
	 */
	@Override
	protected URLConnection openConnection(URL url) throws IOException {
		
		return new RsrcURLConnection(url, this.classLoader);
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
}
