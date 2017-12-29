package com.mark.Crawer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class RenRen  {
	private static final String indexURL = "http://www.renren.com/";
	private static final String loginURL = "http://www.renren.com/PLogin.do";
	private static final String redirectURL = "http://blog.renren.com/blog/304317577/449470467";

	private DefaultHttpClient httpClient = new DefaultHttpClient();
	private HttpResponse response;
	public static void main(String[] args) {
		RenRen renren = new RenRen();
		renren.printText();
	}

	private void printText() {
		if(login()){
			String redirectLocation = getRedirectLocation();
			System.out.println("redirectLocation:   "+redirectLocation);
			if(redirectLocation != null){
				System.out.println(getText(redirectLocation));
			}
		}

	}

	private String getText(String redirectLocation) {
		HttpGet httpGet = new HttpGet(redirectLocation);
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		String responseBody = "";
		try{
			responseBody = httpClient.execute(httpGet,responseHandler);
		}catch(Exception e){
			e.printStackTrace();
			responseBody = null;
		}finally{
			httpGet.abort();
			httpClient.getConnectionManager().shutdown();
		}
		return responseBody;
	}

	private String getRedirectLocation() {
		Header locationHeader = response.getFirstHeader("Location");
		if(locationHeader == null){
			return null;
		}
		return locationHeader.getValue();
	}

	private boolean login() {
		HttpPost httppost = new HttpPost(loginURL);
		Map<String, String> hiddenMap = getHiddenMap();
		hiddenMap.put("origURL", redirectURL);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		for(String key:hiddenMap.keySet()){
			nvps.add(new BasicNameValuePair(key, hiddenMap.get(key)));
		}
		nvps.add(new BasicNameValuePair("password","7561669dong"));
		nvps.add(new BasicNameValuePair("autoLogin","true"));
		nvps.add(new BasicNameValuePair("email","13160675997"));
		try{
			httppost.setEntity(new UrlEncodedFormEntity(nvps,HTTP.UTF_8));
			response = httpClient.execute(httppost);
			
			HttpEntity entity = response.getEntity();
			if(entity != null){
				String body = EntityUtils.toString(entity,"utf-8");
				System.out.println(body);
			}
		}catch(Exception exception){
			exception.printStackTrace();
			return false;
		}finally{
			httppost.abort();
		}
		return true;
	}

	private Map<String, String> getHiddenMap() {
		Map<String,String> hiddenMap = new HashMap<String, String>();
		String body = null;

		try{
			HttpGet httpGet = new HttpGet(indexURL);
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();

			if(entity != null){
				body = EntityUtils.toString(entity,"utf-8");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		if(body != null){
			Document document = Jsoup.parse(body);
			Element form = document.select("form[class=login-form]").get(0);
			Elements inputs = form.select("input[type=hidden]");
			for(Element input:inputs){
				String name = input.attr("name");
				String value = input.attr("value");
				hiddenMap.put(name, value);
			}
		}
		return hiddenMap;
	}
}
