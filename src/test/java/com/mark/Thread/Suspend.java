package com.mark.Thread;

class Synchronized{
	synchronized public void print(){
		System.out.println("begin");
		if(Thread.currentThread().getName().equals("a")){
			System.out.println("a 线程永远suspend了！");
			Thread.currentThread().suspend();
		}
		System.out.println("end");
	}
}

public class Suspend {

	public static void main(String[] args) {
		try{
			final Synchronized object = new Synchronized();
			Thread thread1 = new Thread(){
				public void run(){
					//调用同步方法
					object.print();
				}
			};
			
			thread1.setName("a");
			thread1.start();
			thread1.sleep(1000);
			
			Thread thread2 = new Thread(){
				public void run(){
					System.out.println("Thread2 启动了!");
					//调用同步方法
					object.print();
				}
			};
			thread2.start();
		}catch(Exception e){
			
		}
		
	}

}
