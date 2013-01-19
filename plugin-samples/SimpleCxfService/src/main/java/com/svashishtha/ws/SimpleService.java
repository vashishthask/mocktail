package com.svashishtha.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 2.6.2
 * 2012-12-30T12:33:08.151+08:00
 * Generated source version: 2.6.2
 * 
 */
@WebService(targetNamespace = "http://svashishtha.com/ws", name = "SimpleService")
@XmlSeeAlso({ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface SimpleService {

    @WebResult(name = "concatResponse", targetNamespace = "http://svashishtha.com/ws", partName = "parameters")
    @WebMethod(action = "http://svashishtha.com/ws/NewOperation")
    public java.lang.String concat(
        @WebParam(partName = "parameters", name = "concatRequest", targetNamespace = "http://svashishtha.com/ws")
        ConcatRequest parameters
    );
}
