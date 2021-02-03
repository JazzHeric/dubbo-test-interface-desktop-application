package tools;

import model.Config;

import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.Properties;



public class PropertyTools {

	/**
	 * 获取config文件中内容
	 * @return
	 */
	public static Config getConfig() throws IOException {
		Config config = new Config();
		createUserDataFile();
		File configFile = new File(System.getProperty("user.dir") + "/userData/userConfig.properties");
		FileInputStream ins = new FileInputStream(configFile);
		Properties prop = new Properties();
		prop.load(ins);
		String zookeeper = prop.getProperty("zookeeper");
		String application = prop.getProperty("application");
		String interfaze = prop.getProperty("interface");
		String methodName = prop.getProperty("methodName");
		String requestClass = prop.getProperty("requestClass");
		String version = prop.getProperty("version");
		String group = prop.getProperty("group");
		config.setZooKeeper(zookeeper).setApplication(application).setInterfaze(interfaze).setMethodName(methodName)
				.setVersion(version).setRequestClass(requestClass).setGroup(group);
		ins.close();
		return config;
	}
	
	/**
	 * 写入config数据
	 * @param config
	 * @throws IOException
	 */
	public static void writeConfig(Config config) throws IOException{
		createUserDataFile();
		File configFile = new File(System.getProperty("user.dir") + "/userData/userConfig.properties");
		FileOutputStream fout = new FileOutputStream(configFile);
		Properties prop = new Properties();
		prop.setProperty("zookeeper", config.getZooKeeper());
		prop.setProperty("application", config.getApplication());
		prop.setProperty("interface", config.getInterfaze());
		prop.setProperty("methodName", config.getMethodName());
		prop.setProperty("requestClass", config.getRequestClass());
		prop.setProperty("version", config.getVersion());
		prop.setProperty("group", config.getGroup());
		prop.store(fout, "User configuration modify at " + new Date().toString());
		fout.close();
	}
	
	/**
	 * 用户请求参数获取
	 * @return
	 * @throws IOException
	 */
	public static String getUserParam() throws IOException{
		createUserDataFile();
		File paramFile = new File(System.getProperty("user.dir") + "/userData/userParam.properties");
		FileInputStream ins = new FileInputStream(paramFile);
		Properties prop = new Properties();
		prop.load(ins);
		String userParams = prop.getProperty("request");
		ins.close();
		return userParams;
	}
	
	/**
	 * 
	 * @param userParam
	 * @throws IOException
	 */
	public static void writeUserParam(String userParam) throws IOException{
		createUserDataFile();
		File paramFile = new File(System.getProperty("user.dir") + "/userData/userParam.properties");
		FileOutputStream fout = new FileOutputStream(paramFile);
		Properties prop = new Properties();
		prop.setProperty("request", userParam);
		prop.store(fout, "User request params modify at " + new Date().toString());
		fout.close();
	}
	
	private static void createUserDataFile() throws IOException{
		File filePath = new File(System.getProperty("user.dir") + "/userData");
		if(!filePath.exists()) filePath.mkdir();
		File configFile = new File(System.getProperty("user.dir") + "/userData/userConfig.properties");
		if(!configFile.exists()) configFile.createNewFile();
		File paramFile = new File(System.getProperty("user.dir") + "/userData/userParam.properties");
		if(!paramFile.exists()) paramFile.createNewFile();
	}


	private static void createFxFile() throws IOException{
		File filePath = new File(System.getProperty("user.dir") + "/userData");
		if(!filePath.exists()) filePath.mkdir();
		File paramFile = new File(System.getProperty("user.dir") + "/userData/userFxParam.properties");
		if(!paramFile.exists()) paramFile.createNewFile();
	}



	public static Config getJFConfig() throws IOException {
		createFxFile();
		Config config = new Config();
		File configFile = new File(System.getProperty("user.dir") + "/userData/userFxParam.properties");
		FileInputStream ins = new FileInputStream(configFile);
		Properties prop = new Properties();
		prop.load(ins);
		String zookeeper = prop.getProperty("zookeeper");
		String application = prop.getProperty("application");
		String interfaze = prop.getProperty("interface");
		String methodName = prop.getProperty("methodName");
		String version = prop.getProperty("version");
		String group = prop.getProperty("group");
		String requestParam = prop.getProperty("requestParam");
		config.setZooKeeper(zookeeper).setApplication(application).setInterfaze(interfaze).setMethodName(methodName)
				.setVersion(version).setGroup(group).setRequestParam(requestParam);
		ins.close();
		return config;
	}


	public static void saveJFConfig(Config config) throws IOException{
		createFxFile();
		File configFile = new File(System.getProperty("user.dir") + "/userData/userFxParam.properties");
		FileOutputStream fout = new FileOutputStream(configFile);
		Properties prop = new Properties();
		prop.setProperty("zookeeper", config.getZooKeeper() == null ? "" : config.getZooKeeper());
		prop.setProperty("interface", config.getInterfaze() == null ? "" : config.getInterfaze());
		prop.setProperty("methodName", config.getMethodName() == null ? "" : config.getMethodName());
		prop.setProperty("version", config.getVersion() == null ? "" : config.getVersion());
		prop.setProperty("group", config.getGroup() == null ? "" : config.getGroup());
		prop.setProperty("requestParam", config.getRequestParam());
		prop.store(fout, "User configuration modify at " + new Date().toString());
		fout.close();
	}
}
