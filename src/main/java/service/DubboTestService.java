package service;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import model.Config;
import model.Parameters;
import tools.StringUtils;

import java.util.List;
import java.util.Map;

public class DubboTestService {

	/**
	 * 泛化调用获取响应信息 1.0版本
	 * @param config
	 * @param requestParams
	 * @return
	 */
	public Object getResponse(Config config, String requestParams){

		GenericService genericService = this.getGenericSerivce(config);
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

	/**
	 * 泛化调用获取响应信息 2.0版本
	 * @param config
	 * @param requestParams
	 * @return
	 */
	public Object getResponse(Config config, List<Parameters> requestParams){
		GenericService genericService = this.getGenericSerivce(config);
		if(requestParams.size() == 1){
			return genericService.$invoke(config.getMethodName(), null, null);
		}
		String[] parameterTypes = new String[requestParams.size() - 1];
		Object[] objectParams = new Object[requestParams.size() - 1];
		for(int i = 0; i < requestParams.size() - 1; i++){
			parameterTypes[i] = requestParams.get(i).getParamType();
			String paramValue = requestParams.get(i).getParamValue();
			if(paramValue.indexOf("{") > -1){
				objectParams[i] = JSON.parseObject(paramValue);
			} else if(paramValue.indexOf("[") > -1) {
				objectParams[i] = JSON.parseArray(paramValue);
			} else {
				objectParams[i] = paramValue;
			}
		}
		return genericService.$invoke(config.getMethodName(), parameterTypes, objectParams);
	}


	/**
	 * 获取泛化调用服务
	 * @description
	 * @param config
	 * @return com.alibaba.dubbo.rpc.service.GenericService
	 */
	public GenericService getGenericSerivce(Config config){
		RegistryConfig registry = new RegistryConfig();
		registry.setAddress(config.getZooKeeper());

		ApplicationConfig application = new ApplicationConfig();
		application.setRegistry(registry);
		application.setName("application");

		ReferenceConfig<GenericService> reference = new ReferenceConfig<>();
		reference.setApplication(application);
		reference.setInterface(config.getInterfaze());
		reference.setTimeout(1000);
		reference.setProtocol("dubbo");
		reference.setGeneric(true);
		reference.setValidation("false");
		if(StringUtils.isNotEmpty(config.getVersion())){
			reference.setVersion(config.getVersion());
		}
		if(StringUtils.isNotEmpty(config.getGroup())){
			reference.setGroup(config.getGroup());
		}
		GenericService genericService = reference.get();
		return genericService;
	}
}
