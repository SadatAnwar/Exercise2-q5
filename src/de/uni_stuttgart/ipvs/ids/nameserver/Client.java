/* 
 * Client.java
 * - perform name resolution query
 *
 * Distributed Systems Exercise
 * Assignment 2 Part II
 */

package de.uni_stuttgart.ipvs.ids.nameserver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class Client {

	// part a) start recursive lookup
	public String recursiveNameResolution(String name, String serverAddress, int serverPort) throws IOException {
   
		DatagramSocket clientSocket = new DatagramSocket(); 
		InetAddress IPAddress = InetAddress.getByName(serverAddress);    
		byte[] sendData = new byte[1024];     
		byte[] receiveData = new byte[1024];      
		sendData = name.getBytes();    
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, serverPort);   
		clientSocket.send(sendPacket);     
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);     
		clientSocket.receive(receivePacket);  
		String modifiedSentence = new String(receivePacket.getData());    
		clientSocket.close(); 

		return modifiedSentence;
	}

	// part b) start iterative lookup
	public String iterativeNameResolution(String name, String serverAddress, int serverPort) {

		// TODO: complete method (using TCP sockets)

		return null;
	}
}
