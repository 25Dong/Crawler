package com.mark.Thread;
/**
 * 各个线程之间不共享数据
 * @author YIMA
 *
 */
public class MyThread3 extends Thread{

	//static private int  count = 5; //静态变量存放于方法区(线程共享)---->Heap
	private int  count = 5;
	
	public MyThread3(String name){
		this.setName(name);
	}
	public void run() {
		super.run();
		while(count > 0){
			count--;
			System.out.println("由"+this.currentThread().getName()+"计算。count="+count);
		}
	}

	public static void main(String[] args) {
		MyThread3 t1 = new MyThread3("t1");
		MyThread3 t2 = new MyThread3("t2");
		MyThread3 t3 = new MyThread3("t3");
		t1.start();
		t2.start();
		t3.start();
	}

}
