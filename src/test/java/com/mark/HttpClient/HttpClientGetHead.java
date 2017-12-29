package com.mark.HttpClient;

import java.io.IOException;



import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * 补充：HttpClient 获得response中的header信息
 * @author YIMA
 *
 */
public class HttpClientGetHead {

	public static void main(String[] args) {
		HttpClientGetHead test = new HttpClientGetHead();
		test.getHeaderFiles();
	}
	
	public void getHeaderFiles(){
		String url = "https://www.baidu.com"; 
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet get = new HttpGet(url);
		HttpResponse response = null;
		try {
			response = httpClient.execute(get);
			Header[] headers = response.getAllHeaders();
			System.out.println("the information of headers:"+headers);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
