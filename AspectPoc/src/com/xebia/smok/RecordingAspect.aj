package com.xebia.smok;


public aspect RecordingAspect {

	pointcut aroundMethodPointcut() : 
		call(* Aspected.*(..));
	
	
	/*before() : callPointcut() {
		Object[] objects = thisJoinPoint.getArgs();
		for(Object object : objects) {
			System.out.println("Arguments " + object.toString());
		}
		System.out.println("Before");
	}*/
	
	Object around() : aroundMethodPointcut() {
		System.out.println("Around Before");
		Object[] objects = thisJoinPoint.getArgs();
		for(Object object : objects) {
			System.out.println("Arguments " + object.toString());
		}
		Object returnValue = proceed();
		System.out.println("Around After");
		return returnValue;
	}
	
	
	/*after() : callPointcut() {
		System.out.println("after");
	}*/
}
