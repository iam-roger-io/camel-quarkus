package com.redhat.consulting.demo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name = "tb_pessoa")
@NamedQuery(name = "findAll", query = "select a from PessoaModel a")
public class PessoaModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5481721059327918490L;
	
    @Id
    @Column(name="pessoaID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
    @Column(name="nome", nullable = false)
    private String nome;
	
    @Column(name="cpf_cnpj", nullable = false)
    private String cpf;
    
    @Column(name="email", nullable = true)
    private String email;
    
    @JsonInclude(Include.NON_NULL)
    @Column(name="verification_code", nullable = true)
	private String verificationCode;
	
    @JsonInclude(Include.NON_NULL)
    @Column(name="verification_date", nullable = true)    
    private String verificationDate;
	
    @Column(name="tipo_pessoa", nullable = true)
    @Enumerated(EnumType.STRING)
    private TipoPessoa tipoPessoa;
    
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
	public TipoPessoa getTipoPessoa() {
		return tipoPessoa;
	}
	public void setTipoPessoa(TipoPessoa tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}
	
}
