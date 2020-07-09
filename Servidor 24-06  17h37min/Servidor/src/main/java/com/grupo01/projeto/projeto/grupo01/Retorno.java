package com.grupo01.projeto.projeto.grupo01;

public class Retorno {
    public String status;
    public String mensagemErro;
    public Object object;

    public Retorno(Object obj) {
        this.status = "Ok";
        this.object = obj;
    }

    public Retorno(String erro) {
        this.status = "Erro";
        this.mensagemErro = erro;
    }
    
    public Retorno(){
        this.status = "Ok";
    }

}
