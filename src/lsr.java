import java.net.Socket;
import java.util.LinkedList;

public class lsr{

	static Interpreter interpreter = new Interpreter();
	static RouterWorker worker;
	static Router router;
	static UDPListener udpListener = new UDPListener();
	static TCPListener tcpListener = new TCPListener();
	
	
	
	public static void main(String[] args) {
		try {
			router = new Router(args);
			worker = new RouterWorker(router);
			RouterRecords.printBroadcasts();
			worker.start();
			udpListener.start();
			tcpListener.start();			
			
			
		} catch (Exception e) {
			System.out.println("initialisation of router failed:");
			e.printStackTrace(System.out);
			System.exit(-1);
		}
	}

	//public static void waitForBootstrap() {
	//	System.out.println("router "+router.getID()+" is ready and waiting.");
//		while (true) {
//			Socket socket = listener.listen();
//			worker.serveBootstrap(socket);
//		}
//	}
}
