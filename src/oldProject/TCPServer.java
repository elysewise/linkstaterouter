

/*
 *
 * tcpServer from Kurose and Ross
 *
 * Usage: java TCPServer [server port]
 */

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class TCPServer {

   

	public static void main(String[] args)throws Exception {


                assert(args.length == 1);
		
		/* change above port number this if required */
		int serverPort = Integer.parseInt(args[0]);

		// create server socket
		ServerSocket welcomeSocket = new ServerSocket(serverPort);
                System.out.println("The server is listening on port number" + serverPort);
        ArrayList<PostItem> PostList = new ArrayList<PostItem>();
                System.out.println("The database for discussion posts has been initialised");

                int i=0;

		while (i ==0){
		    // accept connection from connection queue
		    Socket connectionSocket = welcomeSocket.accept();
		    System.out.println("connection from " + connectionSocket);

		    // create read stream to get input
		    BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
		    String clientSentence;
		    clientSentence = inFromClient.readLine();

		    // process input
		    String capitalizedSentence;
		    capitalizedSentence = clientSentence + '\n';

		    // send reply
		    DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
		    outToClient.writeBytes(capitalizedSentence);

		} // end of while (true)

	} // end of main()

} // end of class TCPServer
