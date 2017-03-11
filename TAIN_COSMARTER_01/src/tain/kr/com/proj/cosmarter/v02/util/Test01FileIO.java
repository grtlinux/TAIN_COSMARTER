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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;

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
public class Test01FileIO {

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
	
	public static void copyFile(String inName, String outName) throws FileNotFoundException, IOException {
		
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		
		try {
			bis = new BufferedInputStream(new FileInputStream(inName));
			bos = new BufferedOutputStream(new FileOutputStream(outName));
			
			copyFile(bis, bos, false);
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
				System.out.printf("Warning: %s is neither file nor directory.", src);
			}
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public static void copyRecursively(File fromDir, File toDir) throws IOException {
		copyRecursively(fromDir, toDir, false);
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

	/*
	 * static test method
	 */
	private static void test01(String[] args) throws Exception {

		if (flag)
			new Test01FileIO();

		if (flag) {

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
