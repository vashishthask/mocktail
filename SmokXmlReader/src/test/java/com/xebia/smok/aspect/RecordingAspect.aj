package com.xebia.smok.aspect;

public aspect RecordingAspect {
	pointcut callPointcut() : 
		call(* $className.*(..));
	
	
	Object around() : callPointcut() {
		System.out.println("I'll do the recording if the recorded file is not there");
		Object[] objects = thisJoinPoint.getArgs();
		for(Object object : objects) {
			System.out.println("Arguments " + object.toString());
		}
		Object returnValue = proceed();
		return returnValue;
	}
}
