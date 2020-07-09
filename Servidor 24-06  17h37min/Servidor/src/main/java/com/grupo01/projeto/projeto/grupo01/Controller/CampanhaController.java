package com.grupo01.projeto.projeto.grupo01.Controller;

import com.grupo01.projeto.projeto.grupo01.Entity.Campanha;
import com.grupo01.projeto.projeto.grupo01.DAO.CampanhaDao;
import com.grupo01.projeto.projeto.grupo01.Retorno;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class CampanhaController {

    @Autowired
    CampanhaDao campanhaDao;

    //----------------------------------------------------------------------//
    //-------------- REALIZA O CADASTRO DE UMA NOVA CAMPANHA ---------------//
    //----------------------------------------------------------------------//

    @PostMapping("/campanhaCadastro")
    public Campanha incluirCampanha(@RequestBody Campanha c){
        return campanhaDao.incluirCampanha(c);
    }

    //----------------------------------------------------------------------//
    //---------------- REALIZA A EXCLUSÃO DE UMA CAMPANHA ------------------//
    //----------------------------------------------------------------------//

    @DeleteMapping("/campanhaDelete/{nome_campanha}")
    public Retorno deletarCampanha(@PathVariable String nome_campanha){
        System.out.println(nome_campanha);
        try{
            campanhaDao.excluirCampanha(nome_campanha);
            return new Retorno();
        } catch (Exception ex){
            ex.printStackTrace();
            return new Retorno(ex.getMessage());
        }
    }

    //----------------------------------------------------------------------//
    //------------- VERIFICA SE A CAMPANHA JÁ EXISTE NO BANCO --------------//
    //----------------------------------------------------------------------//

    @PostMapping("/verificarCampanha")
    public String existeCampanha(@RequestBody String nome_campanha){
        Campanha c;
        System.out.println("Nome campanha android: " + nome_campanha);
        JSONObject object = (JSONObject) (new JSONTokener(nome_campanha).nextValue());
        String nome_campanhaBanco = object.getString("nome_campanha");
        c = campanhaDao.verificarCampanhaJaExistente(nome_campanhaBanco);
        if(c == null){
            return "{\"Error\": true}";
        }
        return new JSONObject(c).toString();
    }

    //----------------------------------------------------------------------//
    //---------------- LISTA TODAS AS CAMPANHAS CADASTRADAS-----------------//
    //----------------------------------------------------------------------//

    @GetMapping("/listarCampanhas")
    public List<Campanha> listarCampanhas(){
        return campanhaDao.listarCampanhas();
    }

    //----------------------------------------------------------------------//
    //----------------- BUSCA DADOS DA CAMPANHA PELO NOME ------------------//
    //----------------------------------------------------------------------//

    @PostMapping("/buscarIdCampanhaPeloNome")
    public String listarCampanhasID(@RequestBody String nome_campanha){
        Campanha c;
        System.out.println("Nome campanha android: " + nome_campanha);
        JSONObject object = (JSONObject) (new JSONTokener(nome_campanha).nextValue());
        String nome_campanhaBanco = object.getString("nome_campanha");
        c = campanhaDao.listarCampanhasID(nome_campanhaBanco);
        if(c == null){
            return "{\"Error\": true}";
        }
        return new JSONObject(c).toString();
    }


    //----------------------------------------------------------------------//
    //------------- BUSCA O TANTO ARRECADADO EM TAL CAMPANHAA --------------//
    //----------------------------------------------------------------------//

    @GetMapping("/arrecadado/{id_campanha}")
    public Campanha arrecadadoCampanha(@PathVariable String id_campanha){
        return campanhaDao.arrecadado(id_campanha);
    }

}
