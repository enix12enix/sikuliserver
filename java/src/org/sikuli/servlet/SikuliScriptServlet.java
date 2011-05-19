package org.sikuli.servlet;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sikuli.SikuliConfig;
import org.sikuli.SikuliEngine;
import org.sikuli.SikuliScript;
import org.sikuli.SikuliScriptParamProcessor;


public class SikuliScriptServlet extends HttpServlet {
	
	private static final long serialVersionUID = -4362913565970646100L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	        throws ServletException, IOException {
		if ( ! SikuliConfig.getInstance().isValidRequest(req) ) {
			resp.sendError(404);
		}
		
		SikuliScript script = new SikuliScript(
				new File(SikuliConfig.getInstance().getBasedir() + URLDecoder.decode(req.getParameter("script"), "utf-8")),
				new SikuliScriptParamProcessor(req));
		SikuliEngine engine = SikuliEngine.getInstance();
		engine.run(script);
		
		resp.setStatus(HttpServletResponse.SC_OK);
		resp.setContentType("text/xml");
		resp.setHeader("Cache-Control", "no-cache");
		resp.getWriter().print(script);
	}

}
