package com.grupo01.projeto.projeto.grupo01.Entity;

import java.io.Serializable;
import java.util.Objects;

public class Pessoa implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String nome;
    private String sobrenome_nome_fantasia;
    private String email;
    private String cpf_cnpj;
    private String senha;
    private String tipo_pessoa;

    public Pessoa(){
    }

    public Pessoa(String nome, String sobrenome_nome_fantasia, String email, String cpf_cnpj, String senha, String tipo_pessoa) {
        this.nome = nome;
        this.sobrenome_nome_fantasia = sobrenome_nome_fantasia;
        this.email = email;
        this.cpf_cnpj = cpf_cnpj;
        this.senha = senha;
        this.tipo_pessoa = tipo_pessoa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome_nome_fantasia() {
        return sobrenome_nome_fantasia;
    }

    public void setSobrenome_nome_fantasia(String sobrenome_nome_fantasia) {
        this.sobrenome_nome_fantasia = sobrenome_nome_fantasia;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf_cnpj() {
        return cpf_cnpj;
    }

    public void setCpf_cnpj(String cpf_cnpj) {
        this.cpf_cnpj = cpf_cnpj;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTipo_pessoa() {
        return tipo_pessoa;
    }

    public void setTipo_pessoa(String tipo_pessoa) {
        this.tipo_pessoa = tipo_pessoa;
    }


}
