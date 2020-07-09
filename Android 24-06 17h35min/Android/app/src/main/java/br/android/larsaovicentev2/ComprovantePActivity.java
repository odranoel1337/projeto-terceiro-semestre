package br.android.larsaovicentev2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.material.snackbar.Snackbar;
import br.android.larsaovicentev2.activitys.AbasActivity;

public class ComprovantePActivity extends AppCompatActivity {

    TextView produto;
    TextView quantidade;
    TextView datap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comprovante_p);
        Button retornar = findViewById(R.id.btnComprovanteProduto);
        produto = findViewById(R.id.valorProduto);
        quantidade = findViewById(R.id.valorQuantidade);
        datap = findViewById(R.id.valorDataP);

        String produtoValor = getIntent().getExtras().getString("produto");
        String quantidadeValor = getIntent().getExtras().getString("quantidade");
        String datapValor = getIntent().getExtras().getString("datap");
        String tipoProduto = getIntent().getExtras().getString("tipo");

        produto.setText(produtoValor);
        quantidade.setText(quantidadeValor +" "+ tipoProduto);
        datap.setText(datapValor);

        final AlertDialog alertDialog = new AlertDialog.Builder(ComprovantePActivity.this)
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
                String sharedSubject = "Venha Ajudar também!!";
                sharing.putExtra(Intent.EXTRA_TEXT, "Acabei de doar no novo App de Doações da instituição São Vicente de Paula e foi muito legal, venha participar também e ajudar os nossos velhinhos!!" +
                        " \nSite: https://www.larsvp.org.br");
                sharing.setType("text/plain");
                sharing.putExtra(Intent.EXTRA_SUBJECT, sharedSubject);
                startActivity(Intent.createChooser(sharing, "Shared Using"));
                alertDialog.cancel();

            }
        });

        retornar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar mySnackbar = Snackbar.make(findViewById(R.id.ComprovanteProduto),
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
    }
}
