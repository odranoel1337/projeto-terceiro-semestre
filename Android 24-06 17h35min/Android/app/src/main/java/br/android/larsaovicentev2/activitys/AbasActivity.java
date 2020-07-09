package br.android.larsaovicentev2.activitys;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import br.android.larsaovicentev2.CampanhasActivity;
import br.android.larsaovicentev2.DoacaoDinheiroActivity;
import br.android.larsaovicentev2.R;

public class AbasActivity extends AppCompatActivity {

    ImageView sobrenos,doar,campanhas, perfil,ajuda;
    Toolbar toolbar;
    CampanhasActivity cA = new CampanhasActivity();
    String idRecebido,nomeRecebido,sobrenome_nome_fantasiaRecebido,emailRecebido,cpf_cnpjRecebido;
    String id,nome,sobrenome,email,cpf_cnpj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abas);

        doar =  findViewById(R.id.imageViewDoarAbas);
        campanhas =  findViewById(R.id.imageViewCampanhasAbas);
        perfil = findViewById(R.id.imageViewPerfil);
        sobrenos =  findViewById(R.id.imageViewSobreNosAbas);
        ajuda =  findViewById(R.id.imageViewAjuda);

        toolbar =  findViewById(R.id.toolbarAbas);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tela Principal");

        final Intent intent = new Intent(this, PerfilActivity.class);

        ajuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://web-chat.global.assistant.watson.cloud.ibm.com/preview.html?region=us-south&integrationID=ba4cd2ad-2b2e-4a07-8189-fb3c7b2e7f85&serviceInstanceID=c7bb1f7d-16eb-48a6-ae59-398b49c87a85")));
            }
        });

        campanhas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AbasActivity.this, CampanhasActivity.class));
            }
        });

        doar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AbasActivity.this, DoacaoDinheiroActivity.class));
            }
        });

        perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle dados = getIntent().getExtras();

                if(getIntent().getStringExtra("id")!=null && getIntent().getStringExtra("nome")!=null && getIntent().getStringExtra("sobrenome_nome_fantasia")!=null && getIntent().getStringExtra("email")!=null &&
                        getIntent().getStringExtra("cpf_cnpj")!=null){

                    idRecebido =dados.getString("id");
                    nomeRecebido = dados.getString("nome");
                    sobrenome_nome_fantasiaRecebido = dados.getString("sobrenome_nome_fantasia");
                    emailRecebido = dados.getString("email");
                    cpf_cnpjRecebido = dados.getString("cpf_cnpj");

                    SharedPreferences sharedPreferences = getSharedPreferences("dadosMinhasDoacoes", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor2 = sharedPreferences.edit();
                    editor2.putString("id", idRecebido);
                    editor2.putString("nome", nomeRecebido);
                    editor2.putString("sobrenome_nome_fantasia", sobrenome_nome_fantasiaRecebido);
                    editor2.putString("email", emailRecebido);
                    editor2.putString("cpf_cnpj", cpf_cnpjRecebido);
                    editor2.apply();

                    SharedPreferences preferences = getSharedPreferences("dadosMinhasDoacoes",MODE_PRIVATE);
                    id = preferences.getString("id","valor nao encontrado");
                    nome = preferences.getString("nome","valor nao encontrado");
                    sobrenome = preferences.getString("sobrenome_nome_fantasia","valor nao encontrado");
                    email = preferences.getString("email","valor nao encontrado");
                    cpf_cnpj = preferences.getString("cpf_cnpj","valor nao encontrado");
                    intent.putExtra("id",id);
                    intent.putExtra("nome",nome);
                    intent.putExtra("sobrenome_nome_fantasia",sobrenome);
                    intent.putExtra("email",email);
                    intent.putExtra("cpf_cnpj",cpf_cnpj);
                }
                startActivity(intent);
            }
        });

        sobrenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AbasActivity.this,SobreNosActivity.class));
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
}
