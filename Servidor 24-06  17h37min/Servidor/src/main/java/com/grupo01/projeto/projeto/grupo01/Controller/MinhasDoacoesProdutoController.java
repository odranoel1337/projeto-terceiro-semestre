package com.grupo01.projeto.projeto.grupo01.Controller;

import com.grupo01.projeto.projeto.grupo01.DAO.MinhasDoacoesProdutoDao;
import com.grupo01.projeto.projeto.grupo01.Entity.MinhaDoacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MinhasDoacoesProdutoController {

    @Autowired
    MinhasDoacoesProdutoDao minhasDoacoesProdutoDao;

    //----------------------------------------------------------------------//
    //------ LISTA TODAS AS DOAÇÕES DE PRODUTOS REALIZADO PELO USER --------//
    //----------------------------------------------------------------------//

    @GetMapping("/listarMinhasDoacoes/{pessoa_id}")
    public List<MinhaDoacao> listarMinhasDoacoes(@PathVariable String pessoa_id){
        return minhasDoacoesProdutoDao.buscarMinhasDoacoesProduto(pessoa_id);
    }

}
