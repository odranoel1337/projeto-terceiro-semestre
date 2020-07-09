package com.grupo01.projeto.projeto.grupo01.DAO;

import com.grupo01.projeto.projeto.grupo01.Entity.MinhaDoacao;
import com.grupo01.projeto.projeto.grupo01.Entity.MinhaDoacaoDinheiro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MinhasDoacoesProdutoDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //----------------------------------------------------------------------//
    //------ LISTA TODAS AS DOAÇÕES DE PRODUTOS REALIZADO PELO USER --------//
    //----------------------------------------------------------------------//

    public List<MinhaDoacao> buscarMinhasDoacoesProduto(String pessoa_id){
        String query = "SELECT d.quantidade,d.data,d.nome_produto,c.nome_campanha,p.unidade FROM DOACAO_PRODUTO d INNER JOIN\n" +
                "(campanha  c INNER JOIN produto p\n" +
                "ON c.tipo_doacao = p.tipo_produto)\n" +
                "ON d.campanha_id = c.id AND d. nome_produto = p.nome_produto  where d.pessoa_id = ?";

        List<MinhaDoacao> listaMinhasDoacoes = new ArrayList<>();
        try(Connection con = jdbcTemplate.getDataSource().getConnection()){
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, pessoa_id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                MinhaDoacao mD = new MinhaDoacao();
                mD.setQuantidade(rs.getString("quantidade"));
                mD.setData_doacao(rs.getString("data"));
                mD.setNome_produto(rs.getString("nome_produto"));
                mD.setNome_campanha(rs.getString("nome_campanha"));
                mD.setUnidade(rs.getString("unidade"));

                listaMinhasDoacoes.add(mD);
            }
            System.out.println("Minhas doacoes listadas com sucesso.");
            rs.close();
            ps.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return listaMinhasDoacoes;
    }


}

