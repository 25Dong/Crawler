package com.mark.TestOther;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class regexTest {

	private static String string= "Japan Kansai-Thailand Service (JST)";

	public static void getTarget(){
		String regex = "\\(([^)]*)\\)";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(string);
		if(matcher.find()){
			System.out.println(matcher.group());
		}
	}

	public static void main(String[] args) {
		getTarget();

	}

}
