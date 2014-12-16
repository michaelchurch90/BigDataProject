import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
	  public final static int SOCKET_PORT = 13267;      // you may change this
	  public final static String SERVER = "Radius";  // localhost
	  public final static String
	       FILE_TO_RECEIVED = "c:/temp/info-downloaded.txt";  // you may change this, I give a
	                                                            // different name because i don't want to
	                                                            // overwrite the one used by server...

	  public final static int FILE_SIZE = 6022386; // file size temporary hard coded
	                                               // should bigger than the file to be downloaded

	  public static void main (String [] args ) throws IOException {
	    
	      BufferedReader breader = null;
	      PrintWriter outputToServer = null;
	    
	    	    Socket sock = null;
	    try {
	      sock = new Socket(SERVER, SOCKET_PORT);
	      System.out.println("Connecting...");

	      

	      breader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
	     
	      outputToServer = new PrintWriter(sock.getOutputStream(),true);
	      
	      String input;
	      input=breader.readLine();
	    	  System.out.println(input);
	    	 input= "ModifiedInput: "+input;
	    	 outputToServer.println(input);
	      System.out.println("File " + FILE_TO_RECEIVED
	          + " downloaded (" + input + " bytes read)");
	      

	      
	    }
	    finally {
	    	if(breader!=null) breader.close();
	    	if(outputToServer != null) outputToServer.close();
	      if (sock != null) sock.close();
	    }
	  }
}
