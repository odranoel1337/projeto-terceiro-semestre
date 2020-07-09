package br.android.larsaovicentev2.classe;


public class Campanha {

    private long id;
    private String nome_campanha;
    private String descricao;
    private String meta;
    private String arrecadado;
    private String dataInicio;
    private String dataFim;
    private String tipo_doacao;

    public Campanha(String id, String nome_campanha, String descricao, String meta, String arrecadado, String dataInicio, String dataFim, String tipo_doacao) {
        this.nome_campanha = nome_campanha;
        this.descricao = descricao;
        this.meta = meta;
        this.arrecadado = arrecadado;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.tipo_doacao = tipo_doacao;

    }

    public Campanha(String nome_campanha, String descricao, String meta, String arrecadado, String dataInicio, String dataFim, String tipo_doacao) {
        this.nome_campanha = nome_campanha;
        this.descricao = descricao;
        this.meta = meta;
        this.arrecadado = arrecadado;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.tipo_doacao = tipo_doacao;
    }

    public Campanha() {
    }

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

    public String getArrecadado() {
        return arrecadado;
    }

    public void setArrecadado(String arrecadado) {
        this.arrecadado = arrecadado;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getDataFim() {
        return dataFim;
    }

    public void setDataFim(String dataFim) {
        this.dataFim = dataFim;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public String getTipo_doacao() {
        return tipo_doacao;
    }

    public void setTipo_doacao(String tipo_doacao) {
        this.tipo_doacao = tipo_doacao;
    }
}



