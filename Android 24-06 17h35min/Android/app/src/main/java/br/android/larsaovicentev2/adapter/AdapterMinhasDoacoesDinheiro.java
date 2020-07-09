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
import br.android.larsaovicentev2.classe.MinhasDoacoesDinheiro;

public class AdapterMinhasDoacoesDinheiro extends RecyclerView.Adapter<AdapterMinhasDoacoesDinheiro.MyViewHolder> {

    List<MinhasDoacoesDinheiro> listaMinhasDoacoesDinheiro;

    public AdapterMinhasDoacoesDinheiro(List<MinhasDoacoesDinheiro> listaMinhasDoacoesDinheiro) {
        this.listaMinhasDoacoesDinheiro = listaMinhasDoacoesDinheiro;
    }

    @NonNull
    @Override
    public AdapterMinhasDoacoesDinheiro.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista =  LayoutInflater.from(parent.getContext()).inflate(R.layout.minhasdoacoesdinheiro_cardiview_layout,parent,false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMinhasDoacoesDinheiro.MyViewHolder holder, int position) {
        final MinhasDoacoesDinheiro minhasDoacoesDinheiro = listaMinhasDoacoesDinheiro.get(position);
        holder.metodo_pagamento.setText(minhasDoacoesDinheiro.getMetodo_pagamento());
        holder.data_doacao.setText((minhasDoacoesDinheiro.getData_doacao()));
        holder.quantidade_doada.setText(minhasDoacoesDinheiro.getQuantidade_doada());
    }

    @Override
    public int getItemCount() {
        return listaMinhasDoacoesDinheiro.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView metodo_pagamento,quantidade_doada,data_doacao;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            metodo_pagamento = itemView.findViewById(R.id.textViewMetodoPagamentoMBD);
            data_doacao = itemView.findViewById(R.id.textViewDataDoacaoMBD);
            quantidade_doada = itemView.findViewById(R.id.textViewQuantidadeDoadaMBD);
        }
    }
}

