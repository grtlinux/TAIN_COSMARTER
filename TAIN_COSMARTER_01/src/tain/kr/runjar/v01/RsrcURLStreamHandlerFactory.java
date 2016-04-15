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

import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;

/**
 * Code Templates > Comments > Types
 *
 * <PRE>
 *   -. FileName   : RsrcURLStreamHandlerFactory.java
 *   -. Package    : tain.kr.com.test.runJar.v01
 *   -. Comment    :
 *                   This class will be compiled into the binary jar-in-jar-loader.zip. This ZIP is used for the "Runnable JAR File Exporter"
 *   -. Author     : taincokr
 *   -. First Date : 2016. 3. 28. {time}
 * </PRE>
 *
 * @author taincokr
 *
 */
public class RsrcURLStreamHandlerFactory implements URLStreamHandlerFactory {

	//private static boolean flag = true;

	//private static final Logger log = Logger.getLogger(RsrcURLStreamHandlerFactory.class);

	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private ClassLoader classLoader;
	private URLStreamHandlerFactory chainFac;
	
	public RsrcURLStreamHandlerFactory(ClassLoader cl) {
		
		this.classLoader = cl;
	}
	
	public URLStreamHandler createURLStreamHandler(String protocol) {
		
		if (JIJConstants.INTERNAL_URL_PROTOCOL.equals(protocol)) {
			return new RsrcURLStreamHandler(this.classLoader);
		}
		
		if (this.chainFac != null) {
			return this.chainFac.createURLStreamHandler(protocol);
		}
		
		return null;
	}
	
	/**
	 * 
	 * Code Templates > Comments > Methods
	 *
	 * <PRE>
	 *   -. ClassName  : RsrcURLStreamHandlerFactory
	 *   -. MethodName : setURLStreamHandlerFactory
	 *   -. Comment    :
	 *                   Allow on other URLStreamHandler to be added.
	 *                   URL.setURLStreamHandlerFactory does not allow
	 *                   multiple factories to be added.
	 *                   The Chained factory is called for all other protocols,
	 *                   except "rsrc". Use null to clear previously set Handler.
	 *                   @param fac another factory to be chained with ours.
	 *   -. Author     : taincokr
	 *   -. First Date : 2016. 3. 28. {time}
	 * </PRE>
	 *
	 * @param fac
	 */
	public void setURLStreamHandlerFactory(URLStreamHandlerFactory fac) {
		this.chainFac = fac;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
}
