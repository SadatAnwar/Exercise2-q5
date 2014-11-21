/* 
 * Client.java
 * - perform name resolution query
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
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;


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

		OutputStream os = null;
        OutputStreamWriter osw =null;
        String ipAdrs=null;
        Socket socket=null;
        InputStream is =null;
    	BufferedReader reader=null;
    	 BufferedWriter writer=null;
        InputStreamReader isr = null;
        String[] child = null;
        String namechild=null;
        String reply=null;
        int port=serverPort;
        // TODO: complete method (using UDP sockets)
     // parsing the name to get each child 		 
     
        if (name.contains("."))
	      {  child=name.split("\\.");
			namechild=child[child.length-1];
	      }
	     else 
	     { System.out.println("invalid address");
	     return "invalid address";
	     }
        for (int i=0;i<child.length;i++)
     		{
     		   try{
		 socket = new Socket (serverAddress, port);
		
		
		//to read and write from the socket
		os=socket.getOutputStream();
		osw= new OutputStreamWriter(os);
		 is=socket.getInputStream();
		 isr=new InputStreamReader(is);
	     reader = new BufferedReader(isr);
	      writer = new BufferedWriter(osw);
	    
		namechild=child[child.length-i-1];
		writer.write(namechild.trim());
		writer.flush();
		
		reply=reader.readLine();
		System.out.println(reply);
		//get the port from the  reply
		if (reply.contains(":"))
		{String[] replyCh=reply.split("\\:");
		port=Integer.parseInt(replyCh[1]);
		ipAdrs=replyCh[0];
		}else{
			System.out.println("there is no server with name");
			ipAdrs=null;
			break;
		}
		socket.close();
		is.close();
		os.close();
		}catch(Exception e){
			System.out.println("socket client cannot establish:"+e);
		}
		
		}
		return ipAdrs;
	}
}
