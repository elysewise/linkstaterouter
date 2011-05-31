import java.util.LinkedList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author elysewise
 */
public class RouterWorker extends Thread {

    NetworkGraph networkGraph;
    Interpreter interpreter = new Interpreter();

    
    public RouterWorker(Router router) {
    	networkGraph = new NetworkGraph(router.getID(), router.getPort());
    }



    public void run() {





    }
    
    

    private void passToGraph(String msg) {
    	if(msg == null) {
    		return;
    	}
    	LinkedList<String> broadcast = interpreter.stringToLinkedList(msg);
    	if(broadcast.remove(0) != "broadcast"  || broadcast.size() != 5) {
    		return;
    	}
    	String result = networkGraph.addBroadcast(broadcast);
    	writeToLog(result);
    }
    
    private void writeToLog(String msg) {
    	System.out.println(msg);
    }
}