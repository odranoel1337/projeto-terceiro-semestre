package br.android.larsaovicentev2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.android.larsaovicentev2.R;
import br.android.larsaovicentev2.classe.MinhasDoacoes;

public class AdapterMinhasDoacoesProduto extends RecyclerView.Adapter<AdapterMinhasDoacoesProduto.MyViewHolder> {


    List<MinhasDoacoes> listaMinhasDoacoes;

    public AdapterMinhasDoacoesProduto(List<MinhasDoacoes> listaMinhasDoacoes) {
        this.listaMinhasDoacoes = listaMinhasDoacoes;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  itemLista =  LayoutInflater.from(parent.getContext()).inflate(R.layout.minhasdoacoesproduto_cardview_layout,parent,false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final MinhasDoacoes minhasDoacoes = listaMinhasDoacoes.get(position);
        holder.titulo_campanha.setText(minhasDoacoes.getTitulo_campanha());
        holder.data_doacao.setText((minhasDoacoes.getData_doacao()));
        holder.tipo_produto.setText(minhasDoacoes.getNome_produto());
        holder.unidade_produto.setText(minhasDoacoes.getUnidade_produto());
        holder.quantidade_doada.setText(minhasDoacoes.getQuantidade_doada());
    }

    @Override
    public int getItemCount() {
        return listaMinhasDoacoes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView titulo_campanha,quantidade_doada,data_doacao,tipo_produto,unidade_produto;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo_campanha = itemView.findViewById(R.id.textViewTituloCampanhaMinhasDoacoesValor);
            data_doacao = itemView.findViewById(R.id.textViewDataDoacaoMinhasDoacoesValor);
            tipo_produto= itemView.findViewById(R.id.textViewTipoProdutoMinhasDoacoesValor);
            unidade_produto= itemView.findViewById(R.id.textViewUnidadeProdutoMinhasDoacoesValor);
            quantidade_doada = itemView.findViewById(R.id.textViewQuantidadeDoadaMinhasDoacoesValor);
        }
    }
}
