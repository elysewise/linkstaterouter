
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
        links = new LinkedList<GraphLink>();
        nodes = new LinkedList<GraphNode>();
        serials = new LinkedList<String>();
        immediates = new LinkedList<GraphNode>();
        broadcasts = new LinkedList<String>();
    }

    public GraphNode getNode(String targetID) {
        for (int i = 0; i < nodes.size(); i++) {
            GraphNode node = nodes.get(i);
            if (node.getID().equals(targetID)) {
                return node;
            }
        }
        return null;
    }
    
    // serial srcNode srcPort destNode destPort cost 
    public String addInformation(LinkedList<String> message) {
    	String serial = message.get(0); //if serial is 0, add it anyway but don't add to serials list
    	GraphNode nodeA = new GraphNode(message.get(1), Integer.parseInt(message.get(2)));
    	GraphNode nodeB = new GraphNode(message.get(3), Integer.parseInt(message.get(4)));
    	int cost = Integer.parseInt(message.get(4));
    	GraphLink mentionedLink = new GraphLink(nodeA, nodeB, cost);
    	String result = ("RECEIVED PACKET (" + serial + ")\n");
    	if(serial.equals("0") || !serials.contains(serial)) {
    	        serials.add(serial);
    	        broadcasts.add(interpreter.linkedListToString(message));
    		return result + addToGraph(serial, nodeA, nodeB, mentionedLink);
    	}
    	else {
    		return result + "\t already added so DROPPED\n";
    	}
    }
    

    //needs to handle immediates.
    public String addToGraph(String serial, GraphNode nodeA, GraphNode nodeB, GraphLink mentionedLink) {
        Boolean isNew = false;
        String actions = "";
        if (!alreadyInGraph(nodeA)) {
            nodes.add(nodeA);
            actions = actions + ("\t added new node " + nodeA.getID() + " to GRAPH\n");
            isNew = true;
        }
        if (!alreadyInGraph(nodeB)) {
            nodes.add(nodeB);
            actions = actions + ("\t added new node " + nodeB.getID() + " to GRAPH\n");
            isNew = true;
        }
        if (!alreadyInGraph(mentionedLink)) {
            links.add(mentionedLink);
            actions = actions + ("\t added link (" + nodeA.getID() + "," + nodeB.getID() + ") to GRAPH\n");
            isNew = true;
            if (mentionedLink.matches(sourceNode)) {
                immediates.add(mentionedLink.getOtherNode(sourceNode));
            }
        }
        else if (!isNew){ 
            actions = actions + " but was redundant information so DROPPED";
        }
        return actions;
    }
    
    
    private boolean alreadyInGraph(GraphNode n) {
    	for( int i=0; i< nodes.size(); i++) {
    		if(nodes.get(i).getID().equals(n.getID())) {
    			if(nodes.get(i).getPort() == n.getPort()) {
    				return true;
    			}
    		}
    	}
    	return false;
    }
    
    private boolean alreadyInGraph(GraphLink e) {
    	for( int i=0; i< links.size(); i++) {
    		GraphLink iLink = links.get(i);
    		if(iLink.getA().equals(e.getA())) {
    			if(iLink.getB().equals(e.getB())) {
    				if(iLink.getCost() == (e.getCost())) {
    	    	    return true;
    			}
    		}
    	}
    	}
    	return false;
        
    
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

