/*
 * (c) University of Zurich 2014
 */

package Assignment1;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class Server {
	private static int port; 
  
	// the data structure to store incoming messages, you are also free to implement your own data structure.
	static LinkedBlockingQueue<String> messageStore =  new LinkedBlockingQueue<String>();
	// holds all the listeners
	static ArrayList<Socket> Listeners = new ArrayList<>();
	private static ServerSocket servSock;
	
	// Listen for incoming client connections and handle them
	public static void main(String[] args) {
		//port number to listen to
		port = Integer.parseInt(args[0]);
		try{
			servSock = new ServerSocket(port);
			
			//infinite loop that constantly listens for clients
			while(true){
				Socket socket = servSock.accept();
				HandleClient Chat = new HandleClient(socket);
				Thread client = new Thread(Chat);
				client.start();
			}
		}
		catch(Exception e){}
	}
}

class HandleClient implements Runnable {
	private final Socket SOCKET;
	private DataInputStream INPUT;
	public HandleClient(Socket socket){
		this.SOCKET=socket;
	}
	
	public void run () {        
	    boolean PRODUCER = false;
	    
	    try{
	    	try{
	    		INPUT = new DataInputStream(SOCKET.getInputStream());
	    		DataOutputStream OUTPUT = new DataOutputStream(SOCKET.getOutputStream());
	    		
	    		while(true){
	    			//If there is nothing left coming in then we end
	    			String INCOMING=INPUT.readUTF();
	    			
	    			switch (INCOMING){
	    			//If the incoming data is a Listener first making contact
	    			//it is added to the list of Listeners then the entire
	    			//database of messages is sent
	    				case "LISTENER":
	    					Server.Listeners.add(SOCKET);
	    					for(String OUTPUT_str : Server.messageStore){
		    					OUTPUT.writeUTF(OUTPUT_str);
		    					OUTPUT.flush();
		    				}
	    					break;
	    				//If it is the first contact by a Producer it is noted
	    				case "PRODUCER":
	    					PRODUCER=true;
	    					break;
    					//If the incoming message is just data we make sure that 
	    				//the current client is a Producer and if it is its data
	    				//is stored in the database.
	    				default:
	    					if(PRODUCER){
	    						Server.messageStore.add(INCOMING);
	    						//Sends the current message to all the Listeners
	    						for(Socket TEMP : Server.Listeners){
	    							DataOutputStream TEMP_OUTPUT = new DataOutputStream(TEMP.getOutputStream());
	    							TEMP_OUTPUT.writeUTF(INCOMING);
	    							OUTPUT.flush();
	    						}
	    					}
	    					break;
	    			}
	    		}
	    	}
	    	finally{SOCKET.close();}
	    }
	    catch(Exception e){System.out.println(e);}
	}
}
