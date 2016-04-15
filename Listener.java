/*
 * (c) University of Zurich 2014
 */


package Assignment1;

import java.net.*;
import edu.princeton.cs.introcs.StdOut;
import java.io.*;

public class Listener
{
    public static void main(String [] args)
   {
      String serverName = args[0];
      int port = Integer.parseInt(args[1]);

      Socket SOCKET;
      DataInputStream INPUT;
      DataOutputStream OUTPUT;
	  // create connection to server
	  // send the string  "LISTENER" to server first!!
	  // continuously receive messages from server
      // using stdout to print out messages Received
      // do not close the connection- keep listening to further messages from the server.
      try{
    	  try{
    		  SOCKET = new Socket(serverName, port);
    		  OUTPUT = new DataOutputStream(SOCKET.getOutputStream());
    		  INPUT = new DataInputStream(SOCKET.getInputStream());
    		  
    		  OUTPUT.writeUTF("LISTENER");
    		  OUTPUT.flush();
    		  while(true){
    			  String temp;
    			  if((temp = INPUT.readUTF()) != null)
    				  StdOut.println(temp);
    		   }
    	  }
    	  finally{}
      }
      catch(Exception e){}
   
   }
}
