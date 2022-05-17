package com.redhat.consulting.demo.model.rest;

import java.io.Serializable;


public class Person implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5481721059327918490L;
	

	private int id;
    private String nome;
    private String cpf;
    private String email;
        
    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}	
	
}
