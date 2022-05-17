package com.redhat.consulting.demo.model;

import java.io.Serializable;

public class Verification implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6524154925930142862L;
	private String verificationCode;
	private String verificationDate;
	
	public String getVerificationCode() {
		return verificationCode;
	}
	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}
	public String getVerificationDate() {
		return verificationDate;
	}
	public void setVerificationDate(String verificationDate) {
		this.verificationDate = verificationDate;
	}	
	
}
