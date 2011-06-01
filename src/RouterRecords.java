import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;


public class RouterRecords {

	static LinkedList<String> broadcasts = new LinkedList<String>();
	static Interpreter interpreter = new Interpreter();
	static int sequenceNum = 0;
	static LinkedList<String> broadcastsCache = new LinkedList<String>();
	static DatagramSocket UDPsocket;
	static ServerSocket TCPSocket;
	static DatagramSocket FloodSendSocket;
	static Socket BootstrapSendSocket;

	
	
	static LinkedList<String> getBroadCastsWithFilter(String filterID) {
		if(filterID == null) {
			return broadcasts;
		}
		LinkedList<String> relevantBroadcasts = new LinkedList<String>();
		for (int i = 0; i < broadcasts.size(); i++) {
			String bd = broadcasts.get(i);
			if (!bd.substring(0, 1).equals(filterID)) {
				relevantBroadcasts.add(bd);
			}
		}
		return relevantBroadcasts;
	}
	
	public static int getAndUseSequence() {
		return sequenceNum ++;
	}
	
	static Boolean isExistingBroadcast(String sequence, String destNode) {
		for(int i=0; i< broadcasts.size(); i++) {
			LinkedList<String> broadcast = interpreter.stringToLinkedList(broadcasts.get(i));
			if(broadcast.get(0).equals(sequence)&&(broadcast.get(3).equals(destNode))) {
				return true;
			}
		}
			return false;
	}
	
	static void printBroadcasts() {
		System.out.println("ALL BROADCASTS:");
			for(int i=0; i< broadcasts.size(); i++) {
				System.out.println(broadcasts.get(i));
		}
	}
	 }
