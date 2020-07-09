package br.android.larsaovicentev2;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.android.larsaovicentev2.activitys.MainActivity;

public class DoacaoProdutoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Toolbar toolbar;
    String titulo_campanha;
    MainActivity mA = new MainActivity();
    String id_campanhaBancoXd;
    List<String> listProduto = new ArrayList<>();
    List<String> listTipoProduto = new ArrayList<>();
    TextView unidadeTV;
    String pos;
    String arrecadado;
    String meta;
    ProgressBar mProgressBar;
    int mProgressStatus = 0;
    TextView TVbarraProgresso;
    String barraProgressoTexto;
    Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_produto);
        Button doarP = findViewById(R.id.btnDoarProduto);
        final ImageView calendario = findViewById(R.id.btnCalendarioP);
        final EditText quantidade = findViewById(R.id.quantidadeProduto);
        final EditText valorData = findViewById(R.id.editTextDataP);
        final Spinner spinnerProdutos = findViewById(R.id.spinnerProduto);
        mProgressBar = findViewById(R.id.progressBarArrecadadoProduto);
        TVbarraProgresso = findViewById(R.id.textViewBarraProgresso);

        unidadeTV = findViewById(R.id.textViewUnidade);
        toolbar = findViewById(R.id.toolbarProduto);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Doação Produto");
        titulo_campanha = getIntent().getExtras().getString("titulo");
        SharedPreferences sharedPreferences = getSharedPreferences("idCampanha", MODE_PRIVATE);
        id_campanhaBancoXd = sharedPreferences.getString("id_campanha_xd", "");
        buscarArrecadado();

        listTipoProduto.add("");
        listProduto.add("Selecione o produto:");
        listarProdutosPorIdCampanha();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), R.layout.support_simple_spinner_dropdown_item, listProduto);
        Spinner spinnerProduto = findViewById(R.id.spinnerProduto);
        spinnerProduto.setAdapter(adapter);
        spinnerProduto.setOnItemSelectedListener(this);

        //----------------------------------------------------------------------//
        //--------------------- REALIZA A DOAÇÃO DE PRODUTO --------------------//
        //----------------------------------------------------------------------//

        doarP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("dadosUsuario", Context.MODE_PRIVATE);
                final String quantidadeValor = quantidade.getText().toString();
                final String tipo = unidadeTV.getText().toString();
                final String data = valorData.getText().toString();
                String pessoa_id = sharedPreferences.getString("id", "");
                String campanha_id = id_campanhaBancoXd;
                final String nome_produto = (String) spinnerProdutos.getSelectedItem();

                if(quantidadeValor.equals("")||data.equals("")){
                    Snackbar mySnackbar = Snackbar.make(findViewById(R.id.LinearProduto),
                            "Preencha todos os Campos.", Snackbar.LENGTH_LONG);
                    mySnackbar.show();
                }else if(tipo.equals("Quantidade")){
                    Snackbar mySnackbar = Snackbar.make(findViewById(R.id.LinearProduto),
                            "Selecione o produto.", Snackbar.LENGTH_LONG);
                    mySnackbar.show();
                }else{
                    try{
                        JSONObject jsonObj = new JSONObject();
                        jsonObj.put("quantidade", quantidadeValor);
                        jsonObj.put("data", data);
                        jsonObj.put("pessoa_id", pessoa_id);
                        jsonObj.put("campanha_id", campanha_id);
                        jsonObj.put("nome_produto", nome_produto);

                        final String json = jsonObj.toString();
                        final String jsonEnviar = json;
                        String url = mA.ipPc() + "doarProduto";

                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                JSONTokener jsonTokener = new JSONTokener(response);
                                try {
                                    JSONObject jobj = (JSONObject) jsonTokener.nextValue();
                                    if(!jobj.has("Error")){
                                        Snackbar mySnackbar = Snackbar.make(findViewById(R.id.activity_produto),
                                                "Doação realizada com sucesso!", Snackbar.LENGTH_LONG);
                                        mySnackbar.show();
                                        final Intent ComprovanteP = new Intent(DoacaoProdutoActivity.this, ComprovantePActivity.class);
                                        ComprovanteP.putExtra("produto", nome_produto);
                                        ComprovanteP.putExtra("quantidade", quantidadeValor);
                                        ComprovanteP.putExtra("tipo", tipo);
                                        ComprovanteP.putExtra("datap", data);
                                        Snackbar mySnackbar2 = Snackbar.make(findViewById(R.id.LinearProduto),
                                                "Gerando Comprovante", Snackbar.LENGTH_LONG);
                                        mySnackbar2.show();
                                        final Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                startActivity(ComprovanteP);
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
                                Toast.makeText(DoacaoProdutoActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
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
                        Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        calendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(DoacaoProdutoActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                valorData.setText(year + "-" + (month+1) + "-" + day);
                            }
                        }, year, month, dayOfMonth);

                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
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
    //---- LISTA OS PRODUTOS DISPONIVEIS PARA DOAÇÃO NA CAMPANHA -----------//
    //----------------------------------------------------------------------//

    public void listarProdutosPorIdCampanha(){
        try{
            String url = mA.ipPc() + "listarProdutos/" + id_campanhaBancoXd;

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
                                JSONObject obj = (JSONObject) jsonArray.get(i);
                                String nome_produtoBanco = obj.getString("nome_produto");
                                String unidade = obj.getString("unidade");
                                listProduto.add(nome_produtoBanco);
                                listTipoProduto.add(unidade);
                            }
                        }
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(DoacaoProdutoActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        try{
            pos = parent.getItemAtPosition(position+1).toString();
            unidadeTV.setText(listTipoProduto.get(position));
        }catch (IndexOutOfBoundsException ie){
            pos = parent.getItemAtPosition(position).toString();
            unidadeTV.setText(listTipoProduto.get(position));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void setProgressBar(){
        if(arrecadado.equals("0.0")){
            String barraProgresso = (int) Float.parseFloat(arrecadado) + "/" + (int) Float.parseFloat(meta);
            TVbarraProgresso.setText(barraProgresso);
        }else {
            mProgressBar.setMax((int) Float.parseFloat(meta));
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (mProgressStatus < (int) Float.parseFloat(arrecadado)) {
                        mProgressStatus--;
                        mProgressStatus++;
                        mProgressStatus++;
                        mProgressStatus++;
                        android.os.SystemClock.sleep(1);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mProgressBar.setProgress(mProgressStatus);
                                barraProgressoTexto = mProgressBar.getProgress() + "/" + (int) Float.parseFloat(meta);
                                TVbarraProgresso.setText(barraProgressoTexto);
                            }
                        });
                    }
                }
            }).start();
        }
    }

    //----------------------------------------------------------------------//
    //---------------- BUSCA O VALOR JÁ ARRECADADO DA CAMPANHA -------------//
    //----------------------------------------------------------------------//

    public void buscarArrecadado(){
        try{
            String url = mA.ipPc() + "arrecadado/" + id_campanhaBancoXd;

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    JSONTokener jsonTokener = new JSONTokener(response);
                    try {
                        JSONObject jobj = (JSONObject) jsonTokener.nextValue();
                            arrecadado = jobj.getString("arrecadado");
                            meta = jobj.getString("meta");
                            setProgressBar();
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(DoacaoProdutoActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
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
}
