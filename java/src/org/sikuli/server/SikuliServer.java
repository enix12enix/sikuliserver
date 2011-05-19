package org.sikuli.server;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHolder;
import org.mortbay.thread.QueuedThreadPool;
import org.sikuli.SikuliConfig;
import org.sikuli.servlet.SikuliScriptServlet;


public class SikuliServer {
	
	private int port;
	
	private Server server;
	
	private Context context;
	
	private void help() {
		System.out.println("Usage: java [args] -jar sikuliserver.jar ");
		System.out.println("where args include:");
		System.out.println("    -Dport        optional        to set sikuli port. Default port is 9000");
		System.out.println("    -Dbasedir     required        to set the siluli script home directory");
		System.out.println("    -Dwhitelist   optional        to set the valid client");
		System.out.println("    -Dprop        optional        to set the properties file");
	}
	
	public void init() {
		try {
			SikuliConfig cf = SikuliConfig.getInstance();
			port = cf.getPort();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Failed to launch sikuli server!");
			help();
			System.exit(0);
		}
	}
	
	public void servletRegister() {
		server = new Server(port);
		
		QueuedThreadPool pool = new QueuedThreadPool();
		pool.setMinThreads(3);
		pool.setLowThreads(0);
		pool.setMaxThreads(3);
		server.setThreadPool(pool);
		
		context = new Context(server,"/",Context.SESSIONS);
		context.addServlet(new ServletHolder(new SikuliScriptServlet()), "/test.do");
	}
	
	private void boot() throws Exception {
		server.start();
		server.join();
	}
	
	public void startup() throws Exception {
		init();
		servletRegister();
		boot();
	}
	
	public Server getServer() {
		return server;
	}
	
	public void stop() throws Exception {
		server.stop();
	}
	
	public static void main(String[] args) throws Exception {
		SikuliServer server = new SikuliServer();
		server.startup();
	}

}
