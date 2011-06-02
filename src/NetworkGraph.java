import java.util.LinkedList;
import java.util.Random;

/**
 * 
 * @author elysewise
 */
public class NetworkGraph {

	final GraphNode sourceNode;
	LinkedList<GraphLink> links;
	LinkedList<GraphNode> nodes;
	LinkedList<GraphNode> immediates;
	Interpreter interpreter = new Interpreter();
	Random generator = new Random();
	int sequenceNum = 0;

	public NetworkGraph(Router router) {
		this.sourceNode = new GraphNode(router.getID(), router.getPort());
		links = new LinkedList<GraphLink>();
		nodes = new LinkedList<GraphNode>();
		immediates = new LinkedList<GraphNode>();
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

	// sequence srcNodeID srcPort destNodeID destPort cost
	public String addInformation(String message) {
		String result = "";
		try {	
		LinkedList<String> broadcast = interpreter.stringToLinkedList(message);
		String source = broadcast.get(0);
		String sequence = broadcast.get(1);
		GraphNode aNode = new GraphNode(broadcast.get(2), Integer.parseInt(broadcast.get(3)));
		GraphNode bNode = new GraphNode(broadcast.get(4), Integer.parseInt(broadcast.get(5)));
		int cost = Integer.parseInt(broadcast.get(6));
		GraphLink mentionedLink = new GraphLink(aNode, bNode, cost);
		result = ("RECEIVED PACKET with identifier(" + source + ", " + sequence
				+ ") from router " + source + '\n');
		if (RouterRecords.isExistingBroadcast(source, sequence)) {
			return result + '\t' + "already added so DROPPED" +'\n';
		}
		RouterRecords.broadcasts.add(message);
	//	System.out.println("adding to broadcasts: "+message);
		return result + addToGraph(aNode, bNode, mentionedLink);
		}
		catch(Exception e) {
			e.printStackTrace();
			return result + "detected packet corruption so DROPPED";
		}
	}

	

	// needs to handle immediates.
	public String addToGraph(GraphNode nodeA, GraphNode nodeB,
			GraphLink mentionedLink) {
		Boolean isNew = false;
		String actions = "";
		if (!alreadyInGraph(nodeA)) {
			nodes.add(nodeA);
			actions = actions
					+ ('\t'+" added new node " + nodeA.getID() + " to GRAPH" + '\n');
			isNew = true;
		}
		if (!alreadyInGraph(nodeB)) {
			nodes.add(nodeB);
			actions = actions
			+ ('\t'+" added new node " + nodeB.getID() + " to GRAPH" + '\n');
			isNew = true;
		}
		if (!alreadyInGraph(mentionedLink)) {
			links.add(mentionedLink);
			actions = actions
					+ ('\t'+" added link (" + nodeA.getID() + "," + nodeB.getID() + ") to GRAPH" + '\n');
			isNew = true;
			if (mentionedLink.matches(sourceNode)) {
				immediates.add(mentionedLink.getOtherNode(sourceNode));
			}
		} else if (!isNew) {
			actions = actions + " but was redundant information so DROPPED";
		}
		return actions;
	}

	
	
	private boolean alreadyInGraph(GraphNode n) {
		for (int i = 0; i < nodes.size(); i++) {
			if (nodes.get(i).getID().equals(n.getID())) {
				return true;
			}
		}
		return false;
	}

	private boolean alreadyInGraph(GraphLink e) {
		for (int i = 0; i < links.size(); i++) {
			GraphLink iLink = links.get(i);
			if (iLink.getA().getID().equals(e.getA().getID())) {
				if (iLink.getB().getID().equals(e.getB().getID())) {
					if (iLink.getCost() == (e.getCost())) {
						return true;
					}
				}
			}
		}
		return false;
	}

	
	
	public LinkedList<GraphNode> getImmediates() {
		return immediates;
	}
}
// public boolean containsNode(GraphNode thisNode) {
// for (int i = 0; i < nodes.size(); i++) {
// if (thisNode.equals(nodes.get(i))) {
// return true;
// }
// }
// return false;
// }
//
// public boolean containsLink(GraphLink thisLink) {
// for (int i = 0; i < links.size(); i++) {
// if (thisLink.equals(links.get(i))) {
// return true;
// }
// }
// return false;
// }
// public String[][][] getTable() {
// String[][][] newTable = new
// String[nodes.size()-1][nodes.size()-1][nodes.size()-1];
// GraphNode currentNode = getNode(sourceID);
// return newTable;
// }
//
// public LinkedList<SPNode> findShortestPaths() {
// LinkedList<SPNode> queue = new LinkedList<SPNode>();
//
// for(int i =0; i< nodes.size(); i++) {
// queue.add(new SPNode(nodes.get(i).identifier, Integer.MAX_VALUE, null));
// }
// queue.add(new SPNode(sourceID, 0, null)); //add source onto queue
// while(!queue.isEmpty()) {
//
// //get u = node with least cost from queue
// SPNode u = queue.get(0);
// for(int j=1; j< queue.size(); j++) {
// SPNode current = queue.get(j);
// if(current.cost< u.cost) {
// u = current;
// }
// }
// }
// return queue;
// }

