package com.mark.Crawer;

import java.util.HashMap;

import com.mark.common.UserAgentUtil;
import com.mark.robotics.Crawer;

public class HamburgsudDatedRouteScheduleCrawler extends Crawer{
	private final static String INDEX_URL = "http://ecom.hamburgsud.com/ecom/en/ecommerce_portal/schedules_2/point_to_point/ep_point_to_point.xhtml";


	public static void main(String[] args) throws Exception {
		HamburgsudDatedRouteScheduleCrawler test= new HamburgsudDatedRouteScheduleCrawler();

		test.executeTask();

	}

	private  String getRouteScheduleResponse() throws Exception {
		HashMap<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		headerMap.put("User-Agent", UserAgentUtil.getOneUserAgent());
		getHttpProxy().setRequestProperties(headerMap);
		
		String respon  = getHTTPResponse(INDEX_URL,null,2);
		
		return null;
	}

	@Override
	public void executeTask() throws Exception {
		String  response = getRouteScheduleResponse();
	}

}
