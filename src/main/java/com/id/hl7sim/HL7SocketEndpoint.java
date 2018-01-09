package com.id.hl7sim;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class HL7SocketEndpoint implements HL7Endpoint {

	private String host;

	private int port;

	public HL7SocketEndpoint(String host, int port) { 
		this.host = host;
		this.port = port;
	}

	@Override
	public OutputStream getOutputStream() {
		Socket socket;
		try {
			socket = new Socket(host, port);
			socket.setTcpNoDelay(true);
			socket.setSoLinger(true, 0);
			socket.setReuseAddress(true);
			final OutputStream output = socket.getOutputStream();
			return new OutputStream() {

				@Override
				public void write(int b) throws IOException {
					output.write(b);
				}

				@Override
				public void close() throws IOException {
					socket.shutdownInput();
					socket.shutdownOutput();
					socket.close();
					super.close();
				}

			};
		} catch (IOException e) {
			throw new IllegalStateException("Socket exception", e);
		}
	}

}
