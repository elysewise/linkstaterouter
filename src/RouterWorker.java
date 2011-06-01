import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author elysewise z3308940
 */
public class RouterWorker extends Thread {

    Router router;
    NetworkGraph networkGraph;
    Interpreter interpreter = new Interpreter();
    Random generator = new Random();
    
    /**
     * constructor for RouterWorker.
     * @param router the router object that this class will be working on
     */
    public RouterWorkeAAr(Router router) {
        this.router = router;
    	networkGraph = new NetworkGraph(router.getID());
    }

    public void run() {





    }
    
    /**
     * initialise router by adding configuration details for neighbours to graph.
     * @throws Exception
     */
    public void initModeSetup() throws Exception {
    	System.out.println("initialising router...");
    	LinkedList<Neighbour> neighbours = router.getNeighbours();
        for(int i=0; i< neighbours.size(); i++) {
            LinkedList<String> broadcast = new LinkedList<String>();
            broadcast.add(router.getID());
            broadcast.add(neighbours.get(i).getID());
            broadcast.add(Integer.toString(neighbours.get(i).getCost()));
            passToGraph(interpreter.linkedListToString(broadcast) );
            passToGraph(interpreter.linkedListToString(broadcast) );
        }
    }
    
    /**
     * setup router by requesting a bootstrap
     * @throws Exception
     */
    public void addModeSetup() throws Exception {
    	System.out.println("adding router to network..."); 
    	bootStrap(router.getNeighbours().get(0));
    	
    }
    
    void bootStrap(Neighbour nbr) {
    	int nbrPort = nbr.getPort();
    	sendTCP("bootstrap");
    	String response = "";
    	while(!response.equals("end")) {
    		handleBroadcast(response);
    		response = receiveTCP();
    		
    	}
    }

    /**
     * pass a string to the graph and record the result in a log.
     * @param msg the string with network information
     */
    private void passToGraph(String msg) {
    	if(msg == null) {
    		return;
    	}
    	String result = networkGraph.addInformation(msg);
    	writeToLog(result);
    }
   
    private Socket openTCPSocket(int port) {
		Socket TCPSocket = null;
		try {
			TCPSocket = new Socket(InetAddress.getLocalHost(), port);
		} catch (Exception e) {
			System.out.println(e);
			}
		return TCPSocket;
    }
    
    private void sendTCP(String message, Socket socket) {
        DataOutputStream out;
		try {
			out = new DataOutputStream(socket.getOutputStream());
			out.writeBytes(message + '\n');
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
	private String receiveTCP(Socket socket) {
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
    
    /**
     * write a string to this router's log file.
     * @param msg the content to be added to log
     */
    private void writeToLog(String msg) {
    	System.out.println(msg);
    }
    
    
    /**
     * generate a random value (in milliseconds) between 0-5secs
     * @return randomly generated time value
     */
    private int getRandomWaitTime() {
    	return generator.nextInt(5000);
    }
}