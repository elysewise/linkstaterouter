/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author elysewise
 */


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;


/*
*
*  client for TCPClient from Kurose and Ross
*
*  * Usage: java TCPClient [0]mode [1]polling_interval [2]username [3]server_addr [4]server port
*/
import java.io.*;
import java.net.*;

public final class reader {
    static final int PAGES = 4;
    static final int LINES = 9;
    static final int PAGE1 = 0;
    static final int PAGE2 = PAGE1 + LINES;
    static final int PAGE3 = PAGE2 + LINES;
    static final int PAGE4 = PAGE3 + LINES;
    static ArrayList<BookItem> exupery = new ArrayList<BookItem> (LINES * PAGES);
    static ArrayList<BookItem> shelly = new ArrayList<BookItem> (LINES * PAGES);
    static ArrayList<BookItem> joyce = new ArrayList<BookItem> (LINES * PAGES);
    DatabaseItem currentItem = null;
    static String space =" ";
     

	static Timer timer;

	public static void main(String[] args) throws Exception {

		assert(args.length == 5);
                loadBooks();

		ArrayList<PostItem> postList = new ArrayList<PostItem>();
		//testPrint();

		assert(args[0].equals("push")||args[0].equals("pull"));
		String mode = args[0];

		int pollingInterval = Integer.parseInt(args[1]);

		String username = args[2];
                int i =0;

		// get server address
		String serverName = args[3];
		InetAddress serverIPAddress = InetAddress.getByName(serverName);

		// get server port
		int serverPort = Integer.parseInt(args[4]);

		// create socket which connects to server
		Socket clientSocket = new Socket(serverIPAddress, serverPort);

		while(i ==0) {
                DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		outToServer.writeBytes("register" + space + username + space + mode + space + '\n');

		// get input from keyboard
		String command;
                System.out.println("Waiting for text...");
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		command = inFromUser.readLine();

		// write to server
	//	DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		outToServer.writeBytes(command + '\n');

		// create read stream and receive from server
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		String sentenceFromServer;
		sentenceFromServer = inFromServer.readLine();

		// print output
		System.out.println("From Server: " + sentenceFromServer);
            }
		// close client socket
		clientSocket.close();

	} // end of main

	private static void loadBooks() throws IOException {
    	setUpList (exupery, "exupery", "exupery_page1", "exupery_page2", "exupery_page3", "exupery_page4");
    	setUpList (shelly, "shelly", "shelly_page1", "shelly_page2", "shelly_page3", "shelly_page4");
    	setUpList (joyce, "joyce", "Joyce_page1", "Joyce_page2", "Joyce_page3", "Joyce_page4");
    }

    private static void setUpList (ArrayList<BookItem> bookList, String author, String page1, String page2, String page3, String page4) throws IOException {

    ArrayList<BookItem> pageList = extractPage(page1, author, 1);
	appendPage ( bookList, pageList);
	pageList = extractPage(page2, author, 2);
	appendPage ( bookList, pageList);
	pageList = extractPage(page3, author, 3);
	appendPage ( bookList, pageList);
	pageList = extractPage(page4, author, 4);
	appendPage ( bookList, pageList);
    }

	private static void appendPage(ArrayList<BookItem> bookList, ArrayList<BookItem> pageList) {
		for(int i=0; i<9; i++) {
			bookList.add(pageList.get(i));
		}
	}

    private static ArrayList<BookItem> extractPage( String filename, String authName, int page ) throws IOException {
    	ArrayList<BookItem> list = new ArrayList<BookItem> (LINES);
    	BufferedReader reader = new BufferedReader(new FileReader(filename));
        String nextLine = "";
        int lineNumber =0;
        while ((nextLine = reader.readLine()) != null && lineNumber < 9) {
        	BookItem newItem = new BookItem(authName, page, lineNumber, nextLine);
        	list.add(newItem);
        	lineNumber++;
        }
        return list;
     }

    private void error() {
		System.out.println("Incorrect usage");
		System.out.println("args: mode polling_interval user_name server_name server_port_number");
		return;
	}

    private void testPrint() {
    	System.out.println("Exupery Page 1:");
    	for (int i =0; i<9; i++ ) {
    		System.out.println(exupery.get(i).data());
    	}
    	System.out.println("Joyce Page 3:");
    	for (int i =PAGE3; i<PAGE4; i++ ) {
    		System.out.println(joyce.get(i).data());
     	}
    }

    private static void systemInitialise(String[] args) throws IOException {

	
		

    }

} // end of class TCPClient




    