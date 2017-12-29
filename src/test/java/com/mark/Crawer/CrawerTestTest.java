package com.mark.Crawer;
import org.junit.Before;
import org.junit.Test;

import com.mark.robotics.Crawer;


public class CrawerTestTest {
	Crawer crawer = null;
	@Before
	public void setUp() throws Exception {
		Class crawerclass = null;
		crawerclass = Class.forName("com.mark.Crawer."+"CrawerTest");
		crawer = (Crawer) crawerclass.newInstance();
		
	}
	
	@Test
	public void test(){
		try {
			crawer.executeTask();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
