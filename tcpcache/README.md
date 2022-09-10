#tcpcache

tcpcache is a fork on http://ws.apache.org/commons/tcpmon/ and can be used to cache/save tcp response.

From the original tcpmon source, GUI part is stripped. "tcpcache" is useful in following scenarios: 

* **Caching:**
SOAP or TCP response can be cached. In that case configuration (mocktailconfig.properties) need to have
"chachingOn" property as "true".

* **Printing SOAP response:**
If you don't want caching ON, you can switch that off using configuration. In that case, 
it works as tcpmon without GUI which is useful to print SOAP responses.

### Sample tcpcache.properties

	## by default caching is ON. In this case the library works as stripped down version of tcpmon
	## which could be used in test cases or java classes as proxy to print the tcp responses.
	## In order to switch caching OFF, change cachingOn to false
	cachingOn=true

	## directory where cached recording will be stored. default is taken as sec/test/resources
	recordingDir=src/test/resources
	
### Sample usage of tcpcache in CXF based web service client.

	SimpleService_Service ss = new SimpleService_Service(wsdlURL,
		SERVICE_NAME);
	SimpleService port = ss.getP1();

	// for tcpcache to work -- start
	TcpCache tcpCache = new TcpCache(1234, "127.0.0.1", 8080,
		SimpleService_Service.class, "main", CacheMode.RECORDING_NEW);
	BindingProvider bp = (BindingProvider) port;
	Map<String, Object> context = bp.getRequestContext();
	context.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
		"http://localhost:1234/ws/p1");
	// for tcpcache to work -- end

	{
	    System.out.println("Invoking concat...");
	    ConcatRequest _concat_parameters = new ConcatRequest();
	    _concat_parameters.setS1("First Value");
	    _concat_parameters.setS2("Second Value");
	    java.lang.String _concat__return = port.concat(_concat_parameters);
	    System.out.println("concat.result=" + _concat__return);
	}
	
### Sample usage of tcpcache in Axis2 based web service client
	
	//SimpleServiceStub service = new SimpleServiceStub("http://localhost:8080/axis2/services/SimpleService");
		
	//for tcpcache to work - start
	TcpCache tcpCache = new TcpCache(1234, "127.0.0.1", 8080, SimpleClient.class , "main", CacheMode.RECORDING_NEW);
	SimpleServiceStub service = new SimpleServiceStub("http://localhost:1234/axis2/services/SimpleService");
	//for tcpcache to work - end
		
	ConcatRequest request = new ConcatRequest();
	request.setS1("abc");
	request.setS2("123");
	ConcatResponse response = service.concat(request);
	System.out.println(response.getConcatResponse());
