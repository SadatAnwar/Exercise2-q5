/* 
 * Nameserver.java
 * - implement a name server process
 *
 * Distributed Systems Exercise
 * Assignment 2 Part II
 */

package de.uni_stuttgart.ipvs.ids.nameserver;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;

public class Nameserver {

	private final static String IP = "localhost";

	private HashMap<String, Nameserver> children;
	private HashMap<String, String> savedNames;
	private int tcpPort;
	private int udpPort;
	private String domain;

	private TCPListener tcpListener;
	private UDPListener udpListener;

	public Nameserver(String domain, Nameserver parent, int udpPort, int tcpPort) { // DO NOT MODIFY!
		this.domain = domain;
		this.tcpPort = tcpPort;
		this.udpPort = udpPort;
		children = new HashMap<String, Nameserver>();
		savedNames = new HashMap<String, String>();

		tcpListener = new TCPListener(this, tcpPort);
		udpListener = new UDPListener(this, udpPort);
		tcpListener.start();
		udpListener.start();
	}

	public void addChild(String domain, Nameserver nameserver) { // DO NOT MODIFY!
		children.put(domain, nameserver);
	}

	public void addNameAddressPair(String name, String address) { // DO NOT MODIFY!
		savedNames.put(name, address);
	}

	public int getUdpPort() { // DO NOT MODIFY!
		return udpPort;
	}

	public int getTcpPort() { // DO NOT MODIFY!
		return tcpPort;
	}

	public String getAddress() { // DO NOT MODIFY!
		return IP;
	}

	public String getDomain() { // DO NOT MODIFY!
		return domain;
	}

	// part a) recursive lookup
	public String lookupRecursive(String name) throws Exception {
		// diagnostic output - DO NOT MODIFY!!
		System.err.println("[INFO] lookupRecursive(\"" + name + "\")" +
		                   " on Nameserver \"" + this.domain + "\"");

		// TODO: implement recursive lookup
		// Start implementing here
		String[] child;
		if(name.contains(".")) {
			child = name.split("\\.");
		} else {
			if(savedNames.containsKey(name.trim())){
				String addressIP= savedNames.get(name.trim());
				return addressIP;
			}
			else {
				return "Invalid address";
			}
		}
		String lookUp =child[child.length-1].trim();
		if(children.containsKey(lookUp)){
			Nameserver thisChild = children.get(lookUp);
			String newName = name.substring(0,name.lastIndexOf("."));

			DatagramSocket clientSocket = new DatagramSocket(); 
			InetAddress IPAddress = InetAddress.getByName(IP);    
			byte[] sendData = new byte[1024];     
			byte[] receiveData = new byte[1024];      
			sendData = newName.getBytes();    
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, thisChild.getUdpPort());   
			clientSocket.send(sendPacket);     
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);     
			clientSocket.receive(receivePacket);  
			String modifiedSentence = new String(receivePacket.getData());    
			clientSocket.close(); 
			return modifiedSentence;
		}
		return "Path not found";

	}

	// part b) iterative lookup
	public String lookupIterative(String name) {
		// diagnostic output - DO NOT MODIFY!!
		System.err.println("[INFO] lookupIterative(\"" + name + "\")" +
		                   " on Nameserver \"" + this.domain + "\"");

		// TODO: implement iterative lookup
		// Start implementing here
		if(children.containsKey(name)){
			System.out.println("name in children:"+name);
			Nameserver thisChild = children.get(name);
			int port=thisChild.getTcpPort();
			String ipAdrs=thisChild.getAddress();
			
		return ipAdrs+":"+port;
		}else
			return "there is no server with this name";
	}

	// TODO: add additional methods if necessary...

}
