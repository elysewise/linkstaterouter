
public class lsr {


    Interpreter interpreter = new Interpreter();
    public static void main(String[] args) {
        try{
            Router router = new Router(args);
            RouterWorker routerWorker = new RouterWorker(router);
            if(router.getMode().equals("init")) {
                routerWorker.initModeSetup();
            }
            else {
               //routerWorker.addModeSetup();
            }
            routerWorker.run();
        }
    catch(Exception e) {
        System.out.println("initialisation of router failed:");
        System.out.println(e);
        System.exit(-1);
    }
}
                
}
