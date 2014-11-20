/* 
 * TCPListener.java
 * - implement a TCP listener thread for Nameserver instances
 *
 * Distributed Systems Exercise
 * Assignment 2 Part II
 */

package de.uni_stuttgart.ipvs.ids.nameserver;

// part a) listen for TCP connections for iterative lookup
public class TCPListener extends Thread {

	private int port;
	private Nameserver nameserver; // for issuing callbacks to owner

	public TCPListener(Nameserver nameserver, int port) {
		this.nameserver = nameserver;
		this.port = port;
	}

	public void run() {

		/* TODO: implement TCP listener here
		 *     - pass requests to owner using nameserver.lookupIterative(...)
		 */
	}
}