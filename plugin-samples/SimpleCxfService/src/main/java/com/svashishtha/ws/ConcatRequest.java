
package com.svashishtha.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="s1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="s2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "s1",
    "s2"
})
@XmlRootElement(name = "concatRequest")
public class ConcatRequest {

    @XmlElement(required = true)
    protected String s1;
    @XmlElement(required = true)
    protected String s2;

    /**
     * Gets the value of the s1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getS1() {
        return s1;
    }

    /**
     * Sets the value of the s1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setS1(String value) {
        this.s1 = value;
    }

    /**
     * Gets the value of the s2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getS2() {
        return s2;
    }

    /**
     * Sets the value of the s2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setS2(String value) {
        this.s2 = value;
    }

}
