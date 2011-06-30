import com.xebia.smok.SmokContext;





public class Greeter {

	public String SayHello(){
		System.out.println("A hello from normal class");
		return "hi";
	}
	
	public static void main(String[] args) {
		SmokContext context = SmokContext.getSmokContext("C:\\sandy\\recording");
		Greeter greeter = new Greeter();
		greeter.SayHello();
	}
	
}
