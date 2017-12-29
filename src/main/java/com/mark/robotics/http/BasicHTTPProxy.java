package com.mark.robotics.http;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.CookieStore;

import com.mark.common.StringUtil;
import com.mark.robotics.HTTPClient.HTTPClientGet;

public abstract class BasicHTTPProxy implements HTTPProxy {

	public static final String HTTP_RESPONSE_SERVER_ERROR = "Server returned HTTP response code: 50";
	protected String proxyhost = "10.224.1.109";//代理主机
	protected int proxyport = 24444;//代理端口
	protected boolean enableCookie = false;
	protected boolean enableRedirect = true;
	protected Map<String, String> cookies = new HashMap<String, String>();
	protected Map<String, String> requestProperties = new HashMap<String, String>();
	protected Integer httpTimeOut;
	protected HTTPGetUtil httpGetUtil;

	//HTTP Get
	public String getContentByHTTPGet(String url, Map<String, String> params)
			throws Exception {
		if(StringUtil.isNullOrEmpty(url)){
			return null;
		}
		try{
			setHttpGetUtil(new HTTPGetUtil());
			HttpPostAndGet httpPost = new HttpPostAndGet("10.224.1.109", 24444);
			prepareCookie(httpPost);

			String httpGetURL = constructHTTPGetURL(url, params);//构造URL
			String response = httpPost.getHttpResponseWithURLDirectlyAndURIParameter(httpGetURL,"", 1);

			this.storeCookie(httpPost);
			return response;
		}catch(Exception e){
			System.out.println("get fail");
			e.printStackTrace();
		}
		return null;
	}
	//HTTP GET：根据请求参数构造Request URL
	protected String constructHTTPGetURL(String url, Map<String, String> params) {
		if(null != httpGetUtil){
			return httpGetUtil.constructURLForHTTPGet(url, params);
		}
		return url;
	}

	//HTTP Post
	public String getContentByHTTPPost(String url, Map<String, String> params)
			throws Exception {
		if(StringUtil.isNullOrEmpty(url)){
			return null;
		}
		try{
			setHttpGetUtil(new HTTPGetUtil());
			HttpPostAndGet httpPost = new HttpPostAndGet(proxyhost, proxyport);
			//配置从cookie信息
			prepareCookie(httpPost);

			String httpPostParams = constructHTTPPostURL(params);//构造请求体的内内容
			String response = httpPost.getHttpResponseWithURLDirectlyAndURIParameter(url, httpPostParams, 0);

			//保存从cookie
			this.storeCookie(httpPost);
			return response;

		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch (Exception e) {
			System.out.println("psot fail");
			e.printStackTrace();
		}
		//***111
		return url;
	}
	//HTTP Post:根据请求体中的参数构造一个请求体
	private String constructHTTPPostURL(Map<String, String> params) {
		if(httpGetUtil != null){
			return httpGetUtil.constructURLForHTTPPost(params);
		}else{
			return null;
		}
	}

	//*************************未来*********
	public String getJPGByHTTPClientGet(String url) {
		String result = null;
		HTTPClientGet httpClient = new HTTPClientGet();
		result = httpClient.getJPGByHTTPClientGet(url);
		return result;
	}
	//get and set
	public HTTPGetUtil getHttpGetUtil() {
		return httpGetUtil;
	}

	public void setHttpGetUtil(HTTPGetUtil httpGetUtil) {
		this.httpGetUtil = httpGetUtil;
	}

	private void storeCookie(HttpPostAndGet httpPost) {
		this.setCookies(httpPost.getCookies());
	}

	private void setCookies(Map<String, String> cookies) {
		if(cookies != null && cookies.size()>0){
			if(this.cookies != null){
				for(String key:cookies.keySet()){
					this.cookies.put(key, cookies.get(key));
				}
			}else{
				this.cookies = cookies;
			}
		}
	}

	private void prepareCookie(HttpPostAndGet httpPost) {
		httpPost.setEnableCookie(enableCookie);
		httpPost.setCookies(getCookies());
		httpPost.setEnableRedirect(enableRedirect);
		httpPost.setRequestProperties(getRequestProperties());
		if (httpTimeOut != null) {
			httpPost.setHTTPTimeOut(httpTimeOut);
		}
	}

	public Map<String, String> getCookies() {
		return cookies;
	}

	public Map<String, String> getRequestProperties() {
		return requestProperties;
	}

	public void setRequestProperties(Map<String, String> requestProperties) {
		this.requestProperties = requestProperties;
	}

	public void setEnableCookie(boolean enableCookie) {
		this.enableCookie = enableCookie;
	}

	protected boolean isEnableCookie() {
		return enableCookie;
	}

	public void addRequestProperties(String key, String value) {
		this.requestProperties.put(key, value);
	}
	

}

