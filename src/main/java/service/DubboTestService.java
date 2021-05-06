package service;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import model.Config;
import model.Parameters;
import tools.StringUtils;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
			String originParamType = requestParams.get(i).getParamType();
			parameterTypes[i] = getParameterType(originParamType);
			String paramValue = requestParams.get(i).getParamValue();
			if(paramValue.indexOf("{") > -1){
				JSONObject originParamValue = JSON.parseObject(paramValue);
				if(StringUtils.isNotEmpty(getGenericType(originParamType))) {
					String genericTypeKey = getGenericTypeKey(originParamValue);
					JSONObject genericTypeValue = originParamValue.getJSONObject(genericTypeKey);
					genericTypeValue.put("class", getGenericType(originParamType));
				}
				objectParams[i] = originParamValue;
			} else if(paramValue.indexOf("[") > -1) {
				objectParams[i] = JSON.parseArray(paramValue);
			} else {
				objectParams[i] = paramValue;
			}
		}
		return genericService.$invoke(config.getMethodName(), parameterTypes, objectParams);
	}

	/**
	 * 实际的类型
	 */
	private String getParameterType(String paramType) {
		if(paramType.indexOf("<") > -1) {
			return paramType.substring(0, paramType.indexOf("<"));
		}
		return paramType;
	}


	private String getGenericTypeKey(JSONObject mapData) {
		Set<Map.Entry<String, Object>> entries = mapData.entrySet();
		Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
		while (iterator.hasNext()){
			Map.Entry<String, Object> next = iterator.next();
			if(next.getValue() instanceof JSONObject) {
				return next.getKey();
			}
		}
		return null;
	}

	/**
	 * 可能存在的泛型类
	 */
	private String getGenericType(String paramType) {
		if(paramType.indexOf("<") > -1) {
			return paramType.substring(paramType.indexOf("<") + 1, paramType.indexOf(">"));
		}
		return null;
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
		reference.setTimeout(10000);
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


	public static void main(String[] args) {
		/*String test = "{\"pageNum\":1,\"pageSize\":20,\"queryParameter\":{\"ownerUserId\":\"12\",\"authUserId\":\"89\",\"filterBeginTime\":\"2020-10-10\",\"filterEndTime\":\"2020-12-30\",\"storeIdList\":[\"202011081000\",\"202011081001\"]}}";
		JSONObject jsonObject = JSON.parseObject(test);
		System.out.println(getGenericTypeKey(jsonObject));*/
	}
}
