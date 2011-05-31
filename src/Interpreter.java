import java.util.LinkedList;
import java.util.StringTokenizer;

//performs basic functions for interpreting messages
public class Interpreter {

    public LinkedList<String> stringToLinkedList(String data) {
        LinkedList<String> words = new LinkedList<String>();
        if (data.isEmpty()) {
            return words;
        }
        StringTokenizer token = new StringTokenizer(data);
        while (token.hasMoreTokens()) {
            words.add(token.nextToken());
        }
        return words;
    }


    public String linkedListToString(LinkedList<String> data) {
        String result = new String();
        result = data.get(0);
        for (int i = 1; i < data.size(); i++) {
            result = result.concat(" " + data.get(i));
        }
        return result;
    }
}
