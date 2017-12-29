package com.mark.Crawer;
//success
import com.mark.common.StringUtil;
import com.mark.robotics.Crawer;

public class  CrawerHTTPClientGetDemo extends Crawer{

	public static void main(String[] args) throws Exception {
//		Class crawerclass = null;
//		crawerclass = Class.forName("com.mark.Crawer."+"CrawerHTTPGetDemo");
//		Crawer crawer = (Crawer) crawerclass.newInstance();
//		crawer.executeTask();
		CrawerHTTPClientGetDemo test = new  CrawerHTTPClientGetDemo();
		test.executeTask();
	}

	@Override
	public void executeTask() throws Exception {
		String response = getResponseByGet();
		System.out.println(response);
	}

	private String getResponseByGet() throws Exception {
		String request_url = "http://www.baidu.com/";
		String response = StringUtil.EMPTY_STRING;
		getHttpProxy().setEnableCookie(true);//这是可以保存cookie信息
		getHttpClientProxy().setEnableCookie(true);
		response = getHTTPResponse(request_url, null,3);
		return response;
	}

}
