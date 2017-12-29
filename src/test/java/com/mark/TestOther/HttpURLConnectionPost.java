package com.mark.TestOther;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class HttpURLConnectionPost {
	private static final String TARGET_URL ="http://www.hmm21.com/ebiz/schedule/getSailingSchedules.jsp";
	private static String queryparameter;// post 请求的参数;
	private static String user_agent_value="Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Trident/4.0; GTB7.5; .NET4.0C; .NET4.0E; .NET CLR 2.0.50727)";

	//是否设置代理
	private boolean needProxy = false;
	private final String hostname ="";//代理IP
	private final int port = 8080;//代理端口
	
	public int time_out_value = 3 * 60 * 1000;//等待超时时间
	
	public static void main(String[] args) throws IOException {
		HttpURLConnectionPost test = new HttpURLConnectionPost();
		Map<String,String> paramMap = constructPostMap();
		queryparameter = constructPostParams(paramMap);
		System.out.println(test.getResponse());
	}

	private static String constructPostParams(Map<String, String> paramMap) {
		StringBuffer sb = new StringBuffer();
		for(String key:paramMap.keySet()){
			if(key == null){
				return null;
			}
			
			if(!(sb.toString() != ""&&sb.toString().contains("?"))){
				sb.append("?");
			}else{
				sb.append("&");
			}
			sb.append(key);
			sb.append("=");
			sb.append(paramMap.get(key));
		}
		return sb.toString().substring(1);
	}

	private static Map<String, String> constructPostMap() {
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("key1", "value1");
		paramMap.put("key2", "value2");
		paramMap.put("key3", "value3");
		paramMap.put("key4", "value4");
		paramMap.put("key5", "value5");
		return paramMap;
	}

	private String getResponse() throws IOException {
		URL url = null;
		HttpURLConnection http = null;
		OutputStreamWriter out = null;
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
			http.setRequestMethod("POST");
			http.setConnectTimeout(time_out_value);//连接超时 单位毫秒
			http.setReadTimeout(time_out_value);//读取超时 单温毫秒
			
			//发送POST请求必须设置如下两行
			http.setDoInput(true);//以后就可以使用conn.getInputStream().read();默认值是true
			http.setDoOutput(true);//以后就可以使用conn.getOutputStream().write() 
			
			//设置头部的请求参数
			http.setRequestProperty("User-Agent", user_agent_value);

			// 获取URLConnection对象对应的输出流
			out = new OutputStreamWriter(http.getOutputStream(),"UTF-8");
			if(queryparameter != null){
				out.write(queryparameter);// 发送请求参数
			}
			//out.flush();
			out.close();
			
			//获得输入流--->读取数据
			inputStream = http.getInputStream();
			bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

			//获得输出流--->写入数据
			String sCurrentLine = null;
			while((sCurrentLine = bufferedReader.readLine())!= null){
				sTotalString.append(sCurrentLine);
				sTotalString.append("\r\n");
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

}
