package br.android.larsaovicentev2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import br.android.larsaovicentev2.activitys.MainActivity;
import br.android.larsaovicentev2.adapter.AdapterCampanha;
import br.android.larsaovicentev2.classe.Campanha;

public class CampanhasActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Campanha> listaCampanha = new ArrayList<>();
    Toolbar toolbar;
    MainActivity mA = new MainActivity();
    AdapterCampanha adapterCampanha = new AdapterCampanha(listaCampanha);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campanhas2);

        recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager((getApplicationContext()));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterCampanha);
        listarCampanhas();

        toolbar = findViewById(R.id.toolbarCampanhas);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Campanhas");
    }

    public void criarCampanha2(Campanha c) {
        listaCampanha.add(c);
    }

    public void startActivityDoacao(View v) {
        Intent intent = new Intent(CampanhasActivity.this, DoacaoProdutoActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuLogOut:
                Toast.makeText(this, "Fazendo Log Out", Toast.LENGTH_SHORT).show();
                final Intent i = new Intent(this, MainActivity.class);
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
            case R.id.menuGerenciarCampanha:
                isAdminGerenciarCampanha();
                break;
            case R.id.menuGerenciarProduto:
                isAdminGerenciarProduto();
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void criarCampanha(String nomeCampanhaConfirmado, String descricaoConfirmado, String metaConfirmado, String dataInicioConfirmado, String dataFimConfirmado, String spinnerConfirmado) {
    }

    //----------------------------------------------------------------------//
    //---------------- VERIFICA SE O USUÁRIO É ADMINISTRADOR ---------------//
    //----------------------------------------------------------------------//

    public boolean isAdmin(){
        SharedPreferences sharedPreferences = getSharedPreferences("dadosUsuario", Context.MODE_PRIVATE);
        String loginPessoa = sharedPreferences.getString("nome", "");
        String senhaPessoa = sharedPreferences.getString("senha", "");

        if (!loginPessoa.equals("adm") && !senhaPessoa.equals("adm")) {
            return false;
        } else {
            return true;
        }
    }

    public void isAdminGerenciarCampanha() {
        if(isAdmin()){
            final Intent intent = new Intent(this, GerenciarCampanhasActivity.class);
            startActivity(intent);
            finish();
        }else if(!isAdmin()){
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.activity_campanhas2),
                    "Você não é um administrador", Snackbar.LENGTH_LONG);
            mySnackbar.show();
        }
    }

    public void isAdminGerenciarProduto() {

        if(isAdmin()){
            final Intent intent = new Intent(this, GerenciarProdutoActivity.class);
            startActivity(intent);
            finish();
        }else if(!isAdmin()){
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.activity_campanhas2),
                    "Você não é um administrador", Snackbar.LENGTH_LONG);
            mySnackbar.show();
        }
    }

    //----------------------------------------------------------------------//
    //---------------------- LISTA TODAS AS CAMPANHAS ----------------------//
    //----------------------------------------------------------------------//

    public void listarCampanhas() {
        try {
            String url = mA.ipPc() + "listarCampanhas";
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        response = new String(response.getBytes("ISO-8859-1"), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    JSONTokener jsonTokener = new JSONTokener(response);
                    try {
                        JSONArray jsonArray = (JSONArray) jsonTokener.nextValue();
                        if (!jsonArray.equals("Error")) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Campanha c = new Campanha();
                                JSONObject obj = (JSONObject) jsonArray.get(i);

                                c.setId(obj.getInt("id"));
                                c.setNome_campanha(obj.getString("nome_campanha"));
                                c.setDescricao(obj.getString("descricao"));
                                c.setMeta(obj.getString("meta"));
                                c.setArrecadado(obj.getString("arrecadado"));
                                c.setDataInicio(obj.getString("data_inicio"));
                                c.setDataFim(obj.getString("data_fim"));
                                c.setTipo_doacao(obj.getString("tipo_doacao"));
                                criarCampanha2(c);
                            }
                            adapterCampanha.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(CampanhasActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    return null;
                }
            };
            requestQueue.add(stringRequest);
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}



