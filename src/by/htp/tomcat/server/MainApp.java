package by.htp.tomcat.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MainApp {

	public static void main(String[] args) {

		ServerSocket serverSocket = null;
		try {
			try {
				serverSocket = new ServerSocket(8085);
				System.out.println("Server started");

				while (true) {
					Socket clientConnection = serverSocket.accept();
					System.out.println("client connected");

					BufferedInputStream bufferInput = null;
					PrintWriter writer = null;
					try {

						InputStream input = clientConnection.getInputStream();
						bufferInput = new BufferedInputStream(input);

						OutputStream output = clientConnection.getOutputStream();
						writer = new PrintWriter(output);

						writer.print("HTTP/1.1 200 \r\n");
						writer.print("Content-Type: text/html\r\n");

						// writer.print("Connection: close\r\n");
						writer.print("\r\n");
						writer.print("<html><head></head><body><h1>Hello User!</h1></body></html>");
						writer.flush();

						int size = input.available();
						System.out.println("size: " + size);
						byte[] bytes = new byte[size];

						bufferInput.read(bytes);
						System.out.print(new String(bytes));
						
					} finally {
						bufferInput.close();
						writer.close();
						clientConnection.close();
					}

				}
			} finally {
				if (serverSocket != null) {
					serverSocket.close();
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
