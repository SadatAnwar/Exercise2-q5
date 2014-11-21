/* 
 * TCPListener.java
 * - implement a TCP listener thread for Nameserver instances
 *
 * Distributed Systems Exercise
 * Assignment 2 Part II
 */

package de.uni_stuttgart.ipvs.ids.nameserver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

// part a) listen for TCP connections for iterative lookup
public class TCPListener extends Thread {

	private int port;
	private Nameserver nameserver; // for issuing callbacks to owner

	public TCPListener(Nameserver nameserver, int port) {
		this.nameserver = nameserver;
		this.port = port;
	}

	public void run() {
		Socket socketClient = null;
		ServerSocket server = null;
		OutputStream os = null;
		OutputStreamWriter osw = null;
		InputStream is = null;
		BufferedReader reader = null;
		BufferedWriter writer = null;
		InputStreamReader isr = null;
		
		// to read and write from the socket

		/*
		 * TODO: implement TCP listener here - pass requests to owner using
		 * nameserver.lookupIterative(...)
		 */
		while(true){
		try {
			server = new ServerSocket(port);
		} catch (IOException e) {
			System.out.println("server cannt create"+e);
		}
		System.out.println("waiting for client on port :"+port);

			try {
				
				socketClient = server.accept();
				System.out.println("acccept for client request"+socketClient.toString());

				//if (socketClient != null) {
					// to receive the name
					is = socketClient.getInputStream();
					isr = new InputStreamReader(is);
					reader = new BufferedReader(isr);
					String request;
					while( (request = reader.readLine())==null){}
					System.out.println("request:"+request);
					
					String reply = nameserver.lookupIterative(request.trim());
					// to send the reply to client
					os = socketClient.getOutputStream();
					osw = new OutputStreamWriter(os);
					writer = new BufferedWriter(osw);
					writer.write(reply);
					writer.flush();
					if (os != null)
						os.close();
					if (is != null)
						is.close();
					if (socketClient != null)
						socketClient.close();
				if (server != null)
					server.close();

			} catch (IOException e) {
				System.out.println("server cannt accept the connect");
			} finally {
				try{
					System.out.println("finally");
					if (server != null)
						server.close();
					if (os != null)
						os.close();
					if (is != null)
						is.close();
					if (socketClient != null)
						socketClient.close();

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		}
		}
	}
