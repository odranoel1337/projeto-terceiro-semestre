package com.grupo01.projeto.projeto.grupo01.Controller;

import com.grupo01.projeto.projeto.grupo01.DAO.DoacaoProdutoDao;
import com.grupo01.projeto.projeto.grupo01.Entity.DoacaoProduto;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DoacaoProdutoController {

    @Autowired
    DoacaoProdutoDao doacaoProdutoDao;

    //----------------------------------------------------------------------//
    //-------------------- REALIZA DOAÇÃO DE UM PRODUTO --------------------//
    //----------------------------------------------------------------------//

    @PostMapping("/doarProduto")
    public String doarProduto(@RequestBody String dadosDoacaoProduto){
        System.out.println(dadosDoacaoProduto);
        DoacaoProduto dp;
        JSONObject object = (JSONObject) (new JSONTokener(dadosDoacaoProduto).nextValue());
        String quantidadeBanco = object.getString("quantidade");
        String dataBanco = object.getString("data");
        String pessoa_idBanco = object.getString("pessoa_id");
        String campanha_idBanco = object.getString("campanha_id");
        String nome_produtoBanco = object.getString("nome_produto");

        dp = doacaoProdutoDao.fazerDoacao(quantidadeBanco, dataBanco, pessoa_idBanco, campanha_idBanco, nome_produtoBanco);
        if(dp == null){
            return "{\"Error\": true}";
        }
        return new JSONObject(dp).toString();
    }
}
