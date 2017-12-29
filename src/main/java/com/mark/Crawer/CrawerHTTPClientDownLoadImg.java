package com.mark.Crawer;

import com.mark.robotics.Crawer;
/*
 * 使用HTTPClient下载图片的demo
 */
public class CrawerHTTPClientDownLoadImg extends Crawer {

	@Override
	public void executeTask() throws Exception {
//		getHttpClientProxy().setPicDir("");
		getHTTPResponse("http://imgsrc.baidu.com/baike/pic/item/9345d688d43f87942cba42dcdb1b0ef41ad53afe.jpg", null, 5);
	}

	public static void main(String[] args) throws Exception {
		CrawerHTTPClientDownLoadImg test = new CrawerHTTPClientDownLoadImg();
		test.executeTask();
	}

}
