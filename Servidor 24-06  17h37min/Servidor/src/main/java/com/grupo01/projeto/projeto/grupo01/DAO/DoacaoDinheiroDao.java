package com.grupo01.projeto.projeto.grupo01.DAO;

import com.grupo01.projeto.projeto.grupo01.Entity.DoacaoDinheiro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;

@Repository
public class DoacaoDinheiroDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //----------------------------------------------------------------------//
    //-------------------- REALIZA A DOAÇÃO EM DINHEIRO --------------------//
    //----------------------------------------------------------------------//

    public DoacaoDinheiro fazerDoacao(DoacaoDinheiro dp) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = jdbcTemplate.getDataSource().getConnection();
            ps = con.prepareStatement("INSERT INTO DOACAO_DINHEIRO "
                    + "(quantidade, data, pessoa_id, metodo_pagamento)"
                    + "VALUES"
                    + "(?, ?, ?, ?)");
            ps.setFloat(1, dp.getQuantidade());
            ps.setString(2, dp.getData());
            ps.setLong(3, dp.getPessoa_id());
            ps.setString(4, dp.getMetodo_pagamento());
            ps.executeUpdate();

            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Doação realizada com sucesso.");
        return dp;
    }

}
