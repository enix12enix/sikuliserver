package org.sikuli;

import java.io.File;

public class SikuliScript {
	
	private File script;
	
	private STATUS status = STATUS.FAIL;
	
	private Exception error;
	
	private EngineArroundProcessor processor;
	
	public enum STATUS {
		PASS, FAIL;
	}
	
	public SikuliScript(File script) {
		this.script = script;
	}
	
	public SikuliScript(File script, EngineArroundProcessor processor) {
		this.script = script;
		this.processor = processor;
	}
	
	public void setProcessor(EngineArroundProcessor processor) {
		this.processor = processor;
	}
	
	public void setStatus(STATUS status) {
		this.status = status;
	}
	
	public void setError(Exception error) {
		this.error = error;
	}
	
	public STATUS getStatus() {
		return status;
	}
	
	public File getScript() {
		return script;
	}
	
	public Exception getError() {
		return error;
	}
	
	public EngineArroundProcessor getProcessor() {
		return processor;
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		builder.append("<script>");
		builder.append("<name>");
		builder.append(script.getAbsolutePath());
		builder.append("</name>");
		builder.append("<status>");
		builder.append(status.name());
		builder.append("</status>");
		if (error != null) {
			builder.append("<error>");
			builder.append("<exception>" + error.getClass().getCanonicalName() + "</exception>");
			if (error.getMessage() != null) {
				builder.append("<message>" + error.getMessage() + "</message>");
			}
			builder.append("</error>");
		}
		builder.append("</script>");
		
		return builder.toString();
	}
	

}
