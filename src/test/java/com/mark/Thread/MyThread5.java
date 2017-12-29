package com.mark.Thread;

import com.mark.TestOther.thisTest;

class login{
	private static String userName;
	private static String passWord;
	
	public static  void doPost(String userName,String passWord){
		try{
			userName = userName;
			if(userName.equals("a")){
				Thread.sleep(5000);
			}
			passWord = passWord;
			System.out.println("UserName="+userName+"PassWord="+passWord);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}

class Alogin implements Runnable{
	public void run() {
		login.doPost("a", "aa");
	}
}

class BLogin implements Runnable{
	public void run() {
		login.doPost("b", "bb");
	}
}

public class MyThread5 {
	public static void main(String[] args) {
		Alogin a = new Alogin();
		Thread thread = new Thread(a);
		thread.start();
		BLogin b = new BLogin();
		Thread thread2 = new Thread(b);
		thread2.start();
	}
}
