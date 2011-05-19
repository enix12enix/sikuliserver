package org.sikuli;

import static junit.framework.Assert.*;
import org.junit.Test;
import org.sikuli.SikuliUtil;


public class TestSikuliUtil {
	
	@Test
	public void testStackTraceToStr() {
		try {
			throw new IllegalArgumentException();
		} catch (Exception e) {
			assertNotNull(SikuliUtil.stackTraceToStr(e));
		}
	}
	
	@Test
	public void testSikuliScriptExists() {
		String testscript1 = "testscript.py";
		String testscript2 = "testscript.sikuli";
		assertFalse(SikuliUtil.sikuliScriptExists(testscript1));
		assertFalse(SikuliUtil.sikuliScriptExists(testscript2));
	}

}
