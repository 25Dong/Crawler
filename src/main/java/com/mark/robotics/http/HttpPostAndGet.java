package com.mark.robotics.http;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.util.Strings;

import com.mark.common.StringUtil;

public class HttpPostAndGet {

	private static transient final Log log = LogFactory.getLog(HttpPostAndGet.class);
	private String httprequestMethod = "POST"; // default-->POST,0-->post,1-->get,others-->post

	private String proxyServer = "zha";
	private int proxyPort = 8080;
	private boolean needProxy = false;

	private boolean enableCookie = false; 
	private boolean enableRedirect = true;
	private Map<String, String> cookies;
	private Map<String, String> requestProperties;
	public int time_out_value = 5 * 60 * 1000;
	private static final String HTTP_REQUEST_METHOD_POST = "POST";
	private static final String HTTP_REQUEST_METHOD_GET = "GET";

	public HttpPostAndGet(String proxyServer,int proxyPort){
		this.proxyServer = proxyServer;
		this.proxyPort = proxyPort;
		if(proxyServer == null||proxyServer.trim().length()==0){
			needProxy = false;
		}else{
			needProxy = true;
		}
		log.info("proxy:"+proxyServer+":"+proxyPort);
	}


	public void setCookies(Map<String, String> cookies) {
		if(cookies != null && cookies.size()>0){
			if(this.cookies != null){
				for(String key:cookies.keySet()){
					this.cookies.put(key, cookies.get(key));
				}
			}else{
				this.cookies = cookies;
			}
		}
	}

	public void setHTTPTimeOut (int time_out_value) {
		this.time_out_value = time_out_value;
	}
	/***********************************/
	public boolean isEnableCookie() {
		return enableCookie;
	}

	public void setEnableCookie(boolean enableCookie) {
		this.enableCookie = enableCookie;
	}

	public boolean isEnableRedirect() {
		return enableRedirect;
	}

	public void setEnableRedirect(boolean enableRedirect) {
		this.enableRedirect = enableRedirect;
	}


	public Map<String, String> getRequestProperties() {
		return requestProperties;
	}


	public void setRequestProperties(Map<String, String> requestProperties) {
		this.requestProperties = requestProperties;
	}




	private void dealwithHttps(HttpsURLConnection httpsConn) {
		//???
		HostnameVerifier hv = new HostnameVerifier() {

			public boolean verify(String urlHostname, SSLSession session) {
				// TODO Auto-generated method stub
				return true;
			}
		};
		SSLContext sslContext = getTrustAllSSLContext();
		if(sslContext != null){
			httpsConn.setSSLSocketFactory(sslContext.getSocketFactory());
		}
		httpsConn.setHostnameVerifier(hv);
	}


	private SSLContext getTrustAllSSLContext() {
		//??
		X509TrustManager xtm = new X509TrustManager(){

			public void checkClientTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
				// TODO Auto-generated method stub

			}

			public void checkServerTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
				// TODO Auto-generated method stub

			}

			public X509Certificate[] getAcceptedIssuers() {
				// TODO Auto-generated method stub
				return null;
			}

		};
		SSLContext sslContext = null;
		try{
			sslContext = SSLContext.getInstance("TLS");
			X509TrustManager[] xtmArray = new X509TrustManager[] { xtm };
			sslContext.init(null, xtmArray, new java.security.SecureRandom());
		}catch(GeneralSecurityException gse){
			gse.printStackTrace();
		}
		return sslContext;
	}


	public Map<String, String> getCookies() {
		return cookies;
	}

	/**
	 * 使用java.net包下的方法实现网络请求（GET方法和POST方法）
	 * 
	 * 当使用的GET请求方法的时候，传进来的URL是构造好请求参数的URL（如果有请求参数的话）；queryparameter为空
	 * 当使用的POST的请方法时，传进的URL是目标URL，queryparameter代表着已经构造好的POST的请求体
	 * 
	 * @param requestURL：请求的URL（如果是get方法，此时URL已经是构造好的URL
	 * @param queryparameter:(post)方法时的请求体的内容
	 * @param method == 1:提交的是get请求
	 * @param method == 其他:提交的是POST请求
	 *
	 * @return 服务器response的信息
	 * @throws IOException 
	 */
	protected String getHttpResponseWithURLDirectlyAndURIParameter(String requestURL, String queryparameter,int method) throws IOException {
		log.info("getHttpResponseWithURLDirectly(),requestURL: "+ requestURL + ",method: " + method);

		if(method == 1){
			httprequestMethod = HTTP_REQUEST_METHOD_GET;
		}else{
			httprequestMethod = HTTP_REQUEST_METHOD_POST;
		}

		OutputStreamWriter out = null;
		BufferedReader l_reader = null;
		StringBuffer sTotalString = null;
		URL url = new URL(requestURL);
		HttpURLConnection http = null;

		if (needProxy) {//判断是否设置代理构造http
			InetSocketAddress inetSocketAddress = new InetSocketAddress(proxyServer, proxyPort);
			Proxy proxy = new Proxy(Proxy.Type.HTTP, inetSocketAddress);
			http = (HttpURLConnection) url.openConnection(proxy);
		} else {
			http = (HttpURLConnection) url.openConnection();
		}
		http.setConnectTimeout(time_out_value);//设置连接超时
		http.setReadTimeout(time_out_value);//设置读写超时
		http.setRequestMethod(httprequestMethod);//设置请求方法
		http.setDoInput(true);//以后就可以使用conn.getInputStream().read();默认值是true
		if(httprequestMethod.equalsIgnoreCase(HTTP_REQUEST_METHOD_POST)){
			http.setDoOutput(true);//如果是post方法两个都需要设置
		}

		//设置请求头信息
		SetRequestPropertyIfNecessary(http);
		//设置cookie
		SetCookieToRequestIfNecessary(http);
		//设置重定向
		SetRedirectIfNecessary(http);
		try {
			//如果是POST方法,往连接中发送数据
			if(httprequestMethod.equalsIgnoreCase(HTTP_REQUEST_METHOD_POST)&&StringUtil.isNotNullNorEmpty(queryparameter)){
				out = new OutputStreamWriter(http.getOutputStream(), "UTF-8");
				out.write(queryparameter);
				out.close();
			}

			//获得输入流--->读取数据
			sTotalString = new StringBuffer();
			l_reader = new BufferedReader(new InputStreamReader(http.getInputStream(), "UTF-8"));

			//获得输出流--->写入数据
			String sCurrentLine = null;
			while((sCurrentLine = l_reader.readLine())!= null){
				sTotalString.append(sCurrentLine);
				sTotalString.append("\r\n");
			}

			//保存response中的cookie信息
			GetCookieFromResponseIfNecessary(http);
			//添加重定向的地址
			GetRedirectLocationFromResponseIfNecessary(http, sTotalString);
			return sTotalString.toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(out != null){
				out.close();
			}
			if(l_reader != null){
				l_reader.close();
			}
			if(http != null){
				http.disconnect();
			}

		}
		return null;
	}



	private void GetRedirectLocationFromResponseIfNecessary(
			HttpURLConnection http, StringBuffer sTotalString) {
		if (!enableRedirect) {
			return;
		}
		try {
			if (HttpURLConnection.HTTP_MOVED_TEMP == http.getResponseCode()) {
				sTotalString.append(http.getHeaderField("location"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private void SetRedirectIfNecessary(HttpURLConnection http) {
		if (!enableRedirect) {
			return;
		}
		http.setInstanceFollowRedirects(enableRedirect);
	}


	private void SetCookieToRequestIfNecessary(HttpURLConnection http) {
		if (!enableCookie || MapUtils.isEmpty(cookies)) {
			return;
		}
		http.setRequestProperty("Cookie", buildCookieContent());
	}


	private String buildCookieContent() {
		StringBuffer cookieStr = new StringBuffer();
		for (String key : cookies.keySet()) {
			cookieStr.append(key).append("=").append(cookies.get(key)).append(";");
		}
		return cookieStr.toString();
	}


	private void GetCookieFromResponseIfNecessary(HttpURLConnection http) {
		//如果cookie已经保存了信息或者不设置保存cookie就直接返回
		if(!enableCookie || MapUtils.isNotEmpty(cookies)){
			return;
		}

		if(CollectionUtils.isEmpty(http.getHeaderFields().get("Set-Cookie"))){
			return;
		}

		if(null == cookies){
			cookies = new HashMap<String, String>();
		}

		for(String cookie:http.getHeaderFields().get("Set-Cookie")){
			if(StringUtil.isNullOrEmpty(cookie)||!cookie.contains("=")){
				continue;
			}

			String cookieName = cookie.substring(0, cookie.indexOf("="));
			String cookieValue = cookie.substring(cookie.indexOf("=") + 1);
			if (cookie.contains(";")) {
				cookieValue = cookie.substring(cookie.indexOf("=") + 1, cookie.indexOf(";"));
			}

			cookies.put(cookieName, cookieValue);
		}
	}


	private void SetRequestPropertyIfNecessary(HttpURLConnection http) {
		if(requestProperties.size()==0){
			return;
		}
		//设置请求头中的信息，
		for (String requestProperty : requestProperties.keySet()) {
			http.setRequestProperty(requestProperty, requestProperties.get(requestProperty));
		}
	}


	//------------new add
	public String getHttpResponseWithURIParameter(String url,Map<String, String> httpPostParams) {
		String result = "";
		HttpResponse response = null;
		HttpClient httpClient = new DefaultHttpClient();
		HttpHost targetHost = new HttpHost("blog.csdn.net");
		HttpGet httpGet = new HttpGet("/");

		Iterator<Entry<String, String>> iterator =  httpPostParams.entrySet().iterator();
		while(iterator.hasNext()){
			Map.Entry<String, String> entry =  (Entry<String, String>) iterator.next();
			String key = entry.getKey();
			String value = entry.getValue();
			httpGet.setHeader(key, value);
		}
		try {
			response = httpClient.execute(targetHost, httpGet);
			HttpEntity entity = response.getEntity();
			if(entity != null){
				result = EntityUtils.toString(entity, "utf-8");
				EntityUtils.consume(entity);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			httpClient.getConnectionManager().shutdown();
		}
		return result;
	}
}
