package com.example.gerativi.model;

import java.io.Serializable;

public class Tarefa implements Serializable {

    private  Long id;
    private String nomeTarefa;
    private String prioridade;

    public Tarefa() {
    }

    public Tarefa(Long id, String nomeTarefa) {
        this.id = id;
        this.nomeTarefa = nomeTarefa;
    }

    public Tarefa(Long id, String nomeTarefa, String prioridade) {
        this.id = id;
        this.nomeTarefa = nomeTarefa;
        this.prioridade = prioridade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeTarefa() {
        return nomeTarefa;
    }

    public void setNomeTarefa(String nomeTarefa) {
        this.nomeTarefa = nomeTarefa;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }
}
