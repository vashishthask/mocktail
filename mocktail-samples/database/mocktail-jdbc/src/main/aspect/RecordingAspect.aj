package in.malonus.mocktail.metadata;


public aspect RecordingAspect {

	pointcut callPointcut() : 
		call(* in.malonus.mocktail.metadata.Greeter.*(..));
	
	
	before() : callPointcut() {
		System.out.println("Before");
	}
	
	after() : callPointcut() {
		System.out.println("after");
	}
}
