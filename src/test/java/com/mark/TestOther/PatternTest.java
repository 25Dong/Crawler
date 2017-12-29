package com.mark.TestOther;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternTest {

	public static void main(String[] args) {
		PatternTest test = new PatternTest();
		test.test1();
//		test.test2();
	}

	private void test2() {
		String regex ="\\s+href=\"([^\"]+)";//正则表达式
		String expression = "id=Content href=\"javascript:WebForm(new WebForm(PointToPointDetail.aspx&quot;, false, true))";
		Pattern p = Pattern.compile(regex);
		Matcher matcher = p.matcher(expression);
		
	}

	private void test1() {
		//	正则表达式： \s+href="([^"]+)
		//	\s+:前面出现一个，或者多个的空白字符（包括空格、制表符、换页符）;这里就是匹配到href前面的空格
		//	[^"]:匹配除了双引号的其他字符
		//	[^"]+:匹配一个以上
		//	([^"]+):匹配 [^"]+ 并获取这一匹配。
		String regex ="Kelvin";//正则表达式
		String expression = "Kelvin Li and Kelvin Chan are both working in Kelvin Chen's KelvinSoftShop company";
		
		Pattern p = Pattern.compile(regex);
		Matcher matcher = p.matcher(expression);
		StringBuffer sb = new StringBuffer();
		int i = 0;
		boolean result = matcher.find();//使用find()方法查找第一个匹配的对象 
		while(result){
			System.out.println(matcher.group());
			i++;
			matcher.appendReplacement(sb, regex);
			System.out.println("第"+i+"次匹配后sb的内容是："+sb);
			result = matcher.find();//继续查找下一个匹配对象 
		}
		//最后调用appendTail()方法将最后一次匹配后的剩余字符串加到sb里； 
		matcher.appendTail(sb);
		System.out.println("调用m.appendTail(sb)后sb的最终内容是:"+ sb.toString()); 
	}

}
