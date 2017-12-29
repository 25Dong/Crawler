package com.mark.Crawer;

import com.mark.robotics.Crawer;

public class CrawerYMLU extends Crawer{

	public static void main(String[] args) throws Exception {
		for(int i = 0;i<10;i++){
			CrawerYMLU test = new CrawerYMLU();
			test.executeTask();
		}
		
	}

	@Override
	public void executeTask() throws Exception {
		String target_url = "http://o-www.yangming.com/e-service/schedule/PointToPoint.aspx";
		getHttpProxy().setEnableCookie(true);
		getHTTPResponse(target_url, null, 1);
		System.out.println(getHttpProxy().getCookies());
	}

}
