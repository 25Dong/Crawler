package com.mark.robotics.http;

import java.net.URLEncoder;
import java.util.Map;





import org.apache.logging.log4j.Logger;

import com.mark.common.StringUtil;


public class HTTPGetUtil {
	public final static String URL_PARAM_BEGIN = "?";
	public final static String URL_PARAM_CONNECT = "&";
	public final static String ISO_8859_1_ENCODING = "iso-8859-1";
	public final static String ACTION_CONSTRUCT_URL_FOR_HTTP_GET = "constructURLForHTTPGet";
	public final static String URL_PARAM_PAIR = "=";
	private String paramConnector = URL_PARAM_CONNECT;
	private String encoding = ISO_8859_1_ENCODING;
	private boolean needEncode = true;
	private Logger logger;
	private boolean supportPairNoCareValue = false;

	public String getParamConnector() {
		return paramConnector;
	}

	public void setParamConnector(String paramConnector) {
		this.paramConnector = paramConnector;
	}

	public String constructURLForHTTPPost(Map<String, String> params) {
		if(params == null||params.size() == 0){
			return null;
		}else{
			return constructURLByParams(params);
		}
	}

	//根据param参数构建URL
	private String constructURLByParams(Map<String, String> params) {
		StringBuffer sb = new StringBuffer();
		for(String key:params.keySet()){
			constructURLByParam(sb, "", key, params.get(key));
		}
		///???
		return sb.toString().substring(1);
	}

	private void constructURLByParam(StringBuffer sb, String url,
			String key, String value) {
		if(StringUtil.isNullOrEmpty(key)){
			return;
		}
		if(!isContainParam(sb.toString())){
			sb.append(URL_PARAM_BEGIN);
		}else{
			sb.append(getParamConnector());
		}
		sb.append(encodeParam(key));
		if (supportPairNoCareValue || StringUtil.isNotNullNorEmpty(value)) {
			sb.append(URL_PARAM_PAIR).append(encodeParam(value));
		}
	}

	private String encodeParam(String param) {
		if (StringUtil.isNullOrEmpty(param)) {
			return StringUtil.EMPTY_STRING;
		}

		if (!needEncode) {
			return param;
		}

		String encodedParam = param;
		try {
			encodedParam = URLEncoder.encode(param, encoding);
		} catch (Exception e) {
			logger.error(ACTION_CONSTRUCT_URL_FOR_HTTP_GET, e);
		}
		return encodedParam;
	}

	private boolean isContainParam(String url) {
		return (StringUtil.isNotNullNorEmpty(url)) && (url.contains(URL_PARAM_BEGIN));
	}

	public String constructURLForHTTPGet(String url, Map<String, String> params) {
		if (StringUtil.isNullOrEmpty(url) || (params == null || 0 == params.size())) {
			return url;
		}else{
			return constructURLByParams(url, params);
		}
	}

	private String constructURLByParams(String url, Map<String, String> params) {
		StringBuffer sb = new StringBuffer(url);
		for (String key : params.keySet()) {
			constructURLByParam(sb, url, key, params.get(key));
		}
		return sb.toString();
	}
}
