package com.grupo01.projeto.projeto.grupo01.Entity;

public class DoacaoProduto {

    private long id;
    private float quantidade;
    private String data;
    private long pessoa_id;
    private long campanha_id;
    private String nome_produto;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(float quantidade) {
        this.quantidade = quantidade;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public long getPessoa_id() {
        return pessoa_id;
    }

    public void setPessoa_id(long pessoa_id) {
        this.pessoa_id = pessoa_id;
    }

    public long getCampanha_id() {
        return campanha_id;
    }

    public void setCampanha_id(long campanha_id) {
        this.campanha_id = campanha_id;
    }

    public String getNome_produto() {
        return nome_produto;
    }

    public void setNome_produto(String nome_produto) {
        this.nome_produto = nome_produto;
    }
}
