package com.mark.robotics;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.CookieStore;

import com.mark.robotics.http.BasicHTTPProxy;
import com.mark.robotics.http.HTTPClientProxy;

//protected BasicHTTPProxy httpProxy ;
public abstract class Crawer {
//	protected BasicHTTPProxy httpProxy = new BasicHTTPProxy();
	protected HTTPClientProxy httpClientProxy = new HTTPClientProxy();
	//HttpClient Post 请求的一些参数
	protected boolean isRediect =true;
	protected  Map<String, String> httpClientHeaderMap = new HashMap<String, String>();
	CookieStore cookieStore = null ;
	
	
	
	

	public abstract void executeTask() throws Exception;

	public String getHTTPResponse(String url,Map<String, String> paramMap,int method) throws Exception{
		String response = null;

		switch(method){
		//java.net：Get
		case 1:response = getHttpClientProxy().getContentByHTTPGet(url,paramMap);
		break;
		//java.net：Post
		case 2:response = getHttpClientProxy().getContentByHTTPPost(url,paramMap);
		break;
		//HTTPClient：Get
		case 3:response = getHttpClientProxy().getContentByHTTPClientGet(url, paramMap);
		break;
		//HTTPClient:Post
		case 4:response = getHttpClientProxy().getContentByHTTPClientPost(url, paramMap, cookieStore, httpClientHeaderMap, isRediect);
		break;
		//HTTPClient:DownLoading picture
		case 5:getHttpClientProxy().downloadingImgByHttpClient(url, null);
		break;
		
		}
		return response;
	}


	
	//
	public HTTPClientProxy getHttpClientProxy() {
		return httpClientProxy;
	}

	public void setHttpClientProxy(HTTPClientProxy httpClientProxy) {
		this.httpClientProxy = httpClientProxy;
	}
	
	public boolean isRediect() {
		return isRediect;
	}

	public void setRediect(boolean isRediect) {
		this.isRediect = isRediect;
	}

	public Map<String, String> getHttpClientHeaderMap() {
		return httpClientHeaderMap;
	}

	public void setHttpClientHeaderMap(Map<String, String> httpClientHeaderMap) {
		this.httpClientHeaderMap = httpClientHeaderMap;
	}

	public CookieStore getCookieStore() {
		return cookieStore;
	}

	public void setCookieStore(CookieStore cookieStore) {
		this.cookieStore = cookieStore;
	}
	
	public HTTPClientProxy getHttpProxy(){
		return httpClientProxy;
	}
}

