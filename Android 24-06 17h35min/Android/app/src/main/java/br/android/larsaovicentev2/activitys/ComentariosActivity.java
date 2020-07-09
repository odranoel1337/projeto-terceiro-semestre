package br.android.larsaovicentev2.activitys;

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
import android.view.View;
import android.widget.Button;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.android.larsaovicentev2.GerenciarCampanhasActivity;
import br.android.larsaovicentev2.R;
import br.android.larsaovicentev2.adapter.AdapterComentario;
import br.android.larsaovicentev2.classe.Campanha;
import br.android.larsaovicentev2.classe.Comentario;

public class ComentariosActivity extends AppCompatActivity {

    List<Comentario> listaComentario = new ArrayList<>();
    Toolbar toolbar;
    RecyclerView recyclerViewComentario;
    Button btnEnviar;
    EditText comentario;
    String comentario_confirmado;
    String data_comentario;
    String horario_comentario;
    String nome_pessoa;
    String titulo_campanha;
    String id_campanhaBancoXd;
    EditText barraDigitarET;

    MainActivity mA = new MainActivity();
    AdapterComentario adapterComentario = new AdapterComentario(listaComentario);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentarios);

        toolbar = findViewById(R.id.toolbarComentario);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Comentário");
        barraDigitarET = findViewById(R.id.ETComentario);

        recyclerViewComentario = findViewById(R.id.recyclerViewComentario);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        comentario = findViewById(R.id.ETComentario);
        titulo_campanha = getIntent().getExtras().getString("titulo");

        SharedPreferences sharedPreferences = getSharedPreferences("idCampanha", MODE_PRIVATE);
        id_campanhaBancoXd = sharedPreferences.getString("id_campanha_xd", "");

        btnEnviar = findViewById(R.id.buttonComentarioCardView);
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dadosInserirComentario();
            }
        });

        recyclerViewComentario.setLayoutManager(layoutManager);
        recyclerViewComentario.setHasFixedSize(true);
        listarComentarios();
        recyclerViewComentario.setAdapter(adapterComentario);
    }

    public void listarComentarios(Comentario co) {
        listaComentario.add(co);
    }

    //----------------------------------------------------------------------//
    //-------------- VERIFICA OS DADOS PARA ENVIO DO COMENTARIO ------------//
    //----------------------------------------------------------------------//

    public void dadosInserirComentario(){
        String valor_comentario = comentario.getText().toString();
        if(valor_comentario.equals("")){
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.comentarioActivity),
                    "Preencha os campos vazio.", Snackbar.LENGTH_LONG);
            mySnackbar.show();
        } else{
            comentario_confirmado = valor_comentario;
            SimpleDateFormat formataData = new SimpleDateFormat("dd-MM-yyyy");
            Date data = new Date();
            data_comentario = formataData.format(data);
            SimpleDateFormat formataHora = new SimpleDateFormat("HH:mm");
            Date horario = new Date();
            horario_comentario = formataHora.format(horario);
            SharedPreferences sharedPreferences = getSharedPreferences("dadosUsuario", Context.MODE_PRIVATE);
            nome_pessoa = sharedPreferences.getString("nome", "") + " " + sharedPreferences.getString("sobrenome_nome_fantasia", "");
            inserirComentario();
        }
    }

    //----------------------------------------------------------------------//
    //-------------------------- ENVIA O COMENTÁRIO ------------------------//
    //----------------------------------------------------------------------//

    public void inserirComentario(){
        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("mensagem", comentario_confirmado);
            jsonObj.put("data_mensagem", data_comentario);
            jsonObj.put("horario_mensagem", horario_comentario);
            jsonObj.put("nome_pessoa", nome_pessoa);
            jsonObj.put("id_campanha", id_campanhaBancoXd);
            String json = jsonObj.toString();
            final String jsonEnviar = json;
            String url = mA.ipPc() + "comentariosEnvio";

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(ComentariosActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
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
        Comentario co = new Comentario();
        co.setNome_pessoa("Você");
        co.setComentario(comentario_confirmado);
        co.setData_comentario(data_comentario);
        co.setHorario_comentario(horario_comentario);
        listarComentarios(co);
        adapterComentario.notifyDataSetChanged();
        recyclerViewComentario.scrollToPosition(listaComentario.size()-1);
        barraDigitarET.setText("");
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
    //---------------------- LISTA TODOS OS COMENTÁRIOS --------------------//
    //----------------------------------------------------------------------//

    public void listarComentarios(){
        refresh(1000);
        try{
            String url = mA.ipPc() + "listarComentariosId/" + id_campanhaBancoXd;

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
                        if(!jsonArray.equals("Error")){
                            listaComentario.clear();
                            for(int i = 0; i < jsonArray.length(); i++){
                                Comentario co = new Comentario();
                                JSONObject obj = (JSONObject) jsonArray.get(i);
                                String id = obj.getString("id_campanha");
                                SharedPreferences sharedPreferences = getSharedPreferences("dadosUsuario", Context.MODE_PRIVATE);
                                String nomeUsuLog = sharedPreferences.getString("nome", "") + " " + sharedPreferences.getString("sobrenome_nome_fantasia", "");
                                if(nomeUsuLog.equals(obj.getString("nome_pessoa"))){
                                    co.setNome_pessoa("Você");
                                }else {
                                    co.setNome_pessoa(obj.getString("nome_pessoa"));
                                }
                                co.setComentario(obj.getString("mensagem"));
                                co.setData_comentario(obj.getString("data_mensagem"));
                                co.setHorario_comentario(obj.getString("horario_mensagem"));
                                listarComentarios(co);
                            }
                            adapterComentario.notifyDataSetChanged();
                        }
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(ComentariosActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
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

    //----------------------------------------------------------------------//
    //----- METODO PARA ATUALIZAR A PÁGINA COMENTARIOS A CADA 1 SEGUNDO ----//
    //----------------------------------------------------------------------//

    private void refresh(int milliseconds){
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                listarComentarios();
            }
        };
        handler.postDelayed(runnable, milliseconds);
    }
}
