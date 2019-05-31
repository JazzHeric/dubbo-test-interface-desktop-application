package service;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.alibaba.fastjson.JSON;
import model.Config;
import tools.StringUtils;

public class DubboTestService {

	/**
	 * 泛化调用获取响应信息
	 * @param config
	 * @param requestParams
	 * @return
	 */
	public Object getResponse(Config config, String requestParams){
		RegistryConfig registry = new RegistryConfig();
		registry.setAddress(config.getZooKeeper());
		
		ApplicationConfig application = new ApplicationConfig();
		application.setRegistry(registry);
		application.setName(config.getApplication());

		ReferenceConfig<GenericService> reference = new ReferenceConfig<GenericService>();
		reference.setApplication(application);
		reference.setInterface(config.getInterfaze());
		reference.setTimeout(1000);
		reference.setProtocol("dubbo");
		reference.setGeneric(true);
		if(StringUtils.isNotEmpty(config.getVersion())){
			reference.setVersion(config.getVersion());
		}
		if(StringUtils.isNotEmpty(config.getGroup())){
			reference.setGroup(config.getGroup());
		}
		GenericService genericService = reference.get();
		//TODO 基本数据类型 和 多参数调用
		String[] parameterTypes = new String[]{config.getRequestClass()};
		Map<String, Object> params = new HashMap<>();
		params = JSON.parseObject(requestParams);
		params.put("class", config.getRequestClass());
		Object response = null;
		response = genericService.$invoke(config.getMethodName(), parameterTypes, new Object[]{params});
		return response;
	}
}
