package com.mark.Crawer;

import java.util.HashMap;
import java.util.Map;

import com.mark.common.StringUtil;
import com.mark.common.UserAgentUtil;
import com.mark.robotics.Crawer;

public class CrawerPostDemo extends Crawer{

	@SuppressWarnings("rawtypes")
	public static void main(String[] args) throws Exception {
		Class crawerclass = null;
		crawerclass = Class.forName("com.mark.Crawer."+"CrawerPostDemo");
		Crawer crawer = (Crawer) crawerclass.newInstance();
		crawer.executeTask();
	}

	@Override
	public void executeTask() throws Exception {
		String response = getResponseByPostByParam();
		System.out.println(response);
	}

	private String getResponseByPostByParam() throws Exception {
		final String target_url = "http://www.hmm21.com/ebiz/schedule/getSailingSchedules.jsp";
		final String referer_url = "http://www.hmm21.com/ebiz/schedule/tradeLaneForm.jsp";
		
		String response = StringUtil.SPACE_STRING;
		
		getHttpProxy().setEnableCookie(true);
		Map<String, String> headMap = new HashMap<String, String>();
		headMap.put("User-Agent", UserAgentUtil.getOneUserAgent());
		getHttpProxy().setRequestProperties(headMap);
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("date", "20171201");
		paramMap.put("duration", "2");
		paramMap.put("destinationCode", "SGSIN");
		paramMap.put("fromTag", "SSK");
		paramMap.put("nation", "");
		paramMap.put("originCode", "HKHKG");
		paramMap.put("sort", "R");
		paramMap.put("userId", "");

		boolean enableReferer = true;
		if (enableReferer) {
			getHttpProxy().addRequestProperties("Referer", referer_url);
		}
		
		response = getHTTPResponse(target_url, paramMap, 2);
		
		return response;
	}

}
