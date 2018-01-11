package com.id.hl7sim;

@SuppressWarnings("serial")
public class HL7BuilderException extends RuntimeException {

	public HL7BuilderException() {
		super();
	}
 
	public HL7BuilderException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public HL7BuilderException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public HL7BuilderException(String arg0) {
		super(arg0);
	}

	public HL7BuilderException(Throwable arg0) {
		super(arg0);
	}
	

}
