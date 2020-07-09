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

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import br.android.larsaovicentev2.activitys.MainActivity;
import br.android.larsaovicentev2.adapter.AdapterMinhasDoacoesProduto;
import br.android.larsaovicentev2.classe.MinhasDoacoes;

public class MinhasDoacoesProdutoActivity extends AppCompatActivity {

    Toolbar toolbar;
    MainActivity mA = new MainActivity();
    List<MinhasDoacoes> listaMinhasDoacoes = new ArrayList<>();
    RecyclerView recyclerViewMinhasDoacoesProduto;
    AdapterMinhasDoacoesProduto adapterMinhasDoacoesProduto = new AdapterMinhasDoacoesProduto(listaMinhasDoacoes);
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minhas_doacoes_campanha);

        toolbar =  findViewById(R.id.toolbarMinhasDoacoesProduto);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Minhas Doações em Campanhas");
        pegarDadosUsuario();
        listarMinhasDoacoes();

        recyclerViewMinhasDoacoesProduto = findViewById(R.id.recyclerViewMinhasDoacoesProduto);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewMinhasDoacoesProduto.setHasFixedSize(true);
        recyclerViewMinhasDoacoesProduto.setLayoutManager(layoutManager);
        recyclerViewMinhasDoacoesProduto.setAdapter(adapterMinhasDoacoesProduto);
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

    public void criarDoacao(MinhasDoacoes minhasDoacoes) {
        listaMinhasDoacoes.add(minhasDoacoes);
    }

    //----------------------------------------------------------------------//
    //---------- BUSCA AS DOAÇÕES DE PRODUTO FEITAS PELO USUÁRIO -----------//
    //----------------------------------------------------------------------//

    public void listarMinhasDoacoes(){
        try{
            String url = mA.ipPc() + "listarMinhasDoacoes/" + id;

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
                                MinhasDoacoes mD = new MinhasDoacoes();
                                JSONObject obj = (JSONObject) jsonArray.get(i);
                                mD.setQuantidade_doada(obj.getString("quantidade"));
                                mD.setData_doacao(obj.getString("data_doacao"));
                                mD.setNome_produto(obj.getString("nome_produto"));
                                mD.setTitulo_campanha(obj.getString("nome_campanha"));
                                mD.setUnidade_produto(obj.getString("unidade"));
                                criarDoacao(mD);
                            }
                            adapterMinhasDoacoesProduto.notifyDataSetChanged();
                        }
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MinhasDoacoesProdutoActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
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
                Toast.makeText(this,"Fazendo Log Out", Toast.LENGTH_SHORT).show();
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
