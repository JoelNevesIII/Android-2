package br.univates.appunivates;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;

import com.santalu.maskara.widget.MaskEditText;

import java.util.ArrayList;

import br.univates.appunivates.adapter.LinguagemAdapter;
import br.univates.appunivates.adapter.PessoaAdapter;
import br.univates.appunivates.controller.LinguagemController;
import br.univates.appunivates.controller.PessoaController;
import br.univates.appunivates.model.Linguagem;
import br.univates.appunivates.model.Nota;
import br.univates.appunivates.model.Pessoa;
import br.univates.appunivates.tools.Globais;

public class PessoaActivity extends AppCompatActivity {
    Pessoa objeto;
    PessoaController controller;
    Context context;
    ArrayList<Pessoa> listagem;
    PessoaAdapter adapter;
    ListView ltvPessoa;
    MaskEditText telefone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pessoa);
        context = PessoaActivity.this;
        ltvPessoa = findViewById(R.id.ltvPessoa);

    }


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
        EditText txtNome = findViewById(R.id.txtnomePessoa);
        telefone  = findViewById(R.id.txtTelefone);
        Context context = this;
        Pessoa objeto;

        try{


            String nome = txtNome.getText().toString().trim();
            if(!nome.equals("")) {
                if(nome.length() > 30){
                    Globais.exibirMensagem(context,
                            "O nome é muito grande, credo.");
                    return;
                }

                objeto = new Pessoa();
                objeto.setNome(nome);
                objeto.setTelefone(telefone.getMasked());

                PessoaController controller = new PessoaController(context);
                controller.incluir(objeto);

            }

        }catch (Exception ex){
            Globais.exibirMensagem(context, ex.getMessage());
            Log.e("ERRO", ex.getMessage());
        }
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

            adapter = new PessoaAdapter(context, listagem);
            ltvPessoa.setAdapter(adapter);

        }catch (Exception ex){
            Globais.exibirMensagem(context, ex.getMessage());
            Log.e("ERRO", ex.getMessage());
        }
    }
}