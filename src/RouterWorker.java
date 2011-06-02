import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.Timer;

/**
 * 
 * @author elysewise z3308940
 */
public class RouterWorker extends Thread {
	Router router;
	NetworkGraph networkGraph;
	Interpreter interpreter = new Interpreter();
	Random generator = new Random();
	LogManager logManager = null;
	SocketManager socketManager;
	static final int FIVE_SECONDS = 5000;
	static final int ONE_MINUTE = 60000;
	InetAddress local;
	boolean waitAnotherMin = false;
	Timer floodTimer;
	Timer dijkstraTimer;
	Timer timeoutTimer;

	/**
	 * constructor for RouterWorker.
	 * 
	 * @param router
	 *            the router object that this class will be working on
	 * @throws SocketException
	 */
	public RouterWorker(Router router) throws Exception {
		this.router = router;
		networkGraph = new NetworkGraph(router);
		logManager = new LogManager(router.getID());
		local = InetAddress.getLocalHost();
		socketManager = new SocketManager(router);

		floodTimer = new Timer(FIVE_SECONDS, new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				flood();
			}
		});
		dijkstraTimer = new Timer(FIVE_SECONDS, new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				runDijkstra();
			}
		});
		timeoutTimer = new Timer(ONE_MINUTE * 2, new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});

	}

	public void run() {
		System.out.println("WORKER IS RUNNING.");
		try {
			setup();
			if (router.getMode().equals("add")) {
				// requestBootstrap(router.getNeighbours().get(0));
			} else if (router.getMode().equals("init")) {
				waitAnotherMin = true;
			}
			// wait random time 0-5 seconds
			Thread.sleep(getRandomWaitTime());
			RouterRecords.printBroadcasts();
			floodTimer.start();
			dijkstraTimer.start();
			timeoutTimer.start();
			while (true) {

			}
		} catch (Exception e) {
			System.out.println("RouterWorkerError");
			e.printStackTrace(System.out);
			System.exit(-1);
		}
	}

	/**
	 * initialise router by adding configuration details for neighbours to
	 * graph.
	 * 
	 * @throws Exception
	 */
	public void setup() throws Exception {
		System.out.println("initialising router...");
		LinkedList<Neighbour> neighbours = router.getNeighbours();
		for (int i = 0; i < neighbours.size(); i++) {
			LinkedList<String> broadcast = new LinkedList<String>();
			broadcast.add(router.getID());
			broadcast.add(RouterRecords.getAndUseSequence());
			broadcast.add(router.getID());
			broadcast.add(Integer.toString(router.getPort()));
			broadcast.add(neighbours.get(i).getID());
			broadcast.add(Integer.toString(neighbours.get(i).getPort()));
			broadcast.add(Integer.toString(neighbours.get(i).getCost()));
			passBroadcastToGraph(interpreter.linkedListToString(broadcast));
			passBroadcastToGraph(interpreter.linkedListToString(broadcast));
		}
	}

	public void runDijkstra() {
		NetworkGraph network = networkGraph; // take copy so that updates from
												// other threads can still occur
		if (waitAnotherMin) {
			waitAnotherMin = false;
			return;
		}
		System.out.println("RUNNING DIJKSTRA...");
		this.addFromCache();
		RouterRecords.printBroadcasts();
		final int INFINITY = Integer.MAX_VALUE;
		final int TRUE = 1;
		final int FALSE = 0;
		final int NOTSET = -1;
		int[] D = network.getD();
		Arrays.fill(D, INFINITY);
		String[] names = network.getNames();
		int numberOfNodes = names.length;
		int srcIndex = NOTSET;
		int destIndex = NOTSET;
		int[] parents = new int[numberOfNodes];
		Arrays.fill(parents, NOTSET);
		int[] closed = new int[numberOfNodes];
		Arrays.fill(closed, FALSE);
		Boolean finished = false;

		System.out.println("CURRENT ALGORITHM VALUES: ");
		System.out.println("names     " + Arrays.toString(names));
		System.out.println("leastcost " + Arrays.toString(D));
		System.out.println("parents   " + Arrays.toString(parents));
		System.out.println("closed    " + Arrays.toString(closed));

		// get index for start
		for (int x = 0; x < numberOfNodes; x++) {
			if (names[x].equals(router.getID())) {
				srcIndex = x;
			}
		}

		// Initialise D[] with values from source
		for (int i = 0; i < numberOfNodes; i++) {
			D[i] = network.cost(names[srcIndex], names[i]);
			if (D[i] != Integer.MAX_VALUE) {
				parents[i] = srcIndex;
			}
		}

		D[srcIndex] = 0;

		while (!finished) {

			// find index of smallest value in d which is not closed.
			srcIndex = NOTSET;
			for (int j = 0; j < numberOfNodes; j++) {
				if (closed[j] == FALSE) {
					if (srcIndex == NOTSET) {
						srcIndex = j;
					} else if (D[j] < D[srcIndex]) {
						srcIndex = j;
					}
				}
			}

			if (srcIndex == NOTSET) {
				finished = true;
			}

			else {
			System.out.println("CURRENT ALGORITHM VALUES: ");
			System.out.println("names     " + Arrays.toString(names));
			System.out.println("leastcost " + Arrays.toString(D));
			System.out.println("parents   " + Arrays.toString(parents));
			System.out.println("closed    " + Arrays.toString(closed));

			closed[srcIndex] = TRUE;

			// update D[] with any shorter paths found
			for (int j = 0; j < numberOfNodes; j++) {
				int oldCost = D[j];
				if(oldCost == INFINITY) {
					
				}
				int newCost = D[srcIndex]
						+ network.cost(names[srcIndex], names[j]);
				if (newCost < oldCost) {
					D[j] = newCost;
					parents[j] = parents[srcIndex];
				}
			}
		}
		}
		System.out.println("FINAL ALGORITHM VALUES: ");
		System.out.println("names     " + Arrays.toString(names));
		System.out.println("leastcost " + Arrays.toString(D));
		System.out.println("parents   " + Arrays.toString(parents));
		System.out.println("closed    " + Arrays.toString(closed));
	}

	/**
	 * send a request to a neighbour for bootstrap information receive all
	 * bootstrap responses and add them to graph.
	 * 
	 * @param nbr
	 *            the neighbouring router that request will be sent to
	 */
	// public void requestBootstrap(Neighbour nbr) {
	// System.out.println("router "+router.getID()+" requesting a bootstrap from "+nbr.getID());
	// Socket socket = socketManager.openTCPSocket(nbr.getPort());
	// // socketManager.sendTCP(socket, "boostrap " + router.getID() + " "
	// // + router.getPort());
	// String sentence = null;
	// while (true) {
	// sentence = socketManager.receiveTCP(socket);
	// LinkedList<String> broadcast = interpreter
	// .stringToLinkedList(sentence);
	// if (broadcast.get(0).equals("broadcast")) {
	// broadcast.remove(0);
	// networkGraph.addInformation(interpreter
	// .linkedListToString(broadcast));
	// }
	// if(broadcast.get(0).equals("end")) {
	// try {
	// System.out.println("bootstrap complete.");
	// socket.close();
	// } catch (IOException e) {
	// e.printStackTrace(System.out);
	// }
	// }
	// }
	// }

	public void flood() {
		System.out.println("FLOODING...");
		addFromCache();
		LinkedList<GraphNode> immediates = networkGraph.getImmediates();
		System.out.println("there are " + immediates.size() + "immediates");

		for (int i = 0; i < immediates.size(); i++) {
			GraphNode immediate = immediates.get(i);
			LinkedList<String> broadcasts = RouterRecords
					.getBroadCastsWithFilter(immediate.getID());
			if (broadcasts != null) {
				for (int j = 0; j < broadcasts.size(); j++) {
					byte[] buf = new byte[256];
					String data = broadcasts.get(j);
					buf = Arrays.copyOf(data.getBytes(), buf.length);
					DatagramPacket packet = new DatagramPacket(buf, buf.length,
							router.getLocal(), immediate.getPort());
					try {
						// System.out.println("I am sending : "+new
						// String(packet.getData()));
						// System.out.println("To: "+packet.getPort());
						DatagramSocket socket = new DatagramSocket();
						socket.send(packet);
					} catch (Exception e) {
						e.printStackTrace(System.out);
					}
				}
			}
		}
	}

	void addFromCache() {
		LinkedList<String> cache = RouterRecords.broadcastsCache;
		// System.out.println("adding "+cache.size()+" broadcasts from cache");
		for (int i = 0; i < cache.size(); i++) {
			this.passBroadcastToGraph(cache.get(i));
		}
	}

	/**
	 * pass a string to the graph and record the result in a log.
	 * 
	 * @param msg
	 *            the string with network information
	 */
	private void passBroadcastToGraph(String msg) {
		if (msg == null) {
			return;
		}
		String result = networkGraph.addInformation(msg);
		writeToLog(result);
	}

	/**
	 * write a string to this router's log file.
	 * 
	 * @param msg
	 *            the content to be added to log
	 */
	private void writeToLog(String msg) {
		// System.out.println(msg);
		// logManager.addToLog(msg);
	}

	/**
	 * generate a random value (in milliseconds) between 0-5secs
	 * 
	 * @return randomly generated time value
	 */
	private long getRandomWaitTime() {
		return (long) generator.nextInt(5000);
	}
}