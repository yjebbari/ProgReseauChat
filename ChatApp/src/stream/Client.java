package stream;

public class Client {
	private String name;
	private String ipAddress;
	private String status;
	private EchoClient echoClient;
	
	public Client(String name, String ipAddress, String status, EchoClient echoClient) {
		super();
		this.name = name;
		this.ipAddress = ipAddress;
		this.status = status;
		this.echoClient = echoClient;
	}

	public Client() {
		super();
	}

	// Getters and setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public EchoClient getEchoClient() {
		return echoClient;
	}

	public void setEchoClient(EchoClient echoClient) {
		this.echoClient = echoClient;
	}
	
	
	
}
