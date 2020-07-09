package br.android.larsaovicentev2;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import java.util.ArrayList;
import java.util.List;

import br.android.larsaovicentev2.activitys.AbasActivity;
import br.android.larsaovicentev2.activitys.MainActivity;

public class AddProdutoActivity extends AppCompatActivity {

    Toolbar toolbar;
    Spinner spinnerAddProduto;
    EditText nome_produtoET;
    EditText unidade_produtoET;
    MainActivity mA = new MainActivity();
    Button adicionarProduto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_produto);

        toolbar =  findViewById(R.id.toolbarAddProduto);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Adicionar Produto");
        nome_produtoET = findViewById(R.id.editTextNomeProdutoAddProduto);
        unidade_produtoET = findViewById(R.id.editTextUnidadeProdutoAddProduto);
        adicionarProduto = findViewById(R.id.buttonAdicionarProdutoAddProduto);

        List<String> list = new ArrayList<>();
        list.add("Alimento");
        list.add("Roupa");
        list.add("Higiene");

        spinnerAddProduto = findViewById(R.id.spinnerAddProduto);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, list);
        spinnerAddProduto.setAdapter(adapter);

        adicionarProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nome_produtoET.getText().toString().equals("") || unidade_produtoET.getText().toString().equals("")){
                    Snackbar mySnackbar = Snackbar.make(findViewById(R.id.addProdutoActivity),
                            "Preencha todos os campos.", Snackbar.LENGTH_LONG);
                    mySnackbar.show();
                }else{
                    incluirProduto();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_gerenciar_campanhas,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuLogOutGerenciar:
                Toast.makeText(this,"Fazendo Log Out", Toast.LENGTH_SHORT).show();
                final Intent i = new Intent(this, AbasActivity.class);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        finishAffinity();
                    }
                }, 500);
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //----------------------------------------------------------------------//
    //-------------------- REALIZA O CADASTRO DO PRODUTO -------------------//
    //----------------------------------------------------------------------//

    public void incluirProduto(){
        final String tipo_produto = (String) spinnerAddProduto.getSelectedItem();
        try{
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("nome_produto", nome_produtoET.getText().toString());
            jsonObj.put("tipo_produto", tipo_produto);
            jsonObj.put("unidade", unidade_produtoET.getText().toString());
            String json = jsonObj.toString();
            final String jsonEnviar = json;
            String url = mA.ipPc() + "produtoCadastro";

            RequestQueue requestQueue = Volley.newRequestQueue(this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    JSONTokener jsonTokener = new JSONTokener(response);
                    try {
                        JSONObject jobj = (JSONObject) jsonTokener.nextValue();
                        if(!jobj.has("Error")){
                            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.addProdutoActivity),
                                    "Produto cadastrado com sucess!", Snackbar.LENGTH_LONG);
                            mySnackbar.show();
                            final Intent i = new Intent(getApplicationContext(), AbasActivity.class);
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    finish();
                                }
                            }, 500);
                        }
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(AddProdutoActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
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
}
