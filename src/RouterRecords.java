import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;


public class RouterRecords {

	static LinkedList<String> broadcasts = new LinkedList<String>();
	static Interpreter interpreter = new Interpreter();
	static int sequenceNum = 0;
	static LinkedList<String> broadcastsCache = new LinkedList<String>();
	static int broadcastLength;
	

	
	
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
	
	public static String getAndUseSequence() {
		String sequence ="";
		if(sequenceNum<10) {
			sequence =("000"+sequenceNum);
		}
		else if(sequenceNum<100) {
			sequence= ("00"+sequenceNum);
		}
		else if(sequenceNum<1000) {
			sequence ="0"+sequenceNum;
		}
		else {
			sequence = Integer.toString(sequenceNum);
		}
		sequenceNum++;
		return sequence;
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
