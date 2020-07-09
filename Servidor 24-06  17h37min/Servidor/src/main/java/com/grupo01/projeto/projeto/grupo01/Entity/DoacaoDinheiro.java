package com.grupo01.projeto.projeto.grupo01.Entity;

public class DoacaoDinheiro {

    private float quantidade;
    private String data;
    private String metodo_pagamento;
    private long pessoa_id;

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

    public String getMetodo_pagamento() {
        return metodo_pagamento;
    }

    public void setMetodo_pagamento(String metodo_pagamento) {
        this.metodo_pagamento = metodo_pagamento;
    }

    public long getPessoa_id() {
        return pessoa_id;
    }

    public void setPessoa_id(long pessoa_id) {
        this.pessoa_id = pessoa_id;
    }

    @Override
    public String toString() {
        return "DoacaoDinheiro{" +
                "quantidade=" + quantidade +
                ", data='" + data + '\'' +
                ", metodo_pagamento='" + metodo_pagamento + '\'' +
                ", pessoa_id='" + pessoa_id + '\'' +
                '}';
    }
}
