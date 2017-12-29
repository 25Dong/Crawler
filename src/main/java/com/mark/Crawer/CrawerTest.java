package com.mark.Crawer;

import java.util.HashMap;
import java.util.Map;

import com.mark.common.UserAgentUtil;
import com.mark.robotics.Crawer;
import com.mark.robotics.http.BasicHTTPProxy;

public class CrawerTest extends Crawer{

	private static final String TARGET_URL = "http://eservice.sinokor.co.kr/ASPNETService/COM/SC_VesselSchedule.aspx";


	public static void main(String[] args) throws Exception {
		CrawerTest test = new CrawerTest();
		test.executeTask();
	}

	@Override
	public void executeTask() throws Exception {

		//String response = getHomePageHTMl();
		String result = getHttpProxy().getJPGByHTTPClientGet("https://ss0.bdstatic.com/k4oZeXSm1A5BphGlnYG/newmusic/lovesong.png");
		System.out.println(result);

	}

	private String getHomePageHTMl() throws Exception {
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("User-Agent", UserAgentUtil.getOneUserAgent());
		paramMap.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		paramMap.put("Cache-Control","max-age=0");
		paramMap.put("Accept-Language", "zh-CN,zh;q=0.8");
		paramMap.put("Proxy-Connection", "keep-alive");
		paramMap.put("Upgrade-Insecure-Requests", "1");
		paramMap.put("Host", "eservice.sinokor.co.kr");
		paramMap.put("Accept-Encoding", "gzip, deflate");
		getHttpProxy().setRequestProperties(paramMap);
		getHttpProxy().setEnableCookie(true);
		String respon  = getHTTPResponse(TARGET_URL, paramMap,3);
		return respon;
		}

}
