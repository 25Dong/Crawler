package com.mark.TestOther;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;

class Person{
	private String name;
	private int age;
	
	public Person(String name,int age){
		this.name = name;
		this.age = age;
	}
}


public class ObjectToJSON {
	
	private List<String> list;
	private Map<String, Object> map;
	
	//将一个List转换成JSON
	public  JSONArray ListToJson(){
		list = new ArrayList<String>();
		list.add("name1");
		list.add("name2");
		
	
		JSONArray jsonArray = new JSONArray(list);
		return jsonArray;
	}
	
	public static void print(JSONArray josnArray){
		System.out.println("JSON数组的内容是："+josnArray);
	}
	public static void main(String[] args) {
		ObjectToJSON  test = new ObjectToJSON();
		print(test.ListToJson());
	}
}
