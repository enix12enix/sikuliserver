package org.sikuli;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Constructor;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.sikuli.SikuliScript;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;


public class SikuliScriptXmlParser {
	
	private Document doc;
	
	public SikuliScriptXmlParser(InputStream is) throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(); 
    	DocumentBuilder db = dbf.newDocumentBuilder();
    	doc = db.parse(is);
	}
	
	public String retriveName() {
		return doc.getElementsByTagName("name").item(0).getFirstChild().getNodeValue();
	}
	
	public SikuliScript.STATUS retriveStatus() {
		String value = doc.getElementsByTagName("status").item(0).getFirstChild().getNodeValue();
		return SikuliScript.STATUS.valueOf(value);
	}
	
	public Exception retriveError() throws Exception {
		NodeList excepts = doc.getElementsByTagName("exception");
		if (excepts.getLength() == 0) {
			return null;
		}
		String exceptName = excepts.item(0).getFirstChild().getNodeValue();
		Class<?> clz =  Class.forName(exceptName);
		NodeList messages = doc.getElementsByTagName("message");
		if (messages.getLength() == 0) {
			return (Exception) clz.newInstance();
		} else {
			Constructor<?> con = clz.getConstructor(String.class);
			return (Exception) con.newInstance(messages.item(0).getFirstChild().getNodeValue());
		}
	}
	
	public SikuliScript toSikuliScript() throws Exception {
		SikuliScript script = new SikuliScript(new File(retriveName()));
		script.setStatus(retriveStatus());
		Exception e = retriveError();
		if (e != null) {
			script.setError(e);
		}
		return script;
	}

}
