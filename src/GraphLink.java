/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author elysewise
 */
public class GraphLink {

    GraphNode a;
    GraphNode b;
    int cost;

    public GraphLink(GraphNode a, GraphNode b, int cost) {
        this.a = a;
        this.b = b;
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }

    public boolean matches (GraphNode target ) {
        if( this.a.equals(target) || this.b.equals(target))
        {
        return true;
        }
        return false;
    }
    
    public GraphNode getOtherNode(GraphNode thisNode) {
        if(thisNode.equals(a)) {
            return b;
        }
        if(thisNode.equals(b)) {
            return a;
        }
        return null;
    }
    
    public void updateNode(GraphNode newNode) {
    	if( newNode.identifier.equals(a)) {
    		a = newNode;
    	}
    	else if( newNode.identifier.equals(b)) {
    		b = newNode;
    	}
    }
}
