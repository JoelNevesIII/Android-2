package br.univates.appunivates;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import br.univates.appunivates.controller.LinguagemController;
import br.univates.appunivates.model.Linguagem;
import br.univates.appunivates.model.Nota;
import br.univates.appunivates.tools.Globais;

public class LinguagensActivity extends AppCompatActivity {

    EditText txtNome;
    EditText txtDescricao;
    Linguagem objeto;
    Nota notasObjeto;
    LinguagemController controller;
    Context context;
    Spinner notasSpinner;
    ArrayList<Nota> notas;
    int id_linguagem;
    int id_notas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linguagens);

        txtNome = findViewById(R.id.txtNome_linguagem);
        txtDescricao = findViewById(R.id.txtDescricao_linguagem);
        context = LinguagensActivity.this;
        notasSpinner = findViewById(R.id.spiner_nota);

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
            }

        }else{
            id_linguagem = 0;
        }
        //String[] notas = getResources().getStringArray(R.array.notas);

        notas = new ArrayList<>();
        notas.add(new Nota(0, "Selecione..."));
        notas.add(new Nota(1, "Nota 1"));
        notas.add(new Nota(2, "Nota 2"));
        notas.add(new Nota(3, "Nota 3"));
        notas.add(new Nota(4, "Nota 4"));

        ArrayAdapter<Nota> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, notas);
        notasSpinner.setAdapter(adapter);
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
            int idNota = notasSpinner.getId();
            if(!nome.equals("") && !descricao.equals("")) {

                if(nome.length() > 30){
                    Globais.exibirMensagem(context,
                            "O nome é muito grande, credo.");
                    return;
                }

                objeto = new Linguagem();
                objeto.setNome(nome);
                objeto.setDescricao(descricao);
                id_notas = notasSpinner.getSelectedItemPosition();
                controller = new LinguagemController(context);

                boolean retorno = false;
                if(id_linguagem == 0){
                    retorno = controller.incluir(objeto, id_notas);
                }else{
                    objeto.setId(id_linguagem);

                    retorno = controller.alterar(objeto, id_notas);
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