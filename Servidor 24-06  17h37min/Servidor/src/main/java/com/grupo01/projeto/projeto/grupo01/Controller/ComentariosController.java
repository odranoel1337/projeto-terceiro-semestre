package com.grupo01.projeto.projeto.grupo01.Controller;

import com.grupo01.projeto.projeto.grupo01.DAO.ComentariosDAO;
import com.grupo01.projeto.projeto.grupo01.Entity.Comentarios;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ComentariosController {

    @Autowired
    ComentariosDAO comentariosDAO;

    //----------------------------------------------------------------------//
    //------------------------- ENVIA UM COMENTARIO ------------------------//
    //----------------------------------------------------------------------//

    @PostMapping("/comentariosEnvio")
    public Comentarios enviarComentario(@RequestBody Comentarios co){
        System.out.println(co.getMensagem());
        return comentariosDAO.incluirComentario(co);
    }

    //----------------------------------------------------------------------//
    //--------------- LISTA TODOS OS COMENTARIOS DA CAMPANHA ---------------//
    //----------------------------------------------------------------------//

    @GetMapping("/listarComentariosId/{id_campanha}")
    public  List<Comentarios> listarComentarios(@PathVariable String id_campanha){
        return comentariosDAO.listarComentariosId(id_campanha);
    }

}
