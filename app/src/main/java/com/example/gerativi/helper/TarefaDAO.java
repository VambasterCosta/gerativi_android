package com.example.gerativi.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.gerativi.helper.ITarefaDAO;
import com.example.gerativi.model.Tarefa;

import java.util.ArrayList;
import java.util.List;

public class TarefaDAO implements ITarefaDAO {

    DbHelper db;

    private SQLiteDatabase escrever;
    private SQLiteDatabase ler;

    public TarefaDAO(Context context) {
          this.db = new DbHelper(context);
          escrever = db.getWritableDatabase();
          ler = db.getReadableDatabase();
    }


    @Override
    public boolean salvar(Tarefa tarefa) {

        ContentValues cv =  new ContentValues();
        cv.put("nomeTarefa", tarefa.getNomeTarefa());
        cv.put("prioridade", tarefa.getPrioridade());
       try{
           escrever.insert(DbHelper.TAREFAS_TABELA, null, cv);
           Log.i("INFO", "Tarefa Salva com sucesso");
       }catch (Exception e){
           Log.i("INFO", "Erro ao salvar Tarefa "+tarefa.getNomeTarefa()+" - "+e.getMessage());
           return false;
       }

        return true;
    }

    @Override
    public boolean atualizar(Tarefa tarefa) {
        ContentValues cv =  new ContentValues();
        cv.put("nomeTarefa", tarefa.getNomeTarefa());
        cv.put("prioridade", tarefa.getPrioridade());
        String[] args = {tarefa.getId().toString()};
        try{
            escrever.update(DbHelper.TAREFAS_TABELA, cv,"id=? ;",args);
            Log.i("INFO", "Tarefa ATUALIZADA com sucesso");
        }catch (Exception e){
            Log.i("INFO", "Erro ao Atualizar Tarefa "+e.getMessage());
        }

        return true;
    }

    @Override
    public boolean deletar(Tarefa tarefa) {
        ContentValues cv =  new ContentValues();
        cv.put("nomeTarefa", tarefa.getNomeTarefa());
        String[] args = {tarefa.getId().toString()};
        try{
            escrever.delete(DbHelper.TAREFAS_TABELA,"id=? ;", args);
            Log.i("INFO", "Tarefa DELETADA com sucesso");
        }catch (Exception e){
            Log.i("INFO", "Erro ao DELETAR Tarefa "+e.getMessage());
        }

        return true;
    }

    @Override
    public List<Tarefa> listarTarefas() {
        List<Tarefa> listaTarefas = new ArrayList<>();

        String sql =  "SELECT * FROM "+ DbHelper.TAREFAS_TABELA+" ; ";

        Cursor c = ler.rawQuery(sql, null);


        while (c.moveToNext()){
        Tarefa t = new Tarefa(c.getLong(c.getColumnIndex("id")), c.getString(c.getColumnIndex("nomeTarefa")), c.getString(c.getColumnIndex("prioridade")));
        listaTarefas.add(t);
        }

        return listaTarefas;
    }
}
