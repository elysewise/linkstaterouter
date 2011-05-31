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

    Router router;
    NetworkGraph networkGraph;
    Interpreter interpreter = new Interpreter();

    
    public RouterWorker(Router router) {
        this.router = router;
    	networkGraph = new NetworkGraph(router.getID(), router.getPort());
    }



    public void run() {





    }
    
    public void initModeSetup() throws Exception {
    	System.out.println("initialising router");
    	LinkedList<String[]> neighbours = router.getNeighbours();
        for(int i=0; i< neighbours.size(); i++) {
            LinkedList<String> broadcast = new LinkedList<String>();
            broadcast.add("0");
            broadcast.add(router.getID());
            broadcast.add(Integer.toString(router.getPort()));
            broadcast.add(neighbours.get(i)[0]);
            broadcast.add(neighbours.get(i)[1]);
            broadcast.add(neighbours.get(i)[2]);
            String broadcastString = interpreter.linkedListToString(broadcast); 
            System.out.println("broadcasting: "+broadcastString);
            passToGraph(interpreter.linkedListToString(broadcast) );
        }

    }

    private void passToGraph(String msg) {
    	if(msg == null) {
    		return;
    	}
    	LinkedList<String> broadcast = interpreter.stringToLinkedList(msg);
    	String result = networkGraph.addInformation(broadcast);
    	System.out.println("i am adding things!");
    	writeToLog(result);
    }
   
    private void writeToLog(String msg) {
    	System.out.println(msg);
    }
}