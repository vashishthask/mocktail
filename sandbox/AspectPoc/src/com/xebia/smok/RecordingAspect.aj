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
		
		if ( thisJoinPoint.getThis() != null) {
            System.out.println("getThis()=" + thisJoinPoint.getThis());
        }
        if ( thisJoinPoint.getTarget() != null) {
            System.out.println("getTarget()=" + thisJoinPoint.getTarget());
        }
        System.out.println("toLongString()=" + thisJoinPoint.getStaticPart()); 
		return returnValue;
	}
	
	
	/*after() : callPointcut() {
		System.out.println("after");
	}*/
}
