package com.mark.Crawer;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import com.mark.robotics.HTTPClient.HTTPClientGet;

public class HttpClientGet {

	public static void main(String[] args) {
		HTTPClientGet get = new HTTPClientGet();
		try {
//			System.out.println(get.getContentByHTTPClientGet("http://www.goldstarline.com/",1));
			System.out.println("---------------------------------");
			System.out.println(get.getContentByHTTPClientGet("http://www.goldstarline.com/p2p.aspx?hidSearch=true&hidFromHomePage=false&hidSearchType=4&id=166&l=4&txtPOL=HKHKG&txtPOD=JPTYO&rb=Dep&txtDateFrom=24/11/2017&txtDateTo=30/11/2017",2));
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
