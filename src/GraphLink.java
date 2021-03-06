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

    public GraphNode getA() {
    	return a;
    }
    
    public GraphNode getB() {
    	return b;
    }
    
    
    public int getCost() {
        return cost;
    }

    public boolean matches (GraphNode target ) {
        if( this.a.getID().equals(target.getID()) || this.b.getID().equals(target.getID()))
        {
        return true;
        }
        return false;
    }
    
    public boolean matches (String aID, String bID) {
        if( this.a.getID().equals(aID) && this.b.getID().equals(bID))
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
    	if( newNode.getID().equals(a)) {
    		a = newNode;
    	}
    	else if( newNode.getID().equals(b)) {
    		b = newNode;
    	}
    }
}
