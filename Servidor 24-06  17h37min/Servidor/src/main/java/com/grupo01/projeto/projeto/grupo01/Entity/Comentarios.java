package com.grupo01.projeto.projeto.grupo01.Entity;

public class Comentarios {

    private int id;
    private String mensagem;
    private String data_mensagem;
    private String horario_mensagem;
    private String nome_pessoa;
    private int id_campanha;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getData_mensagem() {
        return data_mensagem;
    }

    public void setData_mensagem(String data_mensagem) {
        this.data_mensagem = data_mensagem;
    }

    public String getHorario_mensagem() {
        return horario_mensagem;
    }

    public void setHorario_mensagem(String horario_mensagem) {
        this.horario_mensagem = horario_mensagem;
    }

    public String getNome_pessoa() {
        return nome_pessoa;
    }

    public void setNome_pessoa(String nome_pessoa) {
        this.nome_pessoa = nome_pessoa;
    }

    public int getId_campanha() {
        return id_campanha;
    }

    public void setId_campanha(int id_campanha) {
        this.id_campanha = id_campanha;
    }
}
