package com.id.hl7sim.hl7;

import java.util.List;

public interface HL7Sender {

	void writeLogFile(List<String> allHL7s);

	void sendMessage(String message);

	void sendListOfMessages(List<String> allMessages);

}