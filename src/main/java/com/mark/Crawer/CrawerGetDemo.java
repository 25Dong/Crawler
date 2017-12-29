package com.mark.Crawer;

import java.util.HashMap;
import java.util.Map;

import com.mark.common.StringUtil;
import com.mark.common.UserAgentUtil;
import com.mark.robotics.Crawer;
import com.mark.robotics.http.HTTPGetUtil;
/**
 *使用java.net 的get模式request
 * @author YIMA
 *
 */
public class CrawerGetDemo extends Crawer{

	@SuppressWarnings("rawtypes")
	public static void main(String[] args) throws Exception {
		
		Class crawerclass = null;
		crawerclass = Class.forName("com.mark.Crawer."+"CrawerGetDemo");
		Crawer crawer = (Crawer) crawerclass.newInstance();
		crawer.executeTask();
	}

	@Override
	public void executeTask() throws Exception {
		
		
		
		String response = getResponseByGetByParam();
		System.out.println(response);
		//System.out.println(httpProxy.getCookies());
//		String response2 = getResponseByGetNotParam();//不带参数
//		System.out.println(response2);
	}

	/**
	 * 不带参数的get方法
	 * @return
	 * @throws Exception
	 */
	private String getResponseByGetNotParam() throws Exception {
		String request_url = "http://www.baidu.com/";
		String response = StringUtil.EMPTY_STRING;
		response = getHTTPResponse(request_url, null,1);
		return response;
	}

	/**
	 * 带有参数的get方法
	 * @return
	 * @throws Exception
	 */
	private String getResponseByGetByParam() throws Exception {

		String request_url = "http://www.hmm21.com/ebiz/schedule/tradeLaneForm.jsp";
		// String
		String response = StringUtil.EMPTY_STRING;
		// 设置头部
		//getHttpProxy().setCookies(null);
		Map<String, String> headMap = new HashMap<String, String>();
		headMap.put("User-Agent", UserAgentUtil.getOneUserAgent());
		getHttpProxy().setRequestProperties(headMap);
		getHttpProxy().setEnableCookie(true);

		// 设置请求体(请求体的参数不一定全写)
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("listopt", "L");
		paramMap.put("date", "20170809");
		paramMap.put("isFrequentlyUsed1", "N");
		paramMap.put("isFrequentlyUsed2", "N");
		paramMap.put("chk", "N");
		paramMap.put("changeLocation", "Y");
		paramMap.put("first", "Y");
		paramMap.put("fromTag", "SSL");
		paramMap.put("location_type", "on");
		paramMap.put("sort", "R");

		paramMap.put("origin_nation", "HK");
		paramMap.put("destination_nation", "SG");

		response = getHTTPResponse(request_url, paramMap,1);
		Map<String,String> testMap = new HashMap<String, String>();
		testMap.put("Cookie", "dddd1");
		testMap.put("Cookie2", "dddd2");
		testMap.put("Cookie3", "dddd3");
		testMap.put("Cookie4", "dddd4");
		testMap.put("Cookie5", "dddd5");
		getHttpProxy().setRequestProperties(testMap);
		response = getHTTPResponse(request_url, paramMap,1);
		return response;
	}

}
