package com.xebia.smok;


public aspect RecordingAspect {

	pointcut callPointcut() : 
		call(* Aspected.*(..));
	
	pointcut callPointcut2() : 
		call(* SecondAspected.*(..));
	
	before() : callPointcut() {
		Object[] objects = thisJoinPoint.getArgs();
		for(Object object : objects) {
			System.out.println("Arguments " + object.toString());
		}
		System.out.println("Before");
	}
	
	before() : callPointcut2() {
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
