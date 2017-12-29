package com.mark.robotics.http;

import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.commons.lang3.StringUtils;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.CollectingAlertHandler;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class HtmlUnit {
	private final  String TARGET_URL = "https://www.hapag-lloyd.com/en/schedules/interactive.html";

	public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		HtmlUnit test = new HtmlUnit();
//		test.getResponse();
		String response = test.getResponse1();
		System.out.println(response);
	}

	private String getResponse1() throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		String response = null;
		
		@SuppressWarnings("resource")
		WebClient webClient = new WebClient(BrowserVersion.CHROME);
		webClient.getOptions().setUseInsecureSSL(true);
		//启动JS
		webClient.getOptions().setJavaScriptEnabled(true);
		//禁用CSS，可以避免自动二次请求CSS进行渲染
		webClient.getOptions().setCssEnabled(false);
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		//启动客户端重定向
		webClient.getOptions().setRedirectEnabled(true);
		//js运行错误时，是否抛出异常  
	    webClient.getOptions().setThrowExceptionOnScriptError(true);  
	    //设置超时  
	    webClient.getOptions().setTimeout(50000);  
	    HtmlPage page = webClient.getPage(TARGET_URL);
	    
	 // 等待JS驱动dom完成获得还原后的网页  
	    webClient.waitForBackgroundJavaScript(100000);  
		
		response = page.asXml();
		
		
		return response;
	}

	private void getResponse() {
		String targetURL = "https://www.hapag-lloyd.com/en/schedules/interactive.html";
		String resposne = StringUtils.EMPTY;
		WebClient webClient = getClient();
		try {
			 HtmlPage page = webClient.getPage(targetURL);
			resposne = page.asXml();
		} catch (FailingHttpStatusCodeException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(resposne);
		
	}

	private WebClient getClient() {
		WebClient webClient = new WebClient(BrowserVersion.FIREFOX_45);
		webClient.getOptions().setJavaScriptEnabled(false);
	    webClient.setJavaScriptTimeout(60*1000);
	    webClient.getOptions().setCssEnabled(true);
	    webClient.getOptions().setActiveXNative(true);
	    webClient.getOptions().setPopupBlockerEnabled(true);
	    webClient.getOptions().setRedirectEnabled(true);
	    webClient.getOptions().setTimeout(10000);
	    webClient.getOptions().setDoNotTrackEnabled(true);
	    webClient.getCookieManager().setCookiesEnabled(true);
	    webClient.getOptions().setThrowExceptionOnFailingStatusCode(true);
	    webClient.getOptions().setThrowExceptionOnScriptError(false);
	    webClient.getOptions().setUseInsecureSSL(true);
	    webClient.getOptions().setSSLInsecureProtocol("TLSv1.2");
	    webClient.setAjaxController(new NicelyResynchronizingAjaxController());
	    webClient.setAlertHandler(new CollectingAlertHandler());
	    return webClient;
	}

}
