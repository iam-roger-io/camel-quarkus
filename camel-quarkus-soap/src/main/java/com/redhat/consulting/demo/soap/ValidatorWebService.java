package com.redhat.consulting.demo.soap;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElement;

import com.redhat.consulting.demo.model.DocumentRequest;
import com.redhat.consulting.demo.model.DocumentResponse;


@WebService
public interface ValidatorWebService {

    @WebMethod
    DocumentResponse validateDocument(@XmlElement(required=true) @WebParam(name = "request") DocumentRequest request);
}