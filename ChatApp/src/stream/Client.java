package stream;

public class Client {
	private String name;
	private String ipAddress;
	private String status;
	private ClientThreadSend threadSend;
	private ClientThreadReceive threadReceive;

	public Client(String name, String ipAddress, String status, ClientThreadSend threadSend, ClientThreadReceive threadReceive) {
		super();
		this.name = name;
		this.ipAddress = ipAddress;
		this.status = status;
		this.threadSend = threadSend;
		this.threadReceive = threadReceive;
	}
	
	public Client(ClientThreadSend threadSend, ClientThreadReceive threadReceive) {
		super();
		this.threadSend = threadSend;
		this.threadReceive = threadReceive;
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

	public ClientThreadSend getThreadSend() {
		return threadSend;
	}

	public void setThreadSend(ClientThreadSend threadSend) {
		this.threadSend = threadSend;
	}

	public ClientThreadReceive getThreadReceive() {
		return threadReceive;
	}

	public void setThreadReceive(ClientThreadReceive threadReceive) {
		this.threadReceive = threadReceive;
	}

	
	
	
	
}
