
package com.redhat.consulting.demo.soap;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.redhat.consulting.demo.soap package. 
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

    private final static QName _ValidateDocument_QNAME = new QName("http://soap.demo.consulting.redhat.com/", "validateDocument");
    private final static QName _ValidateDocumentResponse_QNAME = new QName("http://soap.demo.consulting.redhat.com/", "validateDocumentResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.redhat.consulting.demo.soap
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ValidateDocument }
     * 
     */
    public ValidateDocument createValidateDocument() {
        return new ValidateDocument();
    }

    /**
     * Create an instance of {@link ValidateDocumentResponse }
     * 
     */
    public ValidateDocumentResponse createValidateDocumentResponse() {
        return new ValidateDocumentResponse();
    }

    /**
     * Create an instance of {@link DocumentRequest }
     * 
     */
    public DocumentRequest createDocumentRequest() {
        return new DocumentRequest();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateDocument }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ValidateDocument }{@code >}
     */
    @XmlElementDecl(namespace = "http://soap.demo.consulting.redhat.com/", name = "validateDocument")
    public JAXBElement<ValidateDocument> createValidateDocument(ValidateDocument value) {
        return new JAXBElement<ValidateDocument>(_ValidateDocument_QNAME, ValidateDocument.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateDocumentResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ValidateDocumentResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://soap.demo.consulting.redhat.com/", name = "validateDocumentResponse")
    public JAXBElement<ValidateDocumentResponse> createValidateDocumentResponse(ValidateDocumentResponse value) {
        return new JAXBElement<ValidateDocumentResponse>(_ValidateDocumentResponse_QNAME, ValidateDocumentResponse.class, null, value);
    }

}
