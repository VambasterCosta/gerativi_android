package com.example.gerativi.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.gerativi.R;
import com.example.gerativi.helper.TarefaDAO;
import com.example.gerativi.model.Tarefa;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class AtividadeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextInputEditText textInputTarefa;
    private Tarefa tarefaAtualiza;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividade);

        textInputTarefa = findViewById(R.id.TITarefa);
        //Recuperar a tarefa caso seja uma edição;

        tarefaAtualiza =(Tarefa) getIntent().getSerializableExtra("tarefaSelecionada");

        //configurar caixa de Texto
        if(tarefaAtualiza != null){
            textInputTarefa.setText(tarefaAtualiza.getNomeTarefa());
        }

        spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),R.array.planets_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);



    }

    public void salvarTarefa(View  view){

        Toast.makeText(getApplicationContext(), spinner.getSelectedItem().toString() , Toast.LENGTH_LONG).show();

        TarefaDAO tdao = new TarefaDAO(getApplicationContext());

          if(tarefaAtualiza == null){//Salvar
              if(!textInputTarefa.getText().toString().isEmpty()){
                  Tarefa tarefa = new Tarefa();
                  tarefa.setNomeTarefa(textInputTarefa.getText().toString());
                  tarefa.setPrioridade(spinner.getSelectedItem().toString());

                  if (tdao.salvar(tarefa)) {
                      Toast.makeText(getApplicationContext(), "Salvo com Sucesso!", Toast.LENGTH_LONG).show();
                      textInputTarefa.setText("");
                  } else {
                      Toast.makeText(getApplicationContext(), "ERRO ao salvar a tarefa", Toast.LENGTH_LONG).show();
                  }

              }else {
                  Toast.makeText(getApplicationContext(), "Informe uma tarefa antes de Salvar!", Toast.LENGTH_LONG).show();
              }
          }else{//Atualizar
              tarefaAtualiza.setNomeTarefa(textInputTarefa.getText().toString());
              tarefaAtualiza.setPrioridade(spinner.getSelectedItem().toString());
              if(!textInputTarefa.getText().toString().isEmpty()){
                  if ( tdao.atualizar(tarefaAtualiza)) {
                      Toast.makeText(getApplicationContext(), "Atualizada com Sucesso!", Toast.LENGTH_LONG).show();
                      finish();
                  } else {
                      Toast.makeText(getApplicationContext(), "ERRO ao alterar a tarefa", Toast.LENGTH_LONG).show();
                  }
              }else {
                  Toast.makeText(getApplicationContext(), "O campo deve estar preenchido", Toast.LENGTH_LONG).show();
              }
          }

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        Toast.makeText(getApplicationContext(), adapterView.getSelectedItem().toString(), Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
