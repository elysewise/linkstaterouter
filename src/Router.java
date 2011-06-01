import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.LinkedList;


public class Router {
	private String ROUTERMODE;
	private String ROUTERIDENTITY;
	private int PORT;
	private FileManager fileManager = new FileManager();
	private Interpreter interpreter = new Interpreter();
    private LinkedList<Neighbour> neighbours = new LinkedList<Neighbour>();
	InetAddress local;
	/**
	 * construct a router given two args: mode , identifier
	 * @param args the command line arguments used to establish router
	 */
	public Router(String[] args){
		
		// extract info from args
		try {
			local = InetAddress.getLocalHost();
			ROUTERMODE = args[0].toLowerCase();
			if(!ROUTERMODE.equals("init") && !ROUTERMODE.equals("add")) {
				throw new Exception();
			}
			ROUTERIDENTITY = args[1].toLowerCase();
			if(ROUTERIDENTITY.length() != 1 || !Character.isLetter(ROUTERIDENTITY.charAt(0))) {
				throw new Exception();
			}
			LinkedList<String> configDetails = readConfigurationFile();
			setSockets();
			setConfiguration(configDetails);
			
			//start running of router
                        successful();
		}
		catch (Exception e) {
			System.out.print("Initialisation Error: ");
			e.printStackTrace(System.out);
			exit(-1);
		}
	}
	
	/**
	 * Set 
	 * the configuration details of router from config file information
	 * Set neighbour router objects
	 * @param configDetails
	 * @throws Exception
	 */
	private void setConfiguration(LinkedList<String> configDetails) throws Exception {

            LinkedList<String> firstLine = interpreter.stringToLinkedList(configDetails.get(0));
            //check that config file matches router identity
            if(!firstLine.get(0).equals(ROUTERIDENTITY)) {
            	System.out.println("problem is: no identity match");
            	throw new Exception();	
		    }
            PORT = Integer.parseInt(firstLine.get(1));
                //set up neighbours
           
            for(int i=3; i< configDetails.size(); i++) {
                LinkedList<String> nbrDetails = interpreter.stringToLinkedList(configDetails.get(i));
                String id = nbrDetails.get(0);
                int port = Integer.parseInt(nbrDetails.get(1));
                int cost = Integer.parseInt(nbrDetails.get(2));
                neighbours.add(new Neighbour(id,port,cost));
            }
            if(neighbours.size() != Integer.parseInt(configDetails.get(2).substring(0,1))) {

            	System.out.println("problem is: neighbour numbers don't match");
            	throw new Exception();
            }
	}

	/**
	 * attempt to read config file into a linked list of strings.
	 * handles exceptions for file not found etc.
	 * @return LinkedList<String> configuration file contents
	 */
	private LinkedList<String> readConfigurationFile() {
		LinkedList<String> configDetails = null;
		try {
		configDetails = fileManager.readFile("config_"+ROUTERIDENTITY+".txt");
		}
		catch (Exception e) {
			System.out.println(e);
			exit(-1);
		}
		return configDetails;
	}

	
        /**
         * Print router information to inform user of successful initialisation of router
         */
        private void successful() {
            System.out.println("SUCCESSFULLY INITIALISED ROUTER:");
            System.out.println("--> Identity: "+ROUTERIDENTITY);
            System.out.println("--> Mode:     "+ROUTERMODE);
            System.out.println("--> Port:     "+PORT);

            System.out.println("Neighbours: " + neighbours.size());
                for(int i =0; i< neighbours.size(); i++) {
                    String nbr = neighbours.get(i).getID();
                    System.out.println("--> "+nbr);
                }
            }
        
	public String getID() {
		return this.ROUTERIDENTITY;
	}
	
	public int getPort() {
		return this.PORT;
	}
	
	public String getMode() {
		return this.ROUTERMODE;
	}

        public LinkedList<Neighbour> getNeighbours() {
            return neighbours;
        }
        
        public void setSockets() throws Exception {	
        	RouterRecords.UDPsocket =  new DatagramSocket(this.getPort());
	    	RouterRecords.TCPSocket = new ServerSocket(this.getPort()) ;
	    	System.out.println("udp and tcp listening sockets created successfully!");
	    }
        
        public InetAddress getLocal() {
        	return local;
        }
        
	/**
	 * Inform user of program termination, then exit program.
	 * @param status the status for program to exit with
	 */
	private void exit(int status) {
		System.out.println("Ending execution of program...");
		System.exit(status);
	}
}