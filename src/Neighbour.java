public class Neighbour {

	private final String identifier;
	private final int port;
	private final int cost;

	/**
	 * @param id the alpha-character that identifies this neighbour router
	 * @param port	the port for this neighbour router
	 * @param cost the cost between the source router and this neighbour
	 */
	public Neighbour(String id, int port, int cost) {
		this.identifier = id;
		this.port = port;
		this.cost = cost;
	}

	public String getID() {
		return this.identifier;
	}

	public int getPort() {
		return this.port;
	}

	public int getCost() {
		return this.cost;
	}
}
