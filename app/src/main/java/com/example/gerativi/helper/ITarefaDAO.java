package com.example.gerativi.helper;

import com.example.gerativi.model.Tarefa;

import java.util.List;

public interface ITarefaDAO {

    public boolean salvar(Tarefa terefa);
    public boolean atualizar(Tarefa tarefa);
    public boolean deletar(Tarefa tarefa);
    public List<Tarefa> listarTarefas();
}
