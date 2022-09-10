package in.malonus.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(endpointInterface = "in.malonus.ws.SimpleService")
public class SimpleServiceImpl implements SimpleService {

	@WebResult(name = "concatResponse", targetNamespace = "http://svashishtha.com/ws", partName = "parameters")
	@WebMethod(action = "http://svashishtha.com/ws/NewOperation")
    public String concat(@WebParam(partName = "parameters", name = "concatRequest", targetNamespace = "http://svashishtha.com/ws") ConcatRequest parameters) {
        return parameters.getS1() + parameters.getS2();
    }

}
