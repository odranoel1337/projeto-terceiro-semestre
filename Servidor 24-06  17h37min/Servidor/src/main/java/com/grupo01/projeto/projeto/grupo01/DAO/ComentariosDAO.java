package com.grupo01.projeto.projeto.grupo01.DAO;

import com.grupo01.projeto.projeto.grupo01.Entity.Comentarios;
import com.grupo01.projeto.projeto.grupo01.Entity.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ComentariosDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //----------------------------------------------------------------------//
    //------------------------- ENVIA UM COMENTARIO ------------------------//
    //----------------------------------------------------------------------//

    public Comentarios incluirComentario(Comentarios co){
        Connection con = null;
        PreparedStatement ps = null;
        try{
            con = jdbcTemplate.getDataSource().getConnection();

            ps = con.prepareStatement("INSERT INTO comentario " +
                    "(mensagem, data_mensagem, horario_mensagem, nome_pessoa, id_campanha)" +
                    "VALUES" +
                    "(?, ?, ?, ?, ?)");

            ps.setString(1, co.getMensagem());
            ps.setString(2, co.getData_mensagem());
            ps.setString(3, co.getHorario_mensagem());
            ps.setString(4, co.getNome_pessoa());
            ps.setInt(5, co.getId_campanha());
            ps.executeUpdate();

            ps.close();
            con.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        System.out.println("Comentario Inserido com Sucesso!");
        return co;
    }

    //----------------------------------------------------------------------//
    //--------------- LISTA TODOS OS COMENTARIOS DA CAMPANHA ---------------//
    //----------------------------------------------------------------------//

    public List<Comentarios> listarComentariosId(String id_campanha){
        String query = "select * from comentario where id_campanha = " + id_campanha;
        List<Comentarios> listaComentarios = new ArrayList<>();

        try(Connection con = jdbcTemplate.getDataSource().getConnection()){
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                Comentarios co = new Comentarios();
                co.setId(rs.getInt("id"));
                co.setMensagem(rs.getString("mensagem"));
                co.setData_mensagem(rs.getString("data_mensagem"));
                co.setHorario_mensagem(rs.getString("horario_mensagem"));
                co.setNome_pessoa(rs.getString("nome_pessoa"));
                co.setId_campanha(rs.getInt("id_campanha"));
                listaComentarios.add(co);
            }
            stmt.close();
            rs.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return listaComentarios;
    }


}
