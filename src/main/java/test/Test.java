package test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Test {

	
	

	public static void main(String[] args) {
		System.out.println(Test.class.getClassLoader().getResource("").getPath());	
		System.out.println(Test.class.getClassLoader().getResourceAsStream("userData/config.properties"));
		InputStream ins = Thread.currentThread().getContextClassLoader().getResourceAsStream("userData/config.properties");
		
		Properties prop = new Properties();
		try {
			prop.load(ins);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String key = prop.getProperty("zookeeper");
		prop.setProperty("interface", "com.weimob.test");
		System.out.println(key);
		
	}
}
