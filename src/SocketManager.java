import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;


public class SocketManager {


	Router router;
	
	SocketManager(Router router) {
		this.router = router;
	}
	
    /**
     * create a TCP socket to a router
     * @param port the port for remote router
     * @return Socket the established socket
     */
    public Socket openTCPSocket(int port) {
		Socket TCPSocket = null;
		try {
			TCPSocket = new Socket(InetAddress.getLocalHost(), port);
		} catch (Exception e) {
			System.out.println(e);
			}
		return TCPSocket;
    }
    
    
    
	
	/**
	 * @param socket the TCP socket used for receiving
	 * @return String the line of text received from socket connection
	 */
	public String receiveTCP(Socket socket) {
		// create read stream and receive from socket
		BufferedReader in;
		String sentence = null;
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			sentence = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sentence;
    }
	
}
