#tcpcache

tcpcache is a fork on http://ws.apache.org/commons/tcpmon/ and can be used to cache/save tcp response.

From the original tcpmon source, GUI part is stripped and caching has been implemented. 

If you don't want caching ON, you can switch that off using configuration. In that case, 
it works as tcpmon without GUI which is useful if you want to put a proxy in between 
your tcp source and target. This is useful in order to print SOAP responses.

## Sample mocktailconfig.properties

	## by default caching is ON. In this case the library works as stripped down version of tcpmon
	## which could be used in test cases or java classes as proxy to print the tcp responses.
	## In order to switch caching OFF, change cachingOn to false
	cachingOn=true

	## directory where cached recording will be stored. default is taken as sec/test/resources
	recordingDir=src/test/resources
	
