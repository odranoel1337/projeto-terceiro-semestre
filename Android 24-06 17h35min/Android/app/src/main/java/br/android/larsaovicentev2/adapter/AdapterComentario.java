package br.android.larsaovicentev2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.android.larsaovicentev2.R;
import br.android.larsaovicentev2.classe.Comentario;

public class AdapterComentario extends RecyclerView.Adapter<AdapterComentario.MyViewHolder> {


    List<Comentario> listaComentario;

    public AdapterComentario(List<Comentario> listaComentario) {
        this.listaComentario = listaComentario;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.comentarios_cardview_layout,parent,false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Comentario comentario = listaComentario.get(position);
        holder.nome_pessoa.setText(comentario.getNome_pessoa());
        holder.comentario.setText(comentario.getComentario());
        holder.data_comentario.setText((comentario.getData_comentario()));
        holder.horario_comentario.setText(comentario.getHorario_comentario());
    }

    @Override
    public int getItemCount() {
        return listaComentario.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nome_pessoa,comentario,data_comentario,horario_comentario;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nome_pessoa = itemView.findViewById(R.id.textViewNomeUsuarioComentario);
            comentario = itemView.findViewById(R.id.textViewComentarioPessoaComentario);
            data_comentario = itemView.findViewById(R.id.textViewDataComentario);
            horario_comentario = itemView.findViewById(R.id.textViewHorarioComentario);
        }
    }
}
