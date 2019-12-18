package com.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.util.LoggingAspect;

public class Test1 {
   
	public static void main(String[] args) {
		ClassPathXmlApplicationContext cs = new ClassPathXmlApplicationContext("applicationContext.xml");
		LoggingAspect bean = (LoggingAspect)cs.getBean("loggingAspect");
		System.out.print(bean);
	}
}
