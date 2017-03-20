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
package tain.kr.sigar.v01;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarLoader;

/**
 * Code Templates > Comments > Types
 *
 * <PRE>
 *   -. FileName   : Runner.java
 *   -. Package    : tain.kr.com.test.sigar.v01
 *   -. Comment    :
 *   -. Author     : taincokr
 *   -. First Date : 2017. 3. 17. {time}
 * </PRE>
 *
 * @author taincokr
 *
 */
public final class Runner {

	private static boolean flag = true;

	private static final Logger log = Logger.getLogger(Runner.class);

	///////////////////////////////////////////////////////////////////////////////////////////////

	private static HashMap<String, Boolean> wantedJars = new HashMap<String, Boolean>();
	private static final String JAR_EXT = ".jar";

	static {
		wantedJars.put("junit", Boolean.FALSE);
		wantedJars.put("log4j", Boolean.FALSE);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * constructor
	 */
	public Runner() {
		if (flag)
			log.debug(">>>>> in class " + this.getClass().getSimpleName());
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
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////

	private static void printMissingJars() {
		
		for (Iterator<Map.Entry<String, Boolean>> it = wantedJars.entrySet().iterator(); it.hasNext();)
		{
			Map.Entry<String, Boolean> entry = (Map.Entry<String, Boolean>) it.next();
			String jar = (String)entry.getKey();
			if (wantedJars.get(jar) == Boolean.FALSE) {
				System.out.println("Unable to locate: " + jar + JAR_EXT);
			}
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	private static boolean missingJars() {
		
		for (Iterator<Map.Entry<String, Boolean>> it = wantedJars.entrySet().iterator(); it.hasNext();)
		{
			Map.Entry<String, Boolean> entry = (Map.Entry<String, Boolean>) it.next();
			String jar = (String)entry.getKey();
			if (wantedJars.get(jar) == Boolean.FALSE) {
				return true;
			}
		}

		return false;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	public static URL[] getLibJars(String dir) throws Exception {
		
		File[] jars = new File(dir).listFiles(new FileFilter() {
			
			@Override
			public boolean accept(File file) {
				
				String name = file.getName();
				int jarIx = name.indexOf(JAR_EXT);
				if (jarIx == -1) {
					return false;
				}
				
				int ix = name.indexOf('-');
				if (ix != -1) {
					name = name.substring(0, ix); //versioned .jar
				} else {
					name = name.substring(0, jarIx);
				}

				if (wantedJars.get(name) != null) {
					wantedJars.put(name, Boolean.TRUE);
					return true;
				} else {
					return false;
				}
			}
		});

		if (jars == null) {
			return new URL[0];
		}

		URL[] urls = new URL[jars.length];

		for (int i=0; i<jars.length; i++) {
			URL url = new URL("jar", null, "file:" + jars[i].getAbsolutePath() + "!/");

			urls[i] = url;
		}

		return urls;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	private static void addURLs(URL[] jars) throws Exception {
		
		URLClassLoader loader = (URLClassLoader)Thread.currentThread().getContextClassLoader();

		//bypass protected access.
		Method addURL = URLClassLoader.class.getDeclaredMethod("addURL", new Class[] { URL.class });

		addURL.setAccessible(true); //pound sand.

		for (int i=0; i<jars.length; i++) {
			addURL.invoke(loader, new Object[] { jars[i] });
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	private static boolean addJarDir(String dir) throws Exception {
		
		URL[] jars = getLibJars(dir);
		addURLs(jars);
		return !missingJars();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	private static String getenv(String key) {
		
		try {
			return System.getenv("ANT_HOME"); //check for junit.jar
		} catch (Error e) {
			/*1.4*/
			Sigar sigar = new Sigar();
			try {
				return sigar.getProcEnv("$$", "ANT_HOME");
			} catch (Exception se) {
				return null;
			} finally {
				sigar.close();
			}
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * static test method
	 */
	private static void test01(String[] args) throws Exception {

		if (flag) {
			/*
			 * begin
			 */
			if (args.length < 1) {
				args = new String[] { "Shell" };
			} else {
				//e.g. convert
				//          "ifconfig", "eth0"
				//   to:
				// "Shell", "ifconfig", "eth0"
				if (Character.isLowerCase(args[0].charAt(0))) {
					String[] nargs = new String[args.length + 1];
					System.arraycopy(args, 0, nargs, 1, args.length);
					nargs[0] = "Shell";
					args = nargs;
				}
			}
			
			String name = args[0];

			String[] pargs = new String[args.length - 1];
			System.arraycopy(args, 1, pargs, 0, args.length-1);

			String sigarLib = SigarLoader.getLocation();

			String[] dirs = { sigarLib, "lib", "." };
			for (int i=0; i<dirs.length; i++) {
				if (addJarDir(dirs[i])) {
					break;
				}
			}

			if (missingJars()) {
				File[] subdirs = new File(".").listFiles(new FileFilter() {
					@Override
					public boolean accept(File file) {
						return file.isDirectory();
					}
				});

				for (int i=0; i<subdirs.length; i++) {
					File lib = new File(subdirs[i], "lib");
					if (lib.exists()) {
						if (addJarDir(lib.getAbsolutePath())) {
							break;
						}
					}
				}

				if (missingJars()) {
					String home = getenv("ANT_HOME"); //check for junit.jar
					if (home != null) {
						addJarDir(home + "/lib");
					}
				}
			}

			Class<?> cmd = null;
			String[] packages = {
				"org.hyperic.sigar.cmd.",
				"org.hyperic.sigar.test.",
				"org.hyperic.sigar.",
				"org.hyperic.sigar.win32.",
				"org.hyperic.sigar.jmx.",
			};

			for (int i=0; i<packages.length; i++) {
				try {
					cmd = Class.forName(packages[i] + name);
					break;
				} catch (ClassNotFoundException e) {}
			}

			if (cmd == null) {
				System.out.println("Unknown command: " + args[0]);
				return;
			}

			Method main = cmd.getMethod("main", new Class[] { String[].class });

			try {
				main.invoke(null, new Object[] { pargs });
			} catch (InvocationTargetException e) {
				Throwable t = e.getTargetException();
				if (t instanceof NoClassDefFoundError) {
					System.out.println("Class Not Found: " +
									   t.getMessage());
					printMissingJars();
				} else {
					t.printStackTrace();
				}
			}
		}
	}

	/*
	 * main method
	 */
	public static void main(String[] args) throws Exception {

		if (flag)
			log.debug(">>>>> " + new Object() {
			}.getClass().getEnclosingClass().getName());

		if (flag)
			test01(args);
	}
}
