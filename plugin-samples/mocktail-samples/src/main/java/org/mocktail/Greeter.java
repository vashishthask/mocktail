package org.mocktail;
public class Greeter {

    public String greet() {
        System.out.println("A hello from normal class");
        return "bye";
    }

    public static void main(String[] args) {
        Greeter greeter = new Greeter();
        greeter.greet();
    }

}
