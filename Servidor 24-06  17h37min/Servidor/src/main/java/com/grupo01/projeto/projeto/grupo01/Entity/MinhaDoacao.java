package com.grupo01.projeto.projeto.grupo01.Entity;

public class MinhaDoacao {
    private String nome_campanha;
    private String data_doacao;
    private String nome_produto;
    private String unidade;
    private String quantidade;

    public MinhaDoacao() {
    }

    public MinhaDoacao(String nome_campanha, String data_doacao, String nome_produto, String unidade, String quantidade) {
        this.nome_campanha = nome_campanha;
        this.data_doacao = data_doacao;
        this.nome_produto = nome_produto;
        this.unidade = unidade;
        this.quantidade = quantidade;
    }

    public String getNome_campanha() {
        return nome_campanha;
    }

    public void setNome_campanha(String nome_campanha) {
        this.nome_campanha = nome_campanha;
    }

    public String getData_doacao() {
        return data_doacao;
    }

    public void setData_doacao(String data_doacao) {
        this.data_doacao = data_doacao;
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

    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }
}
