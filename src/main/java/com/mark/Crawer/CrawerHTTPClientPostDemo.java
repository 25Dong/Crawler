package com.mark.Crawer;

import java.util.HashMap;
import java.util.Map;

import com.mark.common.StringUtil;
import com.mark.common.UserAgentUtil;
import com.mark.robotics.Crawer;

public class CrawerHTTPClientPostDemo extends Crawer {

	public static void main(String[] args) throws Exception {
		CrawerHTTPClientPostDemo Crawer = new CrawerHTTPClientPostDemo();
		Crawer.executeTask();
	}
	
	@Override
	public void executeTask() throws Exception {
		String response  = getResponseByPost();
		System.out.println(response);
	}

	private String getResponseByPost() throws Exception {
		String response = StringUtil.SPACE_STRING;
		final String target_url = "http://www.hmm21.com/ebiz/schedule/getSailingSchedules.jsp";
		final String referer_url = "http://www.hmm21.com/ebiz/schedule/tradeLaneForm.jsp";
		
		Map<String,String> headerMap = new HashMap<String, String>();
		headerMap.put("User-Agent", UserAgentUtil.getOneUserAgent());
		headerMap.put("Referer", referer_url);
		
		Map<String, String> paramMap =new HashMap<String, String>();
		paramMap.put("date", "20171201");
		paramMap.put("duration", "2");
		paramMap.put("destinationCode", "SGSIN");
		paramMap.put("fromTag", "SSK");
		paramMap.put("nation", "");
		paramMap.put("originCode", "HKHKG");
		paramMap.put("sort", "R");
		paramMap.put("userId", "");
		
		setHttpClientHeaderMap(headerMap);
		getHttpClientProxy().setEnableCookie(true);
		response = getHTTPResponse(target_url, paramMap, 4);
		System.out.println(getHttpClientProxy().getCookieStore());
		return response;
	}
}
