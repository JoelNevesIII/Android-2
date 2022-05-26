package br.univates.appunivates;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import com.santalu.maskara.widget.MaskEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
    ListView ltvPessoa;
    MaskEditText txttelefone;
    EditText txtdata;
    PessoaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pessoa);
        context = PessoaActivity.this;
        ltvPessoa = findViewById(R.id.ltvPessoa);
        txttelefone = findViewById(R.id.txtTelefone);
        txtdata = findViewById(R.id.txtdtaNasc);
        adapter = new PessoaAdapter(context, listagem);


        txtdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar calendario = Calendar.getInstance();

                Date data;

                try{
                    if(txtdata.getText().toString().equals("")){
                        calendario = Calendar.getInstance();
                    }else{
                        String dtStart = txtdata.getText().toString();
                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                        try {
                            data = format.parse(dtStart);
                            calendario.setTime(data);

                        } catch (ParseException e) {
                            e.printStackTrace();
                            calendario = Calendar.getInstance();
                        }
                    }

                    int ano = calendario.get(Calendar.YEAR);
                    int mes = calendario.get(Calendar.MONTH);
                    int dia = calendario.get(Calendar.DAY_OF_MONTH);

                    new DatePickerDialog(context, android.R.style.Widget_DatePicker, mDateSetListener, ano, mes, dia).show();

                }catch (Exception ex){
                    calendario = Calendar.getInstance();
                }


            }
        });


    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String data =(String.format("%02d", dayOfMonth)) + "/"+ (String.format("%02d", monthOfYear + 1)) + "/" + (String.format("%02d", year));

            txtdata.setText(data);
        }
    };


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
        txttelefone  = findViewById(R.id.txtTelefone);
        Context context = this;
        Pessoa objeto;
        String data = txtdata.getText().toString();

        String data_formatada = Globais.converterData(data, "dd/MM/yyyy", "yyyy-MM-dd");

        try{


            String nome = txtNome.getText().toString().trim();
            if(!nome.equals("")) {
                if(nome.length() > 30){
                    Globais.exibirMensagem(context,
                            "Seu nome é muito grande!");
                    return;
                }

                objeto = new Pessoa();
                objeto.setNome(nome);
                objeto.setTelefone(txttelefone.getMasked());
                objeto.setData(data_formatada);
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