package com.ttdev.ss;

import org.apache.cxf.tools.wsdlto.WSDLToJava;

public class CodeGenerator {

	public static void main(String[] args) {
		WSDLToJava.main(new String[] { "-client", "-d", "src/main/java",
				"file:src/main/resources/SimpleService.wsdl" });
	}
}
