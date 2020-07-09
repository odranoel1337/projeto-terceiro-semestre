package com.grupo01.projeto.projeto.grupo01.Entity;

import java.util.ArrayList;
import java.util.List;

public class Campanha {

    private long id;
    private String nome_campanha;
    private String descricao;
    private float meta;
    private float arrecadado;
    private String data_inicio;
    private String data_fim;
    private String tipo_doacao;
    private List<Produto> produtos = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome_campanha() {
        return nome_campanha;
    }

    public void setNome_campanha(String nome_campanha) {
        this.nome_campanha = nome_campanha;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public float getMeta() {
        return meta;
    }

    public void setMeta(float meta) {
        this.meta = meta;
    }

    public float getArrecadado() {
        return arrecadado;
    }

    public void setArrecadado(float arrecadado) {
        this.arrecadado = arrecadado;
    }

    public String getData_inicio() {
        return data_inicio;
    }

    public void setData_inicio(String data_inicio) {
        this.data_inicio = data_inicio;
    }

    public String getData_fim() {
        return data_fim;
    }

    public void setData_fim(String data_fim) {
        this.data_fim = data_fim;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public String getTipo_doacao() {
        return tipo_doacao;
    }

    public void setTipo_doacao(String tipo_doacao) {
        this.tipo_doacao = tipo_doacao;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }
}
