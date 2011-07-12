package com.xebia.smok.aspect.recorder;


public class RecordingAspected {

	public int add(int no1, int no2) {
		System.out.println("Doing addition");
		return no1 + no2;
	}

	public void sub(int firstNo, int secondNo) {
		System.out.println("Doing substraction");
	}

	public void print() {
		System.out.println("In print");
	}

	public static void main(String[] args) {
		/*SmokContext smokContext = SmokContainer.getSmokContext();
		DirectFieldAccessor dfa = new DirectFieldAccessor(SmokContainer.getSmokContext());
		dfa.setPropertyValue("recordingDirectory","c:\\sandy\\recording\\test");*/
		
		RecordingAspected aspected = new RecordingAspected();
		aspected.add(13, 25);
		aspected.sub(13, 25);
		aspected.print();
	}

}
