
package com.redhat.consulting.generated.soap.model.types;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import com.redhat.consulting.demo.soap.DocumentRequest;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.redhat.consulting.generated.soap.model.types package. 
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

    private final static QName _Contracts_QNAME = new QName("http://com.redhat.consulting.demo.soap.model/types", "contracts");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.redhat.consulting.generated.soap.model.types
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DocumentResponse }
     * 
     */
    public DocumentResponse createDocumentResponse() {
        return new DocumentResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DocumentRequest }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DocumentRequest }{@code >}
     */
    @XmlElementDecl(namespace = "http://com.redhat.consulting.demo.soap.model/types", name = "contracts")
    public JAXBElement<DocumentRequest> createContracts(DocumentRequest value) {
        return new JAXBElement<DocumentRequest>(_Contracts_QNAME, DocumentRequest.class, null, value);
    }

}
