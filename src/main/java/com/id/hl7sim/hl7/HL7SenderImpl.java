package com.id.hl7sim.hl7;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HL7SenderImpl implements HL7Sender {

	private HL7Endpoint endpoint;

	public HL7SenderImpl(HL7Endpoint endpoint) {
		super();
		this.endpoint = endpoint; 
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.id.hl7sim.HL7Sender#writeLogFile(java.util.List)
	 */
	@Override
	public void writeLogFile(List<String> allHL7s) {
		if (allHL7s == null) {
			throw new IllegalArgumentException("No HL7 Messages there");
		} else {

			BufferedWriter writer = null;
			try {
				File logFile = new File("hl7LogFile_" + LocalDateTime.now().toString());
				System.out.println(logFile.getCanonicalPath());
				writer = new BufferedWriter(new FileWriter(logFile));
				for (String hl7message : allHL7s) {
					writer.write(hl7message);
				}
				writer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.id.hl7sim.HL7Sender#sendMessage(java.lang.String)
	 */
	@Override
	public void sendMessage(String message) {

		int retries = 3;

		while (retries > 0) {

			System.out.println("sendMessage: " + Thread.currentThread().getName());
			try (OutputStream outputStream = endpoint.getOutputStream()) {
				outputStream.write(message.getBytes(StandardCharsets.UTF_8));
				return;
			} catch (IOException e) {
				System.out.println("Error occured during send, retrying...");
				try {
					TimeUnit.SECONDS.sleep(2);
				} catch (InterruptedException e1) {
					// Ignore InterruptedException
				}
			}
			retries--;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.id.hl7sim.HL7Sender#sendListOfMessages(java.util.List)
	 */
	@Override
	public void sendListOfMessages(List<String> allMessages) {
		for (String message : allMessages) {
			sendMessage(message);
		}
	}

}
