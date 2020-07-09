package com.grupo01.projeto.projeto.grupo01.DAO;

import com.grupo01.projeto.projeto.grupo01.Entity.MinhaDoacaoDinheiro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.integration.IntegrationProperties;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MinhasDoacoesDinheiroDao {

    @Autowired
    JdbcTemplate jdbcTemplate;


    //----------------------------------------------------------------------//
    //------ LISTA TODAS AS DOAÇÕES EM DINHEIRO REALIZADO PELO USER --------//
    //----------------------------------------------------------------------//

    public List<MinhaDoacaoDinheiro> buscarMinhasDoacoesDinheiro(String pessoa_id){
        String sql ="select metodo_pagamento, data,quantidade from doacao_dinheiro where pessoa_id=?";
        List<MinhaDoacaoDinheiro> listaDoacaoDinheiro = new ArrayList<>();

        try(Connection con = jdbcTemplate.getDataSource().getConnection()){
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, pessoa_id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                MinhaDoacaoDinheiro minhaDoacaoDinheiro= new MinhaDoacaoDinheiro();
                minhaDoacaoDinheiro.setMetodo_pagamento(rs.getString("metodo_pagamento"));
                minhaDoacaoDinheiro.setData(rs.getString("data"));
                minhaDoacaoDinheiro.setQuantidade(rs.getString("quantidade"));
                listaDoacaoDinheiro.add(minhaDoacaoDinheiro);
            }
            System.out.println("Minhas doacoes listadas com sucesso.");
            rs.close();
            ps.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return listaDoacaoDinheiro;
    }

}
