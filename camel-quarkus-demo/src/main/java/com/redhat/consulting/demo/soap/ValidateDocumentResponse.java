
package com.redhat.consulting.demo.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.redhat.consulting.generated.soap.model.types.DocumentResponse;


/**
 * <p>Classe Java de validateDocumentResponse complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="validateDocumentResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="return" type="{http://com.redhat.consulting.demo.soap.model/types}DocumentResponse" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "validateDocumentResponse", propOrder = {
    "_return"
})
public class ValidateDocumentResponse {

    @XmlElement(name = "return")
    protected DocumentResponse _return;

    /**
     * Obtém o valor da propriedade return.
     * 
     * @return
     *     possible object is
     *     {@link DocumentResponse }
     *     
     */
    public DocumentResponse getReturn() {
        return _return;
    }

    /**
     * Define o valor da propriedade return.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentResponse }
     *     
     */
    public void setReturn(DocumentResponse value) {
        this._return = value;
    }

}
