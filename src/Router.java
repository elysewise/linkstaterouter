import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;


public class Router {
	static String ROUTERMODE;
	static String ROUTERIDENTITY;
	static int PORT;
	FileManager fileManager = new FileManager();
	Interpreter interpreter = new Interpreter();
        LinkedList<String[]> neighbours = new LinkedList<String[]>();
	
	
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
			System.out.println("Initialisation Error");
			exit(-1);
		}
	}
	
	private void setConfiguration(LinkedList<String> configDetails) throws Exception {

            LinkedList<String> firstLine = interpreter.StringToLinkedList(configDetails.get(0));
            //check that config file matches router identity
            if(!firstLine.get(0).equals(ROUTERIDENTITY)) {
			throw new Exception();	
		}
            PORT = Integer.parseInt(firstLine.get(1));
                //set up neighbours
           
            for(int i=3; i< configDetails.size(); i++) {
                 String[] nbrEntry = new String[3];
                LinkedList<String> nbrDetails = interpreter.StringToLinkedList(configDetails.get(i));
                nbrEntry[0] = nbrDetails.get(0);
                nbrEntry[1] = nbrDetails.get(1);
                nbrEntry[2] = nbrDetails.get(2);
                neighbours.add(nbrEntry);
            }
            if(neighbours.size() != Integer.parseInt(configDetails.get(2))) {
                throw new Exception();
            }
	}

	private LinkedList<String> readConfigurationFile() {
		LinkedList<String> configDetails = null;
		try {
		configDetails = fileManager.readFile("config_"+ROUTERIDENTITY+".txt");
                //System.out.println("config file found: "+configDetails);
		
		}
		catch (Exception e) {
			System.out.println(e);
			exit(-1);
		}
		return configDetails;
	}

        private void successful() {
            System.out.println("SUCCESSULY ADDED ROUTER:");
            System.out.println("--> Identity: "+ROUTERIDENTITY);
            System.out.println("--> Mode:     "+ROUTERMODE);
            System.out.println("--> Port:     "+PORT);

            System.out.println("Neighbours: " + neighbours.size());
                for(int i =0; i< neighbours.size(); i++) {

                    String[] nbr = neighbours.get(i);
                    System.out.println("--> "+Arrays.toString(nbr));
                }
            }
        
	
	
	private void exit(int status) {
		System.out.println("Ending execution of program...");
		System.exit(status);
	}
}
