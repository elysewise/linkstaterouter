import java.util.LinkedList;

/**
 *
 * @author elysewise
 */
public class NetworkGraph {

	final GraphNode sourceNode;
    LinkedList<GraphLink> links;
    LinkedList<GraphNode> nodes;
    LinkedList<String> serials;
    LinkedList<GraphNode> immediates;
    LinkedList<String> broadcasts;
    Interpreter interpreter = new Interpreter();
    
    public NetworkGraph(String sourceID, int sourcePort) {
        this.sourceNode = new GraphNode(sourceID, sourcePort);
    }
        
    public GraphNode getNode(String targetID) {
        for (int i = 0; i < nodes.size(); i++) {
            GraphNode node = nodes.get(i);
            if (node.identifier.equals(targetID)) {
                return node;
            }
        }
        return null;
    }
   
    
    //needs to handle immediates.
    // serial srcNode srcPort destNode destPort cost
    public String addBroadcast (LinkedList<String> broadcast) {
    	String serial = broadcast.get(0);
    	String actions = ("RECEIVED PACKET ("+serial+")\n");
    	if (serials.contains(serial)){
    		return (actions + "\t already added so DROPPED\n");
    	}
    	GraphNode nodeA = new GraphNode(broadcast.get(1), Integer.parseInt(broadcast.get(2)));
    	if( !nodes.contains(nodeA)) {
    		nodes.add(nodeA);
    		actions = actions + ("\t added new node "+nodeA.identifier+" to GRAPH\n"); 
    	}
    	GraphNode nodeB = new GraphNode(broadcast.get(3), Integer.parseInt(broadcast.get(4)));
    	if( !nodes.contains(nodeB)) {
    		nodes.add(nodeB);
    		actions = actions + ("\t added new node "+nodeB.identifier+" to GRAPH\n");
    	}
    	int cost = Integer.parseInt(broadcast.get(4));
    	GraphLink mentionedLink = new GraphLink(nodeA, nodeB, cost);
    	if( !links.contains(mentionedLink)) {
    		links.add(mentionedLink);
    		actions = actions + ("\t added link ("+nodeA.identifier+","+nodeB.identifier+") to GRAPH\n");
    		if(mentionedLink.matches(sourceNode)) {
    			immediates.add(mentionedLink.getOtherNode(sourceNode));
    		}
    	}
    	serials.add(serial);
    	broadcasts.add(interpreter.linkedListToString(broadcast));
    	return actions;
    }
}


//public boolean containsNode(GraphNode thisNode) {
//  for (int i = 0; i < nodes.size(); i++) {
//      if (thisNode.equals(nodes.get(i))) {
//          return true;
//      }
//  }
//  return false;
//}
//
//public boolean containsLink(GraphLink thisLink) {
//  for (int i = 0; i < links.size(); i++) {
//      if (thisLink.equals(links.get(i))) {
//          return true;
//      }
//  }
//  return false;
//}



//public String[][][] getTable() {
//  String[][][] newTable = new String[nodes.size()-1][nodes.size()-1][nodes.size()-1];
//  GraphNode currentNode = getNode(sourceID);
//  return newTable;
//}



//
//public LinkedList<SPNode> findShortestPaths() {
//    LinkedList<SPNode> queue = new LinkedList<SPNode>();
//
//    for(int i =0; i< nodes.size(); i++) {
//        queue.add(new SPNode(nodes.get(i).identifier, Integer.MAX_VALUE, null));
//    }
//    queue.add(new SPNode(sourceID, 0, null));   //add source onto queue
//    while(!queue.isEmpty()) {
//        
//        //get u = node with least cost from queue
//        SPNode u = queue.get(0);
//        for(int j=1; j< queue.size(); j++) {
//            SPNode current = queue.get(j);
//            if(current.cost< u.cost) {
//                u = current;
//            }
//        }        
//    }
//    return queue;
//}
