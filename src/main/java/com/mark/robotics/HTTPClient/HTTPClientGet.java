package com.mark.robotics.HTTPClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Header;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;

import com.mark.common.StringUtil;

/**
 * 
 * @author YIMA
 *
 */
public class HTTPClientGet {
	private String picDir = "D:\\aaMARK\\aa";
	private 	CookieStore cookieStore = new BasicCookieStore();
	DefaultHttpClient httpClient  = new DefaultHttpClient();

	public String getContentByHTTPClientGet(String url,int time) throws ClientProtocolException, IOException{
		
		
		
		
		///----------------
		String response = StringUtil.EMPTY_STRING;


		HttpResponse httpResponse = null;

		HttpGet httpGet = new HttpGet("http://www.goldstarline.com/p2p.aspx?");
		httpResponse = httpClient.execute(httpGet);
		org.apache.http.Header[] header = httpResponse.getAllHeaders();
		CookieStore dd =  httpClient.getCookieStore();
		//输出响应码
		System.out.println(httpResponse.getStatusLine().getStatusCode());
		
		
		
		
		BasicClientCookie cookie2 = new BasicClientCookie("TSPD_101","08b0950a45ab2800e3c8994d00f9d747af7e046cf27a0dfd440f9c80afa03e0dfa11bee5935e03baefc5269ef9b58db208d0fb585b0510000311ed3b27f6e0c18048106af6adcd52:");
		cookie2.setVersion(0);
		cookie2.setDomain("www.goldstarline.com");
		cookie2.setPath("/");
		cookie2.setSecure(false);
		cookie2.setExpiryDate(DateUtils.addDays(new Date(), 1));
		
		BasicClientCookie cookie = new BasicClientCookie("TSc8be1df5_rc", "TSc8be1df5_rc=1&TSc8be1df5_id=5&TSc8be1df5_cr=08b0950a45ab2800bbd7c107bf6934dcaa1710d7ce97060ca8a2bf1630114e0f275130f955bdf5d5484d4b22688cb462:08937bbbfb04800011834a9bcc97bc25cd1d5c29ecf816c7e030d4220cfe0549b1ff5073b8cb08824fedc6c4a1012824eef7c90f55d89104d80167c85ba9d0f18fd5d28aaa48441d9b483d2fbea5486d7d3bfc4293c8b672a9e78cef09aca6322d724c1d4f566a0ef24cbef45260ddd4c0d24f7701e58f28e275388f5b11f05b6fe0575004b892d4&TSc8be1df5_ef=&TSc8be1df5_pg=0&TSc8be1df5_ct=0&TSc8be1df5_rf=0");
		cookie.setVersion(0);
		cookie.setDomain("www.goldstarline.com");
		cookie.setPath("/");
		cookie.setExpiryDate(new Date());
		cookie.setSecure(false);
		
		BasicClientCookie cookie3 = new BasicClientCookie("TSc8be1df5_rc", "jzdzy1qpi1i4hghh0skkxecz");

		BasicClientCookie cookie4 = new BasicClientCookie("TSc8be1df5_rc", "01bbeceaf757462e446b4a01c272b4e8f314341d9a5ce67d7f2c90f78092ce00a08c33a26f16d86546cf1619bc12caf4f5a674490b");

		cookieStore.addCookie(cookie2);
		//cookieStore.addCookie(cookie);
		
		HttpGet httpGet2 = new HttpGet(url);
		httpClient.setCookieStore(cookieStore);
		httpResponse = httpClient.execute(httpGet2);
		HttpEntity entity = httpResponse.getEntity();
		if(entity != null){
			response = EntityUtils.toString(entity,"UTF-8");
			EntityUtils.consume(entity);
			return response;
		}
		httpClient.getConnectionManager().shutdown();
		return response;
	}

	public String getJPGByHTTPClientGet(String url) {
		String result = "success";
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String name = FilenameUtils.getName(url);
		try{
			HttpGet get = new HttpGet(url);
			HttpResponse reponse = httpClient.execute(get);
			HttpEntity entity = reponse.getEntity();
			InputStream in = entity.getContent();

			File file =  new File(picDir,name);
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
		}catch(Exception e){
			return "error";
		}
		return result;
	}


}
