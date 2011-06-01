import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.LinkedList;

public class TCPListener {

	public void run() {
		Socket TCPSocket = null;
		try {
			TCPSocket = RouterRecords.TCPSocket.accept();
			handleRequest(TCPSocket);
			closeSocket(TCPSocket);
			// process input
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void handleRequest(Socket socket) {
		// create read stream and receive from socket
		BufferedReader in;
		String sentence = null;
		try {
			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			sentence = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (sentence.contains("bootstrap")) {
			// send reply
			System.out.println("received bootstrap request");
			serveBootstrap(socket);
		}
		return;
	}

	/**
	 * send all broadcasts over TCP in order to bootstrap a new router
	 * 
	 * @param socket
	 *            the TCP socket to send info across
	 */
	public void serveBootstrap(Socket socket) {
		LinkedList<String> broadcasts = RouterRecords
				.getBroadCastsWithFilter(null);
		for (int i = 0; i < broadcasts.size(); i++) {
			sendTCP(socket, "broadcast " + broadcasts.get(i));
		}
		sendTCP(socket, "end bootstrap");
	}

	/**
	 * Send a String of text across a TCP socket.
	 * 
	 * @param message
	 *            the text to be sent
	 * @param socket
	 *            the TCP socket for sending text over
	 */
	public void sendTCP(Socket socket, String message) {
		DataOutputStream out;
		try {
			out = new DataOutputStream(socket.getOutputStream());
			out.writeBytes(message + '\n');
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void closeSocket(Socket socket) {
		try {
			socket.getOutputStream().close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}