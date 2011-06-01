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

    public GraphNode(String id) {
        this.identifier = id;
    }
    
//    public GraphNode(String id, int port) {
//    	this.identifier= id;
//    	this.port = port;
//    }
    
    public String getID() {
    	return this.identifier;
    }
    
//    public void setPort(int portNumber) {
//    	this.port = portNumber;
//    }
//    
//    public int getPort() {
//    	return this.port;
//    }
    
    public String toString() {
    	return this.identifier;
    }
}
