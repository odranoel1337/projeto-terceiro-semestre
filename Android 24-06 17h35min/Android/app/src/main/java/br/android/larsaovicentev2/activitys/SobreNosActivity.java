package br.android.larsaovicentev2.activitys;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import br.android.larsaovicentev2.FacebookActivity;
import br.android.larsaovicentev2.R;
import br.android.larsaovicentev2.SiteIntActivity;

public class SobreNosActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageView site;
    ImageView facebook;
    ImageView mapa;
    TextView endereco;
    TextView telefoneTxt;
    ImageView telefoneIMG;
    TextView emailTXT;
    ImageView emailIMG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre_nos);

        toolbar =  findViewById(R.id.toolbarPadrao);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Sobre Nós");

        site = findViewById(R.id.imgSite);
        facebook = findViewById(R.id.imgFacebook);
        mapa = findViewById(R.id.imgMapa);
        endereco = findViewById(R.id.txtEndereco);
        telefoneTxt = findViewById(R.id.txtContato);
        telefoneIMG = findViewById(R.id.imgContato);
        emailTXT = findViewById(R.id.txtContatoEmail);
        emailIMG = findViewById(R.id.imgContatoEmail);

        mapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com.br/maps/place/Lar+S%C3%A3o+Vicente+de+Paulo/@-22.598279,-46.9173498,17.5z/data=!4m5!3m4!1s0x94c8e413e76d11dd:0x8fa8a3c168c9115c!8m2!3d-22.5982738!4d-46.916261")));
            }
        });

        endereco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com.br/maps/place/Lar+S%C3%A3o+Vicente+de+Paulo/@-22.598279,-46.9173498,17.5z/data=!4m5!3m4!1s0x94c8e413e76d11dd:0x8fa8a3c168c9115c!8m2!3d-22.5982738!4d-46.916261")));
            }
        });

        site.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SobreNosActivity.this, SiteIntActivity.class));
            }
        });

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SobreNosActivity.this, FacebookActivity.class));
            }
        });

        telefoneTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:(19)3896-2503"));
                startActivity(intent);
            }
        });

        telefoneIMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:(19)3896-2503"));
                startActivity(intent);
            }
        });

        emailTXT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_SEND);
                    String[] recipients={"contato@larsvp.org.br"};
                intent.putExtra(Intent.EXTRA_EMAIL, recipients);
                intent.setType("text/html");
                intent.setPackage("com.google.android.gm");
                startActivity(Intent.createChooser(intent, "Send mail"));
            }
        });

        emailIMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_SEND);
                String[] recipients={"contato@larsvp.org.br"};
                intent.putExtra(Intent.EXTRA_EMAIL, recipients);
                intent.setType("text/html");
                intent.setPackage("com.google.android.gm");
                startActivity(Intent.createChooser(intent, "Send mail"));
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
