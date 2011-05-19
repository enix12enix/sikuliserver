package org.sikuli;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.sikuli.exception.IllegalSikuliScriptArgumentException;


public class SikuliScriptParamProcessor implements EngineArroundProcessor {

	private Map<String, String> scriptParams;
	
	private HttpServletRequest req;
	
    public final Pattern PARAM_PATTERN = Pattern.compile("^argv[1-9][0-9]*$");
    
    public SikuliScriptParamProcessor(HttpServletRequest req) {
    	this.req = req;
    	scriptParams = new HashMap<String, String>();
    }
	
	public boolean isValidParam(String param) {
		return PARAM_PATTERN.matcher(param).matches();
	}
	
	private void extractParameters(HttpServletRequest req) throws IOException {
	    
		Enumeration<?> e = req.getParameterNames();
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			if ("script".equals(key)) {
				continue;
			}
			if (isValidParam(key)) {
				scriptParams.put(key, req.getParameter(key));
			} else {
				throw new IllegalSikuliScriptArgumentException(key + " is not valid argument");
			}
		}
	}

	public void preRun() throws IOException {
		extractParameters(req);
		for (Entry<String, String> ety: scriptParams.entrySet()) {
			System.setProperty(ety.getKey(), ety.getValue());
		}
		
	}

	public void postRun() {
		for (Entry<String, String> ety: scriptParams.entrySet()) {
			System.clearProperty(ety.getKey());
		}
		
	}

}
