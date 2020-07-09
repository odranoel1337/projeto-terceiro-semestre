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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;
import org.json.JSONTokener;

import br.android.larsaovicentev2.activitys.AbasActivity;
import br.android.larsaovicentev2.activitys.MainActivity;

public class RemoverCampanhaActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText nomeCampanhaRemoverET;
    Button removerCampanha;
    MainActivity mA = new MainActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remover_campanha);

        nomeCampanhaRemoverET = findViewById(R.id.editTextNomeCampanhaRemover);
        toolbar =  findViewById(R.id.toolbarRemoverCampanha);
        removerCampanha = findViewById(R.id.buttonRemoverCampanha);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Remover Campanha");

        removerCampanha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removerCampanha();
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
    //--------- REMOVE UMA CAMPANHA PELO NOME QUE O USUÁRIO DIGITAR --------//
    //----------------------------------------------------------------------//

    public void removerCampanha(){
        final String nomeCampanha = nomeCampanhaRemoverET.getText().toString();
        if(nomeCampanha.equals("")){
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.activityRemoverCampanha),
                    "Preencha os campos vazio.", Snackbar.LENGTH_LONG);
            mySnackbar.show();
        } else{
            try{
                RequestQueue queue = Volley.newRequestQueue(this);
                String url = mA.ipPc() + "campanhaDelete/" + nomeCampanha;

                StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONTokener jsonTokener = new JSONTokener(response);
                        try{
                            JSONObject jobj = (JSONObject) jsonTokener.nextValue();
                            if(!jobj.getString("status").equals("Ok")){
                                Snackbar mySnackbar = Snackbar.make(findViewById(R.id.activityRemoverCampanha),
                                        "Nome de campanha não existe!", Snackbar.LENGTH_LONG);
                                mySnackbar.show();
                                nomeCampanhaRemoverET.setText("");
                            }else{
                                Snackbar mySnackbar = Snackbar.make(findViewById(R.id.activityRemoverCampanha),
                                        "Campanha " + nomeCampanha + " removida com sucesso!", Snackbar.LENGTH_LONG);
                                mySnackbar.show();
                                final Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        finish();
                                    }
                                }, 500);
                            }
                        }catch (Exception ex){
                            ex.printStackTrace();
                        }
                    }}, null);
                queue.add(stringRequest);
                }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }
}
