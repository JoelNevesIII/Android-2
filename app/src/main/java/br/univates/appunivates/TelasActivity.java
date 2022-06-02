package br.univates.appunivates;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TelasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telas);

        Button btnLinguagens;
        Button btnPessoas;
        Context context = this;

        btnLinguagens = findViewById(R.id.btnLinguagem);
        btnPessoas = findViewById(R.id.btnPessoas);

        btnLinguagens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ListaLinguagensActivity.class);
                startActivity(intent);
            }
        });
        btnPessoas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PessoaListaActivity.class);
                startActivity(intent);

            }
        });

    }
}