package br.android.larsaovicentev2.adapter;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.UnsupportedEncodingException;
import java.util.List;

import br.android.larsaovicentev2.DoacaoProdutoActivity;
import br.android.larsaovicentev2.R;
import br.android.larsaovicentev2.activitys.ComentariosActivity;
import br.android.larsaovicentev2.activitys.MainActivity;
import br.android.larsaovicentev2.classe.Campanha;

public class AdapterCampanha extends RecyclerView.Adapter<AdapterCampanha.MyViewHolder> {

    List<Campanha> listaCampanha;
    MainActivity mA = new MainActivity();
    String id_campanhaBanco;

    public AdapterCampanha(List<Campanha> listaCampanha) {
        this.listaCampanha = listaCampanha;
    }

    public AdapterCampanha() {
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.campanha_cardview_layout,parent,false);
        return new MyViewHolder(itemLista,parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Campanha campanha = listaCampanha.get(position);
        holder.titulo.setText(campanha.getNome_campanha());
        holder.descricao.setText(campanha.getDescricao());
        holder.meta.setText(campanha.getMeta());
        holder.dataInicio.setText(campanha.getDataInicio());
        holder.dataFim.setText(campanha.getDataFim());
    }

    @Override
    public int getItemCount() {
        return listaCampanha.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView titulo,descricao,meta,dataInicio,dataFim;
        Button comentario,doacaoProduto;

        public MyViewHolder(@NonNull View itemView, final Context context) {
            super(itemView);
            titulo = itemView.findViewById(R.id.textViewTitulo);
            descricao = itemView.findViewById(R.id.textViewDescricao);
            meta = itemView.findViewById(R.id.textViewMetaValor);
            dataInicio = itemView.findViewById(R.id.textViewDataInicioValor);
            dataFim = itemView.findViewById(R.id.textViewDataFimValor);
            comentario = itemView.findViewById(R.id.buttonComentarioCardView);
            doacaoProduto = itemView.findViewById(R.id.buttonAddCampanha);

            comentario.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Intent campanha_selecionada = new Intent(context, ComentariosActivity.class);
                    campanha_selecionada.putExtra("titulo", titulo.getText());
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            final String nome_campanhaFinal = titulo.getText().toString();
                            try{
                                final JSONObject jsonObj = new JSONObject();
                                jsonObj.put("nome_campanha", nome_campanhaFinal);

                                final String json = jsonObj.toString();
                                final String jsonEnviar = json;
                                String url = mA.ipPc() + "buscarIdCampanhaPeloNome";

                                RequestQueue requestQueue = Volley.newRequestQueue(context);
                                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        JSONTokener jsonTokener = new JSONTokener(response);
                                        JSONObject jobj = null;
                                        try {
                                            jobj = (JSONObject) jsonTokener.nextValue();
                                            if(!jobj.has("Error")){
                                                id_campanhaBanco = jobj.getString("id");
                                                SharedPreferences sharedPreferences = context.getSharedPreferences("idCampanha", Context.MODE_PRIVATE);
                                                SharedPreferences.Editor editor2 = sharedPreferences.edit();
                                                editor2.putString("id_campanha_xd", id_campanhaBanco);
                                                editor2.apply();
                                                ((AppCompatActivity)context).startActivityForResult(campanha_selecionada,0);
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
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
                                Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, 500);
                }
            });

            doacaoProduto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Intent campanha_selecionada = new Intent(context, DoacaoProdutoActivity.class);
                    campanha_selecionada.putExtra("titulo", titulo.getText());
                    System.out.println(titulo.getText());
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            final String nome_campanhaFinal = titulo.getText().toString();
                            try{
                                final JSONObject jsonObj = new JSONObject();
                                jsonObj.put("nome_campanha", nome_campanhaFinal);

                                final String json = jsonObj.toString();
                                final String jsonEnviar = json;
                                String url = mA.ipPc() + "buscarIdCampanhaPeloNome";

                                RequestQueue requestQueue = Volley.newRequestQueue(context);
                                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        JSONTokener jsonTokener = new JSONTokener(response);
                                        JSONObject jobj = null;
                                        try {
                                            jobj = (JSONObject) jsonTokener.nextValue();
                                            if(!jobj.has("Error")){
                                                id_campanhaBanco = jobj.getString("id");
                                                SharedPreferences sharedPreferences = context.getSharedPreferences("idCampanha", Context.MODE_PRIVATE);
                                                SharedPreferences.Editor editor2 = sharedPreferences.edit();
                                                editor2.putString("id_campanha_xd", id_campanhaBanco);
                                                editor2.apply();
                                                ((AppCompatActivity)context).startActivityForResult(campanha_selecionada,0);
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
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
                                Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, 500);
                }
            });
        }
    }
}
