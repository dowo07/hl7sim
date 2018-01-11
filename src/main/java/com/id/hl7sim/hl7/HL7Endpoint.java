package com.id.hl7sim.hl7;

import java.io.OutputStream;

public interface HL7Endpoint {
	
	OutputStream getOutputStream();
	
}