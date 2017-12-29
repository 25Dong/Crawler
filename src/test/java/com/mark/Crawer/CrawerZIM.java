package com.mark.Crawer;

import java.util.HashMap;
import java.util.Map;

import com.mark.common.UserAgentUtil;
import com.mark.robotics.Crawer;
/**
 * zim 直接通过get方法访问 可以通过12-12
 * @author YIMA
 *
 */
public class CrawerZIM extends Crawer{

	public static void main(String[] args) {
		CrawerZIM zim = new CrawerZIM();
		try {
			zim.executeTask();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void executeTask() throws Exception {
		Map<String,String> headMap = new HashMap<String, String>();
		headMap.put("User-Agent", UserAgentUtil.getOneUserAgent());
		getHttpProxy().setRequestProperties(headMap);
		final String target_url = "http://www.zim.com/pages/findyourroute.aspx?origincode=HKHKG%3b10&origincodetext=Hong+Kong+--+Hong+Kong&destcode=SGSIN%3b10&destcodetext=Singapore+--+Singapore&fromdate=12%2f12%2f2017&mode=1&searchdimention=0&todate=23%2f01%2f2018&schedule=view0";
		String response =  getHTTPResponse(target_url, null, 1);
		System.out.println(response);
	}

}
