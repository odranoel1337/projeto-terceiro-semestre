package com.grupo01.projeto.projeto.grupo01.DAO;

import com.grupo01.projeto.projeto.grupo01.Entity.Campanha;
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
public class CampanhaDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //----------------------------------------------------------------------//
    //-------------- REALIZA O CADASTRO DE UMA NOVA CAMPANHA ---------------//
    //----------------------------------------------------------------------//

    public Campanha incluirCampanha(Campanha c){
        Connection con = null;
        PreparedStatement ps = null;
        try{
            con = jdbcTemplate.getDataSource().getConnection();
            ps = con.prepareStatement("INSERT INTO CAMPANHA "
                    + "(nome_campanha, descricao, meta, arrecadado, data_inicio, data_fim, tipo_doacao)"
                    + "VALUES"
                    + "(?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, c.getNome_campanha());
            ps.setString(2, c.getDescricao());
            ps.setFloat(3, c.getMeta());
            ps.setFloat(4, c.getArrecadado());
            ps.setString(5, c.getData_inicio());
            ps.setString(6, c.getData_fim());
            ps.setString(7, c.getTipo_doacao());

            ps.executeUpdate();

            ps.close();
            con.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        System.out.println("Campanha Adicionada com sucesso.");
        return c;
    }

    //----------------------------------------------------------------------//
    //---------------- REALIZA A EXCLUSÃO DE UMA CAMPANHA ------------------//
    //----------------------------------------------------------------------//

    public void excluirCampanha(String nome_campanha) throws Exception{
        System.out.println("Nome delete:" +nome_campanha);
        String query2 = " delete from campanha where nome_campanha = '" + nome_campanha + "';";
        try(Connection con = jdbcTemplate.getDataSource().getConnection()){
            Statement stmt = con.createStatement();
            int rs2 = stmt.executeUpdate(query2);
            if(rs2 == 0){
                throw new Exception("Campanha inexistente");
            }
            stmt.close();
        }
    }

    //----------------------------------------------------------------------//
    //------------- VERIFICA SE A CAMPANHA JÁ EXISTE NO BANCO --------------//
    //----------------------------------------------------------------------//

    public Campanha verificarCampanhaJaExistente(String nome_campanha){
        String query = "select * from campanha where nome_campanha = ?";
        Campanha c = new Campanha();
        try(Connection con = jdbcTemplate.getDataSource().getConnection()){
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, nome_campanha);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                c.setNome_campanha(rs.getString("nome_campanha"));
            }else{
                return null;
            }
            System.out.println("Nome de campanha já cadastrada");
            rs.close();
            ps.close();
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return c;
    }

    //----------------------------------------------------------------------//
    //---------------- LISTA TODAS AS CAMPANHAS CADASTRADAS-----------------//
    //----------------------------------------------------------------------//

    public List<Campanha> listarCampanhas(){
        String query = "select * from campanha";

        List<Campanha> listaCampanhas = new ArrayList<>();
        try(Connection con = jdbcTemplate.getDataSource().getConnection()){
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                Campanha c = new Campanha();
                c.setId(rs.getLong("id"));
                c.setNome_campanha(rs.getString("nome_campanha"));
                c.setDescricao(rs.getString("descricao"));
                c.setMeta(rs.getFloat("meta"));
                c.setArrecadado(rs.getFloat("arrecadado"));
                c.setData_inicio(rs.getString("data_inicio"));
                c.setData_fim(rs.getString("data_fim"));
                c.setTipo_doacao(rs.getString("tipo_doacao"));
                listaCampanhas.add(c);
            }
            stmt.close();
            rs.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return listaCampanhas;
    }

    //----------------------------------------------------------------------//
    //----------------- BUSCA DADOS DA CAMPANHA PELO NOME ------------------//
    //----------------------------------------------------------------------//

    public Campanha listarCampanhasID(String nome_campanha){
        String query = "select * from campanha where nome_campanha = ?";

        Campanha c = new Campanha();
        try(Connection con = jdbcTemplate.getDataSource().getConnection()){
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, nome_campanha);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                c.setId(rs.getLong("id"));
                c.setNome_campanha(rs.getString("nome_campanha"));
                c.setDescricao(rs.getString("descricao"));
                c.setMeta(rs.getFloat("meta"));
                c.setArrecadado(rs.getFloat("arrecadado"));
                c.setData_inicio(rs.getString("data_inicio"));
                c.setData_fim(rs.getString("data_fim"));
            }else{
                return null;
            }
            System.out.println("Campanha encontrada com sucesso.");
            ps.close();
            rs.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return c;
    }

    //----------------------------------------------------------------------//
    //------------- BUSCA O TANTO ARRECADADO EM TAL CAMPANHAA --------------//
    //----------------------------------------------------------------------//

    public Campanha arrecadado(String id_campanha){
        String query = "select meta, arrecadado from campanha where id = ?";

        Campanha c = new Campanha();
        try(Connection con = jdbcTemplate.getDataSource().getConnection()){
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, id_campanha);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                c.setArrecadado(rs.getFloat("arrecadado"));
                c.setMeta(rs.getFloat("meta"));
            }else{
                return null;
            }
            rs.close();
            ps.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return c;
    }

}
