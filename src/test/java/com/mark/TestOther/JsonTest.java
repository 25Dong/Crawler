package com.mark.TestOther;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonTest {

	private final static String Json = "{"
			+ "\"sites\": "
			+ "[{ \"name\":\"菜鸟教程\" , \"url\":\"www.runoob.com\" }, "
			+ "{ \"name\":\"google\" , \"url\":\"www.google.com\" }, "
			+ "{ \"name\":\"微博\" , \"url\":\"www.weibo.com\" }"
			+ "]"
			+ "}";
	
	public static void main(String[] args) {
		JSONObject json = new JSONObject(Json);
		
		JSONArray jsonArray = json.getJSONArray("sites");
		//遍历
		for(int i = 0;i<jsonArray.length();i++){
			JSONObject jsonObject =(JSONObject) jsonArray.get(i);
			String name = jsonObject.getString("name");
			String url = jsonObject.getString("url");
			System.out.println(name+"  "+url);
		}
	}

}
