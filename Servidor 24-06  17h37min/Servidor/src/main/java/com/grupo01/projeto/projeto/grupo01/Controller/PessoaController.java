package com.grupo01.projeto.projeto.grupo01.Controller;

import com.grupo01.projeto.projeto.grupo01.DAO.PessoaDao;
import com.grupo01.projeto.projeto.grupo01.Entity.Comentarios;
import com.grupo01.projeto.projeto.grupo01.Entity.Pessoa;
import com.grupo01.projeto.projeto.grupo01.Entity.Produto;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@RestController

public class PessoaController {
    private Map<String, Pessoa> pessoaMap = new TreeMap<>();

    @Autowired
    PessoaDao pessoaDao;

    //----------------------------------------------------------------------//
    //------------------------- INCLUIR UMA PESSOA -------------------------//
    //----------------------------------------------------------------------//

    @PostMapping("/pessoaCadastro")
    public Pessoa incluirPessoa (@RequestBody Pessoa p){
        return pessoaDao.incluirPessoa(p);
    }

    //----------------------------------------------------------------------//
    //---------------------- VALIDAÇÃO LOGIN PESSOA ------------------------//
    //----------------------------------------------------------------------//

    @PostMapping("/pessoalogin")
    public String loginPessoa(@RequestBody String loginEsenha) {
        Pessoa p;
        System.out.println("Login e senha do android: " + loginEsenha);
        JSONObject object = (JSONObject) (new JSONTokener(loginEsenha)).nextValue();
        String loginPessoa = object.getString("login");
        String senhaPessoaa = object.getString("senha");
        System.out.println("Login do banco: " + loginPessoa);
        System.out.println("Senha do banco: " + senhaPessoaa);
        p = pessoaDao.login(loginPessoa, senhaPessoaa);
        if(p == null){
            return "{\"Error\": true}";
        }
        return new JSONObject(p).toString();
    }

    //----------------------------------------------------------------------//
    //------- VERIFICAR SE JA EXISTE CPF/CNPJ E EMAIL PESSOA ---------------//
    //----------------------------------------------------------------------//

    @PostMapping("/pessoaVerificaCPFCNPJ")
    public String existeCPF_CNPJ(@RequestBody String cpf_cnpj_email){
        Pessoa p;
        System.out.println("CPF/CNPJ do android: " + cpf_cnpj_email);
        JSONObject object = (JSONObject) (new JSONTokener(cpf_cnpj_email).nextValue());
        String cpf_cnpjBanco = object.getString("cpf_cnpj");
        String emailBanco = object.getString("email");
        System.out.println("CPF/CNPJ banco: " + cpf_cnpjBanco);
        System.out.println("Email banco: " + emailBanco);
        p = pessoaDao.verificaCPF_CNPJ(cpf_cnpjBanco, emailBanco);
        if(p == null){
            return "{\"Error\": true}";
        }
        return new JSONObject(p).toString();
    }
}
