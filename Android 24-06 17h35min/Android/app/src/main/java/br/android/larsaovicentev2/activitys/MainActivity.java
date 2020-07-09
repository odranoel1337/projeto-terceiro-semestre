package br.android.larsaovicentev2.activitys;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
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

public class MainActivity extends AppCompatActivity {

    TextView crieUma;
    Button logar;
    EditText loginMain;
    EditText senhaMain;
    CheckBox lembrarLogin;
    RequestQueue queue;
    String idRecebido;
    String nomeRecebido;
    String senhaRecebida;
    String sobrenome_nome_fantasiaRecebido;
    String emailRecebido;
    String cpf_cnpjRecebido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logar = findViewById(R.id.buttonEntrar);
        crieUma = findViewById(R.id.textViewCadastreseAgora);
        loginMain = findViewById(R.id.editTextLogin);
        senhaMain = findViewById(R.id.editTextSenha);
        lembrarLogin = findViewById(R.id.checkBoxLembrarLogin);
        queue = Volley.newRequestQueue(MainActivity.this);

        crieUma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, EscolhaActivity.class));
            }
        });

        logar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 entrar();
            }
        });
        recuperarDados();
    }

    public void entrar() {

        String login = loginMain.getText().toString();
        String senha = senhaMain.getText().toString();

        if (login.equals("") || senha.equals("")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.mainActivity),
                    "Preencha os campos vazio.", Snackbar.LENGTH_LONG);
            mySnackbar.show();
        }else{
            requestDadosLogin();
        }
    }

    //----------------------------------------------------------------------//
    //------------------------- LEMBRAR O LOGIN ----------------------------//
    //----------------------------------------------------------------------//

    private void recuperarDados(){
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        loginMain.setText(sharedPref.getString("loginMain", ""));
        boolean check = sharedPref.getBoolean("Check", false);
        lembrarLogin.setChecked(check);
    }

    //----------------------------------------------------------------------//
    //--------------- BUSCA DADOS DO LOGIN NO SERVIDOR/BANCO ---------------//
    //----------------------------------------------------------------------//

    public void requestDadosLogin(){
        final String login = loginMain.getText().toString();
        String senha = senhaMain.getText().toString();
        try{
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("login", login);
            jsonObj.put("senha", senha);

            final String json = jsonObj.toString();
            final String jsonEnviar = json;
            String url = ipPc() + "pessoalogin";

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    JSONTokener jsonTokener = new JSONTokener(response);
                    try {
                        JSONObject jobj = (JSONObject) jsonTokener.nextValue();
                        if(!jobj.has("Error")){
                            idRecebido = jobj.getString("id");
                            nomeRecebido = jobj.getString("nome");
                            senhaRecebida = jobj.getString("senha");
                            sobrenome_nome_fantasiaRecebido = jobj.getString("sobrenome_nome_fantasia");
                            emailRecebido = jobj.getString("email");
                            cpf_cnpjRecebido = jobj.getString("cpf_cnpj");
                            login();
                        }else{
                            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.mainActivity),
                                    "Dados Incorretos!", Snackbar.LENGTH_LONG);
                            mySnackbar.show();
                            loginMain.setText("");
                            senhaMain.setText("");
                        }
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
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
    //------------------------- REALIZA O LOGIN EM SI ----------------------//
    //----------------------------------------------------------------------//

    public void login(){
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        if(lembrarLogin.isChecked()){
            editor.putString("loginMain", loginMain.getText().toString());
            editor.putBoolean("Check", true);
        }else{
            editor.putString("loginMain", "");
            editor.putBoolean("Check", false);
        }
        editor.commit();
        SharedPreferences sharedPreferences = getSharedPreferences("dadosUsuario", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPreferences.edit();
        editor2.putString("id", idRecebido);
        editor2.putString("nome", nomeRecebido);
        editor2.putString("senha", senhaRecebida);
        editor2.putString("sobrenome_nome_fantasia", sobrenome_nome_fantasiaRecebido);
        editor2.putString("email", emailRecebido);
        editor2.putString("cpf_cnpj", cpf_cnpjRecebido);
        editor2.apply();

        final Intent intent = new Intent(getApplicationContext(),AbasActivity.class);
        intent.putExtra("id",idRecebido);
        intent.putExtra("nome",nomeRecebido);
        intent.putExtra("sobrenome_nome_fantasia",sobrenome_nome_fantasiaRecebido);
        intent.putExtra("email",emailRecebido);
        intent.putExtra("cpf_cnpj",cpf_cnpjRecebido);

        startActivity(intent);
        finish();
    }

    public String ipPc(){
        String ip = "http://192.168.1.105:8081/";
        return ip;
    }
}
