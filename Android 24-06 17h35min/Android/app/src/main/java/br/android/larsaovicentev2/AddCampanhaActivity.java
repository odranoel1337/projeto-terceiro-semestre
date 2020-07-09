package br.android.larsaovicentev2;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
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

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.android.larsaovicentev2.activitys.CadastroPessoaFisicaActivity;
import br.android.larsaovicentev2.activitys.MainActivity;

public class AddCampanhaActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText nomeCampanhaET;
    EditText descricaoET;
    EditText metaET;
    EditText dataInicioET;
    EditText dataFimET;

    ImageView calendarioInicio;
    ImageView calendarioFim;

    String nomeCampanhaConfirmado;
    String descricaoConfirmado;
    String metaConfirmado;
    String dataInicioConfirmado;
    String dataFimConfirmado;
    String spinnerConfirmado;
    Button buttonCadastrarCampanha;
    Spinner spinnerTipoDoacao;
    MainActivity mA = new MainActivity();
    CampanhasActivity cA = new CampanhasActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_campanha);

        nomeCampanhaET = findViewById(R.id.editTextNomeCampanha);
        descricaoET = findViewById(R.id.editTextDescricaoCampanha);
        metaET = findViewById(R.id.editTextMetaCampanha);
        dataInicioET = findViewById(R.id.editTextDataInicioCampanha);
        dataFimET = findViewById(R.id.editTextDataFimCampanha);
        spinnerTipoDoacao = findViewById(R.id.spinnerCampanha);
        buttonCadastrarCampanha = findViewById(R.id.buttonAddCampanha2);
        calendarioInicio = findViewById(R.id.btnCalendarioDataInicio);
        calendarioFim = findViewById(R.id.btnCalendarioDataFim);

        toolbar = findViewById(R.id.toolbarAddCampanha);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Adicionar Campanha");

        List<String> list = new ArrayList<>();
        list.add("Alimento");
        list.add("Roupa");
        list.add("Higiene");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, list);
        spinnerTipoDoacao.setAdapter(adapter);

        buttonCadastrarCampanha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dadosCadastroCampanha();
            }
        });

        calendarioInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddCampanhaActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                dataInicioET.setText(year + "-" + (month+1) + "-" + day);
                            }
                        }, year, month, dayOfMonth);

                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        calendarioFim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddCampanhaActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                dataFimET.setText(year + "-" + (month+1) + "-" + day);
                            }
                        }, year, month, dayOfMonth);

                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_gerenciar_campanhas, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //----------------------------------------------------------------------//
    //------------- VERIFICA OS DADOS PARA CADASTRO DE CAMPANHA ------------//
    //----------------------------------------------------------------------//

    private void dadosCadastroCampanha() {
        String nomeCampanha = nomeCampanhaET.getText().toString();
        String descricaoCampanha = descricaoET.getText().toString();
        String meta = metaET.getText().toString();
        String dataInicio = dataInicioET.getText().toString();
        String dataFim = dataFimET.getText().toString();

        if (nomeCampanha.equals("") || descricaoCampanha.equals("") || meta.equals("") || dataInicio.equals("") || dataFim.equals("")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.addActivityCampanha),
                    "Preencha os campos vazio.", Snackbar.LENGTH_LONG);
            mySnackbar.show();
        } else {
            nomeCampanhaConfirmado = nomeCampanha;
            descricaoConfirmado = descricaoCampanha;
            metaConfirmado = meta;
            dataInicioConfirmado = dataInicio;
            dataFimConfirmado = dataFim;
            int posicao = spinnerTipoDoacao.getSelectedItemPosition();
            spinnerConfirmado = spinnerTipoDoacao.getSelectedItem().toString();

            requestDadosValidaCampanha();
        }
    }

    //----------------------------------------------------------------------//
    //-------------- VERIFICA SE JÁ EXISTE O NOME DA CAMPANHA --------------//
    //----------------------------------------------------------------------//

    public void requestDadosValidaCampanha() {
        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("nome_campanha", nomeCampanhaConfirmado);
            String json = jsonObj.toString();
            final String jsonEnviar = json;
            String url = mA.ipPc() + "verificarCampanha";

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    JSONTokener jsonTokener = new JSONTokener(response);
                    try {
                        JSONObject jobj = (JSONObject) jsonTokener.nextValue();
                        if (!jobj.has("Error")) {
                            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.addActivityCampanha),
                                    "Nome de campanha já cadastrado!", Snackbar.LENGTH_LONG);
                            mySnackbar.show();
                            nomeCampanhaET.setText("");
                        } else {
                            cadastrarCampanha();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(AddCampanhaActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }) {
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
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //----------------------------------------------------------------------//
    //-------------------- REALIZA O CADASTRO DA CAMPANHA ------------------//
    //----------------------------------------------------------------------//

    public void cadastrarCampanha() {
        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("nome_campanha", nomeCampanhaConfirmado);
            jsonObj.put("descricao", descricaoConfirmado);
            jsonObj.put("meta", metaConfirmado);
            jsonObj.put("data_inicio", dataInicioConfirmado);
            jsonObj.put("data_fim", dataFimConfirmado);
            jsonObj.put("tipo_doacao", spinnerConfirmado);
            String json = jsonObj.toString();
            final String jsonEnviar = json;
            String url = mA.ipPc() + "campanhaCadastro";

            RequestQueue requestQueue = Volley.newRequestQueue(this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(AddCampanhaActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }) {
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
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        Snackbar mySnackbar = Snackbar.make(findViewById(R.id.addActivityCampanha),
                "Cadastrado com sucesso!", Snackbar.LENGTH_LONG);
        mySnackbar.show();
        cA.criarCampanha(nomeCampanhaConfirmado, descricaoConfirmado, metaConfirmado, dataInicioConfirmado, dataFimConfirmado, spinnerConfirmado);
        final Intent i = new Intent(this, GerenciarCampanhasActivity.class);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
            }
        }, 500);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuLogOutGerenciar:
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
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
