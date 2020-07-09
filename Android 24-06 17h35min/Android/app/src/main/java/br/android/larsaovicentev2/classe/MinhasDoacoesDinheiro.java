package br.android.larsaovicentev2.classe;

public class MinhasDoacoesDinheiro {

    private String metodo_pagamento;
    private String quantidade_doada;
    private String data_doacao;

    public String getMetodo_pagamento() {
        return metodo_pagamento;
    }

    public void setMetodo_pagamento(String metodo_pagamento) {
        this.metodo_pagamento = metodo_pagamento;
    }

    public String getQuantidade_doada() {
        return quantidade_doada;
    }

    public void setQuantidade_doada(String quantidade) {
        this.quantidade_doada = quantidade;
    }

    public String getData_doacao() {
        return data_doacao;
    }

    public void setData_doacao(String data_doacao) {
        this.data_doacao = data_doacao;
    }
}
