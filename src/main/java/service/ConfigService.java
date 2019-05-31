package service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooKeeper;
import tools.NetTools;


public class ConfigService {

	/**
	 * 校验zookeeper连接状态
	 * @param zookeeper
	 * @return
	 */
	public boolean checkZooKeeper(String zookeeper){
		boolean status = false;
		List<String> address = this.getZkAddress(zookeeper);
		if(address == null || address.size() == 0) return status;
		for(String ips : address){
			String ip = ips.split(":")[0];
			status = status || NetTools.isPing(ip);
		}
		if(!status) return status;
		for(String ips : address){
			try {
				ZooKeeper zk = new ZooKeeper(ips, 2000 ,new Watcher() {
	                @Override
	                public void process(WatchedEvent event) {
	                    if (event.getType() == EventType.None)  return;
	                }
	            });
				status = true;
				zk.close();
				break;
	        } catch (IOException | InterruptedException e) {
	        	continue;
	        }
		}
		return status;
	}
	
	/**
	 * 获取IP地址以及端口号
	 * @param zookeeper
	 * @return
	 */
	public List<String> getZkAddress(String zookeeper){
		List<String> address = new ArrayList<>();
		String pattern = "((\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\:\\d{1,5})";
		Matcher matcher = Pattern.compile(pattern).matcher(zookeeper);
		while(matcher.find()){
			address.add(matcher.group());
		}
		return address;
	}
	
	public static void main(String[] args) {
		ConfigService configService = new ConfigService();
		String zookeeper = "zookeeper://10.12.252.2:2181?backup=172.21.245.204:2181,172.21.245.90:2181";
		System.out.println(configService.checkZooKeeper(zookeeper));
	}
}
