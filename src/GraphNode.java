/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author elysewise
 */
public class GraphNode {

    private final String identifier;
    private final int port;
    
    public GraphNode(String id, int port) {
    	this.identifier= id;
    	this.port = port;
    }
    
    public String getID() {
    	return this.identifier;
    }
    
    public int getPort() {
    	return this.port;
    }
    
    public String toString() {
    	return this.identifier;
    }
    
    public boolean equals(GraphNode other) {  	
    	if(other.getID().equals(this.getID())) {
    	return true;
    	}
    	return false;
    }
}
