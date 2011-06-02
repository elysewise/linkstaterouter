import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPListener extends Thread{

	
	Interpreter interpreter = new Interpreter();
	DatagramSocket socket;
	
	public UDPListener(DatagramSocket UDPSocket) {
		this.socket = UDPSocket;
	}
	
	
	public void run() {

		System.out.println("UDP IS RUNNING.");
		while(true) {
			
		try {
			System.out.println("UDP SOCKET IS ON: "+socket.getLocalPort());
			byte[] buf = new byte[23];
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
	this.socket.receive(packet);
	String broadcast = interpreter.packetDataToString(packet.getData());
			System.out.println("I received a BROADCAST: "+broadcast);
			RouterRecords.broadcastsCache.add(broadcast);
			// process input
		} catch (IOException e) {
			e.printStackTrace();
		}
		}
	}	
}