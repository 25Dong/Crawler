package com.mark.TestOther;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
/***
 * 1.构造一个URL对象
 * 2.调用这个这个URL对象的openConnection()--->获得一个URLConnection对象
 * 3.配置URLConnection
 * 4.读取首部信息
 * 5.获得输入流--->读取数据
 * 6.获得输出流--->写入数据
 * 7.关闭连接
 * @author YIMA
 *
 */
public class HttpURLConnectionTest {
	private static String TARGET_URL ="http://o-www.yangming.com/e-service/schedule/PointToPoint.aspx";
	private static String user_agent_value="Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Trident/4.0; GTB7.5; .NET4.0C; .NET4.0E; .NET CLR 2.0.50727)";
	//是否需要保存cookie信息
	private boolean enableCookie = true;
	private Map<String,String> cookieMap ;

	//是否设置代理
	private boolean needProxy = false;
	private final String hostname ="";//代理IP
	private final int port = 8080;//代理端口
	
	public int time_out_value = 3 * 60 * 1000;//等待超时时间

	public static void main(String[] args) throws IOException, InterruptedException{
		for(int i = 1; i<=50 ;i++){
			HttpURLConnectionTest test = new HttpURLConnectionTest();
			test.getResponse();
			System.out.println("第"+i+"次：   "+test.cookieMap);
			Thread.sleep(1000);
		}
		
	}

	private String getResponse() throws IOException {
		URL url = null;
		HttpURLConnection http = null;
		BufferedReader bufferedReader =null;
		InputStream inputStream = null;
		StringBuffer sTotalString = new StringBuffer();
		try{
			//构造一个URL对象
			url = new URL(TARGET_URL);

			//调用这个这个URL对象的openConnection()
			if(needProxy){
				InetSocketAddress inetSocketAddress = new InetSocketAddress(hostname, port);
				Proxy proxy = new Proxy(Proxy.Type.HTTP,inetSocketAddress);
				http = (HttpURLConnection) url.openConnection(proxy);
			}else{
				http = (HttpURLConnection) url.openConnection();
			}


			//设置请求的方式和相关信息
			http.setRequestMethod("GET");
			http.setConnectTimeout(time_out_value);
			http.setReadTimeout(time_out_value);
			http.setDoInput(true);//以后就可以使用conn.getInputStream().read();默认值是true
			//设置头部的请求参数
			//http.setRequestProperty("User-Agent", user_agent_value);

			//添加cookie信息
			//SetCookieToRequestIfNecessary(http);

			//获得输入流--->读取数据
			inputStream = http.getInputStream();
			bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

			//获得输出流--->写入数据
			String sCurrentLine = null;
			while((sCurrentLine = bufferedReader.readLine())!= null){
				sTotalString.append(sCurrentLine);
				sTotalString.append("\r\n");
			}

			//是否需要保存cookie
			if(enableCookie){
				parseCookieProperties(http);
			}

			return sTotalString.toString();
		}catch(MalformedURLException e){
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			bufferedReader.close();
			inputStream.close();
			http.disconnect();
		}
		return null;
	}


	//设置cookie值
	private void SetCookieToRequestIfNecessary(HttpURLConnection http) {
		if(!enableCookie || cookieMap!=null ){
			return;
		}
		String cookieValue = buildCookieContent();
		http.setRequestProperty("Cookie", cookieValue);
	}

	//构造cookieValue值
	private String buildCookieContent() {
		StringBuffer cookieStr = new StringBuffer();
		for(String key:cookieMap.keySet()){
			cookieStr.append(key).append("=").append(cookieMap.get(key));
		}
		return cookieStr.toString();
	}

	//取出response信息中的cookie信息保存到一个Map中
	//这里只保存cookie中的name 和 value 的信息，而path,demain等没有保存
	private void parseCookieProperties(HttpURLConnection http) {
		//如果服务器返回的信息中没有Set-Cookie，就结束
		if(http.getHeaderFields().get("Set-Cookie").size()==0){
			return;
		}

		if(cookieMap == null){
			cookieMap = new HashMap<String, String>();
		}

		for(String cookie:http.getHeaderFields().get("Set-Cookie")){
			
			if(cookie == "" || !cookie.contains("=")){
				continue;
			}

			String cookieName = cookie.substring(0, cookie.indexOf("="));
			String cookieValue = cookie.substring(cookie.indexOf("=") + 1);

			if(cookie.contains(";")){
				cookieValue = cookie.substring(cookie.indexOf("=") + 1, cookie.indexOf(";"));
			}

			cookieMap.put(cookieName, cookieValue);
		}
	}
}
