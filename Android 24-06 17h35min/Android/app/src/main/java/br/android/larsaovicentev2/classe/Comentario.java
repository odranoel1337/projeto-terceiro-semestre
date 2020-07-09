package br.android.larsaovicentev2.classe;

public class Comentario {

    private String nome_pessoa;
    private String comentario;
    private String data_comentario;
    private String horario_comentario;
    private int id_campanha;

    public Comentario(){}

    public Comentario(String nome_pessoa, String comentario, String data_comentario, String horario_comentario) {
        this.nome_pessoa = nome_pessoa;
        this.comentario = comentario;
        this.data_comentario = data_comentario;
        this.horario_comentario = horario_comentario;
    }

    public String getNome_pessoa() {
        return nome_pessoa;
    }

    public void setNome_pessoa(String nome_pessoa) {
        this.nome_pessoa = nome_pessoa;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getData_comentario() {
        return data_comentario;
    }

    public void setData_comentario(String data_comentario) {
        this.data_comentario = data_comentario;
    }

    public String getHorario_comentario() {
        return horario_comentario;
    }

    public void setHorario_comentario(String horario_comentario) {
        this.horario_comentario = horario_comentario;
    }

    public int getId_campanha() {
        return id_campanha;
    }

    public void setId_campanha(int id_campanha) {
        this.id_campanha = id_campanha;
    }



}
