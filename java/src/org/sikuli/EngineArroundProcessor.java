package org.sikuli;

import java.io.IOException;

public interface EngineArroundProcessor {
	
	void preRun() throws IOException;
	
	void postRun();

}
