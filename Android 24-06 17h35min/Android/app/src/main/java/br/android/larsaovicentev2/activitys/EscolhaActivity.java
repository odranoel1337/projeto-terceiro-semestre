package br.android.larsaovicentev2.activitys;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import br.android.larsaovicentev2.R;

public class EscolhaActivity extends AppCompatActivity {

    ImageView pessoaFisica;
    ImageView pessoaJuridica;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolha);

        pessoaFisica =  findViewById(R.id.imageViewPessoaFisica);
        pessoaJuridica = findViewById(R.id.imageViewPessoaJuridica);
        toolbar =  findViewById(R.id.toolbarPadrao);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Cadastro Escolha");

        pessoaJuridica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { startActivity(new Intent(EscolhaActivity.this,CadastroPessoaJuridicaActivity.class));
            }
        });

        pessoaFisica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { startActivity(new Intent(EscolhaActivity.this, CadastroPessoaFisicaActivity.class));
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, MainActivity.class));
                finishAffinity();
                break;
            default:break;
        }
        return true;
    }
}
