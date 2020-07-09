package br.android.larsaovicentev2;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.android.larsaovicentev2.activitys.MainActivity;

public class DoacaoDinheiroActivity extends AppCompatActivity {

    Toolbar toolbar;
    String idPessoa;
    String valorConfirmado;
    String metodo_pagamentoConfirmado;
    String dataConfimada;
    MainActivity mA = new MainActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinheiro);
        Button realizarDoacao = findViewById(R.id.btnDoarDinheiro);
        final ImageView calendarioD = findViewById(R.id.btnAbreCalndarioD);
        final EditText valor = findViewById(R.id.EditTextValor);
        final EditText valorDataD = findViewById(R.id.editTextDataPagamento);
        final Spinner formaPag = findViewById(R.id.spinnerForma);

        SharedPreferences sharedPreferences = getSharedPreferences("dadosUsuario", MODE_PRIVATE);
        idPessoa = sharedPreferences.getString("id", "");

        toolbar =  findViewById(R.id.toolbarPadrao);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Doação Dinheiro");

        realizarDoacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valorDoacao = valor.getText().toString();
                valorConfirmado = valorDoacao;
                String formaPagDoacao = (String) formaPag.getSelectedItem();
                metodo_pagamentoConfirmado = formaPagDoacao;
                String dataDoacaoD = valorDataD.getText().toString();
                dataConfimada = dataDoacaoD;
                if(valorDoacao.equals("")||dataDoacaoD.equals("")){
                    Snackbar mySnackbar = Snackbar.make(findViewById(R.id.LinearDinheiro),
                            "Preencha todos os Campos", Snackbar.LENGTH_LONG);
                    mySnackbar.show();
                }else{
                    doar();
                }
            }
        });

        calendarioD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(DoacaoDinheiroActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                valorDataD.setText(day + "/" + (month+1) + "/" + year);

                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        List<String> list = new ArrayList<>();
        list.add("Boleto");
        list.add("Cartão(Pagar na Instituição)");
        list.add("Dinheiro(Pagar na Instituição)");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, list);
        Spinner spinner = findViewById(R.id.spinnerForma);
        spinner.setAdapter(adapter);

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

    //----------------------------------------------------------------------//
    //-------------------- REALIZA A DOAÇÃO EM DINHEIRO --------------------//
    //----------------------------------------------------------------------//

    public void doar(){
        try{
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("quantidade", valorConfirmado);
            jsonObj.put("data", dataConfimada);
            jsonObj.put("pessoa_id", idPessoa);
            jsonObj.put("metodo_pagamento", metodo_pagamentoConfirmado);
            String json = jsonObj.toString();
            final String jsonEnviar = json;
            String url = mA.ipPc() + "doarDinheiro";

            RequestQueue requestQueue = Volley.newRequestQueue(this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(DoacaoDinheiroActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
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
        Snackbar mySnackbar = Snackbar.make(findViewById(R.id.LinearDinheiro),
                "Doação realizada com sucesso!", Snackbar.LENGTH_LONG);
        mySnackbar.show();

        final Intent ComprovanteD = new Intent(DoacaoDinheiroActivity.this, ComprovanteDActivity.class);
        ComprovanteD.putExtra("valor", valorConfirmado);
        ComprovanteD.putExtra("formaPagamento", metodo_pagamentoConfirmado);
        ComprovanteD.putExtra("datad", dataConfimada);
        Snackbar mySnackbar2 = Snackbar.make(findViewById(R.id.LinearDinheiro),
                "Gerando Comprovante", Snackbar.LENGTH_LONG);
        mySnackbar2.show();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(ComprovanteD);
                finish();
            }
        }, 500);
    }
}
