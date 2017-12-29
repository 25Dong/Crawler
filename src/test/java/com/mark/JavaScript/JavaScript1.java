package com.mark.JavaScript;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class JavaScript1 {

	public static void main(String[] args) {
		String scriptText = "function greet(name) { return('Hello, ' ,name); } ";  
		
		ScriptEngineManager factory = new ScriptEngineManager();
		ScriptEngine engine = factory.getEngineByName("JavaScript");
		try {
			engine.eval(scriptText);
		} catch (ScriptException e1) {
			e1.printStackTrace();
		}
		Invocable invocable = (Invocable) engine;
		try {
			String s=(String) invocable.invokeFunction("greet", "Alex");
			System.out.println(s);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (ScriptException e) {
			e.printStackTrace();
		}
	}

}
