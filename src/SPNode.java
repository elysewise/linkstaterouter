/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author elysewise
 */
public class SPNode {

    String ID;
    int cost;
    String parentID;

    public SPNode(String ID, int cost, String parentID) {
        this.ID = ID;
        this.cost = cost;
        this.parentID = parentID;
    }
}
