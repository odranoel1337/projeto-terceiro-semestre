package com.grupo01.projeto.projeto.grupo01.Entity;

public class Produto {

    private long id;
    private long id_campanha;
    private String tipo_produto;
    private String nome_produto;
    private String unidade;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId_campanha() {
        return id_campanha;
    }

    public void setId_campanha(long id_campanha) {
        this.id_campanha = id_campanha;
    }

    public String getTipo_produto() {
        return tipo_produto;
    }

    public void setTipo_produto(String tipo_produto) {
        this.tipo_produto = tipo_produto;
    }

    public String getNome_produto() {
        return nome_produto;
    }

    public void setNome_produto(String nome_produto) {
        this.nome_produto = nome_produto;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }
}
