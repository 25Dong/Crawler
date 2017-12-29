package com.mark.Crawer;
/**
 * HttpClient GET方法请求(配置请求参数，设置request头部信息，保存cookie)
 */
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class httpClientGetTest {
	private static final String target_url = "https://www.baidu.com/";
	private final int connectTimeout = 9000;//连接超时
	private final int socketTimeout = 9000;//读取数据超时
	private CookieStore cookieStore ;
	
	public static void main(String[] args) {
		httpClientGetTest test = new httpClientGetTest();
		String response = test.getResponse2();
		System.out.println(response);
	}

	private String getResponse2() {
		String response = "";
		
		//设置配置信息
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectTimeout(connectTimeout)
				.setConnectionRequestTimeout(connectTimeout)
				.setSocketTimeout(socketTimeout).build();
		
		CloseableHttpClient httpClient = HttpClientBuilder.create()
				.setDefaultRequestConfig(requestConfig).build();//配置参数创建一个客户端
		HttpGet httpGet = new HttpGet(target_url);//创建一个get方法
		
		//设置头部信息
		httpGet.setHeader("User-Agent", "****");
		httpGet.setHeader("Accept-Charset", "");
		
		//设置请求cookie
		 ((AbstractHttpClient) httpClient).setCookieStore(cookieStore);
		try {
			
			HttpResponse httpresponse = httpClient.execute(httpGet);//获得网页的内容
			HttpEntity entity = httpresponse.getEntity();//查看返回的内容
			if(entity != null){
				response = EntityUtils.toString(entity,"utf-8");//读取内容流，并以字符串形式返回
				EntityUtils.consume(entity);//关闭内容流
			}
			
			//获取cookie信息
			cookieStore = ((AbstractHttpClient) httpClient).getCookieStore();
			 
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			httpClient.getConnectionManager().shutdown();//释放连接
		}
		return response;
	}

}
