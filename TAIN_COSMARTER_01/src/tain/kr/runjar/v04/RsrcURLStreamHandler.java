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

	@SuppressWarnings("unused")
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
	
	@Override
	protected void parseURL(URL url, String spec, int start, int limit) {

		String file;
		
		if (spec.startsWith("rsrc:"))
			file = spec.substring(5);
		else if (url.getFile().equals("./"))
			file = spec;
		else if (url.getFile().endsWith("/"))
			file = url.getFile() + spec;
		else
			file = spec;
		
		/*
		 * 
		 *  * protected void setURL(URL u,
		 *                        String protocol,
		 *                        String host,
		 *                        int port,
		 *                        String authority,
		 *                        String userInfo,
		 *                        String path,
		 *                        String query,
		 *                        String ref);
		 *
		 *  * Parameters:
		 *      u - the URL to modify.
		 *      protocol - the protocol name.
		 *      host - the remote host value for the URL.
		 *      port - the port on the remote machine.
		 *      authority - the authority part for the URL.
		 *      userInfo - the userInfo part of the URL.
		 *      path - the path component of the URL.
		 *      query - the query part for the URL.
		 *      ref - the reference.
		 *
		 *  * Sets the fields of the URL argument to the indicated values.
		 *  Only classes derived from URLStreamHandler are supposed
		 *  to be able to call the set method on a URL.
		 *
		 */
		setURL(url, "rsrc", "", -1, null, null, file, null, null);
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
