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
package tain.kr.com.proj.cosmarter.v02.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.log4j.Logger;

/**
 * Code Templates > Comments > Types
 *
 * <PRE>
 *   -. FileName   : Test01FileIO.java
 *   -. Package    : tain.kr.com.proj.cosmarter.v02.util
 *   -. Comment    :
 *   -. Author     : taincokr
 *   -. First Date : 2017. 3. 11. {time}
 * </PRE>
 *
 * @author taincokr
 *
 */
public final class Test01FileIO {

	private static boolean flag = true;

	private static final Logger log = Logger.getLogger(Test01FileIO.class);

	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final String KEY_ENCODING = "tain.fileio.encoding";
	private static final String KEY_BLKSIZ = "tain.fileio.blksiz";

	/*
	 * defaut values
	 */
	public static String ENCODING = "UTF-8";
	private static int BLKSIZ = 16384;

	///////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * constructor
	 */
	private Test01FileIO() throws Exception {
		
		/*
		 * nothing to do
		 */
		Test01FileIO.ENCODING = Param.getInstance().getString(KEY_ENCODING);
		Test01FileIO.BLKSIZ = Integer.parseInt(Param.getInstance().getString(KEY_BLKSIZ));
		
		if (flag)
			log.debug(">>>>> in class " + this.getClass().getSimpleName());
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public static void copyFile(String inName, String outName) throws FileNotFoundException, IOException {
		copyFile(inName, outName, false);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////

	public static void copyFile(String inName, String outName, boolean flgOsClose) throws FileNotFoundException, IOException {
		
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		
		try {
			bis = new BufferedInputStream(new FileInputStream(inName));
			bos = new BufferedOutputStream(new FileOutputStream(outName));
			
			copyFile(bis, bos, flgOsClose);
		} finally {
			if (bis != null) try { bis.close(); } catch (IOException e) {}
			if (bos != null) try { bos.flush(); bos.close(); } catch (IOException e) {}
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public static void copyFile(InputStream is, OutputStream os, boolean flgOsClose) throws IOException {
		
		byte[] bytRead = new byte[Test01FileIO.BLKSIZ];
		int nRead;
		
		while ((nRead = is.read(bytRead)) != -1) {
			os.write(bytRead, 0, nRead);
		}
		
		is.close();
		
		os.flush();
		
		if (flgOsClose)
			os.close();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public static void copyFile(Reader reader, Writer writer, boolean flgWriterClose) throws IOException {
		
		int ch;
		
		while ((ch = reader.read()) != -1) {
			writer.write(ch);
		}
		
		reader.close();
		
		writer.flush();
		
		if (flgWriterClose)
			writer.close();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public static void copyFile(String inName, PrintWriter pw, boolean flgPwClose) throws FileNotFoundException, IOException {
		
		BufferedReader br = new BufferedReader(new FileReader(inName));
		
		copyFile(br, pw, flgPwClose);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public static void copyFile(File file, File target) throws IOException {
		
		if (!file.exists() || !file.isFile() || !file.canRead()) {
			throw new IOException(String.format("File is not a readable file '%s'", file));
		}
		
		File dest = target;
		if (target.isDirectory()) {
			dest = new File(dest, file.getName());
		}
		
		InputStream is = null;
		OutputStream os = null;
		
		try {
			is = new FileInputStream(file);
			os = new FileOutputStream(dest);
			
			byte[] bytRead = new byte[Test01FileIO.BLKSIZ];
			int nRead;
			while ((nRead = is.read(bytRead)) != -1) {
				os.write(bytRead, 0, nRead);
			}
			
			os.flush();
		} finally {
			if (is != null) try { is.close(); } catch (IOException e) {}
			if (os != null) try { os.close(); } catch (IOException e) {}
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public static void copyFileBuffered(String inName, String outName) throws FileNotFoundException, IOException {
		
		InputStream is = null;
		OutputStream os = null;
		
		try {
			is = new FileInputStream(inName);
			os = new FileOutputStream(outName);
			
			byte[] bytRead = new byte[Test01FileIO.BLKSIZ];
			int nRead;
			while ((nRead = is.read(bytRead)) != -1) {
				os.write(bytRead, 0, nRead);
			}
			
			os.flush();
		} finally {
			if (is != null) try { is.close(); } catch (IOException e) {}
			if (os != null) try { os.close(); } catch (IOException e) {}
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public static void copyRecursively(File fromDir, File toDir, boolean flgCreate) throws IOException {
		
		if (!fromDir.exists()) {
			throw new IOException(String.format("Source directory '%s' does not exist.", fromDir));
		}
		
		if (flgCreate) {
			toDir.mkdirs();
		} else if (!toDir.exists()) {
			throw new IOException(String.format("Destination directory '%s' must exist.", toDir));
		}
		
		for (File src : fromDir.listFiles()) {
			if (src.isDirectory()) {
				File destSubDir = new File(toDir, src.getName());
				copyRecursively(src, destSubDir, true);
			} else if (src.isFile()) {
				copyFile(src, toDir);
			} else {
				System.out.printf("Warning: %s is neither file nor directory.\n", src);
			}
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public static void copyRecursively(File fromDir, File toDir) throws IOException {
		
		copyRecursively(fromDir, toDir, false);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public static void deleteRecursively(File startDir) throws IOException {
		
		String startDirPath = startDir.getCanonicalPath();
		
		for (File file : startDir.listFiles()) {
			if (!file.getCanonicalPath().startsWith(startDirPath)) {
				throw new IOException(String.format("Attempted to go out of '%s'.", startDir));
			}
			
			if (file.isDirectory()) {
				deleteRecursively(file);
			}
		}
		
		for (File file : startDir.listFiles()) {
			file.delete();
			if (file.exists()) {
				System.err.printf("'%s' did not get deleted.!!", file);
			}
		}
		
		startDir.delete();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public static void copyRecursively(JarFile base, JarEntry startingDir, File toDir) throws IOException {
		
		if (!startingDir.isDirectory()) {
			throw new IOException(String.format("Starting point '%s' is not a directory.", startingDir));
		}
		
		if (!toDir.exists()) {
			throw new IOException(String.format("Destination dir '%s' must exist.", toDir));
		}
		
		Enumeration<JarEntry> all = base.entries();
		while (all.hasMoreElements()) {
			JarEntry file = all.nextElement();
			
			if (file.isDirectory()) {
				copyRecursively(base, file, new File(toDir, file.getName()));
			} else {
				InputStream is = null;
				OutputStream os = null;
				
				try {
					is = base.getInputStream(file);
					os = new FileOutputStream(new File(toDir, file.getName()));
					
					copyFile(is, os, false);
					
					os.flush();
				} finally {
					if (is != null) try { is.close(); } catch (IOException e) {}
					if (os != null) try { os.close(); } catch (IOException e) {}
				}
			}
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public static String readLine(String inName) throws FileNotFoundException, IOException {
		
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(new FileReader(inName));
			String line = null;
			line = br.readLine();
			return line;
		} finally {
			if (br != null) try { br.close(); } catch (IOException e) {}
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public static String readerToString(Reader reader) throws IOException {
		
		StringBuffer sb = new StringBuffer();
		
		char[] chRead = new char[Test01FileIO.BLKSIZ];
		int nRead;
		
		while ((nRead = reader.read(chRead)) != -1) {
			sb.append(chRead, 0, nRead);
		}
		
		return sb.toString();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public static String inputStreamToString(InputStream is) throws IOException {
		
		return readerToString(new InputStreamReader(is));
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public static String readAsString(String fileName) throws IOException {
		
		return readerToString(new FileReader(fileName));
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public static void stringToFile(String text, String fileName) throws IOException {
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
		bw.write(text);
		bw.flush();
		bw.close();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public static BufferedReader openFile(String fileName) throws IOException {
		
		return new BufferedReader(new FileReader(fileName));
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

	/*
	 * static test method
	 */
	private static void test01(String[] args) throws Exception {

		if (flag) {
			System.out.printf("[%s]\n", Test01FileIO.readLine("./project/doc/doc.log"));
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
