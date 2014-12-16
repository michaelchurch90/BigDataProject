import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class WorkerRunnable implements Runnable{
	protected Socket clientSocket = null;
    protected String serverText   = null;
    protected BufferedReader breader = null;

    public WorkerRunnable(Socket clientSocket, String serverText) {
        this.clientSocket = clientSocket;
        this.serverText   = serverText;
    }

    public void run() {
        try {
//            InputStream input  = clientSocket.getInputStream();
//            OutputStream output = clientSocket.getOutputStream();
            PrintWriter printWriter=new PrintWriter(clientSocket.getOutputStream(),true);
	    System.out.println(serverText);
	          printWriter.println(serverText);
		  
	         breader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));       
	         String clientInput;
		 String tokens[] = serverText.split("/");
	         FileWriter fw = new FileWriter(tokens[tokens.length-1]);
	         PrintWriter outFile = new PrintWriter(fw);
	      
	          while((clientInput =  breader.readLine())!=null)
	          {
	          System.out.println(clientInput);
	          outFile.println(clientInput);
	          }
	          outFile.close();
	          fw.close();
	          System.out.println("Done.");
//	         output.write(("HTTP/1.1 200 OK\n\nWorkerRunnable: " + this.serverText + " - yep..").getBytes());
//            output.close();
//            input.close();
            System.out.println("Request processed: " + serverText);
            clientSocket.close();
	    
        } catch (IOException e) {
            //report exception somewhere.
            e.printStackTrace();
        }
    }
}
