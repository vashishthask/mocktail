
package in.malonus.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the in.malonus.ws package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ConcatResponse_QNAME = new QName("http://svashishtha.com/ws", "concatResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: in.malonus.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ConcatRequest }
     * 
     */
    public ConcatRequest createConcatRequest() {
        return new ConcatRequest();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://svashishtha.com/ws", name = "concatResponse")
    public JAXBElement<String> createConcatResponse(String value) {
        return new JAXBElement<String>(_ConcatResponse_QNAME, String.class, null, value);
    }

}
