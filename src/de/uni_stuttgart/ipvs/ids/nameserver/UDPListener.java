/* 
 * UDPListener.java
 * - implement a UPD listener thread for Nameserver instances
 *
 * Distributed Systems Exercise
 * Assignment 2 Part II
 */

package de.uni_stuttgart.ipvs.ids.nameserver;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

// part b) listen for UDP datagrams for recursive lookup
public class UDPListener extends Thread {

	private int port;
	private Nameserver nameserver; // for issuing callbacks to owner

	public UDPListener(Nameserver nameserver, int port) {
		this.nameserver = nameserver;
		this.port = port;
	}

	public void run() {

		/* TODO: implement UDP listener here
		 *     - pass requests to owner using nameserver.lookupRecursive(...)
		 */
		try {
			DatagramSocket serverSocket = new DatagramSocket(port);

			while(true)
			{
				
				byte[] receiveData = new byte[1024];
				byte[] sendData = new byte[1024];
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				serverSocket.receive(receivePacket); 
				String name = new String( receivePacket.getData()); 
				String address = nameserver.lookupRecursive(name);

				InetAddress IPAddress = receivePacket.getAddress();  
				int port = receivePacket.getPort();            
				sendData = address.getBytes();         
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port); 
				serverSocket.send(sendPacket);                
				}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
