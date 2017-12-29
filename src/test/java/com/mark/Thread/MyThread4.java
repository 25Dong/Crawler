package com.mark.Thread;

public class MyThread4 extends Thread {

	private int count = 5;
	public static void main(String[] args) {
		MyThread4 thread = new MyThread4();
		Thread a = new Thread(thread,"A");
		Thread b = new Thread(thread,"B");
		Thread c = new Thread(thread,"C");
		Thread d = new Thread(thread,"D");
		a.start();
		b.start();
		c.start();
		d.start();
		c.start();
	}

	@SuppressWarnings("static-access")
	@Override
	synchronized public void run() {
		super.run();
		count--;
		System.out.println("由"+this.currentThread().getName()+"计算。count="+count);
	}

}
