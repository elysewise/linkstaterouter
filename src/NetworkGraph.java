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
	LinkedList<String> broadcasts;
	Interpreter interpreter = new Interpreter();
	Random generator = new Random();
	int sequenceNum = 0;

	public NetworkGraph(String sourceID) {
		this.sourceNode = new GraphNode(sourceID);
		links = new LinkedList<GraphLink>();
		nodes = new LinkedList<GraphNode>();
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

	// srcNodeID destNodeID cost
	public String addInformation(String message) {
		String result = "";

		LinkedList<String> broadcast = interpreter.stringToLinkedList(message);
		GraphNode srcNode = new GraphNode(broadcast.get(0));
		GraphNode destNode = new GraphNode(broadcast.get(1));
		int cost = Integer.parseInt(broadcast.get(2));
		GraphLink mentionedLink = new GraphLink(srcNode, destNode, cost);
		result = ("RECEIVED PACKET with identifier(" + destNode + ", " + cost
				+ ") from router " + srcNode + "\n");
		if (isExistingBroadcast(message)) {
			return result + "\t already added so DROPPED\n";
		}
		broadcasts.add(message);
		return result + addToGraph(srcNode, destNode, mentionedLink);

	}

	Boolean isExistingBroadcast(String bc) {
		if (broadcasts.contains(bc))
			return true;
		else
			return false;
	}

	// needs to handle immediates.
	public String addToGraph(GraphNode nodeA, GraphNode nodeB,
			GraphLink mentionedLink) {
		Boolean isNew = false;
		String actions = "";
		if (!alreadyInGraph(nodeA)) {
			nodes.add(nodeA);
			actions = actions
					+ ("\t added new node " + nodeA.getID() + " to GRAPH\n");
			isNew = true;
		}
		if (!alreadyInGraph(nodeB)) {
			nodes.add(nodeB);
			actions = actions
					+ ("\t added new node " + nodeB.getID() + " to GRAPH\n");
			isNew = true;
		}
		if (!alreadyInGraph(mentionedLink)) {
			links.add(mentionedLink);
			actions = actions
					+ ("\t added link (" + nodeA.getID() + "," + nodeB.getID() + ") to GRAPH\n");
			isNew = true;
			if (mentionedLink.matches(sourceNode)) {
				immediates.add(mentionedLink.getOtherNode(sourceNode));
			}
		} else if (!isNew) {
			actions = actions + " but was redundant information so DROPPED";
		}
		return actions;
	}

	public int getAndUseSequence() {
		return sequenceNum ++;
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

	public LinkedList<String> getBroadcasts(String destID) {
		LinkedList<String> relevantBroadcasts = new LinkedList<String>();
		for (int i = 0; i < broadcasts.size(); i++) {
			String bd = broadcasts.get(i);
			if (!bd.substring(0, 1).equals(destID)) {
				relevantBroadcasts.add(bd);
			}
		}
		return relevantBroadcasts;
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

