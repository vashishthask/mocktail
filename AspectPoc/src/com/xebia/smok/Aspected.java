package com.xebia.smok;

public class Aspected {
	
	public int add(int firstNo, int secondNo){
		System.out.println("Doing addition");
		return firstNo + secondNo;
	}
	
	public void sub(int firstNo, int secondNo){
		System.out.println("Doing substraction");
	}

	public void print(){
		System.out.println("In print");
	}
	
	
	public static void main(String[] args) {
		Aspected aspected = new Aspected();
		aspected.add(13, 25);
		aspected.sub(13, 25);
		aspected.print();
	}

}
