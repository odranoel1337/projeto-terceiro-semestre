package com.grupo01.projeto.projeto.grupo01.Controller;

import com.grupo01.projeto.projeto.grupo01.DAO.ProdutoDao;
import com.grupo01.projeto.projeto.grupo01.Entity.Campanha;
import com.grupo01.projeto.projeto.grupo01.Entity.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProdutoController {

    @Autowired
    ProdutoDao produtoDao;

    //----------------------------------------------------------------------//
    //------------------- LISTA OS PRODUTOS DA CAMPANHA --------------------//
    //----------------------------------------------------------------------//

    @GetMapping("/listarProdutos/{id_campanha}")
    public List<Produto> listarProdutos(@PathVariable String id_campanha){
        System.out.println(id_campanha);
        return produtoDao.listarProdutos(id_campanha);
    }

    //----------------------------------------------------------------------//
    //---------------------- CADASTRA UM PRODUTO NOVO ----------------------//
    //----------------------------------------------------------------------//

    @PostMapping("/produtoCadastro")
    public Produto incluirProduto(@RequestBody Produto p){
        return produtoDao.inserirProduto(p);
    }
}
