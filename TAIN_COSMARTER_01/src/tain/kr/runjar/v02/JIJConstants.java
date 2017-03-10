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


/**
 * Code Templates > Comments > Types
 *
 * <PRE>
 *   -. FileName   : JIJConstants.java
 *   -. Package    : tain.kr.com.test.runJar.v02
 *   -. Comment    :
 *                   Constants used in the Jar-in-Jar loader.
 *                   Some of these are duplicated in JIJConstants in the source for the Runnable Jar File export Wizard:
 *                   {@link org.eclipse.jdt.internal.ui.jarpackagerfat}
 *   -. Author     : taincokr
 *   -. First Date : 2016. 3. 28. {time}
 * </PRE>
 *
 * @author taincokr
 *
 */
final class JIJConstants {

	//private static boolean flag = true;

	//private static final Logger log = Logger.getLogger(RsrcURLStreamHandlerFactory.class);

	///////////////////////////////////////////////////////////////////////////////////////////////
	
	static final String REDIRECTED_CLASS_PATH_MANIFEST_NAME  = "Rsrc-Class-Path";  //$NON-NLS-1$
	static final String REDIRECTED_MAIN_CLASS_MANIFEST_NAME  = "Rsrc-Main-Class";  //$NON-NLS-1$
	static final String DEFAULT_REDIRECTED_CLASSPATH         = "";  //$NON-NLS-1$
	static final String MAIN_METHOD_NAME                     = "main";  //$NON-NLS-1$
	static final String JAR_INTERNAL_URL_PROTOCOL_WITH_COLON = "jar:rsrc:";  //$NON-NLS-1$
	static final String JAR_INTERNAL_SEPARATOR               = "!/";  //$NON-NLS-1$
	static final String INTERNAL_URL_PROTOCOL_WITH_COLON     = "rsrc:";  //$NON-NLS-1$
	static final String INTERNAL_URL_PROTOCOL                = "rsrc";  //$NON-NLS-1$
	static final String PATH_SEPARATOR                       = "/";  //$NON-NLS-1$
	static final String CURRENT_DIR                          = "./";  //$NON-NLS-1$
	static final String UTF8_ENCODING                        = "UTF-8";  //$NON-NLS-1$
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////

}
