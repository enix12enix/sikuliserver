package org.sikuli.exception;

import java.io.IOException;

public class IllegalSikuliScriptArgumentException extends IOException {

	private static final long serialVersionUID = 5311983911954711063L;
	
	public IllegalSikuliScriptArgumentException(String message) {
		super(message);
	}

}
