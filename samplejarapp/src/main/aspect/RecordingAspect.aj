package com.xebia.smok;


public aspect RecordingAspect {

	pointcut callPointcut() : 
		call(* com.xebia.Greeter.*(..));
	
	
	before() : callPointcut() {
		System.out.println("Before");
	}
	
	after() : callPointcut() {
		System.out.println("after");
	}
}
