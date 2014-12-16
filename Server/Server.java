import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.lang.Thread;

public class Server {

	  public final static int SOCKET_PORT = 13267;  // you may change this
	  public final static String FILE_TO_SEND = "info.txt";  // you may change this

	  public static void main (String [] args ) throws IOException {
		ArrayList<String>urlList = new ArrayList<String>();
	    BufferedReader breader = null;
	    ServerSocket servsock = null;
//	    Socket sock = null;
	    File myFile = new File (FILE_TO_SEND);
	    Scanner inputFile = new Scanner(myFile);
	    PrintWriter printWriter =null;
	    
	    while(inputFile.hasNext())
	    	urlList.add(inputFile.nextLine());
	    
	    int urlPos=0;
	    
	    try {
	      servsock = new ServerSocket(SOCKET_PORT);
	      while (urlPos<urlList.size()) {
	        System.out.println("Waiting...");
	        try {
	          Socket sock = servsock.accept();
	          System.out.println("Accepted connection : " + sock);

	      //--------------new thread 
	          System.out.println("Sending " + FILE_TO_SEND + "(" + urlList.get(urlPos) + ")");
//---------------------------
	          new Thread(new WorkerRunnable(sock, urlList.get(urlPos))).start();
	          
//	          printWriter.println(urlList.get(urlPos));
//	          
//	          breader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
//	          printWriter.println(sock.getInputStream());
	          
	          //-----------end thread
	          urlPos++;
	        }
	        finally {
	          if (breader != null) breader.close();
	          if (printWriter != null) printWriter.close();
	          if (inputFile != null) inputFile.close();
//	          if (sock!=null) sock.close();
	        }
	      }
	    }
	    finally {
	    	
	    	if (servsock != null) servsock.close();
	    }
	  }
}
