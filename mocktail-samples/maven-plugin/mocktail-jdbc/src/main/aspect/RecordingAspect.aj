package com.svashishtha.mocktail.metadata;


public aspect RecordingAspect {

	pointcut callPointcut() : 
		call(* com.svashishtha.mocktail.metadata.Greeter.*(..));
	
	
	before() : callPointcut() {
		System.out.println("Before");
	}
	
	after() : callPointcut() {
		System.out.println("after");
	}
}
