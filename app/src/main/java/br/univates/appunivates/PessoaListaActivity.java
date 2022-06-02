package br.univates.appunivates;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import br.univates.appunivates.adapter.PessoaAdapter;
import br.univates.appunivates.controller.PessoaController;
import br.univates.appunivates.model.Pessoa;
import br.univates.appunivates.tools.Globais;

public class PessoaListaActivity extends AppCompatActivity {

    Button btnNova;
    Context context;
    PessoaController controller;
    ArrayList<Pessoa> listagem;
    PessoaAdapter adapter;
    ListView ltvPessoa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pessoa_lista);

        context = this;
        btnNova = findViewById(R.id.btnNovaPessoa);

        btnNova.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PessoaActivity.class);
                startActivity(intent);
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        atualizarLista();
    }

    private void atualizarLista(){
        try {

            controller = new PessoaController(context);
            listagem = controller.lista();
            ltvPessoa = findViewById(R.id.ltvLista_Pessoa);

            adapter = new PessoaAdapter(context, listagem);
            ltvPessoa.setAdapter(adapter);

        }catch (Exception ex){
            Globais.exibirMensagem(context, ex.getMessage());
            Log.e("ERRO", ex.getMessage());
        }
    }
}
