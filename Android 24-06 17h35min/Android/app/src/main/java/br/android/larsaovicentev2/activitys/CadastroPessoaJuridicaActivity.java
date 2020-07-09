package br.android.larsaovicentev2.activitys;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.UnsupportedEncodingException;

import br.android.larsaovicentev2.R;
import br.android.larsaovicentev2.classe.ValidarCPFeCNPJ;

public class CadastroPessoaJuridicaActivity extends AppCompatActivity {

    EditText nomeEmpresaET;
    EditText nomeFantasiaET;
    EditText emailET;
    EditText cnpjET;
    EditText senhaET;
    EditText confirmarSenhaET;
    Button buttonJuridicaCadastrar;
    Toolbar toolbar;

    String nomeConfirmado;
    String sobrenome_nome_fantasiaConfirmado;
    String emailConfirmado;
    String cpf_cnpjConfirmado;
    String senhaConfirmada;
    String tipo_pessoaConfirmada;
    MainActivity mA = new MainActivity();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_juridica);

        nomeEmpresaET = findViewById(R.id.editTextJuridicaNome);
        nomeFantasiaET = findViewById(R.id.editTextJuridicaNomeFantasia);
        emailET = findViewById(R.id.editTextJuridicaEmail);
        cnpjET = findViewById(R.id.editTextJuridicaCNPJ);
        senhaET = findViewById(R.id.editTextJuridicaSenha);
        confirmarSenhaET = findViewById(R.id.editTextJuridicaConfirmarSenha);
        buttonJuridicaCadastrar = findViewById(R.id.buttonFisicaCadastrar);
        toolbar = findViewById(R.id.toolbarJuridica);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Cadastro Pessoa Juridica");

        buttonJuridicaCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dadosPessoaJuridica();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, EscolhaActivity.class));
                finishAffinity();
                break;
            default:break;
        }
        return true;
    }

    //----------------------------------------------------------------------//
    //------------ VERIFICA SE A DIGITAÇÃO DOS DADOS ESTÁ CORRETA ----------//
    //----------------------------------------------------------------------//

    private void dadosPessoaJuridica(){
        String nome = nomeEmpresaET.getText().toString();
        String nomeFantasia = nomeFantasiaET.getText().toString();
        String email = emailET.getText().toString();
        String cnpj = cnpjET.getText().toString();
        String senha = senhaET.getText().toString();
        String confirmarSenha = confirmarSenhaET.getText().toString();
        ValidarCPFeCNPJ validacaoCNPJ = new ValidarCPFeCNPJ();

        if(nome.equals("") || nomeFantasia.equals("") || email.equals("") || cnpj.equals("") || senha.equals("") || confirmarSenha.equals("")){
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.pessoaJuridicaActivity),
                    "Preencha os campos vazio.", Snackbar.LENGTH_LONG);
            mySnackbar.show();
        }else if(!senha.equals(confirmarSenha)){
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.pessoaJuridicaActivity),
                    "Senhas não conferem.", Snackbar.LENGTH_LONG);
            mySnackbar.show();
            senhaET.setText("");
            confirmarSenhaET.setText("");
        }else if(!validacaoCNPJ.validadorCNPJ(cnpj)){
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.pessoaJuridicaActivity),
                    "CNPJ inválido.", Snackbar.LENGTH_LONG);
            mySnackbar.show();
            cnpjET.setText("");
        } else {
            nomeConfirmado = nome;
            sobrenome_nome_fantasiaConfirmado = nomeFantasia;
            emailConfirmado = email;
            cpf_cnpjConfirmado = cnpj;
            senhaConfirmada = senha;
            tipo_pessoaConfirmada = "J";

            requestDadosValidaCNPJ();
        }
    }

    //----------------------------------------------------------------------//
    //------------ VERIFICA SE JÁ EXISTE O CNPJ OU EMAIL DIGITADO ----------//
    //----------------------------------------------------------------------//

    public void requestDadosValidaCNPJ(){
        try{
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("cpf_cnpj", cpf_cnpjConfirmado);
            jsonObj.put("email", emailConfirmado);
            String json = jsonObj.toString();
            final String jsonEnviar = json;
            String url = mA.ipPc() + "pessoaVerificaCPFCNPJ";

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    JSONTokener jsonTokener = new JSONTokener(response);
                    try{
                        JSONObject jobj = (JSONObject) jsonTokener.nextValue();
                        if(!jobj.has("Error")){
                            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.pessoaJuridicaActivity),
                                    "Email ou cnpj já cadastrado!", Snackbar.LENGTH_LONG);
                            mySnackbar.show();
                            emailET.setText("");
                            cnpjET.setText("");
                        }else{
                            cadastrarJuridica();
                        }
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(CadastroPessoaJuridicaActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }){
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }
                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return jsonEnviar == null ? null : jsonEnviar.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        uee.printStackTrace();
                        return null;
                    }
                }
            };
            requestQueue.add(stringRequest);
        }catch (Exception ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //----------------------------------------------------------------------//
    //------------------------ REALIZA O CADASTRO EM SI --------------------//
    //----------------------------------------------------------------------//

    public void cadastrarJuridica(){
        try{
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("nome", nomeConfirmado);
            jsonObj.put("sobrenome_nome_fantasia", sobrenome_nome_fantasiaConfirmado);
            jsonObj.put("email", emailConfirmado);
            jsonObj.put("cpf_cnpj", cpf_cnpjConfirmado);
            jsonObj.put("senha", senhaConfirmada);
            jsonObj.put("tipo_pessoa", tipo_pessoaConfirmada);
            String json = jsonObj.toString();
            final String jsonEnviar = json;
            String url = mA.ipPc() + "pessoaCadastro";

            RequestQueue requestQueue =Volley.newRequestQueue(this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(CadastroPessoaJuridicaActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }){
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }
                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return jsonEnviar == null ? null : jsonEnviar.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        uee.printStackTrace();
                        return null;
                    }
                }
            };
            requestQueue.add(stringRequest);
        }catch (Exception ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        Snackbar mySnackbar = Snackbar.make(findViewById(R.id.pessoaJuridicaActivity),
                "Cadastrado com sucesso!", Snackbar.LENGTH_LONG);
        mySnackbar.show();
        final Intent i = new Intent(this, MainActivity.class);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        }, 500);
    }
}