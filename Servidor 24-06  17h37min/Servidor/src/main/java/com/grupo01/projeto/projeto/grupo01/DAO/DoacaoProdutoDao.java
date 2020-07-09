package com.grupo01.projeto.projeto.grupo01.DAO;

import com.grupo01.projeto.projeto.grupo01.Entity.DoacaoProduto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Repository
public class DoacaoProdutoDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //----------------------------------------------------------------------//
    //-------------------- REALIZA DOAÇÃO DE UM PRODUTO --------------------//
    //----------------------------------------------------------------------//

    public DoacaoProduto fazerDoacao(String quantidade, String data, String pessoa_id, String campanha_id, String nome_produto) {
        String query = "INSERT INTO DOACAO_PRODUTO "
                + "(quantidade, data, pessoa_id, campanha_id, nome_produto)"
                + "VALUES"
                + "(?, ?, ?, ?, ?);" + " UPDATE Campanha SET Arrecadado = (SELECT SUM(QUANTIDADE) FROM Doacao_produto dp where DP.campanha_id = ?) WHERE id = ?";
        DoacaoProduto dp = new DoacaoProduto();
        try(Connection con = jdbcTemplate.getDataSource().getConnection()) {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, quantidade);
            ps.setString(2, data);
            ps.setString(3, pessoa_id);
            ps.setString(4, campanha_id);
            ps.setString(5, nome_produto);
            ps.setString(6, campanha_id);
            ps.setString(7, campanha_id);
            int rs = ps.executeUpdate();
            if(rs != 1) {
                return null;
            }
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Doação de produto realizada com sucesso.");
        return dp;
    }
}
