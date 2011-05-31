
public class lsr {


    Interpreter interpreter = new Interpreter();
    public static void main(String[] args) {
            Router router = new Router(args);
            if(router != null) {
                RouterWorker newRouterWorker = new RouterWorker(router);
            }

    }
}
