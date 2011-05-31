
public class BookItem extends DatabaseItem {

	private boolean hasPosts = false;
	private boolean newPosts = false;

	protected BookItem(String name, int page, int line, String content) {
		super(name, page, line, content);
	}
	
    public boolean newPosts() {
    	return newPosts;
    }
    
    public boolean hasPosts() {
    	return hasPosts;
    }
    
    public void setNewPosts(boolean condition) {
    	hasPosts =true;
    	newPosts = condition;
    }
    
    public void printItem() {
    	String firstChar = "";
    	if(newPosts()) {
    		firstChar = "n";  
    	}
    	else if(hasPosts()) {
    		firstChar = "m";
    	}
    	String output = data().substring(1);
    	System.out.println(firstChar + output);
    }
}
