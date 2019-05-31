package tools;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class NetTools {
	
	/**
	 * 网络是否通畅
	 * @param ip
	 * @return
	 */
	public static boolean isPing(String ip){
		boolean status = false;
		if (ip != null) {
			try {
				status = InetAddress.getByName(ip).isReachable(3000);
			} catch (UnknownHostException e) {
				
			} catch (IOException e) {
				
			}
		}
		return status;
	}
}
