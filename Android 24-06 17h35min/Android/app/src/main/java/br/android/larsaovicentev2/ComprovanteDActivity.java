package br.android.larsaovicentev2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.material.snackbar.Snackbar;
import br.android.larsaovicentev2.activitys.AbasActivity;

public class ComprovanteDActivity extends AppCompatActivity {

    TextView valor;
    TextView forma;
    TextView datad;
    TextView link;
    View boletoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comprovante_d);

        Button retornar = findViewById(R.id.btnComprovanteDoacao);
        valor = findViewById(R.id.valorDoacao);
        forma = findViewById(R.id.formaPagament);
        datad = findViewById(R.id.valorDataD);
        link = findViewById(R.id.txtLink);
        boletoView = findViewById(R.id.viewBoleto);

        String valorDoacao = getIntent().getExtras().getString("valor");
        String formaDoacao = getIntent().getExtras().getString("formaPagamento");
        String dataDValor = getIntent().getExtras().getString("datad");
        valor.setText(valorDoacao);
        forma.setText(formaDoacao);
        datad.setText(dataDValor);


        final AlertDialog alertDialog = new AlertDialog.Builder(ComprovanteDActivity.this)
                .setTitle("Compartilhe sua Doação")
                .setMessage("Deseja compartilhar sua Doação?")
                .setPositiveButton("Sim,compartilhar",null)
                .setNegativeButton("Não Obrigado",null)
                .show();

        Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharing = new Intent(Intent.ACTION_SEND);
                sharing.setType("text/plain");
                String sharedBody = "Acabei de doar no novo App de Doações da instituição São Vicente de Paula e foi muito legal, venha participar também e ajudar os nossos velhinhos!!" +
                        " \nSite: https://www.larsvp.org.br";
                String sharedSubject = "Venha Ajudar também!!";

                sharing.putExtra(Intent.EXTRA_TEXT, sharedBody);
                sharing.putExtra(Intent.EXTRA_SUBJECT, sharedSubject);

                startActivity(Intent.createChooser(sharing, "Compartilhar"));
                alertDialog.cancel();

            }
        });

        retornar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  Snackbar mySnackbar = Snackbar.make(findViewById(R.id.ComprovanteDinheiro),
                          "Obrigado por Doar", Snackbar.LENGTH_LONG);
                  mySnackbar.show();
                  final Handler handler = new Handler();
                  handler.postDelayed(new Runnable() {
                      @Override
                      public void run() {
                          finish();
                      }
                  }, 500);
            }
        });
        if(formaDoacao.equals("Boleto")){
            boletoView.setVisibility(View.VISIBLE);
            link.setVisibility(View.VISIBLE);
        }
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.larsvp.org.br/carne-boleto")));
            }
        });
    }
}
