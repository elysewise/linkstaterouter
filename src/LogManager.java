import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogManager {

	String logFile = null;
	String routerName = null;
	Logger logger;

	public LogManager(String routerName) {
		this.routerName = routerName;
		this.logFile = ("flooding_log_router_" + routerName + ".txt");
		try {
			logger = Logger.getLogger(routerName);
			FileHandler fh;
			fh = new FileHandler(logFile, true);
			logger.addHandler(fh);
			logger.setLevel(Level.ALL);
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void addToLog(String data) {
		try {
			logger.log(Level.INFO, data);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
