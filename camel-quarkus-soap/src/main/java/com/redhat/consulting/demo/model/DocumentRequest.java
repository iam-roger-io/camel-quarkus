package com.redhat.consulting.demo.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="contracts", namespace = "http://com.redhat.consulting.demo.soap.model/types")
public class DocumentRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -240661877111459264L;
	
	@XmlElement(required = true, nillable = false)		
	private String number;
	
	@XmlElement(required = true, nillable = false)	
	private String type;
	
	@XmlElement(required = true, nillable = false)	
	private String country;
	
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
}
