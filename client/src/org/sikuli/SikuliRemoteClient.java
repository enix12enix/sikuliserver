package org.sikuli;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.sikuli.SikuliScript;


public class SikuliRemoteClient {

	private String serverAddress;
	
	private HttpURLConnection conn;
	
    public SikuliRemoteClient(String server, String port) throws MalformedURLException {
		serverAddress = "http://" + server + ":" + port + "/test.do";
	}
    
    private URL buildURL(String script, String[] args) throws IOException, IllegalFormatArgumentException {
    	if (! isValidScript(script)) {
    		throw new IllegalFormatArgumentException("Parameter script should be started with '/'");
    	}
    	
    	String content = "?script=" + URLEncoder.encode(script, "utf-8");
    	if (args != null) {
    		for (int i = 0; i < args.length; i++) {
    		    content += "&argv" + String.valueOf(i+1) + "=" + URLEncoder.encode(args[i], "utf-8");
    		}
    	}
    	return new URL(serverAddress + content);
    }
    
    public SikuliScript excute(String script) throws Exception {
    	return excute(script, null);
    }
    
    public SikuliScript excute (String script, String[] args) throws Exception {
        initConnection(buildURL(script, args));
    	conn.connect();
    	InputStream is = conn.getInputStream();
    	return new SikuliScriptXmlParser(is).toSikuliScript();
    }
    
    private void initConnection(URL sikuliServerURL) throws IOException {
    	conn = (HttpURLConnection) sikuliServerURL.openConnection();
    	conn.setConnectTimeout(60000);
		conn.setRequestMethod("GET");
		conn.setDoInput(true);
    }
    
    private boolean isValidScript(String script) {
    	if (script == null) {
    		throw new IllegalArgumentException("Parameter script can't be null!");
    	}
    	return script.startsWith("/");
    }
}
