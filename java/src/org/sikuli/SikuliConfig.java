package org.sikuli;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public class SikuliConfig {
	
	public final static int DEFAULT_PORT = 9000;
	
	private Properties prop;
	
	private int port;
	
	private File basedir;
	
	private Set<String> whitelist;
	
	private static SikuliConfig instance;
	

	private final Pattern IP_PATTERN = 
         Pattern.compile("b(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?).)" 
                               + "{3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)b");

	private SikuliConfig() throws IOException {
		processCmdLineParams();
		setBaseDir();
		setWhitelist();
		setPort();
	}
	
	public static SikuliConfig getInstance() throws IOException {
		if (instance == null) {
			instance = new SikuliConfig();
		}
		return instance;
	}
	
	private void processCmdLineParams() {
		String basedir = System.getProperty("basedir");
		String whitelist = System.getProperty("whitelist");
		String port = System.getProperty("port");
		String path = System.getProperty("prop");
		
		prop = new Properties();
		
		try {
			prop.load(new FileInputStream(path));
		} catch (Exception e) {
			
		} finally {
			
			if (port != null) {
				prop.setProperty("port", port);
			}
			
			if (basedir != null) {
				prop.setProperty("basedir", basedir);
			}
			if (whitelist != null) {
				prop.setProperty("whitelist", whitelist);
			}
		}
	}

	private void setBaseDir() throws FileNotFoundException {
		String path = prop.getProperty("basedir");
		if (path == null) {
			throw new IllegalArgumentException();
		}
		File f = new File(path);
		if (! f.isDirectory()) {
			throw new FileNotFoundException();
		}
		this.basedir = f;
	}
	
	private void setWhitelist() throws IOException {
		String value = prop.getProperty("whitelist");
		if (value == null) {
			return;
		}
		String[] values = value.split(",");
		Set<String> set = new HashSet<String>();
		for (String s : values) {
			if (IP_PATTERN.matcher(s).matches()) {
				set.add(s);
			} else {
				throw new IllegalArgumentException();
			}
		}		
	}
	
	private void setPort() {
		String value = prop.getProperty("port");
		if (value == null) {
			port = DEFAULT_PORT;
			prop.setProperty("port", String.valueOf(DEFAULT_PORT));
		} else {
			try {
				port = Integer.parseInt(value);
			} catch (Exception e) {
				throw new IllegalArgumentException();
			}
		}
	}
	
	public boolean isValidRequest(HttpServletRequest req) {
		return whitelist == null ? true : whitelist.contains(req.getRemoteAddr());
		
	}
	
	public int getPort() {
		return port;
	}
	
	public File getBasedir() {
		return basedir;
	}
	
	public Set<String> getWhitelist() {
		return whitelist;
	}
	
}
