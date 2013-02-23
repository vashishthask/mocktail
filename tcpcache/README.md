tcpcache is a fork on http://ws.apache.org/commons/tcpmon/ and can be used to cache/save tcp response.

From the original tcpmon source, GUI part is stripped and caching has been implemented. If you don't 
want caching ON, you can switch that off using configuration. 

In that case, it works as tcpmon without GUI which is useful if you want to put a proxy in between 
your source and target which is used in order to print the request and responses.
