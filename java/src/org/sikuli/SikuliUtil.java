package org.sikuli;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintWriter;

public abstract class SikuliUtil {
	
	public final static String SIKULI_SCRIPT_SUFFIX = ".sikuli";
	
	public static String stackTraceToStr(Exception e) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		e.printStackTrace(new PrintWriter(bos, true));
		return new String(bos.toByteArray());
	}
	
	public static boolean sikuliScriptExists(String filepath) {
		if ( ! filepath.endsWith(SIKULI_SCRIPT_SUFFIX) ) {
			return false;
		}
		File file = new File(filepath);
		return file.exists();
	}
	
}
