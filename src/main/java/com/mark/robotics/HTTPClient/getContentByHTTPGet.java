package com.mark.robotics.HTTPClient;

import org.apache.http.impl.client.DefaultHttpClient;

public class getContentByHTTPGet {
	private static String TARGET_URL ="https://www.baidu.com/";

	public static void main(String[] args) {
		String response = getContentByHTTPGet.getResponse();
		System.out.println(response);

	}

	private static String getResponse() {
		String reponse = null;
		DefaultHttpClient httpClient = new DefaultHttpClient();
		
		return reponse;
	}

}
