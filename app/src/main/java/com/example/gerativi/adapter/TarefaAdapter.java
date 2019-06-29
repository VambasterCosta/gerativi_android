package com.example.gerativi.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gerativi.R;
import com.example.gerativi.model.Tarefa;

import java.util.List;

public class TarefaAdapter extends RecyclerView.Adapter<TarefaAdapter.MyviewHolder> {

    private List<Tarefa> listaTarefas;

    public TarefaAdapter(List<Tarefa> lista) {
        listaTarefas = lista;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_tarefa_adapter,parent, false);
        return new MyviewHolder(itemLista );
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {

        Tarefa tarefa = listaTarefas.get(position);
        holder.tarefa.setText(tarefa.getNomeTarefa());
        holder.prioridade.setText(tarefa.getPrioridade());

    }

    @Override
    public int getItemCount() {
        return this.listaTarefas.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder{
        TextView tarefa;
        TextView prioridade;
    public MyviewHolder(@NonNull View itemView) {
        super(itemView);
        tarefa = itemView.findViewById(R.id.textTarefa);
        prioridade = itemView.findViewById(R.id.textPrioridade);
    }
}


}