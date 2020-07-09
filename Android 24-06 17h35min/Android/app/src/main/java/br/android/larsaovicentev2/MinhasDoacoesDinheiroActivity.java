package br.android.larsaovicentev2;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

import br.android.larsaovicentev2.activitys.MainActivity;
import br.android.larsaovicentev2.adapter.AdapterMinhasDoacoesDinheiro;
import br.android.larsaovicentev2.adapter.AdapterMinhasDoacoesProduto;
import br.android.larsaovicentev2.classe.MinhasDoacoes;
import br.android.larsaovicentev2.classe.MinhasDoacoesDinheiro;

public class MinhasDoacoesDinheiroActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerViewMinhasDoacoesDinheiro;
    List<MinhasDoacoesDinheiro> listaMinhasDoacoesDinheiro = new ArrayList<>();
    AdapterMinhasDoacoesDinheiro adapterMinhasDoacoesDinheiro = new AdapterMinhasDoacoesDinheiro(listaMinhasDoacoesDinheiro);
    String id;
    MainActivity mA = new MainActivity();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minhas_doacoes_dinheiro);

        toolbar =  findViewById(R.id.toolbarMinhasDoacoesDinheiro);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Minhas Doações em Dinheiro");

        pegarDadosUsuario();
        listarMinhasDoacoesDinheiro();

        recyclerViewMinhasDoacoesDinheiro = findViewById(R.id.recyclerViewMinhasDoacoesDinheiro);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewMinhasDoacoesDinheiro.setLayoutManager(layoutManager);
        recyclerViewMinhasDoacoesDinheiro.setHasFixedSize(true);
        recyclerViewMinhasDoacoesDinheiro.setAdapter(adapterMinhasDoacoesDinheiro);
    }

    public void criarDoacaoDineiro(MinhasDoacoesDinheiro minhasDoacoesDinheiro) {
        listaMinhasDoacoesDinheiro.add(minhasDoacoesDinheiro);
    }

    public void pegarDadosUsuario(){
        Bundle dados = getIntent().getExtras();
        if(getIntent().getStringExtra("id")!=null) {
            id = dados.getString("id");
            SharedPreferences sharedPreferences = getSharedPreferences("dadosMinhasDoacoes", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor2 = sharedPreferences.edit();
            editor2.putString("id", id);
            editor2.apply();
            SharedPreferences sharedPreferences1 = getSharedPreferences("dadosUsuario",Context.MODE_PRIVATE);
            id = sharedPreferences1.getString("id","");
        }
    }

    //----------------------------------------------------------------------//
    //---------- BUSCA AS DOAÇÕES EM DINHEIRO FEITAS PELO USUÁRIO ----------//
    //----------------------------------------------------------------------//

    public void listarMinhasDoacoesDinheiro(){
        try{
            String url = mA.ipPc() + "listarMinhasDoacoesDinheiro/" + id;

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    JSONTokener jsonTokener = new JSONTokener(response);
                    try {
                        JSONArray jsonArray = (JSONArray) jsonTokener.nextValue();
                        if (!jsonArray.equals("Error")) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                MinhasDoacoesDinheiro minhasDoacoesDinheiro = new MinhasDoacoesDinheiro();
                                JSONObject obj = (JSONObject) jsonArray.get(i);
                                minhasDoacoesDinheiro.setQuantidade_doada(obj.getString("quantidade"));
                                minhasDoacoesDinheiro.setData_doacao(obj.getString("data"));
                                minhasDoacoesDinheiro.setMetodo_pagamento(obj.getString("metodo_pagamento"));
                                criarDoacaoDineiro(minhasDoacoesDinheiro);
                            }
                            adapterMinhasDoacoesDinheiro.notifyDataSetChanged();
                        }
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MinhasDoacoesDinheiroActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }){
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
        }catch (Exception ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
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
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
