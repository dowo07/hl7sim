package com.id.hl7sim.hl7;

import java.util.List;

import com.id.hl7sim.patient.Patient;

public interface HL7Builder {

	List<String> getAllHL7s();

	void setAllHL7s(List<String> allHL7s);

	String createMessage(Patient patient, Type type, Format format);

	String createPipeMessage(Patient patient, Type type);

	String createPipeMessageAdmission(Patient patient, Type type);

	String createPipeMessageTransfer(Patient patient, Type type);

	String createPipeMessageDischarge(Patient patient, Type type);

}