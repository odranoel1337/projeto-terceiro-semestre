package br.android.larsaovicentev2.activitys;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

public class CadastroPessoaFisicaActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText nomeET;
    EditText sobrenomeET;
    EditText emailET;
    EditText cpfET;
    EditText senhaET;
    EditText confirmarSenhaET;
    Button buttonCadastrar;

    String nomeConfirmado;
    String sobrenome_nome_fantasiaConfirmado;
    String emailConfirmado;
    String cpf_cnpjConfirmado;
    String senhaConfirmada;
    String tipo_pessoaConfirmada;
    MainActivity mA = new MainActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_fisica);

        nomeET = findViewById(R.id.editTextFisicaNome);
        sobrenomeET = findViewById(R.id.editTextFisicaSobrenome);
        emailET = findViewById(R.id.editTextFisicaEmail);
        cpfET = findViewById(R.id.editTextFisicaCPF);
        senhaET = findViewById(R.id.editTextFisicaSenha);
        confirmarSenhaET = findViewById(R.id.editTextFisicaConfirmaSenha);
        buttonCadastrar = findViewById(R.id.buttonFisicaCadastrar);
        toolbar = findViewById(R.id.toolbarPessoaFisica);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Cadastro Pessoa Física");

        buttonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dadosPessoaFisica();
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

    private void dadosPessoaFisica() {
        String nome = nomeET.getText().toString();
        String sobrenome = sobrenomeET.getText().toString();
        String email = emailET.getText().toString();
        String cpf = cpfET.getText().toString();
        String senha = senhaET.getText().toString();
        String confirmarSenha = confirmarSenhaET.getText().toString();
        ValidarCPFeCNPJ validacaoCPF = new ValidarCPFeCNPJ();

        if(nome.equals("") || sobrenome.equals("") || email.equals("") || cpf.equals("") ||  senha.equals("") || confirmarSenha.equals("")){
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.pessoaFisicaActivity),
                    "Preencha os campos vazio.", Snackbar.LENGTH_LONG);
            mySnackbar.show();
        } else if(isDigit(nome)){
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.pessoaFisicaActivity),
                    "Nome não pode contar numero.", Snackbar.LENGTH_LONG);
            mySnackbar.show();
            nomeET.setText("");
        }else if(isDigit(sobrenome)){
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.pessoaFisicaActivity),
                    "Sobrenome não pode contar numero.", Snackbar.LENGTH_LONG);
            mySnackbar.show();
            sobrenomeET.setText("");
        }else if (!senha.equals(confirmarSenha)){
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.pessoaFisicaActivity),
                    "Senhas não conferem.", Snackbar.LENGTH_LONG);
            mySnackbar.show();
            senhaET.setText("");
            confirmarSenhaET.setText("");
        } else if(!validacaoCPF.validadorCPF(cpf)){
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.pessoaFisicaActivity),
                    "CPF inválido.", Snackbar.LENGTH_LONG);
            mySnackbar.show();
            cpfET.setText("");
        }else {
            nomeConfirmado = nome;
            sobrenome_nome_fantasiaConfirmado = sobrenome;
            emailConfirmado = email;
            cpf_cnpjConfirmado = cpf;
            senhaConfirmada = senha;
            tipo_pessoaConfirmada = "F";
            requestDadosValidaCPF();
        }
    }

    public boolean isDigit(String stg){
        for(int i = 0; i < stg.length(); i++){
            if(Character.isDigit(stg.charAt(i))){
                return true;
            }
        }
        return false;
    }

    //----------------------------------------------------------------------//
    //------------- VERIFICA SE JÁ EXISTE O CPF OU EMAIL DIGITADO ----------//
    //----------------------------------------------------------------------//

    public void requestDadosValidaCPF(){
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
                            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.pessoaFisicaActivity),
                                    "Email ou cpf já cadastrado!", Snackbar.LENGTH_LONG);
                            mySnackbar.show();
                            emailET.setText("");
                            cpfET.setText("");
                        }else{
                            cadastrarFisica();
                        }
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(CadastroPessoaFisicaActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
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

    public void cadastrarFisica(){
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
                        Toast.makeText(CadastroPessoaFisicaActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
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
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.pessoaFisicaActivity),
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