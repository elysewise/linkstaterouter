
//this will be the generic object used to store posts and lines from books
public class DatabaseItem {

    private String bookName;
    private int pageNumber;
    private int lineNumber;
    private String data;
  

    protected DatabaseItem(String name, int page, int line, String content) {
        this.bookName = name;
        this.pageNumber = page;
        this.lineNumber = line;
        this.data = content;
    }

    public String bookName() {
        return bookName;
    }

    public int pageNumber() {
        return pageNumber;
    }

    public int lineNumber() {
        return lineNumber;
    }

    public String data() {
        return data;
    }
   
}
