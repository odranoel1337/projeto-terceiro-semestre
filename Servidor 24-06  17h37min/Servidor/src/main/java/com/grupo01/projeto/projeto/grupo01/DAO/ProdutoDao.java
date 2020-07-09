package com.grupo01.projeto.projeto.grupo01.DAO;
import com.grupo01.projeto.projeto.grupo01.Entity.Campanha;
import com.grupo01.projeto.projeto.grupo01.Entity.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProdutoDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    //----------------------------------------------------------------------//
    //------------------- LISTA OS PRODUTOS DA CAMPANHA --------------------//
    //----------------------------------------------------------------------//

    public List<Produto> listarProdutos(String id_campanha){
        String query = "select p.* FROM campanha c left join produto p ON p.tipo_produto = c.tipo_doacao where c.id = ?";

        List<Produto> listaProdutos = new ArrayList<>();
        try(Connection con = jdbcTemplate.getDataSource().getConnection()){
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, id_campanha);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Produto p = new Produto();
                p.setId(rs.getLong("id"));
                p.setTipo_produto(rs.getString("tipo_produto"));
                p.setNome_produto(rs.getString("nome_produto"));
                p.setUnidade(rs.getString("unidade"));

                listaProdutos.add(p);
            }
            System.out.println("Produto encontrado com sucesso.");
            rs.close();
            ps.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return listaProdutos;
    }

    //----------------------------------------------------------------------//
    //---------------------- CADASTRA UM PRODUTO NOVO ----------------------//
    //----------------------------------------------------------------------//

    public Produto inserirProduto(Produto p){
        Connection con = null;
        PreparedStatement ps = null;
        try{
            con = jdbcTemplate.getDataSource().getConnection();
            ps = con.prepareStatement("INSERT INTO PRODUTO"
                    + "(nome_produto, tipo_produto, unidade)"
                    + "VALUES"
                    + "(?, ?, ?)");
            ps.setString(1,p.getNome_produto());
            ps.setString(2, p.getTipo_produto());
            ps.setString(3, p.getUnidade());
            ps.executeUpdate();

            ps.close();
            con.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        System.out.println("Produto adicionado com sucesso.");
        return p;
    }
}