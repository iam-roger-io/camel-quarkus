package com.redhat.consulting.demo.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DocumentResponse", namespace = "http://com.redhat.consulting.demo.soap.model/types")
public class DocumentResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -240661877111459264L;
	
	@XmlElement(name="valid")	
	private boolean isValid;
	
	@XmlElement(name="message")
	private String message;

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
}
