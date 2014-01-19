package com.svashishtha.ws;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

import org.junit.BeforeClass;
import org.junit.Test;

import com.svashishtha.mocktail.tcpcache.TcpCache;

public class SimpleServiceClientTest {

	protected static URL wsdlURL;
	protected static QName serviceName;
	private static SimpleService port;

	static {
		serviceName = new QName("http://svashishtha.com/ws", "SimpleService");
	}

	@BeforeClass
	public static void setUp() throws Exception {
		
		wsdlURL = SimpleService_Service.WSDL_LOCATION;

		SimpleService_Service ss = new SimpleService_Service(wsdlURL, serviceName);
		port = ss.getP1();

		TcpCache tcpCache = new TcpCache(1234, "127.0.0.1", 8080, SimpleService_Service.class, "main");
		tcpCache.start();

		BindingProvider bp = (BindingProvider) port;
		Map<String, Object> context = bp.getRequestContext();
		context.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://localhost:1234/ws/p1");
	}

	@Test
	public void concatString() throws MalformedURLException {

        System.out.println("Invoking concat...");
        ConcatRequest _concat_parameters = new ConcatRequest();
        _concat_parameters.setS1("test");
        _concat_parameters.setS2(" success");
        java.lang.String _concat__return = port.concat(_concat_parameters);
        System.out.println("concat.result=" + _concat__return);
	}

}
