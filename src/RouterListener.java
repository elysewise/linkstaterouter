import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class RouterListener extends Thread {

	int portNumber;
	Router router;
	SocketManager socketManager;

	public RouterListener(Router router) {
		this.router = router;
		this.portNumber = router.getPort();
		socketManager = new SocketManager(router);
	}

	/**
	 * listen for a TCP client request to bootstrap
	 * @return Socket the TCP Socket for sending bootstrap info
	 */
	public Socket listen() {
		ServerSocket welcomeSocket;
		String sentence = "";
		Socket TCPSocket= null;
		try {
			welcomeSocket = router.TCPSocket;
			TCPSocket = welcomeSocket.accept();
			// create read stream to get input
			sentence = socketManager.receiveTCP(TCPSocket);
			// process input
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (sentence.contains("bootstrap")) {
			// send reply
			return TCPSocket;
		} else
			return null;
	}
}
