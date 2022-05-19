package br.univates.appunivates;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import br.univates.appunivates.controller.LinguagemController;
import br.univates.appunivates.model.Linguagem;
import br.univates.appunivates.model.Nota;
import br.univates.appunivates.tools.Globais;

public class LinguagensActivity extends AppCompatActivity {

    Button excluir;
    EditText txtNome;
    EditText txtDescricao;
    Linguagem objeto;
    LinguagemController controller;
    Context context;
    Spinner notasSpinner;
    int id_linguagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linguagens);
        ArrayList<Nota> notas;
        txtNome = findViewById(R.id.txtNome_linguagem);
        txtDescricao = findViewById(R.id.txtDescricao_linguagem);
        context = LinguagensActivity.this;
        notasSpinner = findViewById(R.id.spiner_nota);
        excluir = findViewById(R.id.btnExcluir);


        notas = new ArrayList<>();
        notas.add(new Nota(0, "Selecione..."));
        notas.add(new Nota(1, "Nota 1"));
        notas.add(new Nota(2, "Nota 2"));
        notas.add(new Nota(3, "Nota 3"));
        notas.add(new Nota(4, "Nota 4"));

        ArrayAdapter<Nota> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, notas);
        notasSpinner.setAdapter(adapter);


        excluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller = new LinguagemController(context);
                controller.excluir(id_linguagem);
                Globais.exibirMensagem(context, "Excluido");
                finish();
            }
        });



        //Verificar se veio algum EXTRA da tela anterior
        Bundle extras = getIntent().getExtras();



        if(extras != null){
            id_linguagem = extras.getInt("id", 0);
            //buscar através desta chave
            controller = new LinguagemController(context);
            objeto = controller.buscar(id_linguagem);
            if(objeto != null){
                txtNome.setText(objeto.getNome());
                txtDescricao.setText(objeto.getDescricao());

                for(int i = 0; i < notasSpinner.getAdapter().getCount(); i++){
                    Nota nota  = (Nota) notasSpinner.getItemAtPosition(i);
                    if(nota.getId() == objeto.getNota()){
                        notasSpinner.setSelection(i);
                        break;
                    }
                }
            }



        }else{
            id_linguagem = 0;
            if(id_linguagem == 0){
                excluir.setVisibility(View.GONE);
            }
        }
        //String[] notas = getResources().getStringArray(R.array.notas);

    }





    //Funcao para inflar o menu na tela
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cad, menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){

            case R.id.action_ok:

                //SALVAR
                salvar();

            case R.id.action_cancel:

                finish();

        }

        return super.onOptionsItemSelected(item);
    }

    private void salvar(){
        try{

            String nome = txtNome.getText().toString().trim();
            String descricao = txtDescricao.getText().toString().trim();
            Nota nota = (Nota) notasSpinner.getSelectedItem();
            if(!nome.equals("") && !descricao.equals("")) {

                if(nome.length() > 30){
                    Globais.exibirMensagem(context,
                            "O nome é muito grande, credo.");
                    return;
                }

                objeto = new Linguagem();
                objeto.setNome(nome);
                objeto.setDescricao(descricao);
                objeto.setNota(nota.getId());

                controller = new LinguagemController(context);

                boolean retorno = false;
                if(id_linguagem == 0){
                    retorno = controller.incluir(objeto);
                }else{
                    objeto.setId(id_linguagem);

                    retorno = controller.alterar(objeto);
                }

                if(retorno) {
                    Globais.exibirMensagem(context, "Sucesso");
                    finish();
                }

            }

        }catch (Exception ex){
            Globais.exibirMensagem(context, ex.getMessage());
            Log.e("ERRO", ex.getMessage());
        }
    }

}