package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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

		ReferenceConfig<GenericService> reference = new ReferenceConfig<>();
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
		String[] parameterTypes = config.getRequestClass().split(";");

		List<String> paramList = JSONArray.parseArray(requestParams,String.class);

		Object[] objectParam = new Object[paramList.size()];
		for(int i = 0; i < paramList.size(); i++){
			String paramValue = paramList.get(i);
			Map<String, Object> params = null;
			//是否对象
			if(paramValue.indexOf("{") > -1){
				params = JSON.parseObject(paramValue);
				objectParam[i] = params;
				//params.put("class", config.getRequestClass());
			} else if(paramValue.indexOf("[") > -1) {
				objectParam[i] = JSON.parseArray(paramValue);
			} else {
				objectParam[i] = paramValue;
			}

		}

		Object response = null;
		response = genericService.$invoke(config.getMethodName(), parameterTypes, objectParam);
		return response;
	}

}
