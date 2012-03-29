

import static org.junit.Assert.assertEquals;

import org.junit.Test;



public class GreeterTest{
	
	@Test
	public void shouldGreet(){
		String message = new Greeter().SayHello();
		
		assertEquals("bye", message);
	}
}
