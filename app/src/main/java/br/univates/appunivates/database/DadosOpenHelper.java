package br.univates.appunivates.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.univates.appunivates.tools.Globais;

public class DadosOpenHelper extends SQLiteOpenHelper {

    private static final int VERSION = 3; //versão do banco de dados
    private static final String NM_BANCO = "banco";
    private Context context;

    public DadosOpenHelper(Context context) {
        super(context, NM_BANCO, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            StringBuilder sql = new StringBuilder();
            sql.append(" CREATE TABLE IF NOT EXISTS ");
            sql.append(Tabelas.TB_LINGUAGENS);
            sql.append(" ( ");
            sql.append(" id INTEGER PRIMARY KEY AUTOINCREMENT, ");
            sql.append(" nome VARCHAR(30) NOT NULL, ");
            sql.append(" descricao TEXT, ");
            sql.append(" nota INTEGER ");
            sql.append(" ) ");
            db.execSQL(sql.toString());

            sql = new StringBuilder();
            sql.append(" CREATE TABLE IF NOT EXISTS ");
            sql.append(Tabelas.TB_PESSOAS);
            sql.append(" ( ");
            sql.append(" id INTEGER PRIMARY KEY AUTOINCREMENT, ");
            sql.append(" nome VARCHAR(30) NOT NULL, ");
            sql.append(" data DATE ");
            sql.append(" ) ");

            db.execSQL(sql.toString());

        }catch (Exception ex){
            Globais.exibirMensagem(context, ex.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try{
        if(VERSION >= 2){
            StringBuilder sql = new StringBuilder();
            sql.append(" CREATE TABLE IF NOT EXISTS ");
            sql.append(Tabelas.TB_PESSOAS);
            sql.append(" ( ");
            sql.append(" id INTEGER PRIMARY KEY AUTOINCREMENT, ");
            sql.append(" nome VARCHAR(30) NOT NULL ");
            sql.append(" ) ");
            db.execSQL(sql.toString());
        }

        if (VERSION >= 3){
            StringBuilder sql = new StringBuilder();
            sql.append(" ALTER TABLE ");
            sql.append(Tabelas.TB_PESSOAS);
            sql.append(" ADD telefone VARCHAR(15) ");
            sql.append(" ) ");
            db.execSQL(sql.toString());
        }


        }catch (Exception ex){

        }
    }
}
