package org.mocktail;


public aspect RecordingAspect {

	pointcut callPointcut() : 
		call(* org.mocktail.Greeter.*(..));
	
	
	before() : callPointcut() {
		System.out.println("Before");
	}
	
	after() : callPointcut() {
		System.out.println("after");
	}
}
