package com.grupo01.projeto.projeto.grupo01.Controller;

import com.grupo01.projeto.projeto.grupo01.DAO.DoacaoDinheiroDao;
import com.grupo01.projeto.projeto.grupo01.DAO.DoacaoProdutoDao;
import com.grupo01.projeto.projeto.grupo01.Entity.DoacaoDinheiro;
import com.grupo01.projeto.projeto.grupo01.Entity.DoacaoProduto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class DoacaoDinheiroController {

    @Autowired
    DoacaoDinheiroDao doacaoDinheiroDao;
    @Autowired
    DoacaoProdutoDao doacaoProdutoDao;

    //----------------------------------------------------------------------//
    //-------------------- REALIZA A DOAÇÃO EM DINHEIRO --------------------//
    //----------------------------------------------------------------------//

    @PostMapping("/doarDinheiro")
    public DoacaoDinheiro doarDinheiro(@RequestBody DoacaoDinheiro d){
        System.out.println(d.getMetodo_pagamento());
        System.out.println(d.toString());
        return doacaoDinheiroDao.fazerDoacao(d);
    }
}
