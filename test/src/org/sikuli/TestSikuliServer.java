package org.sikuli;

import static junit.framework.Assert.*;

import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sikuli.SikuliRemoteClient;
import org.sikuli.SikuliScript;
import org.sikuli.exception.ScriptNotExistException;
import org.sikuli.server.SikuliServer;


public class TestSikuliServer {

	private static SikuliServer server;
	
	@BeforeClass
	public static void init() throws Exception {
		System.setProperty("basedir", "/");
		server = new SikuliServer();
		server.init();
		server.servletRegister();
		server.getServer().start();
	}
	
	@AfterClass
	public static void clean() throws Exception {
		System.clearProperty("basedir");
		server.stop();
	}

	@Test
	public void testRunNotExistSikuliScript() throws Exception {
		String testscript = "notExistTestScript";
		SikuliRemoteClient client = new SikuliRemoteClient("localhost", "9000");
		SikuliScript result = client.excute(testscript);
		assertNotNull(result);
		assertEquals(result.getStatus(), SikuliScript.STATUS.FAIL);
		assertTrue(result.getError() instanceof ScriptNotExistException);
	}
	
	@Test
	public void testWrongURL() throws Exception {
		String wrongUrl = "http://localhost:9000/wrong.do";
		int expected = 404;
		
    	HttpURLConnection conn = (HttpURLConnection) new URL(wrongUrl).openConnection();
		conn.setRequestMethod("GET");
		conn.setDoInput(true);
		conn.connect();
		
		assertEquals(expected, conn.getResponseCode());
	}

}
