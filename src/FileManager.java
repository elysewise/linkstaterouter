import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class FileManager {

	public LinkedList<String> readFile(String filename) throws IOException {
		LinkedList<String> extracted = new LinkedList<String>();
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		String nextLine = null;
		while ((nextLine = reader.readLine()) != null) {
			extracted.add(nextLine);
		}
		return extracted;
	}

	public void writeToFile(String data, String filename) {

	}

}
