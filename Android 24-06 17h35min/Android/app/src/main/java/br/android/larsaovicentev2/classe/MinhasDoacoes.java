package br.android.larsaovicentev2.classe;

public class MinhasDoacoes {
    private String titulo_campanha;
    private String data_doacao;
    private String nome_produto;
    private String unidade_produto;
    private String quantidade_doada;

    public MinhasDoacoes() {
    }

    public MinhasDoacoes(String titulo_campanha, String data_doacao, String nome_produto, String unidade_produto, String quantidade_doada) {
        this.titulo_campanha = titulo_campanha;
        this.data_doacao = data_doacao;
        this.nome_produto = nome_produto;
        this.unidade_produto = unidade_produto;
        this.quantidade_doada = quantidade_doada;
    }

    public String getTitulo_campanha() {
        return titulo_campanha;
    }



    public void setTitulo_campanha(String titulo_campanha) {
        this.titulo_campanha = titulo_campanha;
    }

    public String getNome_produto() {
        return nome_produto;
    }

    public void setNome_produto(String nome_produto) {
        this.nome_produto = nome_produto;
    }

    public String getData_doacao() {
        return data_doacao;
    }

    public void setData_doacao(String data_doacao) {
        this.data_doacao = data_doacao;
    }

    public String getUnidade_produto() {
        return unidade_produto;
    }

    public void setUnidade_produto(String unidade_produto) {
        this.unidade_produto = unidade_produto;
    }

    public String getQuantidade_doada() {
        return quantidade_doada;
    }

    public void setQuantidade_doada(String quantidade_doada) {
        this.quantidade_doada = quantidade_doada;
    }
}
