


 //  client for TCPClient from Kurose and Ross
 //
// Usage: java TCPClient [server addr] [server port]
 //
import java.io.*;
import java.net.*;

public class TCPClient {

	public static void main(String[] args) throws Exception {

		// get server address
		String serverName = "localhost";
		if (args.length >= 1)
		    serverName = args[0];
		InetAddress serverIPAddress = InetAddress.getByName(serverName);

		// get server port
		int serverPort = 6789;
		//change above port number if required
		if (args.length >= 2)
		    serverPort = Integer.parseInt(args[1]);

		// create socket which connects to server
		Socket clientSocket = new Socket(serverIPAddress, serverPort);

		// get input from keyboard
		String sentence;
		BufferedReader inFromUser =
			new BufferedReader(new InputStreamReader(System.in));
		sentence = inFromUser.readLine();

		// write to server
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		outToServer.writeBytes(sentence + '\n');

		// create read stream and receive from server
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		String sentenceFromServer;
		sentenceFromServer = inFromServer.readLine();

		// print output
		System.out.println("From Server: " + sentenceFromServer);

		// close client socket
		clientSocket.close();

	} // end of main

} // end of class TCPClient