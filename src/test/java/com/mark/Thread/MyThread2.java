package com.mark.Thread;
/**
 * 随机性
 * 同时代码中的执行stat()方法的顺序不代表着线程的启动顺序
 * @author YIMA
 *
 */
public class MyThread2 extends Thread {

	@Override
	public void run() {
		try{
			for(int i=0;i<10;i++){
				int time = (int) (Math.random()*1000);
				Thread.sleep(time);
				System.out.println("run="+Thread.currentThread().getName());
			}
		}catch(Exception e){
		}
	}

	public static void main(String[] args) {
		try{
			MyThread2 thread = new MyThread2();
			thread.setName("myThread2");
			thread.start();
			for(int i= 0;i<10;i++){
				int time = (int) (Math.random()*1000);
				Thread.sleep(time);
				System.out.println("main"+Thread.currentThread().getName());
			}
		}catch(Exception e){
		}
	}

}
