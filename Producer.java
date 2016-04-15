/*
 * (c) University of Zurich 2014
 */


package Assignment1;

import java.net.*;
import java.io.*;

public class Producer {
	private static Socket SOCKET;
	private static DataOutputStream OUTPUT;
	private static BufferedReader buffReader;
	
	public static void main(String[] args) {
		String serverName = args[0];
		int port = Integer.parseInt(args[1]);
		String clientName = args[2];
		String inputFileName = args[3];

		// create connection to server
		// send the string "PRODUCER" to server first
		// read messages from input file line by line
		// put the client name and colon in front of each message
		// e.g., clientName:....
		// send message until you find ".bye" in the input file
		// close connection
		try{
			try{
				SOCKET= new Socket(serverName,port);
				OUTPUT = new DataOutputStream(SOCKET.getOutputStream());
				
				OUTPUT.writeUTF("PRODUCER");
				OUTPUT.flush();
				sendMessages(inputFileName, clientName);
			}
			finally{
				SOCKET.close();
				OUTPUT.close();
			}
		}
		catch(Exception e){}
	}
	
	private static void sendMessages(String fileName, String clientName) throws IOException{
		FileReader file = new FileReader(fileName);
		buffReader = new BufferedReader(file);
		String currentLine;
		
		while((currentLine=buffReader.readLine())!=null){
			if(currentLine.equals(".bye"))
				break;
			OUTPUT.writeUTF(clientName + ": " + currentLine);
			OUTPUT.flush();
		}		
	}
}
