package com.id.hl7sim;

import java.util.List;

public interface HL7Builder {

	List<String> getAllHL7s();

	void setAllHL7s(List<String> allHL7s);

	String createMessage(Patient patient, Type type, Format format);

	String createPipeMessage(Patient patient, Type type);

	String createPipeMessageAdmission(Patient patient, Type type);

	String createPipeMessageTransfer(Patient patient, Type type);

	String createPipeMessageDischarge(Patient patient, Type type);

}