package com.grupo01.projeto.projeto.grupo01.DAO;

import com.grupo01.projeto.projeto.grupo01.Entity.DoacaoDinheiro;
import com.grupo01.projeto.projeto.grupo01.Entity.Pessoa;
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
public class PessoaDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //----------------------------------------------------------------------//
    //------------------------- INCLUIR UMA PESSOA -------------------------//
    //----------------------------------------------------------------------//

    public Pessoa incluirPessoa(Pessoa p) {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = jdbcTemplate.getDataSource().getConnection();
            stmt = con.prepareStatement(
                    "INSERT INTO PESSOA "
                            + "(nome, sobrenome_nome_fantasia, email, cpf_cnpj, senha, tipo_pessoa)"
                            + "VALUES"
                            + "(?, ?, ?, ?, ?, ?)");
            stmt.setString(1, p.getNome());
            stmt.setString(2, p.getSobrenome_nome_fantasia());
            stmt.setString(3, p.getEmail());
            stmt.setString(4, p.getCpf_cnpj());
            stmt.setString(5, p.getSenha());
            stmt.setString(6, p.getTipo_pessoa());

            stmt.executeUpdate();

            stmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Pessoa adicionada com sucesso.");
        return p;
    }

    //----------------------------------------------------------------------//
    //---------------------- VALIDAÇÃO LOGIN PESSOA ------------------------//
    //----------------------------------------------------------------------//

    public Pessoa login(String loginAndroid, String senhaAndroid) {
        String query = "select * from pessoa where(cpf_cnpj = ? OR email = ?) AND senha = ?";
        Pessoa p = new Pessoa();

        try (Connection con = jdbcTemplate.getDataSource().getConnection()) {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, loginAndroid);
            ps.setString(2, loginAndroid);
            ps.setString(3, senhaAndroid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                p.setId(rs.getLong("id"));
                p.setNome(rs.getString("nome"));
                p.setSenha(rs.getString("senha"));
                p.setSobrenome_nome_fantasia(rs.getString("sobrenome_nome_fantasia"));
                p.setEmail(rs.getString("email"));
                p.setCpf_cnpj(rs.getString("cpf_cnpj"));
                p.setSenha(rs.getString("senha"));
                p.setTipo_pessoa(rs.getString("tipo_pessoa"));
            } else {
                return null;
            }
            rs.close();
            ps.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return p;
    }

    //----------------------------------------------------------------------//
    //------- VERIFICAR SE JA EXISTE CPF/CNPJ E EMAIL PESSOA ---------------//
    //----------------------------------------------------------------------//

    public Pessoa verificaCPF_CNPJ(String cpf_cnpjAndroid, String emailAndroid) {
        String query = "select * from pessoa where(cpf_cnpj = " + "'" + cpf_cnpjAndroid + "'" + " OR email = " + "'" + emailAndroid + "')";
        Pessoa p = new Pessoa();
        try (Connection con = jdbcTemplate.getDataSource().getConnection()) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                String email = rs.getString("email");
                String cpf_cnpj = rs.getString("cpf_cnpj");
                p.setEmail(rs.getString("email"));
                p.setCpf_cnpj(rs.getString("cpf_cnpj"));
            } else {
                return null;
            }
            rs.close();
            stmt.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return p;
    }
}
