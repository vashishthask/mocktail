package com.xebia.smok;


public aspect RecordingAspect {

	pointcut callPointcut() : 
		call(* Aspected.*(..));
	
	before() : callPointcut() {
		Object[] objects = thisJoinPoint.getArgs();
		for(Object object : objects) {
			System.out.println("Arguments " + object.toString());
		}
		System.out.println("Before");
	}
	
	after() : callPointcut() {
		System.out.println("after");
	}
}
