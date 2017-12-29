package com.mark.robotics.http;
/**
 *      getHttpProxy().setRequestProperties(headerMap);
		getHttpProxy().getCookies().clear();
		getHttpProxy().setEnableCookie(true);
 */
import java.util.Map;

import org.apache.http.client.CookieStore;

public interface HTTPProxy {

	public String getContentByHTTPGet(String url, Map<String, String> params) throws Exception; 
	
	public String getContentByHTTPPost(String url, Map<String, String> params) throws Exception; 

	public String getContentByHTTPClientGet(String url,Map<String, String> params)throws Exception;
	
	public String getContentByHTTPClientPost(String requestUrl,Map<String, String> params, CookieStore cookieStore,Map<String, String> headers,boolean isRediect);
	
	//下载图片
	public void downloadingImgByHttpClient(String picURL , String imPath);
}
