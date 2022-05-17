package com.redhat.consulting.demo.model;

import java.io.Serializable;

public class PaisModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3562623590118554426L;
	
	private String codigo;
	private String nome;
	
	public PaisModel() {
		super();	
	}
	public PaisModel(String codigo, String nome) {
		super();
		this.codigo = codigo;
		this.nome = nome;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
