package org.sikuli;

import org.sikuli.exception.ScriptNotExistException;
import org.sikuli.exception.SikuliScriptFailedException;
import org.sikuli.script.ScriptRunner;


public class SikuliEngine {
	
	private ScriptRunner runner;
	
	private static SikuliEngine instance;
	

	private SikuliEngine() {
		runner = ScriptRunner.getInstance(null);
	}
	
	public synchronized static SikuliEngine getInstance() {
		if (instance == null) {
			instance = new SikuliEngine();
		}
		return instance;
	}
	
	public synchronized void run(SikuliScript script) {
		
		if (SikuliUtil.sikuliScriptExists(script.getScript().getAbsolutePath()) ) {
			try {
				script.getProcessor().preRun();
				runner.runPython(script.getScript().getAbsolutePath());
				script.setStatus(SikuliScript.STATUS.PASS);
			} catch (Exception e) {
				script.setStatus(SikuliScript.STATUS.FAIL);
				script.setError(new SikuliScriptFailedException(e.getCause().getMessage()));
			} finally {
				script.getProcessor().postRun();
			}
		} else {
			script.setStatus(SikuliScript.STATUS.FAIL);
			script.setError(new ScriptNotExistException(script.getScript().getAbsolutePath() + " doesn't exist!"));
		}
        
	}

}
