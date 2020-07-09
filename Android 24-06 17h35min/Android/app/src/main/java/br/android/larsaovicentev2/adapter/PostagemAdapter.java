package br.android.larsaovicentev2.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


import br.android.larsaovicentev2.R;
import br.android.larsaovicentev2.classe.Postagem;

public class PostagemAdapter extends RecyclerView.Adapter<PostagemAdapter.MyViewHolder> {

    private List<Postagem> postagens;
    public PostagemAdapter(List<Postagem> listaPostagem) {
        this.postagens = listaPostagem;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemlista = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.campanha_cardview_layout,parent,false);
        return new MyViewHolder(itemlista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Postagem postagem = postagens.get(position);
        holder.titulo.setText(postagem.getTitulo());
        holder.descricao.setText(postagem.getDescricao());
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return postagens.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView titulo;
        private TextView descricao;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.textViewTitulo);
            descricao = itemView.findViewById(R.id.textViewDescricao);
            notifyDataSetChanged();
        }
    }
}
