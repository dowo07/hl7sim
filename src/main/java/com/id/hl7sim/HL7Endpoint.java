package com.id.hl7sim;

import java.io.OutputStream;

public interface HL7Endpoint {
	
	OutputStream getOutputStream();
	
}