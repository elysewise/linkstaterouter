
import java.util.LinkedList;

/**
 *
 * @author elysewise
 */
public class NetworkGraph {

    LinkedList<GraphLink> links;
    LinkedList<GraphNode> nodes;
    final String sourceID;
    String[][][] prevRoutingTable;

    public NetworkGraph(LinkedList<GraphLink> links, LinkedList<GraphNode> nodes, String sourceID) {
        this.links = links;
        this.nodes = nodes;
        this.sourceID = sourceID;
    }

    public String[][][] getTable() {
        String[][][] newTable = new String[nodes.size()-1][nodes.size()-1][nodes.size()-1];
        GraphNode currentNode = getNode(sourceID);












        return newTable;
    }
    
    public LinkedList<SPNode> findShortestPaths() {
        LinkedList<SPNode> queue = new LinkedList<SPNode>();

        for(int i =0; i< nodes.size(); i++) {
            queue.add(new SPNode(nodes.get(i).identifier, Integer.MAX_VALUE, null));
        }





        queue.add(new SPNode(sourceID, 0, null));   //add source onto queue
        while(!queue.isEmpty()) {
            
            //get u = node with least cost from queue
            SPNode u = queue.get(0);
            for(int j=1; j< queue.size(); j++) {
                SPNode current = queue.get(j);
                if(current.cost< u.cost) {
                    u = current;
                }
            }
            
            
            
        }  






        return queue;
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

    //get a list of nodes which are immediate neighbours of arg node.
    public LinkedList<GraphNode> getNeighbours(GraphNode node) {
        LinkedList<GraphNode> neighbours = new LinkedList<GraphNode>();
        String nodeID = node.identifier;
        for (int i = 0; i < links.size(); i++) {
            GraphLink thisLink = links.get(i);
            if (thisLink.matches(node.identifier)) {
                String neighbourID = thisLink.getOtherID(nodeID);
                GraphNode neighbourNode = getNode(neighbourID);
                neighbours.add(neighbourNode);
            }
        }
        if (!neighbours.isEmpty()) {
            return neighbours;
        }
        return null;
    }

    public void addNode(GraphNode newNode) {
        System.out.println("New router added to network: ");
        newNode.printNodeInfo();
        nodes.add(newNode);
    }

    public void addLink(GraphLink newLink) {
        System.out.println("new link between " + newLink.a + " and " + newLink.b);
        links.add(newLink);
    }

    public boolean containsNode(GraphNode thisNode) {
        for (int i = 0; i < nodes.size(); i++) {
            if (thisNode.equals(nodes.get(i))) {
                return true;
            }
        }
        return false;
    }

    public boolean containsLink(GraphLink thisLink) {
        for (int i = 0; i < links.size(); i++) {
            if (thisLink.equals(links.get(i))) {
                return true;
            }
        }
        return false;
    }
}
