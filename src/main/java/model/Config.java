package model;

public class Config {
	
	private String zooKeeper;
	
	private String application;
	
	private String interfaze;
	
	private String methodName;
	
	private String requestClass;
	
	private String version;
	
	private String group;

	public String getZooKeeper() {
		return zooKeeper;
	}

	public Config setZooKeeper(String zooKeeper) {
		this.zooKeeper = zooKeeper;
		return this;
	}

	public String getApplication() {
		return application;
	}

	public Config setApplication(String application) {
		this.application = application;
		return this;
	}

	public String getInterfaze() {
		return interfaze;
	}

	public Config setInterfaze(String interfaze) {
		this.interfaze = interfaze;
		return this;
	}

	public String getMethodName() {
		return methodName;
	}

	public Config setMethodName(String methodName) {
		this.methodName = methodName;
		return this;
	}

	public String getRequestClass() {
		return requestClass;
	}

	public Config setRequestClass(String requestClass) {
		this.requestClass = requestClass;
		return this;
	}

	public String getVersion() {
		return version;
	}

	public Config setVersion(String version) {
		this.version = version;
		return this;
	}

	public String getGroup() {
		return group;
	}

	public Config setGroup(String group) {
		this.group = group;
		return this;
	}
	
}
