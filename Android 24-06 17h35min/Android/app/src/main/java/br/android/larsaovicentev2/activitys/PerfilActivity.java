package br.android.larsaovicentev2.activitys;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
import android.widget.TextView;
import android.widget.Toast;

import br.android.larsaovicentev2.MinhasDoacoesDinheiroActivity;
import br.android.larsaovicentev2.MinhasDoacoesProdutoActivity;
import br.android.larsaovicentev2.R;

public class PerfilActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView nomeTX,sobrenomeTX,emailTX,cpf_cnpjTX;
    String id,nome,sobrenome,email,cpf_cnpj;
    MainActivity mA= new MainActivity();
    Button botaoMinhasDoacoesProduto,botaoMinhasDoacoesDinheiro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        toolbar =  findViewById(R.id.toolbarMinhasDoacoes);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Perfil");

        nomeTX = findViewById(R.id.textViewNomeMinhasDoacoesMD);
        sobrenomeTX = findViewById(R.id.textViewSobrenomeMinhasDoacoesMD);
        emailTX = findViewById(R.id.textViewEmailMinhasDoacoesMD);
        cpf_cnpjTX = findViewById(R.id.textViewCPFCNPJMinhasDoacoesMD);

        pegarDadosUsuario();

        botaoMinhasDoacoesProduto = findViewById(R.id.buttonMinhasDoacoesProdutos);
        botaoMinhasDoacoesDinheiro = findViewById(R.id.buttonMinhasDoacoesDinheiro);

        botaoMinhasDoacoesProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pegarIdUsuarioProduto();
            }
        });

        botaoMinhasDoacoesDinheiro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pegarIdUsuarioDinheiro();
            }
        });

    }

    public void pegarDadosUsuario(){

        Bundle dados = getIntent().getExtras();
        if(getIntent().getStringExtra("id")!=null && getIntent().getStringExtra("nome")!=null && getIntent().getStringExtra("sobrenome_nome_fantasia")!=null && getIntent().getStringExtra("email")!=null &&
                getIntent().getStringExtra("cpf_cnpj")!=null ) {
            id = dados.getString("id");
            nome = dados.getString("nome");
            sobrenome = dados.getString("sobrenome_nome_fantasia");
            email = dados.getString("email");
            cpf_cnpj = dados.getString("cpf_cnpj");

            SharedPreferences sharedPreferences = getSharedPreferences("dadosMinhasDoacoes", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor2 = sharedPreferences.edit();

            editor2.putString("id", id);
            editor2.putString("nome", nome);
            editor2.putString("sobrenome_nome_fantasia", sobrenome);
            editor2.putString("email", email);
            editor2.putString("cpf_cnpj", cpf_cnpj);
            editor2.apply();

            SharedPreferences sharedPreferences1 = getSharedPreferences("dadosMinhasDoacoes",Context.MODE_PRIVATE);

            id = sharedPreferences1.getString("id","");
            nome = sharedPreferences1.getString("nome","");
            sobrenome = sharedPreferences1.getString("sobrenome_nome_fantasia","");
            email = sharedPreferences1.getString("email","");
            cpf_cnpj = sharedPreferences1.getString("cpf_cnpj","");

            nomeTX.setText(nome);
            sobrenomeTX.setText(sobrenome);
            emailTX.setText(email);
            cpf_cnpjTX.setText(cpf_cnpj);

            }
    }

    public void pegarIdUsuarioProduto(){

        final Intent intent = new Intent(this, MinhasDoacoesProdutoActivity.class);
        Bundle dados = getIntent().getExtras();
        if(getIntent().getStringExtra("id")!=null) {
            id = dados.getString("id");
            SharedPreferences sharedPreferences = getSharedPreferences("dadosMinhasDoacoesProduto", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor2 = sharedPreferences.edit();
            editor2.putString("id", id);
            editor2.apply();
            SharedPreferences sharedPreferences1 = getPreferences(Context.MODE_PRIVATE);
            id = sharedPreferences1.getString("id","Valor nao Encontrado");
            intent.putExtra("id",id);
        }
        startActivity(intent);
    }

    public void pegarIdUsuarioDinheiro(){
        final Intent intent2 = new Intent(this, MinhasDoacoesDinheiroActivity.class);
        Bundle dados = getIntent().getExtras();
        if(getIntent().getStringExtra("id")!=null) {
            id = dados.getString("id");
            SharedPreferences sharedPreferences = getSharedPreferences("dadosMinhasDoacoes", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor2 = sharedPreferences.edit();
            editor2.putString("id", id);
            editor2.apply();
            SharedPreferences sharedPreferences1 = getPreferences(Context.MODE_PRIVATE);
            id = sharedPreferences1.getString("id","");
            intent2.putExtra("id",id);
        }
        startActivity(intent2);
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

