package model;

public class Config {
	
	private String zooKeeper;
	
	private String application;
	
	private String interfaze;
	
	private String methodName;
	
	private String requestClass;
	
	private String version;
	
	private String group;

	private String requestParam;

	private Config(Builder builder) {
		this.zooKeeper = builder.zooKeeper;
		this.application = builder.application;
		this.interfaze = builder.interfaze;
		this.methodName = builder.methodName;
		this.requestClass = builder.requestClass;
		this.version = builder.version;
		this.group = builder.group;
		this.requestParam = builder.requestParam;
	}


	public static Builder builder() {
		return new Builder();
	}

	public static Builder newConfig() {
		return new Builder();
	}

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

	public String getRequestParam() {
		return requestParam;
	}

	public void setRequestParam(String requestParam) {
		this.requestParam = requestParam;
	}

	public static final class Builder {
		private String zooKeeper;
		private String application;
		private String interfaze;
		private String methodName;
		private String requestClass;
		private String version;
		private String group;
		private String requestParam;

		private Builder() {
		}

		public Config build() {
			return new Config(this);
		}

		public Builder zooKeeper(String zooKeeper) {
			this.zooKeeper = zooKeeper;
			return this;
		}

		public Builder application(String application) {
			this.application = application;
			return this;
		}

		public Builder interfaze(String interfaze) {
			this.interfaze = interfaze;
			return this;
		}

		public Builder methodName(String methodName) {
			this.methodName = methodName;
			return this;
		}

		public Builder requestClass(String requestClass) {
			this.requestClass = requestClass;
			return this;
		}

		public Builder version(String version) {
			this.version = version;
			return this;
		}

		public Builder group(String group) {
			this.group = group;
			return this;
		}

		public Builder requestParam(String requestParam) {
			this.requestParam = requestParam;
			return this;
		}
	}

	public Config() {
	}
}
