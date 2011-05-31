/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author elysewise
 */
public class GraphLink {

    String a;
    String b;
    int cost;

    public GraphLink(String a, String b, int cost) {
        this.a = a;
        this.b = b;
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }

    public boolean matches (String target ) {
        if( this.a.equals(target) || this.b.equals(target))
        {
        return true;
        }
        return false;
}
    public String getOtherID(String thisID) {
        if(thisID.equals(a)) {
            return b;
        }
        if(thisID.equals(b)) {
            return a;
        }
        return null;
    }
}
