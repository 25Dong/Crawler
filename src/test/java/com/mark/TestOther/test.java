package com.mark.TestOther;

import sun.security.jca.GetInstance;

class Son{
	private int i = 1;

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}
	
}


class Father extends Son{
	
	public void print(){
		int i = getI();
		System.out.println(i);
	}
}
public class test {

	public static void main(String[] args) {
		Son son = new Son();
		son.setI(8);
		Father father = new Father();
		father.print();
	}

}
