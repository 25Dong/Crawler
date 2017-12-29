package com.mark.Crawer;

import java.util.HashMap;
import java.util.Map;

import com.mark.common.UserAgentUtil;
import com.mark.robotics.Crawer;
/**
 * GOSU通过get方法可以直接访问
 * @author YIMA
 *
 */
public class CrawerEGLV extends Crawer{

	public static void main(String[] args) throws Exception {
		   CrawerEGLV test =new CrawerEGLV();
		test.executeTask();
	}

	@Override
	public void executeTask() throws Exception {
		String response = getResponse();
		System.out.print(response);
	}

	private String getResponse() throws Exception {
		Map<String, String> headMap = new HashMap<String, String>();
		headMap.put("User-Agent", UserAgentUtil.getOneUserAgent());
		getHttpProxy().setRequestProperties(headMap);
		final String target_url = "https://www.goldstarline.com/p2p.aspx?hidSearch=true&hidFromHomePage=false&hidSearchType=4&id=166&l=4&txtPOL=HKHKG&txtPOD=SGSIN&rb=Dep&txtDateFrom=07/12/2017&txtDateTo=18/01/2018";
		return getHTTPResponse(target_url, null, 1);
	}

}
