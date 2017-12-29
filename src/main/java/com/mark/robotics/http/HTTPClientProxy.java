package com.mark.robotics.http;
/**
 * httpClient
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import com.mark.common.StringUtil;
import com.mark.common.UserAgentUtil;

@SuppressWarnings("deprecation")
public class HTTPClientProxy extends BasicHTTPProxy{

	private int connectionTimeout = 10 * 60 * 1000;
	private int socketTimeOut = 10 * 60 * 1000;
	private String picDir = "D:\\aaMARK\\aa";
	
	protected String proxyUser = null;
	protected String proxyPwd = null;
	private HTTPClientSSLUtil sslUtil;
	public final static boolean SSL_CERTIFICATE_AUTHENTICATED = true;
	private boolean sslCertificateAuthenticated = SSL_CERTIFICATE_AUTHENTICATED;
	private static transient final Log log = LogFactory.getLog(HTTPClientProxy.class);
	private CookieStore cookieStore;

	//下载图片
	public void downloadingImgByHttpClient(String picURL, String imPath) {
		imPath = picDir;
		
//		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		String name = FilenameUtils.getName(picURL);
		try{
			HttpGet get = new HttpGet(picURL);
			HttpResponse reponse = httpClient.execute(get);
			HttpEntity entity = reponse.getEntity();
			InputStream in = entity.getContent();

			File file =  new File(imPath,name);
			try{
				FileOutputStream fout = new FileOutputStream(file);
				int l = -1;
				byte[] tmp = new byte[1024];
				while((l = in.read(tmp)) != -1){
					fout.write(tmp, 0, l);
				}
				fout.flush();
				fout.close();
			}finally{
				in.close();
			}
			log.info("downing success and Path is"+picDir);
		}catch(Exception e){
			log.error("downing fair");
			e.printStackTrace();
		}
		
	}
	
	//HttpClient GET方法
	public String getContentByHTTPClientGet(String url,
			Map<String, String> params) throws Exception {
		if(StringUtil.isNullOrEmpty(url)){
			return null;
		}
		setHttpGetUtil(new HTTPGetUtil());
		DefaultHttpClient httpClient = generateClient();
		processSSLIfNecessary(httpClient, url);
		String httpGetURL = constructHTTPGetURL(url, params);//根据请求的参数构造最终的请求URL
		String response =StringUtil.SPACE_STRING;
		try{
			response = getContentOfHTTPGetURLByClient(httpClient, httpGetURL);
		}catch(Exception e){
			System.err.println("get fail");
			e.printStackTrace();
		}
		return response;
	}

	private String getContentOfHTTPGetURLByClient(DefaultHttpClient httpClient,
			String httpGetURL) throws ClientProtocolException, IOException {
		String response = StringUtil.SPACE_STRING;
		HttpGet httpGet = new HttpGet(httpGetURL);
		constructHttpGetRequestHead(httpGet);//构造请求头部
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		response = httpClient.execute(httpGet, responseHandler);
		if(isEnableCookie()){
			cookieStore = httpClient.getCookieStore();//保存cookies信息 
		}
		httpClient.getConnectionManager().shutdown();//关闭连接
		return response;
	}

	private void processSSLIfNecessary(DefaultHttpClient httpClient, String url) {
		if(!sslCertificateAuthenticated && null != sslUtil && sslUtil.isSSLSchemaForSpecificURL(url)){
			sslUtil.registerSSL(httpClient);
		}

	}


	private DefaultHttpClient generateClient() {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		if (connectionTimeout > 0) {//设置连接超时
			httpClient.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, connectionTimeout);
		}

		if (socketTimeOut > 0) {//套接字超时时间：即连个连续的数据包之间的闲置时间
			httpClient.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, socketTimeOut);
		}

		if (StringUtil.isNotNullNorEmpty(proxyhost) && proxyport > 0) {//设置代理,用户名和密码
			HttpHost proxy = new HttpHost(proxyhost,proxyport);
			httpClient.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, proxy);
		}

		if(StringUtil.isNotNullNorEmpty(proxyPwd)&&StringUtil.isNotNullNorEmpty(proxyUser)&&StringUtil.isNotNullNorEmpty(proxyhost) && proxyport > 0){
			httpClient.getCredentialsProvider().setCredentials(new AuthScope(proxyhost, proxyport),
					(Credentials) new UsernamePasswordCredentials(proxyUser, proxyPwd));
		}
		return httpClient;
	}


	public String getContentByHTTPClientPost(String requestUrl,Map<String, String> params, CookieStore cookieStore,Map<String, String> headers,boolean isRediect) {
		HttpResponse response = null;
		String body = null;
		try{
			DefaultHttpClient httpclient = generateClient();//初始化httpClient配置信息
			httpclient.getParams().setParameter(HttpMethodParams.USER_AGENT,UserAgentUtil.getOneUserAgent());

			if(isRediect){//是否重定向
				httpclient.setRedirectStrategy(new LaxRedirectStrategy());
			}

			HttpPost post = postForm(requestUrl, params);//构造提交表单数据
			//			constructHttpGetRequestHead(post);//构造请求头部
			prepareHeader(post,headers);

			if(cookieStore != null){//添加cookie信息
				httpclient.setCookieStore(cookieStore);
			}

			response = httpclient.execute(post);
			HttpEntity entity = response.getEntity();
			body = EntityUtils.toString(entity);

			if(isEnableCookie()){
				cookieStore = httpclient.getCookieStore();//保存cookie信息
			}

			httpclient.getConnectionManager().shutdown();
			return body;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}







	private void prepareHeader(HttpPost post, Map<String, String> headers) {
		if(MapUtils.isNotEmpty(headers)){
			for(String key : headers.keySet()){
				post.addHeader(key,headers.get(key));
			}
		}
	}

	private void constructHttpGetRequestHead(HttpRequestBase requestBase) {
		if (requestProperties == null) {
			return;
		}
		for (String key : requestProperties.keySet()) {
			requestBase.addHeader(key, requestProperties.get(key));
		}
	}


	/*HTTPClient POST请求中 模拟表单提交
	 * 
	 * NameValuePair：是一个接口
	 * BasicNameValuePair：是该接口的实现，使用BasicNameValuePair封装表单中的键值对
	 * 为了来记录一个 Web应用程序或提交输出数据；HttpClient 提供了特殊的实体类 UrlEncodedFormEntity
	 * UrlEncodedFormEntity 实例将会使用 URL 编码来编码参数eg：param1=value1&param2=value2：
	 * */
	private HttpPost postForm(String url, Map<String, String> params) {
		HttpPost httpost = new HttpPost(url);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();

		Set<String> keySet = params.keySet();
		for (String key : keySet) {
			nvps.add(new BasicNameValuePair(key, params.get(key)));
		}

		try {
			httpost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			log.error("UnsupportedEncodingException...", e);
		}
		return httpost;
	}

	//HTTPClient 方法中保存Cookie信息
	public CookieStore getCookieStore() {
		return cookieStore;
	}

	public void setCookieStore(CookieStore cookieStore) {
		this.cookieStore = cookieStore;
	}

	public void setPicDir(String picDir) {
		this.picDir = picDir;
	}



}
