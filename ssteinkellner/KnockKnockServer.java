package ssteinkellner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * die klasse erstellt für jede eingehende verbindung einen neuen thread
 * @author SSteinkellner
 * @version 20141023
 */
public class KnockKnockServer {
	protected boolean lauf;
	private int lastport;
	
	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.err.println("Usage: java KnockKnockServer <port number>");
			System.exit(1);
		}
		
		new KnockKnockServer().start(Integer.parseInt(args[0]));
	}
	
	public void start(int portNumber){
		try (
				ServerSocket serverSocket = new ServerSocket(portNumber);
			){
			lauf = true;
			lastport = portNumber;
			
			while (lauf) {
				try (
					Socket clientSocket = serverSocket.accept();
					PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
					BufferedReader in = new BufferedReader(
							new InputStreamReader(clientSocket.getInputStream()));
					){
					if(in.readLine().equalsIgnoreCase("I need a port!")){
						lastport++;
						out.println(""+lastport);
						new Thread(new KnockKnockServerInstance(lastport)).start();
						System.out.println("New Client ("+clientSocket.getInetAddress()+") on Port "+lastport);
					}
				}catch (IOException e) {
					System.out.println("Exception caught when trying to listen on port "
							+ portNumber + " or listening for a connection");
					System.out.println(e.getMessage());
				}
			}
		} catch (IOException e) {
			System.out.println("Exception caught when trying to listen on port "
					+ portNumber + " or listening for a connection");
			System.out.println(e.getMessage());
		}
	}
}