package com.example.gerativi.Activity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.gerativi.R;
import com.example.gerativi.adapter.TarefaAdapter;
import com.example.gerativi.helper.DbHelper;
import com.example.gerativi.helper.RecyclerItemClickListener;
import com.example.gerativi.helper.TarefaDAO;
import com.example.gerativi.model.Tarefa;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView recycleView;
    private TarefaAdapter tarefaAdapter;
    private List<Tarefa> listaTarefas = new ArrayList<>();
    Tarefa tarefaSelecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AtividadeActivity.class);
                startActivity(intent);
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        recycleView = findViewById(R.id.recyclerListaTarefas);

        // adicionar o evento de click no reciclerView
        recycleView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(), recycleView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        /*Passa a tarefa selecionada por parametro para a pagina adicinar tarefa, onde é possivel recupera a tarefa selecionada e alterar os dados;
                        * */
                        tarefaSelecionada = listaTarefas.get(position);
                        Intent intent =  new Intent( MainActivity.this, AtividadeActivity.class);
                        intent.putExtra("tarefaSelecionada", tarefaSelecionada);
                        startActivity(intent);


                        Log.i("clique", "click rapido: "+tarefaSelecionada.getId()+" "+tarefaSelecionada.getNomeTarefa());
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                       tarefaSelecionada = listaTarefas.get(position);

                        Log.i("clique", "click longo");

                        // recuperar tarefa para ser exclida;



                        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                        //Perguntar antes de excluir
                        dialog.setTitle("Confirmar exclusão");
                        dialog.setMessage("Deseja excluir a Tarefa: \""+tarefaSelecionada.getNomeTarefa()+"\" ?");
                        dialog.setPositiveButton("Talvez", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(getApplicationContext(), "Voce clicou em Talvez", Toast.LENGTH_LONG).show();
                            }
                        });
                        dialog.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Nao faz nada
                            }
                        });
                        dialog.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());

                                if (tarefaDAO.deletar(tarefaSelecionada)) {
                                    Toast.makeText(getApplicationContext(), "Excluido com sucesso", Toast.LENGTH_LONG).show();
                                    carregarListaTarefas();
                                } else {
                                    Toast.makeText(getApplicationContext(), "ERRO ao Excluir", Toast.LENGTH_LONG).show();
                                }

                            }
                        });


                        dialog.create();
                        dialog.show();


                    }

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    }
                }
                )
        );




    }

    /*
    * Metodo Implementado para que toda vez que a Activit recarregar o seu ciclo de vida seja atualizada a lista
    * */

    @Override
    protected void onStart() {

        carregarListaTarefas();
        super.onStart();
    }

    /*ReciclerView para listar as tarefas, pois elas devem ser listadas de forma personalizada, onde é criado um Adapter e é passado por parametro para ele uma lista de tarefas, que sera modelado
        para ser mostrado na tela inicial.
        */
    public void carregarListaTarefas(){
         listaTarefas.clear();

        TarefaDAO  tdao = new TarefaDAO(getApplicationContext());
        listaTarefas = tdao.listarTarefas();//busca todas as tarefas cadastradas no banco;

        tarefaAdapter =  new TarefaAdapter( listaTarefas );
        RecyclerView.LayoutManager layoutManeger = new LinearLayoutManager(getApplicationContext());
        recycleView.setLayoutManager(layoutManeger);
        recycleView.setHasFixedSize(true);
        recycleView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        recycleView.setAdapter(tarefaAdapter);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(getApplicationContext(),"Selecionado",Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_categori) {

        } else if (id == R.id.nav_atividade) {
            Intent intent = new Intent(getApplicationContext(), AtividadeActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_sobre) {
            Intent intent = new Intent(getApplicationContext(), SobreActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
