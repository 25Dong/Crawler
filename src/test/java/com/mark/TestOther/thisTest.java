package com.mark.TestOther;


public class thisTest {
	private int a = 10;

	public void test(){
		this.a = 1;
	}
	
	public static void test1(){
		//this.a = 1;//编译时出错
	}
	public static void main(String[] args){
		thisTest test = new thisTest();
	}
}
