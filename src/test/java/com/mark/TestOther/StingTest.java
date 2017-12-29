package com.mark.TestOther;

import java.io.UnsupportedEncodingException;

public class StingTest {
	public static void main(String[] args){
		try {
			//ISO8859-1
		
			String s = new String("&#243".getBytes("Unicode"),"UTF-8");
			System.out.println(s);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}
}
