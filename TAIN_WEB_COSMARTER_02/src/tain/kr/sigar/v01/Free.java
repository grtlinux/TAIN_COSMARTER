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

import org.apache.log4j.Logger;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.Swap;

/**
 * Code Templates > Comments > Types
 *
 * <PRE>
 *   -. FileName   : Free.java
 *   -. Package    : tain.kr.com.test.sigar.v01
 *   -. Comment    :
 *   -. Author     : taincokr
 *   -. First Date : 2017. 3. 17. {time}
 * </PRE>
 *
 * @author taincokr
 *
 */
public final class Free extends SigarCommandBase {

	private static boolean flag = true;

	private static final Logger log = Logger.getLogger(Free.class);

	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * constructor
	 */
	public Free(Shell shell) {
		super(shell);
		
		if (!flag)
			log.debug(">>>>> in class " + this.getClass().getSimpleName());
	}

	public Free() {
		super();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public String getUsageShort() {
		return "Display information about free and used memory";
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public void output(String[] args) throws SigarException {
		
		Mem mem = this.sigar.getMem();
		Swap swap = this.sigar.getSwap();
		
		Object[] header = new Object[] { "total", "used", "free" };
		
		Object[] memRow = new Object[] {
				Free.format(mem.getTotal()),
				Free.format(mem.getUsed()),
				Free.format(mem.getFree()),
		};
		
		Object[] actualRow = new Object[] {
				Free.format(mem.getActualUsed()),
				Free.format(mem.getActualFree()),
		};
		
		Object[] swapRow = new Object[] {
				Free.format(swap.getTotal()),
				Free.format(swap.getUsed()),
				Free.format(swap.getFree()),
		};
		
		printf("%18s %10s %10s", header);
		
		// printf("Mem:   %,10d  %(10d  %10ld", ...);
		printf("Mem:   %10d  %10d %10ld", memRow);
		
		// e.g. linux
		if ((mem.getUsed() != mem.getActualUsed()) || (mem.getFree() != mem.getActualFree())) {
			printf("-/+ buffers/cache: %10ld %10ld", actualRow);
		}
		
		printf("Swap:  %10ld  %10ld %10ld", swapRow);
		
		printf("RAM:   %10ls", new Object[] { mem.getRam() + "MB" });
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
	
	private static Long format(long value) {
		return new Long(value / 1024);
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
			new Free().processCommand(args);
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
