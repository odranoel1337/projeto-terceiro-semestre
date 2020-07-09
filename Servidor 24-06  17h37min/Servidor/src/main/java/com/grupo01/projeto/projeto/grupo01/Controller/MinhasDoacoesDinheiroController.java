package com.grupo01.projeto.projeto.grupo01.Controller;

import com.grupo01.projeto.projeto.grupo01.DAO.MinhasDoacoesDinheiroDao;
import com.grupo01.projeto.projeto.grupo01.Entity.MinhaDoacaoDinheiro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MinhasDoacoesDinheiroController {

    @Autowired
    MinhasDoacoesDinheiroDao dao;

    //----------------------------------------------------------------------//
    //------ LISTA TODAS AS DOAÇÕES EM DINHEIRO REALIZADO PELO USER --------//
    //----------------------------------------------------------------------//

    @GetMapping("/listarMinhasDoacoesDinheiro/{pessoa_id}")
    public List<MinhaDoacaoDinheiro> listarMinhaDoacaoDinheiro(@PathVariable String pessoa_id){
        return dao.buscarMinhasDoacoesDinheiro(pessoa_id);
    }
}
