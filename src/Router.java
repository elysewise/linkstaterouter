import java.util.Arrays;
import java.util.LinkedList;


public class Router {
	private String ROUTERMODE;
	private String ROUTERIDENTITY;
	private int PORT;
	private FileManager fileManager = new FileManager();
	private Interpreter interpreter = new Interpreter();
    private LinkedList<String[]> neighbours = new LinkedList<String[]>();
	
	
	public Router(String[] args){
		
		// extract info from args
		try {
			ROUTERMODE = args[0].toLowerCase();
			if(!ROUTERMODE.equals("init") && !ROUTERMODE.equals("add")) {
				throw new Exception();
			}
			ROUTERIDENTITY = args[1].toLowerCase();
			if(ROUTERIDENTITY.length() != 1 || !Character.isLetter(ROUTERIDENTITY.charAt(0))) {
				throw new Exception();
			}
			LinkedList<String> configDetails = readConfigurationFile();
			setConfiguration(configDetails);
			//start running of router
                        successful();
		}
		catch (Exception e) {
			System.out.print("Initialisation Error: ");
			System.out.println(e);
			exit(-1);
		}
	}
	
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
                 String[] nbrEntry = new String[3];
                LinkedList<String> nbrDetails = interpreter.stringToLinkedList(configDetails.get(i));
                nbrEntry[0] = nbrDetails.get(0);
                nbrEntry[1] = nbrDetails.get(1);
                nbrEntry[2] = nbrDetails.get(2);
                neighbours.add(nbrEntry);
             //   System.out.println("Added neighbour entry: "+ Arrays.toString(nbrEntry));
            }
//            System.out.println("there are "+neighbours.size()+" neighbours");
//        	System.out.println("neighbours are: ");
//        	for(int j =0; j< neighbours.size(); j++) {
//        		System.out.println(neighbours.get(j)[0]+" "+ neighbours.get(j)[1]+" "+ neighbours.get(j)[2]);
//        	}
        	
            if(neighbours.size() != Integer.parseInt(configDetails.get(2).substring(0,1))) {

            	System.out.println("problem is: neighbour numbers don't match");
            	throw new Exception();
            }
	}

	private LinkedList<String> readConfigurationFile() {
		LinkedList<String> configDetails = null;
		try {
		configDetails = fileManager.readFile("config_"+ROUTERIDENTITY+".txt");
               // System.out.println("config file found: "+configDetails);
		
		}
		catch (Exception e) {
			System.out.println(e);
			exit(-1);
		}
		return configDetails;
	}

        private void successful() {
            System.out.println("SUCCESSFULLY ADDED ROUTER:");
            System.out.println("--> Identity: "+ROUTERIDENTITY);
            System.out.println("--> Mode:     "+ROUTERMODE);
            System.out.println("--> Port:     "+PORT);

            System.out.println("Neighbours: " + neighbours.size());
                for(int i =0; i< neighbours.size(); i++) {

                    String[] nbr = neighbours.get(i);
                    System.out.println("--> "+Arrays.toString(nbr));
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

        public LinkedList<String[]> getNeighbours() {
            return neighbours;
        }
	
	private void exit(int status) {
		System.out.println("Ending execution of program...");
		System.exit(status);
	}
}
